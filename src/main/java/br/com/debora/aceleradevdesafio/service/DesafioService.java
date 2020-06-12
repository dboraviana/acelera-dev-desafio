package br.com.debora.aceleradevdesafio.service;

import br.com.debora.aceleradevdesafio.domain.Desafio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

@Service
public class DesafioService {

    public Desafio buscaDesafio(String token) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=" + token))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new ObjectMapper().readValue( response.body(), Desafio.class);
    }

    public Desafio descriptografar(Desafio desafio){
        char[] alfabeto = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder resultado = new StringBuilder();
        String texto = desafio.getCifrado().toLowerCase();
        int tamanhoTexto = texto.length();

        for (int c = 0; c < tamanhoTexto; c++) {
            int indexLetra = isAlfabeto(texto.charAt(c), alfabeto);
            if (indexLetra != -1) {
                resultado.append(alfabeto[getletraDescritografada(indexLetra, alfabeto.length, desafio.getNumeroCasas())]);
            } else {
                resultado.append(texto.charAt(c));
            }
        }
        desafio.setDecifrado(resultado.toString());
        return desafio;
    }

    private static int getletraDescritografada(int indexLetra, int length, int chave) {
        int resultadoAplicacaoDaChave = indexLetra - (chave);
        if (resultadoAplicacaoDaChave < 0) {
            return length + resultadoAplicacaoDaChave;
        }
        return resultadoAplicacaoDaChave;
    }

    private static int isAlfabeto(char caracter, char[] alfabeto) {
        for (int i = 0; i < alfabeto.length; i++) {
            if (alfabeto[i] == caracter) {
                return i;
            }
        }
        return -1;
    }

    public  Desafio resumir(Desafio desafio) {
        String sha1;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(desafio.getDecifrado().getBytes(StandardCharsets.UTF_8));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
            desafio.setResumoCriptografico(sha1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desafio;
    }

    public void salvarArquivo(Desafio desafio) throws JsonProcessingException, FileNotFoundException {
        Formatter formatter = new Formatter(Desafio.ARQUIVO);
        formatter.format(new ObjectMapper().writeValueAsString(desafio));
        formatter.close();
    }
}

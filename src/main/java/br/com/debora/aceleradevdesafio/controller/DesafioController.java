package br.com.debora.aceleradevdesafio.controller;

import br.com.debora.aceleradevdesafio.domain.Desafio;
import br.com.debora.aceleradevdesafio.service.DesafioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping
public class DesafioController {

    @Autowired
    DesafioService service;

    @GetMapping
    public String home (Model model){
        model.addAttribute("desafio", new Desafio());
        return "desafio";
    }

    @PostMapping
    public String enviarToken(@ModelAttribute("desafio") Desafio desafio, Model model) throws IOException, InterruptedException {
        service.buscaDesafio(desafio.getToken());
        model.addAttribute("desafio", service.buscaDesafio(desafio.getToken()));
        model.addAttribute("action", "/descriptografar");
        System.out.println(desafio);
        return "resultado_desafio";
    }

    @PostMapping("/descriptografar")
    public String descriptografar(@ModelAttribute("desafio") Desafio desafio, Model model){
        model.addAttribute("desafio", service.descriptografar(desafio));
        model.addAttribute("action", "/resumir");
        return "resultado_desafio";
    }


    @PostMapping("/resumir")
    public String resumir(@ModelAttribute("desafio") Desafio desafio, Model model){
        model.addAttribute("desafio", service.resumir(desafio));
        model.addAttribute("action", "/salvar");
        return "resultado_desafio";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("desafio") Desafio desafio, Model model) throws FileNotFoundException, JsonProcessingException {
        service.salvarArquivo(desafio);
        model.addAttribute("action", "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=" + desafio.getToken());
        return "desafio-form";
    }



}

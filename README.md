# Desafio Codenation 


### Resumo

Este repositório foi criado para disponibilizar o código utilizado para resolver o desafio Acelera DEV.
 
Foi desenvolvido com; **Java 8+ Spring boot+ Thymeleaf**

# Criptografia de Júlio César
Essa criptografia se baseia na substituição da letra do alfabeto avançado um determinado número de casas. Por exemplo, considerando o número de casas = 3:

**Normal:** a ligeira raposa marrom saltou sobre o cachorro cansado

**Cifrado:** d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr


### Proposta do desafio

Desenvolver um aplicação em qualquer linguagem que seja capaz de fazer uma requisição HTTP do seguinte json:

```
{
	"numero_casas": 10,
	"token":"token_do_usuario",
	"cifrado": "texto criptografado",
	"decifrado": "aqui vai o texto decifrado",
	"resumo_criptografico": "aqui vai o resumo"
}
```

O sistema deve ser capaz de decifrar o texto recuperado considerando as condições recuperadas pelo json. 

**Regras**

* As mensagens serão convertidas para minúsculas tanto para a criptografia quanto para descriptografia. 
* No nosso caso os números e pontos serão mantidos, ou seja: 

**Normal:** 1a.a

**Cifrado:** 1d.d


### Funcionalidades da aplicação

*  Recuperar o desafio;
*  Decifrar o texto;
*  Recuperar Resumo criptográfico;
*  Submeter desafio;

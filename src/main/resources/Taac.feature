#language: pt
#encoding: utf-8

Funcionalidade: Retornar os dados de uma operacao

  @operacaoPortabilidade
  Esquema do Cenario: Uma uri ao ser chamada deve retornar o status '200'
    Dado que estamos realizando uma chamada para o endpoint dadosProposta
    Quando quando passamos um token "<token>" na chamada do endpoint dadosProposta
    E preenchemos o header "<header>" do dadosProposta
    E realizamos a chamada do dadosProposta "<numeroOperacao>"
    Entao o resultado da chamada sera <status> no dadosProposta
    E a mensagem contera "<bodyResponse>" no dadosProposta

    Exemplos:
      | token  | header | numeroOperacao  | status | bodyResponse |
      | Valido | vazio  | 000000000000000 | 200    | OK           |




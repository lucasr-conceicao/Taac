package taac.core;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static taac.utils.Token.getToken;

public class Taac {
    private RequestSpecification request;
    private ValidatableResponse response;

    @Before("@operacaoPortabilidade")
    void setup() {
        RestAssured.baseURI = "";
        response = null;
    }

    @Dado("que estamos realizando uma chamada para o endpoint dadosProposta")
    public void queEstamosRealizandoUmaChamadaParaOEndpointDadosProposta() {
        request = null;
    }

    @Quando("quando passamos um token {string} na chamada do endpoint dadosProposta")
    public void quandoPassamosUmTokenNaChamadaDoEndpointDadosProposta(String token) {
        if (token.equalsIgnoreCase("Valido")) {
            request = given().auth().oauth2(getToken()).relaxedHTTPSValidation();
        } else if (token.equalsIgnoreCase("Vazio")) {
            request = given();
        } else if (token.equals("Invalido")) {
            request = given().auth().oauth2("Token Inválido").relaxedHTTPSValidation();
        }
    }

    @Quando("preenchemos o header {string} do dadosProposta")
    public void preenchemosOheaderDoDadosProposta(String header) {
        if (header.equals("Valido")) {
            request = request
                    .header("Content-Type", "application/json")
                    .header("x-itaugw-aí-id", Constantes.API_GATEWAY)
                    .header("x-itau-apikey", Constantes.APIKEY)
                    .header("x-itau-correlationID",Constantes.CORRELATION_ID);
        } else if (header.equals("Invalido")) {
            request = request
                    .header("Content-Type", "application/json")
                    .header("x-itaugw-aí-id", Constantes.TOKEN_INVALIDO)
                    .header("x-itau-apikey", Constantes.TOKEN_INVALIDO)
                    .header("x-itau-correlationID",Constantes.TOKEN_INVALIDO);
        } else {
            request = request.header("Content-Type", "application/json");
        }
    }

    @Quando("realizamos a chamada do dadosProposta {string}")
    public void realizamosAChamadaDoDadosProposta(String numeroOperacao) {
       response = request.given().when().get(Constantes.URL_DATA).then();
    }

    @Entao("o resultado da chamada sera {int} no dadosProposta")
    public void oResultadoDaChamadaSeraNoDadosProposta(Integer status) {
        response.statusCode(status);
    }

    @Entao("a mensagem contera {string} no dadosProposta")
    public void aMensagemConteraNoDadosProposta(String bodyResponse) {
        switch (bodyResponse) {
            case "OK":
                response.body("data.numero_proposta", equalTo("00000081557863"));
                response.body("data.valor_parcela", is(252.36F));
                response.body("data.taxa_mes", is(1.25F));
                response.body("data.valor_contratado", is(8.657F));
                response.body("data.contratos[0].numero_contrato", equalTo("00000081564741"));
                response.body("data.contratos[0].numero_parcelas", is(12));
                response.body("data.contratos[0].valor_contrato", is(587));
                break;
            case "Forbidden":
                response.body("message", containsString("Forbidden"));
                break;
        }
    }
}

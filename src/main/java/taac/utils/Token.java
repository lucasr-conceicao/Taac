package taac.utils;

import lombok.SneakyThrows;
import taac.core.Constantes;

import static io.restassured.RestAssured.given;

public class Token {

    @SneakyThrows
    public static String getToken() {
        var response = given()
                .contentType("application/x-www-form-urlencoded")
                .param("grant_type", "client_credentials")
                .param("client_id", Constantes.CLIENT_ID)
                .param("client_secret", Constantes.CLIENT_SECRET)
                .when()
                .get(Constantes.URL_TOKEN);
        return response.getBody().asString();
    }
}

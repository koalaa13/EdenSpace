package org.example.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.model.response.InfoResponse;

import java.io.IOException;

public class ApiController {
    private static final String API_URL = "https://datsedenspace.datsteam.dev/player";

    private static final String API_AUTH_HEADER = "X-Auth-Token";

    private static final String API_KEY = "660dce29d27cc660dce29d27ce";
    private final ObjectMapper objectMapper;

    public ApiController() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public InfoResponse infoRequest() {
        InfoResponse universeResponse = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL + "/universe");
            request.setHeader(API_AUTH_HEADER, API_KEY);

            universeResponse = client.execute(
                    request,
                    response -> objectMapper.readValue(response.getEntity().getContent(), InfoResponse.class)
            );
        } catch (IOException e) {
            System.err.println(e);
        }
        return universeResponse;
    }
}

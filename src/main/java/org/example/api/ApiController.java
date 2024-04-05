package org.example.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.model.PlacedFigure;
import org.example.model.response.InfoResponse;
import org.example.model.response.TravelResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiController {
    private static final String API_URL = "https://datsedenspace.datsteam.dev/player";

    private static final String API_AUTH_HEADER = "X-Auth-Token";

    private static final String API_KEY = "660dce29d27cc660dce29d27ce";
    private final ObjectMapper objectMapper;

    private static ApiController INSTANCE = null;
    public static ApiController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiController();
        }
        return INSTANCE;
    }

    private ApiController() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private <T> T responseHandling(HttpResponse response, Class<? extends T> okResponseClass) throws IOException {
        var content = response.getEntity().getContent();
        if (response.getStatusLine().getStatusCode() != 200) {
            String errorMessage = objectMapper.readTree(content).get("error").asText();
            System.err.println(errorMessage);
            return null;
        }
        return objectMapper.readValue(content, okResponseClass);
    }

    public InfoResponse infoRequest() {
        InfoResponse infoResponse = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL + "/universe");
            request.setHeader(API_AUTH_HEADER, API_KEY);

            infoResponse = client.execute(
                    request,
                    response -> responseHandling(response, InfoResponse.class)
            );
        } catch (IOException e) {
            System.err.println(e);
        }
        return infoResponse;
    }

    private static final String TRAVEL_PLANETS_BODY_KEY = "planets";

    public TravelResponse travelRequest(List<String> planetsPath) {
        TravelResponse travelResponse = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            final Map<String, List<String>> body = new HashMap<>();
            body.put(TRAVEL_PLANETS_BODY_KEY, planetsPath);
            final String json = objectMapper.writeValueAsString(body);
            final StringEntity entity = new StringEntity(json);

            HttpPost request = new HttpPost(API_URL + "/travel");
            request.setHeader(API_AUTH_HEADER, API_KEY);
            request.setEntity(entity);

            travelResponse = client.execute(
                    request,
                    response -> responseHandling(response, TravelResponse.class)
            );
        } catch (IOException e) {
            System.err.println(e);
        }
        return travelResponse;
    }

    public void collectRequest(List<PlacedFigure> placedFigures) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            final Map<String, List<List<Integer>>> takenFigures = new HashMap<>();
            for (PlacedFigure placedFigure : placedFigures) {
                takenFigures.put(placedFigure.getName(), placedFigure.getFigure().getCoordsAsNonPrimitive());
            }

            final String json  = objectMapper.writeValueAsString(Map.of("garbage", takenFigures));
            final StringEntity entity = new StringEntity(json);

            HttpPost request = new HttpPost(API_URL + "/collect");
            request.setHeader(API_AUTH_HEADER, API_KEY);
            request.setEntity(entity);

            client.execute(request);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

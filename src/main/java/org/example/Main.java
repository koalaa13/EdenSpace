package org.example;

import org.example.api.ApiController;
import org.example.model.graph.Graph;
import org.example.model.response.ShipResponse;
import org.example.service.UtilService;

public class Main {
    private static final ApiController apiController = new ApiController();
    private static final UtilService utilService = new UtilService();

    public static void main(String[] args) {
        var response = apiController.universeRequest();
        Graph graph = utilService.buildGraph(response);

        ShipResponse shipResponse = apiController.shipRequest();
    }
}
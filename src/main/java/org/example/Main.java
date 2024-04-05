package org.example;

import org.example.api.ApiController;
import org.example.model.graph.Edge;
import org.example.model.graph.Graph;
import org.example.model.response.TravelResponse;
import org.example.service.UtilService;

import java.util.List;
import java.util.Random;

public class Main {
    private static final ApiController apiController = ApiController.getInstance();
    private static final UtilService utilService = UtilService.getInstance();

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("kek");
//        var infoResponse = apiController.infoRequest();
//        Graph graph = utilService.buildGraph(infoResponse);
//        String s = infoResponse.getShip().getPlanet().getName();
//        System.out.println("STARTED FROM");
//        System.out.println(s);
//        while (true) {
//            List<String> possibleTos = graph.getEdgesFrom(s).stream().map(Edge::getTo).toList();
//            String to = possibleTos.get(random.nextInt(possibleTos.size()));
//            TravelResponse travelResponse = apiController.travelRequest(List.of(to));
//            if (travelResponse != null) {
//                System.out.printf("SUCCESSFULLY MOVED FROM %s TO %s%n%n", s, to);
//                System.out.printf("LIST OF GARBAGE ON PLANET %s%n", to);
//                for (var gi : travelResponse.getPlanetGarbage().entrySet()) {
//                    System.out.println(gi.getKey());
//                    int[][] coords = gi.getValue().getCoords();
//                    for (int[] p : coords) {
//                        System.out.println(p[0] + " " + p[1]);
//                    }
//                }
//                System.out.println("------------------------------------------");
//                s = to;
//            }
//            Thread.sleep(1000);
//        }
    }
}
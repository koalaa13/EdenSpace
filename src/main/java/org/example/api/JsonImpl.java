package org.example.api;

import org.example.model.Figure;
import org.example.model.PlacedFigure;
import org.example.model.PlanetInfo;
import org.example.model.graph.Graph;
import org.example.model.response.InfoResponse;
import org.example.model.response.TravelResponse;
import org.example.model.tetris.IShipBaggage;
import org.example.model.tetris.TShipBaggage;
import org.example.service.UtilService;

import java.util.List;
import java.util.Map;

public class JsonImpl implements IJson {
    private final UtilService utilService;
    private final ApiController apiController;

    public JsonImpl() {
        apiController = ApiController.getInstance();
        utilService = UtilService.getInstance();
    }

    private List<PlacedFigure> getPlacedFiguresFromResponse(Map<String, Figure> figureMap) {
        return figureMap.entrySet()
                .stream()
                .map(e ->
                        new PlacedFigure(e.getValue(), e.getKey())
                )
                .toList();
    }

    @Override
    public GameInfo getGameInfo() {
        InfoResponse infoResponse = apiController.infoRequest();
        Graph graph = utilService.buildGraph(infoResponse);

        IShipBaggage shipBaggage = new TShipBaggage(
                infoResponse.getShip().getCapacityX(),
                infoResponse.getShip().getCapacityY()
        );
        List<PlacedFigure> placedFigures = getPlacedFiguresFromResponse(infoResponse.getShip().getGarbage());
        shipBaggage.setLoad(placedFigures);

        return new GameInfo(graph, shipBaggage, infoResponse.getShip().getPlanet().getName());
    }

    @Override
    public MoveInfo move(List<String> trajectory) {
        TravelResponse travelResponse = apiController.travelRequest(trajectory);
        // Имя планеты -- это место назначения
        String name = trajectory.get(trajectory.size() - 1);

        PlanetInfo planetInfo = new PlanetInfo(name, travelResponse.getPlanetGarbage());

        return new MoveInfo(planetInfo, getPlacedFiguresFromResponse(travelResponse.getShipGarbage()));
    }

    @Override
    public void load(List<PlacedFigure> newGarbage) {
        apiController.collectRequest(newGarbage);
    }
}

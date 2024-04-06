package org.example;

import org.example.api.IJson;
import org.example.api.JsonImpl;
import org.example.api.fake.FakeJsons;
import org.example.navigator.Navigator;
import org.example.visual.ship.ConsoleShipVisualizer;
import org.example.visual.ship.ShipVisualizer;

import java.util.stream.Collectors;

// Основной класс, который крутит игру в продакшене или на фейке.
// Если хотите что-то быстро протестить, меняйте Main, а не этот класс
public class Game {
    public static void play() throws Exception {
        playWithJson(new JsonImpl());
    }

    public static void playFake() throws Exception {
        playWithJson(FakeJsons.INSTANCE.get1());
    }

    private static void playWithJson(IJson json) throws Exception {
        ShipVisualizer shipVisualizer = new ConsoleShipVisualizer();
        System.out.println("Game started");
        var gameInfo = json.getGameInfo();
        System.out.println("Info parsed");
        var navigator = new Navigator(gameInfo.getGraph());
        var shipBaggage = gameInfo.getShipBaggage();

        var currentPlanet = gameInfo.getStartPlanet();
        int it = 0;
        while (true) {
            System.out.println("New move: " + ++it);
            System.out.println("Current planet: " + currentPlanet);
            var move = navigator.getMove(currentPlanet, shipBaggage);
            if (move == null) {
                System.out.println("Move not found");
                return;
            }
            System.out.println("Path: " + String.join(" -> ", move));
            var moveInfo = json.move(move);
            var planetInfo = moveInfo.getPlanetInfo();
            System.out.println("Planet: " + planetInfo.getName());
            System.out.println("Current load");
            shipVisualizer.visualize(planetInfo.getGarbageAsList(),
                    shipBaggage.getCapacityX(), shipBaggage.getCapacityY());
            shipBaggage.setLoad(moveInfo.getShipLoad());
            currentPlanet = planetInfo.getName();
            var planet = navigator.setPlanetGarbage(currentPlanet, planetInfo.getGarbage());
            planet.makeOptimalLoad(shipBaggage);
            System.out.println("New load");
            shipVisualizer.visualize(shipBaggage.getLoad(), shipBaggage.getCapacityX(), shipBaggage.getCapacityY());
            json.load(shipBaggage.getLoad());
            shipBaggage.clean();
            System.out.println("Ship is cleaned");
            Thread.sleep(1000);
        }
    }
}

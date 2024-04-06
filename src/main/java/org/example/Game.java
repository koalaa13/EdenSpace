package org.example;

import org.example.api.IJson;
import org.example.api.JsonImpl;
import org.example.api.fake.FakeJsons;
import org.example.navigator.Navigator;

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
        var gameInfo = json.getGameInfo();
        var navigator = new Navigator(gameInfo.getGraph());
        var shipBaggage = gameInfo.getShipBaggage();

        var currentPlanet = "Earth";
        while (true) {
            var move = navigator.getMove(currentPlanet, shipBaggage);
            if (move == null) {
                return;
            }
            var moveInfo = json.move(move);
            var planetInfo = moveInfo.getPlanetInfo();
            shipBaggage.setLoad(moveInfo.getShipLoad());
            currentPlanet = planetInfo.getName();
            var planet = navigator.setPlanetGarbage(currentPlanet, planetInfo.getGarbage());
            planet.makeOptimalLoad(shipBaggage);
            json.load(shipBaggage.getLoad());
            shipBaggage.clean();

            Thread.sleep(1000);
        }
    }
}

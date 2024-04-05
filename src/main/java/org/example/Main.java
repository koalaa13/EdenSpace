package org.example;


import org.example.api.fake.FivePlanetsBambooFakeJson;

public class Main {
    public static void main(String[] args) throws Exception {
        Game.playFake();
        var fakeJson = new FivePlanetsBambooFakeJson();
    }
}
package org.example;

import org.example.api.GameInfo;
import org.example.api.IJson;
import org.example.api.JsonImpl;
import org.example.model.graph.Graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Main {
    private static void check() {
        IJson json = new JsonImpl();
        GameInfo gameInfo = json.getGameInfo();
        Graph graph = gameInfo.getGraph();

        Map<String, String> parent = new HashMap<>();
        Queue<String> q = new ArrayDeque<>();
        String s = "Eden";
        q.add(s);
        parent.put(s, null);
        while (!q.isEmpty()) {
            String v = q.poll();
            for (var to : graph.getEdgesFrom(v).entrySet()) {
                if (!parent.containsKey(to.getKey())) {
                    parent.put(to.getKey(), v);
                    q.add(to.getKey());
                }
            }
        }
        String f = "Keeling";
        if (!parent.containsKey(f)) {
            System.out.println("No path");
            return;
        }
        while (f != null) {
            System.out.println(f);
            f = parent.get(f);
        }
    }

    public static void main(String[] args) throws Exception {
        Game.play();
    }
}
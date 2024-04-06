package org.example.visual.graph;

import kotlin.Pair;
import org.example.model.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FXGraphVisualizer extends JFrame implements GraphVisualizer {
    private double zoom = 1.0;

    private JPanel canvas;

    private Graph graph;

    private List<String> nodes;

    private Map<String, Integer> name2idx;

    private String currentNode;

    private List<String> path;

    private int W = 1450;

    private int H = 850;

    @Override
    public void visualize(Graph graph, String current, List<String> path) {
        this.graph = graph;
        this.nodes = graph.getAllEdges().keySet().stream().toList();
        this.name2idx = new HashMap<>();
        for (int i = 0; i < this.nodes.size(); ++i) {
            this.name2idx.put(this.nodes.get(i), i);
        }
        this.currentNode = current;
        this.path = path;
        repaint();
    }

    public FXGraphVisualizer() {
        super("canvas");

        setSize((int) Math.ceil(W * zoom), (int) Math.ceil(H * zoom));

        canvas = new JPanel() {

            // paint the canvas
            public void paint(Graphics g)
            {
                if (graph != null) {
                    try{
                        paintGraph(g);
                    } catch (Exception e) {
                        System.err.println("Error while updating graph");
                        System.err.println(e);
                    }
                }
            }
        };

        add(canvas);
        show();
    }

    private Pair<Integer, Integer> getNodePos(int count, int index, double extraDelta) {
        int centerW = W / 2;
        int centerH = H / 2;
        var radiusW = (W - centerW) * (0.85 + extraDelta);
        var radiusH = (H - centerH) * (0.85 + extraDelta);
        double angle = 360 * (double) index / (double) count;
        var posW = (int) (centerW + radiusW * Math.sin(Math.toRadians(angle)));
        var posH = (int) (centerH + radiusH * Math.cos(Math.toRadians(angle)));
        return new Pair<>(posW, posH);
    }

    private void paintGraph(Graphics g) {
        var allEdges = graph.getAllEdges();
        var count = allEdges.size();
        g.setColor(new Color(20, 20, 20));
        g.setFont(new Font("Bold", 1, 8));
        for (int i = 0; i < count; ++i) {
            var posS = getNodePos(count, i, 0.07);
            g.drawString(nodes.get(i), posS.getFirst(), posS.getSecond());
            var posO = getNodePos(count, i, 0.0);
            g.drawOval(posO.getFirst(), posO.getSecond(), 6, 6);
        }
        {
            g.setColor(new Color(240, 100, 100));
            var curPos = getNodePos(count, name2idx.get(currentNode), 0.07);
            g.drawString(currentNode, curPos.getFirst(), curPos.getSecond());
        }
        {
            g.setColor(new Color(0, 0, 180));
            var curPos = getNodePos(count, name2idx.get("Eden"), 0.07);
            g.drawString(currentNode, curPos.getFirst(), curPos.getSecond());
        }
        {
            g.setColor(new Color(0, 0, 180));
            var curPos = getNodePos(count, name2idx.get("Earth"), 0.07);
            g.drawString(currentNode, curPos.getFirst(), curPos.getSecond());
        }
        {
            g.setColor(new Color(20, 20, 20));
            for (var entry : allEdges.entrySet()) {
                var fromPos = getNodePos(count, name2idx.get(entry.getKey()), 0.0);
                for (var entry2 : entry.getValue().entrySet()) {
                    var toPos = getNodePos(count, name2idx.get(entry2.getKey()), 0.0);
                    g.drawLine(fromPos.getFirst() + 3, fromPos.getSecond() + 3,
                            toPos.getFirst() + 3, toPos.getSecond() + 3);
                }
            }
        }
        {
            g.setColor(new Color(20, 180, 20));
            for (int i = 0; i < path.size() - 1; ++i) {
                var fromPos = getNodePos(count, name2idx.get(path.get(i)), 0.0);
                var toPos = getNodePos(count, name2idx.get(path.get(i + 1)), 0.0);
                g.drawLine(fromPos.getFirst() + 3, fromPos.getSecond() + 3,
                            toPos.getFirst() + 3, toPos.getSecond() + 3);
            }
        }
    }
}

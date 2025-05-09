package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label {

    private int currentNode;
    private double realisedCost;
    private boolean mark;
    private Node parent;

    public Label(int currentNode, double realisedCost, boolean mark, Node parent){
        this.currentNode = currentNode;
        this.realisedCost = realisedCost;
        this.mark = mark;
        this.parent = parent;
    }

    public int getCurrentNode(){
        return this.currentNode;
    }
    public double getRealisedCost(){
        return this.realisedCost;
    }
    public boolean getMark(){
        return this.mark;
    }
    public Node getParent(){
        return this.parent;
    }

    public double getCost(){
        return getRealisedCost();
    }

    public static void initLabelsArray(Label[] labels,int origin,  double initDist){
        for(Label label : labels){
            label.parent = null;
            label.realisedCost = initDist;
            label.mark = false;
        }
        labels[origin].realisedCost = 0;
    }
}
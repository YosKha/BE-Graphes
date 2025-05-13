package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{

    private Node node;
    private double realisedCost;
    private boolean mark;
    private Arc parent;

    public Label(Double realisedCost, boolean mark){
        this.parent = null;
        this.realisedCost = realisedCost;
        this.mark = mark;
    }

    public Label(Node node, double realisedCost, boolean mark, Arc parent){
        this.node = node;
        this.realisedCost = realisedCost;
        this.mark = mark;
        this.parent = parent;
    }

    public Node getNode(){
        return this.node;
    }
    public void setNode(Node node){this.node = node;}
    public double getRealisedCost(){
        return this.realisedCost;
    }

    public void setRealisedCost(double cost){
        this.realisedCost = cost;
    }

    public boolean getMark(){
        return this.mark;
    }

    public void setMark(boolean mark){
        this.mark = mark;
    }

    public Arc getParent(){
        return this.parent;
    }

    public void setParent(Arc parent){
        this.parent = parent;
    }

    public double getCost(){
        return getRealisedCost();
    }

    @Override
    public int compareTo(Label o) {
        double cost1 = this.realisedCost;
        double cost2 = o.realisedCost;
        if(cost1 == cost2){
            return 0;
        }
        if(cost1 > cost2) {
            return 1;
        }else{
            return -1;
        }
    }
}
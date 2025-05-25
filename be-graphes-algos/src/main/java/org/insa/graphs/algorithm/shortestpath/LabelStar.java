package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label implements Comparable<Label>{

    protected double heuristic;

    public LabelStar(double realisedCost, boolean mark){
        super(realisedCost, mark);
    }

    public LabelStar(Node node, double realisedCost, boolean mark, Arc parent, Node destination){
        super(node, realisedCost, mark, parent);
        heuristic = Point.distance(node.getPoint(), destination.getPoint());
    }

    public double getHeuristic(){
        return this.heuristic;
    }

    public void setHeuristic(Node destination){
        this.heuristic = Point.distance(getNode().getPoint(), destination.getPoint());
    }

    @Override
    public double getTotalCost(){
        return getRealisedCost() + this.heuristic;
    }

    @Override
    public int compareTo(Label o) {
        if(!(o instanceof LabelStar)){
            throw new IllegalArgumentException("Expected LabelStar passed in argument");
        }

        return Double.compare(getTotalCost(), o.getTotalCost());
    }
}

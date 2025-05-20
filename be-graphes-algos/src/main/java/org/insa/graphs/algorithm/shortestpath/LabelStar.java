package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label{

    public LabelStar(double realisedCost, boolean mark){
        super(realisedCost, mark);
    }

    public LabelStar(Node node, double realisedCost, boolean mark, Arc parent){
        super(node, realisedCost, mark, parent);
    }

    @Override
    public double getTotalCost(){
        Point nodePoint = getNode().getPoint();
        Point parentPoint = getParent().getDestination().getPoint();
        return getRealisedCost() + Point.distance(nodePoint, parentPoint);
    }

    @Override
    public int compareTo(Label o) {
        if(!(o instanceof LabelStar)){
            throw new IllegalArgumentException("Expected LabelStar passed in argument");
        }

        return Double.compare(getTotalCost(), o.getTotalCost());
    }
}

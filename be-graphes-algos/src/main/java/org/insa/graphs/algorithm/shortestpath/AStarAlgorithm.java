package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class AStarAlgorithm extends GenericShortestPathAlgorithm<LabelStar> {


    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected LabelStar[] initLabels(int size, Node origin){
        LabelStar[] labels = new LabelStar[size];
        for(int i=0; i<size; i++){
            labels[i] = new LabelStar(Double.POSITIVE_INFINITY, false);
        }
        LabelStar originLabel= new LabelStar(origin, 0, false, null, getInputData().getDestination());
        labels[origin.getId()] = originLabel;
        return labels;
    }

    @Override
    protected void updateLabel(LabelStar label, Node node){
        label.setNode(node);
        label.setHeuristic(getInputData().getDestination());
    }

}

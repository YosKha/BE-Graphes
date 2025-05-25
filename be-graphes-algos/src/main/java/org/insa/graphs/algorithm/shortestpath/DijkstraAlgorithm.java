package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraAlgorithm extends GenericShortestPathAlgorithm<Label> {


    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected Label[] initLabels(int size, Node origin) {
        Label[] labels = new Label[size];
        for (int i = 0; i < size; i++) {
            labels[i] = new Label(Double.POSITIVE_INFINITY, false);
        }
        Label originLabel = new Label(origin, 0, false, null);
        labels[origin.getId()] = originLabel;
        return labels;
    }

    @Override
    protected void updateLabel(Label label, Node node){
        label.setNode(node);
    }

}

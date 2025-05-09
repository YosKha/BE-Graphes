package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();
        final Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        final Node origin = data.getOrigin();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm
        BinaryHeap heap = new BinaryHeap();

        Label[] labels = new Label[nbNodes];
        Label.initLabelsArray(labels, origin.getId(), Double.POSITIVE_INFINITY);

        while(!heap.isEmpty()){

        }

        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}

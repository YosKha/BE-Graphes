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

        // Init labels Array => All labels at anytime
        Label[] labels = new Label[nbNodes];
        for(int i=0; i<nbNodes; i++){
            labels[i] = new Label(Double.POSITIVE_INFINITY, false);
        }
        Label originLabel= new Label(origin, 0, false, null);
        labels[origin.getId()] = originLabel;

        // Initialization binary heap
        BinaryHeap<Label> heap = new BinaryHeap<>();
        heap.insert(originLabel);

        notifyOriginProcessed(origin);

        // Djistra algorithm
        while(!heap.isEmpty()){
            Label currentNodeLabel = heap.deleteMin();
            Node currentNode = currentNodeLabel.getNode();
            List<Arc> successors = currentNode.getSuccessors();
            notifyNodeMarked(currentNode);

            // It reached the destination
            if(currentNode.equals(data.getDestination())){
                notifyDestinationReached(data.getDestination());
                break;
            }

            for(Arc arc : successors){
                Node nextNode = arc.getDestination();
                Label nextNodeLabel = labels[nextNode.getId()];
                nextNodeLabel.setNode(nextNode);

                double costAttempt = currentNodeLabel.getRealisedCost() + arc.getMinimumTravelTime();
                if(costAttempt < nextNodeLabel.getRealisedCost()){
                    System.out.println(costAttempt+"\n");
                    nextNodeLabel.setRealisedCost(costAttempt);
                    nextNodeLabel.setParent(arc);

                    // The mark represent : "Is the label in the heap"
                    if(!nextNodeLabel.getMark()){
                        notifyNodeReached(nextNode);
                        heap.insert(nextNodeLabel);
                        nextNodeLabel.setMark(true);
                    }
                }
            }
        }

        // Browse the shortest path from Destination to the Origin
        Node currentNode = data.getDestination();
        ArrayList<Arc> shortestPathList = new ArrayList<>();
        while(!currentNode.equals(origin)){
            Label currentNodeLabel=labels[currentNode.getId()];
            Arc currentNodeParent = currentNodeLabel.getParent();

            if(currentNodeParent == null){
                return new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE, new Path(graph));
            }
            shortestPathList.add(currentNodeParent);
            currentNode = currentNodeParent.getOrigin();
        }

        Collections.reverse(shortestPathList);
        Path shortestPath = new Path(graph, shortestPathList);
        solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, shortestPath);
        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}

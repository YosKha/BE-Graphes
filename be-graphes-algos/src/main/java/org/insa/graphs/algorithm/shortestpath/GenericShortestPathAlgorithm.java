package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public abstract class GenericShortestPathAlgorithm<E extends Label> extends ShortestPathAlgorithm{

    public GenericShortestPathAlgorithm(ShortestPathData data){
        super(data);
    }

    /**
     * @brief Initialize a E array. It lets subclasses instantiate the all E elements
     *
     * @param size the size of the array
     * @param origin the origin of the algorithm
     * @return the initialized E[] with the all label for all point
     */

    protected abstract E[] initLabels(int size, Node origin);
    protected abstract void updateLabel(E label, Node node);

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();
        final Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        final Node origin = data.getOrigin();
        final Node destination = data.getDestination();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm

        // Init labels Array => All labels at anytime
        E[] labels = initLabels(nbNodes, origin);

        // Initialization binary heap

        BinaryHeap<Label> heap = new BinaryHeap<>();
        heap.insert(labels[origin.getId()]);

        notifyOriginProcessed(origin);

        // Djistra algorithm
        while(!heap.isEmpty()){
            Label currentNodeLabel = heap.deleteMin();
            Node currentNode = currentNodeLabel.getNode();
            List<Arc> successors = currentNode.getSuccessors();
            notifyNodeMarked(currentNode);

            // It reached the destination
            if(currentNode.equals(destination)){
                notifyDestinationReached(data.getDestination());
                break;
            }

            for(Arc arc : successors){
                Node nextNode = arc.getDestination();
                E nextNodeLabel = labels[nextNode.getId()];
                /**nextNodeLabel.setNode(nextNode);
                if(nextNodeLabel instanceof LabelStar){
                    ((LabelStar) nextNodeLabel).setHeuristic(destination);
                }**/
                this.updateLabel(nextNodeLabel, nextNode);

                double costAttempt = currentNodeLabel.getRealisedCost() + arc.getLength();
                if(costAttempt < nextNodeLabel.getRealisedCost()){
                    //System.out.println(costAttempt+"\n");
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
        System.out.println(shortestPath.getMinimumTravelTime());
        solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, shortestPath);
        // when the algorithm terminates, return the solution that has been found
        return solution;
    }
}

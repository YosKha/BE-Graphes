package org.insa.graphs.algorithm.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
public class ShortestPathTest {

    Graph graph = null;
    Path path = null;

    // method to recover the paths
    public static Path readPath(Graph graph,String pathName) {
        try (final PathReader reader = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))))) {
            return reader.readPath(graph);
        } catch (Exception e) {
            System.err.println("Error while reading the path : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    // method to recover the graphs
    public static Graph readGraph(String mapName) {
        try (final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))))) {
            return reader.read();
        } catch (Exception e) {
            System.err.println("Error while reading the graph : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // short journey in a small map
    final static String mapName1= "maps/insa.mapgr";
    final static String pathName1 ="maps/path_fr31insa_rangueil_r2.path";
    // long journey in a small map
    final String mapName2= "maps/insa.mapgr";
    Graph graphe2;



    // short journey in a bigger map
    final static String mapName3= "maps/toulouse.mapgr";
    final static String pathName3 ="maps/path_be_173101_302442.path";
    // long journey in a big map
    final String mapName4= "maps/toulouse.mapgr";

    // inaccessible path by the car
    final String mapName6= "maps/insa.mapgr";
    final String pathName6 ="maps/path_fr31_insa_bikini_motorcar.path";

    // origin = destination
    final String mapName8= "maps/insa.mapgr";
    // compare over time
    final String mapName9 ="maps/haute-garonne.mapgr";
    final String pathName9 ="maps/path_fr31_insa_aeroport_time.path";
    // compare over distance
    final String mapName10 ="maps/haute-garonne.mapgr";
    final String pathName10 ="maps/path_fr31_insa_aeroport_length.path";

//Verifying the valid path
    public static boolean SolutionInfaisableDijkstra (ShortestPathData Data){
        DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(Data);
        ShortestPathSolution SolutionDijkstra = Dijkstra.run();
        if  (SolutionDijkstra.getStatus()!=AbstractSolution.Status.INFEASIBLE){
            return false;
        }
        return true;
    }

    public static boolean SolutionInfaisableBellmanFord (ShortestPathData Data){
        BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(Data);
        ShortestPathSolution SolutionBellmanFord= BellmanFord.run();
        if  (SolutionBellmanFord.getStatus()!=AbstractSolution.Status.INFEASIBLE){
            return false;
        }
        return true;
    }
    public static boolean rightPathDijkstra (ShortestPathData Data,Path startingPath){
        Path foundPath = Path_Dijkstra(Data);
        // no path f
        if (foundPath == null && startingPath == null) {
            System.out.println("No path found !");
            return true;
        }
        if (foundPath == null || startingPath == null) {
            System.out.println("one of the paths is null !");
            return false;
        }
    // Empty path
        if (foundPath.isEmpty() && startingPath.isEmpty()) {
            System.out.println("the paths are empty and valid -> origin = destination");
            return true;
        }
        if (foundPath.isEmpty() || startingPath.isEmpty()) {
            System.out.println("One of the paths is empty !");
            return false;
        }
        if(!(foundPath.isValid()) || !(startingPath.isValid()) ){
            System.out.println(" The path is not Valid !");
            return false;
        }
        // verifying the number of nodes of each path
        if(foundPath.size() != startingPath.size()){
            System.out.println(" Different size of the paths !");
            return false;
        }
        // Comparing the arcs of the two paths
        List<Arc> foundarcs = foundPath.getArcs();
        List<Arc> startingArcs = startingPath.getArcs();
        for (int i=0; i<foundPath.getArcs().size();i++){
            // on recupere les arcs de chaque iteration
            Arc arctrouve = foundarcs.get(i);
            Arc arcrecherche = startingArcs.get(i);
            if(!(arctrouve.getOrigin().equals(arcrecherche.getOrigin())) || !(arctrouve.getDestination().equals(arcrecherche.getDestination()) )){
                System.out.println("We don't have the same paths !");
                return false;
            }
        }

        return true;
    }
    // For small maps
    public static Path Path_Dijkstra(ShortestPathData Data){

        DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(Data);
        ShortestPathSolution SolutionDijkstra = Dijkstra.run();
        return SolutionDijkstra.getPath();
    }

    // Recuperation Path de Dijkstra
    public static Path Path_BellmanFord(ShortestPathData Data){

        BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(Data);
        ShortestPathSolution SolutionBellmanFord = BellmanFord.run();
        return SolutionBellmanFord.getPath();
    }
    // Verifying the existence
    public static boolean ExistancePaths(Path PathDijkstra,Path PathBellmanFord){
        if (PathDijkstra == null || PathBellmanFord == null) {
            System.out.println("The path is NULL !");
            return false;
        }
        else{ return true;}
    }
    //Verifying the validity
    public static boolean ValiditePaths(Path PathDijkstra,Path PathBellmanFord){
        if(!(PathDijkstra.isValid())|| !(PathBellmanFord.isValid()) ){
            System.out.println("The path is not Valid !");
            return false;
        }
        else{ return true;}
    }

    //Verifying the time
    public static boolean equalTime  (ShortestPathData Data){
        Path PathDijkstra =Path_Dijkstra(Data);
        Path PathBellmanFord =Path_BellmanFord(Data);
        if (!ExistancePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }
        if(!ValiditePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }

        double TimeOfDijkstra = PathDijkstra.getMinimumTravelTime();
        double TimeOfBellmanFord = PathBellmanFord.getMinimumTravelTime();
        if(TimeOfDijkstra != TimeOfBellmanFord){
            System.out.println("They have a different timing !");
            return false;
        }
        System.out.println("They have the same timing !");
        return true;
    }
    // Verifying the distance
    public static boolean equalDistance  (ShortestPathData Data){
        Path PathDijkstra =Path_Dijkstra(Data);
        Path PathBellmanFord =Path_BellmanFord(Data);

        if (!ExistancePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }
        if(!ValiditePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }

        double distanceOfDijkstra = PathDijkstra.getLength();
        double distanceOfBellmanFord = PathBellmanFord.getLength();
        if(distanceOfDijkstra != distanceOfBellmanFord){
            System.out.println("Dijkstra and BallmanFord have different distances !");
            return false;
        }
        System.out.println("Dijkstra and BellmanFord have the same distance !");
        return true;
    }
    public static boolean compareTime  (ShortestPathData Data){
        Path PathDijkstra =Path_Dijkstra(Data);
        Path PathBellmanFord =Path_BellmanFord(Data);

        if (!ExistancePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }
        if(!ValiditePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }



        List<Arc> ArcsDijkstra  = PathDijkstra.getArcs();
        List<Arc> ArcsBellmanFord = PathBellmanFord.getArcs();
        if(ArcsDijkstra.size() != ArcsBellmanFord.size()){
            return false;
        }
        // Verifying that they have the same origin and destination for each arc
        for (int i=0;i<ArcsDijkstra.size();i++){
            Node OrigineDijkstra = ArcsDijkstra.get(i).getOrigin();
            Node OrigineBellmanFord = ArcsBellmanFord.get(i).getOrigin();
            Node DestinationDijkstra = ArcsDijkstra.get(i).getDestination();
            Node DestinationBellmanFord = ArcsBellmanFord.get(i).getDestination();

            Double timeArcDijkstra = ArcsDijkstra.get(i).getMinimumTravelTime();
            Double timeArcBellmanFord = ArcsBellmanFord.get(i).getMinimumTravelTime();

            if(OrigineDijkstra.getId()!= OrigineBellmanFord.getId()|| DestinationDijkstra.getId()!= DestinationBellmanFord.getId()  ){
                System.out.println("The arcs are not the same after the arc :"+ i);
                return false;
            }

            if(timeArcDijkstra != timeArcBellmanFord){
                System.out.println("Dijkstra and BellmanFord have different timings !");
                return false;
            }
        }

        System.out.println("Dijkstra and BellmanFord have the same paths");
        return true;
    }
    public static boolean compareDistance(ShortestPathData Data){
        Path PathDijkstra =Path_Dijkstra(Data);
        Path PathBellmanFord =Path_BellmanFord(Data);

        if (!ExistancePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }
        if (!ValiditePaths(PathDijkstra, PathBellmanFord)){
            return false;
        }
        //compare the origin and the destination of each arc
        List<Arc> ArcsDijkstra  = PathDijkstra.getArcs();
        List<Arc> ArcsBellmanFord = PathBellmanFord.getArcs();

        if(ArcsDijkstra.size() != ArcsBellmanFord.size()){
            return false;
        }
        for (int i=0;i<ArcsDijkstra.size();i++){
            Node OrigineDijkstra = ArcsDijkstra.get(i).getOrigin();
            Node OrigineBellmanFord = ArcsBellmanFord.get(i).getOrigin();
            Node DestinationDijkstra = ArcsDijkstra.get(i).getDestination();
            Node DestinationBellmanFord = ArcsBellmanFord.get(i).getDestination();

            float distanceArcDijkstra = ArcsDijkstra.get(i).getLength();
            float distanceArcBellmanFord = ArcsBellmanFord.get(i).getLength();

            if(OrigineDijkstra.getId()!= OrigineBellmanFord.getId()|| DestinationDijkstra.getId()!= DestinationBellmanFord.getId()  ){
                System.out.println("The paths aren't the same after :" + i);
                return false;
            }
            if( distanceArcDijkstra!= distanceArcBellmanFord ){
                System.out.println("They don't have the same size after :" + i);
                return false;
            }
        }

        System.out.println("Dijkstra and BellmanFord have the same path");
        return true;
    }
    // For the bigger maps
    public static void main(String[] args) {
        // recuperation des arcInspectors
        List<ArcInspector> filters = ArcInspectorFactory.getAllFilters();
        ArcInspector Nofilterbylength = filters.get(0);
        ArcInspector OnlyCarByLength = filters.get(1);
        ArcInspector OnlyCarByTime= filters.get(2);
        ArcInspector OnlyPedestrianByTime = filters.get(3);

        Graph graph = readGraph(mapName1);
        Path recoverPath1 = readPath(graph, pathName1);

        ShortestPathData Data1 = new ShortestPathData(graph,recoverPath1.getOrigin(), recoverPath1.getDestination(),Nofilterbylength);

        if(rightPathDijkstra(Data1, recoverPath1)){
            System.out.println("Dijkstra give the right path !\n ");
        }
        else{
            System.out.println("Dijkstra don't dive the right path !\n");
        }

        // Comparing to BellmanFord by the distance
        if(equalDistance(Data1)){
            System.out.println("Dijkstra and BellmanFord give the same distance !\n ");
        }
        else{
            System.out.println("Dijkstra and Bellman Ford don't give the same distance !\n ");
        }
        // Comparing to BellmanFord by the time
        if(equalTime(Data1)){
            System.out.println("Dijkstra and BellmanFord give the same time !\n ");
        }
        else{
            System.out.println("Dijkstra and BellmanFord don't give the same time !\n ");
        }

        // Compare the paths
        if(compareDistance(Data1)){
            System.out.println("Dijkstra and BellmanFord have the same path\n ");
        }
        else{
            System.out.println("Dijkstra and BellmanFord don't give the same path\n ");
        }

        // There is no path !
        ShortestPathData Data2 = new ShortestPathData(graph,recoverPath1.getOrigin(), recoverPath1.getDestination(),OnlyCarByLength);

        if(SolutionInfaisableDijkstra(Data2)){
            System.out.println("Dijkstra algorithm correctly detects no feasible path \n ");
        }
        else{
            System.out.println("Dijkstra algorithm does not detect the correct path\n");
        }

        //Compare to BellmanFord
        if(SolutionInfaisableBellmanFord(Data2)){
            System.out.println("BellmanFord also reports no feasible path \n ");
        }
        else{
            System.out.println("BellmanFord also reports a feasible path\n ");
        }

        // Filter OnlyCarbytime: no path
        ShortestPathData Data3 = new ShortestPathData(graph,recoverPath1.getOrigin(), recoverPath1.getDestination(),OnlyCarByTime);

        // Compare distances
        if(equalDistance(Data3)){
            System.out.println("Dijkstra and BellmanFord give the same distance\n ");
        }
        else{
            System.out.println("Dijkstra and BellmanFord don't give the same distance\n ");
        }

        if(equalTime(Data3)){
            System.out.println("Dijkstra and BellmanFord give the same travel time\n ");
        }
        else{
            System.out.println("Dijkstra and BellmanFord don't give the same travel time\n ");
        }


        // compare times
        if(compareDistance(Data3)){
            System.out.println("Dijkstra and BellmanFord have the same path\n ");
        }
        else{System.out.println("Dijkstra and BellmanFord don't have the same path\n ");}

    }




}





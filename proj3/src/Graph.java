import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
//creating a Graph Object and implementing dijkstra algorithm
public class Graph {
	
	Map<String, double[] > nodesMap;
	ArrayList<Edge> edges;
	Map<String, Node> nodes;
	
	public Graph(){
		nodesMap = new HashMap<String, double[]>();
		edges = new ArrayList<Edge>();
		nodes=new HashMap<String,Node>();
	}
	public void addNode(String IntersectionID, double Latitude, double Longitude) {
		nodesMap.put(IntersectionID, new double[] {Latitude,Longitude});
	}
	public void addEdge(String RoadID, String from, String to) {
		edges.add(new Edge(RoadID, from, to, getLength(from,to)));
	}
	public double getLength( String from,String to) {
		double  distance = Math.sqrt((Math.pow(((nodesMap.get(from)[0] - nodesMap.get(to)[0])*68.99), 2))+
				(Math.pow(((nodesMap.get(from)[1] - nodesMap.get(to)[1])*53.06),2)));
		return distance;
	}
	public void AddingEachEdgetoTwoNodes() {
		//Creating all nodes ready to be updated with edges
		for(String nodeName: nodesMap.keySet()) 
			nodes.put(nodeName, new Node());
		//adding all edges to the nodes, each edge added to two nodes(from and to)
		for(int j = 0; j < edges.size(); j++) {
			nodes.get(edges.get(j).getstartID()).getEdges().add(edges.get(j));
			nodes.get(edges.get(j).getendID()).getEdges().add(edges.get(j));
		}
	}
	public void calculateShortestDistance(String startID, String endID) {
		//startID from source/start
		nodes.get(startID).setDistanceFromSource(0);
		String nextNode = startID;
		
		//visit every node and, no need to continue if we've reached endID.
		while(!nodes.isEmpty() && !nextNode.equals(endID)){
			if(nextNode.equals(""))
				break;
			ArrayList<Edge> currentNodeEdges = nodes.get(nextNode).getEdges();
			//loop around the edges of the current node
			
			for(int joinedEdge = 0 ; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
				String neighbourID = currentNodeEdges.get(joinedEdge).getNeighbor(nextNode);
				//only if not visited
				
				if(!nodes.get(neighbourID).getisVisited()) {
					double tentative = nodes.get(nextNode).getDistanceFromSource()+ currentNodeEdges.get(joinedEdge).getLength();
					
					if(tentative < nodes.get(neighbourID).getDistanceFromSource()) {
						nodes.get(neighbourID).setDistanceFromSource(tentative);
						nodes.get(neighbourID).setPrevious(nextNode);
					}
					
				}
			}
			//all neighbors checked so node is visited
			nodes.get(nextNode).setisVisited(true);
			//next node must be the shortest distance
			nextNode = getNodeShortestDistanced();
		}
	}
	private String getNodeShortestDistanced() {
		String storedNodeIndex = "";
		double storedDist  = Integer.MAX_VALUE;
		for(String nodeName : nodes.keySet()) {
			double currentDist = nodes.get(nodeName).getDistanceFromSource();
			if(!(nodes.get(nodeName).getisVisited()) && currentDist < storedDist) {
				storedDist = currentDist;
				storedNodeIndex = nodeName;
			}
		}
		return storedNodeIndex;
	}
	public List<String> mapPathsTofindShortestDistance(String startID, String endID){
		List<String> path = new ArrayList<>();
		calculateShortestDistance(startID, endID);
		if(startID.equals(endID)) {
			path.add(startID);
			return path;
		}else if(nodes.get(endID).getDistanceFromSource()==Integer.MAX_VALUE)
			return path;
		else {
			Stack<String> stack = new Stack<>();
			String constructPath = endID;
			nodes.get(constructPath).setHighlighted(true);
			stack.push(constructPath);
			while(!(nodes.get(constructPath).getPrevious().equals(startID))) {
				constructPath = nodes.get(constructPath).getPrevious();
				nodes.get(constructPath).setHighlighted(true);
				stack.push(constructPath);
			}
			stack.push(startID);
			nodes.get(startID).setHighlighted(true);
			while(!stack.isEmpty()) {
				path.add(stack.pop());
			}
			return path;
		}			
	}
	
}

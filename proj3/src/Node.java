import java.util.ArrayList;
//creating a node object to model a node in our graph
public class Node{
	private boolean isVisited;
	private String previous;
	private double distanceFromSource = Integer.MAX_VALUE;
	private ArrayList<Edge> edges = new ArrayList<>();
	private boolean isHighlighted;
	
	public double getDistanceFromSource() {return distanceFromSource;}
	public String getPrevious() {return previous;}
	public boolean getisVisited() {return isVisited;}
	public ArrayList<Edge> getEdges(){return edges;}
	public boolean isHighlighted() {return isHighlighted;}

	public void setPrevious(String previous) {this.previous=previous;}
	public void setDistanceFromSource(double distanceFromSource) {this.distanceFromSource= distanceFromSource;}
	public void setisVisited(Boolean isVisited) {this.isVisited=isVisited;}
//	public void setEdges(ArrayList<Edge> edges) {this.edges = edges;}
	public void setHighlighted(boolean isHighlighted) {this.isHighlighted = isHighlighted;}
}
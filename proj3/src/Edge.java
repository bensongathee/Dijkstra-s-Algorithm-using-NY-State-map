//creating an Edge object to model an edge in our graph
public class Edge {
	private String RoadID;
	private String startID;
	private String endID;
	private double length;
	
	public Edge(String RoadID, String startID, String endID, double length) {
		this.RoadID=RoadID;
		this.startID=startID;
		this.endID=endID;
		this.length = length;
	}
	
	public String getRoad() {return RoadID;}
	public String getstartID() {return startID;}
	public String getendID() {return endID;}
	public double getLength() {return length;}
	
	//determines the neighboring node from a supplied node, based on the two nodes connected by this edge
	public String getNeighbor(String name) {
		if(this.startID.equals(name)) 
			return this.endID;
		else
			return this.startID;
	}
}

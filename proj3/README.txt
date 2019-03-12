Name: Benson Gathee
NetID: bgathee
Project partner: Arun Ramesh
Assignment: Project 3

In this project, we implemented Dijkstra's algorithm to find the shortest path between two nodes.

Classes:

Edge.java
 implements the edges: we add the edges by reading the roads from the txt files given and create new edges

Node.java
 implements the nodes. Similarly, we add nodes to the graph by reading the intersections from the txt files

Graph.java
 This is the class that contains the most important methods, including our implementation
 of dijkstras algorithm. We use hashmaps for edges and nodes to decrease run time 
 complexity.
	
 There are comments in the code that describe each method

Mapsmain.java
 This contains the main method for the project, and creates the map

Command line:
	As given in project guideline
		java StreetMap ur.txt --show --directions HOYT MOREY // Showing both map and the directions
		java StreetMap ur.txt --show // Just showing the map
		java StreetMap ur.txt -- directions HOYT MOREY // Showing the map is optional .
 
Possible Extra Credit
	-If there does not exist a path between two nodes, we ask the user if he/she wants
	 to search for another path
		(eg try Monroe show directions i11 i13, and then provide the two points in the console)
	- we import BasicStroke and use the following:
		g2.setStroke(new BasicStroke(2.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f));
	  to make the lines neat and sharper.
	-Code readablity and commenting
	-the map resizes itself with the window.

Runtime analysis
	In this project, after completing several trials for each txt file, we came to the 
	conclusion that ur.txt is obviously the fastest, being the shortest file. Monroe takes
	slightly longer (but relatively fast) as it is 5M, while nys is the slowest, being a large
	file with 35M. The best case should be O(1), due to optimizations we did in the algorithm.
	As we loop over all nodes, the worst case would be O(nlogn).

I guess this will be the last assignment you will grade, so thank you for your help and good luck for your finals!
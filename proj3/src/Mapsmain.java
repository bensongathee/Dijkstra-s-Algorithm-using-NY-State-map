import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
public class Mapsmain extends JComponent{
	Graph graph = new Graph();
    double maxx=-100;
    double minx=100;
	double miny=100;
	double maxy=-100;
	public Mapsmain(String file) {
		try{
            BufferedReader buf = new BufferedReader(new FileReader(file));
            String lineJustFetched = null;
            String[] wordsArray;
            while(true){
                lineJustFetched = buf.readLine();
                if(lineJustFetched == null) 
                    break; 
                else{
                    wordsArray = lineJustFetched.split("\t");
                    if(wordsArray[0].equals("i")) {
                    	graph.addNode(wordsArray[1],Double.parseDouble(wordsArray[2]),Double.parseDouble(wordsArray[3]));
                    	if(minx>Double.parseDouble(wordsArray[2]))
    	                	minx = Double.parseDouble(wordsArray[2]);
                    	if(maxx<Double.parseDouble(wordsArray[2]))
    	                	maxx = Double.parseDouble(wordsArray[2]);
                    	if(miny>Double.parseDouble(wordsArray[3]))
    	                	miny = Double.parseDouble(wordsArray[3]);
    					if(maxy<Double.parseDouble(wordsArray[3]))
    						maxy = Double.parseDouble(wordsArray[3]);
                    }
                    else
                    	graph.addEdge(wordsArray[1],wordsArray[2],wordsArray[3]);      
                }
            }
            buf.close();
        }catch(FileNotFoundException e) {
			e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	public void paintComponent(Graphics g) {
		Graphics2D g1 = (Graphics2D)g;
		for(Edge e : graph.edges) {
			if((graph.nodes.get(e.getstartID()).isHighlighted())&&(graph.nodes.get(e.getendID()).isHighlighted())) {
				g1.setColor(Color.red);
				g1.setStroke(new BasicStroke(2.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f));
			}
			else {
				g1.setColor(Color.black);
				g1.setStroke(new BasicStroke(1.2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f));
			}
			double x1 = ((getWidth()*3/4)/(maxx-minx))*(maxx-(graph.nodesMap.get(e.getstartID())[0]))+100;
			double x2 = ((getWidth()*3/4)/(maxx-minx))*(maxx-(graph.nodesMap.get(e.getendID())[0]))+100;
   			double y1 = ((getHeight()*3/4)/(maxy-miny))*((graph.nodesMap.get(e.getstartID())[1])-miny)+100;
			double y2 = ((getHeight()*3/4)/(maxy-miny))*((graph.nodesMap.get(e.getendID())[1])-miny)+100;
			Line2D line = new Line2D.Double((y1), (x1), (y2), (x2));
			g1.draw(line);
		}
	}
	public static void main(String[] args) {
		String fileName = args[2];
		Mapsmain map = new Mapsmain(fileName);
		map.graph.AddingEachEdgetoTwoNodes();
		JFrame frame = new JFrame("Shortest Path");
		frame.add(map);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		Scanner userInput = new Scanner(System.in);
		if(Arrays.asList(args).contains("--show") && Arrays.asList(args).contains("--directions")) {
			String from = args[5];
			String to = args[6];
			
			
			
			//calculates how long the program takes to run
			long start = System.currentTimeMillis();
			List<String> path = map.graph.mapPathsTofindShortestDistance(from,to);
			long end = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.print("Execution time is " + formatter.format(((end - start) / 1000d)/60) + " minutes\n");
			
			
			
			if(path.size()!=0) {
				System.out.println("Shortest path: "+path+"\n"+map.graph.nodes.get(to).getDistanceFromSource()+" miles.");
				frame.setVisible(true);
			}else {
				while(path.size()==0) {
					System.out.println("No path between "+from+" and "+to+ ".\nDo you wish to continue searching for the shortest path between two points on this map?\nEnter Yes or No");
					String input = userInput.next();
					if(input.toLowerCase().equals("yes")) {
						for(String s : map.graph.nodes.keySet()) {
							map.graph.nodes.get(s).setPrevious(null);
							map.graph.nodes.get(s).setHighlighted(false);
							map.graph.nodes.get(s).setDistanceFromSource(Integer.MAX_VALUE);
							map.graph.nodes.get(s).setPrevious(null);
						}
						System.out.println("Enter the StartID: ");
						from = userInput.next();
						System.out.println("Enter the endID: ");
						to = userInput.next();
						path = map.graph.mapPathsTofindShortestDistance(from,to);
						if(path.size()!=0) {
							System.out.println("Shortest path: "+path+"\n"+map.graph.nodes.get(to).getDistanceFromSource()+" miles.");
							frame.setVisible(true);
							break;
						}
					}
					else {
						System.out.println("Goodbye!");
						break;
					}
				}
				userInput.close();
			}
		}else if(Arrays.asList(args).contains("--show") && !Arrays.asList(args).contains("--directions")) {
			frame.setVisible(true);
		}else if(Arrays.asList(args).contains("--directions") && !Arrays.asList(args).contains("--show")) {
			String from = args[4];
			String to = args[5];
			List<String> path = map.graph.mapPathsTofindShortestDistance(from,to);
			System.out.println("Shortest path: "+path+"\n"+map.graph.nodes.get(to).getDistanceFromSource()+" miles.");
		}else
			System.out.println("Query error! Kindly read the readme file for Query help");
	}
}
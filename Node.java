//Sample code to read in test cases:
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;


	
	 public class Node{
		public String nodeName;
		public double xCor ;
		public double yCor ;
		public double value;
		HashMap<String,Double> arcList = new HashMap<String,Double>();
	
	public  Node(String nodename, double x ,double y)
	{
		nodeName = nodename;
	    xCor     = x;
		yCor     = y;
		value    = 0;
	
	}
	
	public  void addArc(String a,double dist)
	{
		arcList.put(a, dist);
		
	  
	}
	
 public static void travellingSalesman(HashMap<String, Node> nodeMap,  PriorityQueue<Node> optimal)
 {
	 HashMap<String,String> visited = new HashMap<String,String>();
	
	 while(! optimal.isEmpty())
	 {
		 
		 Node strt = optimal.poll();
		 if(! visited.containsKey(strt.nodeName))
				 {
			        System.out.println(strt.nodeName);
			 		visited.put(strt.nodeName, strt.nodeName);
			 		 Iterator<String> itr = strt.arcList.keySet().iterator();
			 		  while(itr.hasNext())
			 		  { 
			 	    	String name = itr.next();
			 	    	if(! visited.containsKey(name))
			 	    		{
			 	    			double a         = strt.arcList.get(name);
			 	    			Node toConsider  = nodeMap.get(name);
			 	    			toConsider.value = a ;
			 	    			optimal.remove(toConsider);
			 	    			optimal.add(toConsider);
			 	    		
			 	    	
			 	    		}
			 		  }
			 		
				 }
	 }
 }
 
 public static double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
	  double R    = 6371; // Radius of the earth in km
	  double dLat = deg2rad(lat2-lat1);  // deg2rad below
	  double dLon = deg2rad(lon2-lon1); 
	  double a    = 
			  		Math.sin(dLat/2) * Math.sin(dLat/2) +
			  		Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
			  		Math.sin(dLon/2) * Math.sin(dLon/2); 
	  double c    = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	  double d    = R * c; // Distance in km
	  return d;
	}

	public static double deg2rad(double deg) {
	  return deg * (Math.PI/180);
	}
	
	
	public static void main (String[] args)
	{
		
		 HashMap<String,Node>   nodeMap = new HashMap<String,Node>();
		 ArrayList<String>      list    = new ArrayList<String>();
		 PriorityQueue<Node> optimal    = new PriorityQueue<Node>(10, new Comparator<Node>() {
		        public int compare(Node node1, Node node2) {
		            return Double.valueOf(node1.value).compareTo(node2.value);}
		    });

		 
    	BufferedReader in = null;
	try {
		in = new BufferedReader(new FileReader("film.txt"));
	} catch (FileNotFoundException e) {

		e.printStackTrace();
	}
    String line;
    try {
		while ((line = in.readLine()) != null) {

			String   a  = ""+ line.substring(0,line.indexOf(" "));
			String[] b  = line.substring(line.indexOf("(")+1, line.indexOf(")")).split("\\,");
			       
			
			Node dani = new Node(a,Double.parseDouble(b[0]),Double.parseDouble(b[1]));
			nodeMap.put(a, dani);
			list.add(a);
			optimal.add(dani);
			
			
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
    for (int i =0;i<list.size();i++)
    {
    	Node toAdd = nodeMap.get(list.get(i));
    	for(int j=0;j<list.size();j++)
    	{
    		Node b = nodeMap.get(list.get(j));
    		if(i != j)
    		{
    			double distance = getDistanceFromLatLonInKm(toAdd.xCor,toAdd.yCor,b.xCor,b.yCor);//(double)Math.sqrt(Math.pow((toAdd.xCor - b.xCor),2) + Math.pow((toAdd.yCor - b.yCor),2));
    			
    			toAdd.addArc(b.nodeName, distance);
    		}
    	}
    }
    
    travellingSalesman(nodeMap,optimal);
    
    
    
    
	}
	}
	
	
	
	

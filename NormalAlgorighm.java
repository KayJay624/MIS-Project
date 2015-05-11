package badania;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class NormalAlgorithm {
	private int vertNum;
	private int[][] adjMatrix;
	
	// wywołać za pomocą NormalAlgorithm nor = new NormalAlgorithm(); nor.algNormal(sciezka);
	
	private int[][] getGraph(String path) throws FileNotFoundException {
	    File file = new File(path);
	    Scanner in = new Scanner(file);
	 
	    String line = in.nextLine();
	    String[] vertices = line.split(",");

	    vertNum = vertices.length; 	     	      
	    adjMatrix = new int[vertNum][vertNum];
	    
	    for(int i = 0; i < vertNum; i++) {		  
	    	for(int j = 0; j < vertNum; j++) {
				  adjMatrix[i][j] = 0;
			}
	    }
	           
	    String scanLine;
	    while(in.hasNextLine()) {
		    scanLine = in.nextLine();
	        String[] edges = scanLine.split(",");
	        int i = Integer.parseInt(edges[0]) - 1;
	        int j = Integer.parseInt(edges[1]) - 1;
	       
	        adjMatrix[i][j] = 1;
	        adjMatrix[j][i] = 1; 	    	  
		}
	    in.close();
	    return adjMatrix;
	}
	
	private void algNormal(String path) {
		try {
			adjMatrix = getGraph(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		vertNum = adjMatrix[0].length;
		Vertexx[] tab = new Vertexx[vertNum];
		Graph<Vertexx, Edgee> g = new SparseMultigraph<Vertexx, Edgee>();
		Graph<Vertexx, Edgee> S = new SparseMultigraph<Vertexx, Edgee>();
		Graph<Vertexx, Edgee> gC =  new SparseMultigraph<Vertexx, Edgee>();;
		
		for(int i = 0; i < vertNum; i++) {
			Vertexx v = new Vertexx(i);
			g.addVertex(v);
			gC.addVertex(v);
			tab[i] = v;
		}
		for(int i = 0; i < vertNum; i++) {
			for(int j = 0; j <= i; j++) {
				if(adjMatrix[i][j] == 1) {
					Edgee e = new Edgee(i, j);
					g.addEdge(e, tab[i], tab[j]);
					gC.addEdge(e, tab[i], tab[j]);	
				}
			}
		}

		Random generator = new Random();
        while(g.getVertexCount() != 0) {
			 Collection<Vertexx> cW = g.getVertices();
			 LinkedList<Vertexx> lW = new LinkedList<Vertexx>(cW); 
			 Integer v = generator.nextInt(lW.size());	
			 
			 if(g.containsVertex(lW.get(v))) {
				 S.addVertex(lW.get(v));
				 				
				 Collection<Vertexx> cS = g.getNeighbors(lW.get(v));
				 LinkedList<Vertexx> lS = new LinkedList<Vertexx>(cS);
				 for(int i = 0; i < lS.size(); i++) {
					 g.removeVertex(lS.get(i));
				 }	 
				 g.removeVertex(lW.get(v));	 
			 }
        }
		Collection<Vertexx> cW = gC.getVertices();
		LinkedList<Vertexx> lW = new LinkedList<Vertexx>(cW); 
		
		Collection<Vertexx> cS = S.getVertices();
		LinkedList<Vertexx> lS = new LinkedList<Vertexx>(cS);
		int[] Stab = new int[lW.size()];
		for(int i = 0; i < lW.size(); i++) {
			for(int j = 0; j < lS.size(); j++) {
				if(lW.get(i).id == lS.get(j).id) {
					Stab[i] = 1;
				}
			}
		}
	}
}

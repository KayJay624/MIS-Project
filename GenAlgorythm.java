package badania;

import java.io.*;
import java.util.*;

public class GenAlgorythm {
	private double etime;
	private int iterations = 0;
	private int k = 0;
	private int vertNum = 0;
	private int edgeNum = 0;
	public int[][] adjMatrix;
	public Population population;
	
	public void run(int populationQuantity, double stopCon, double mutationProbability) {	
		Date start_time = new Date();
		population = new Population(populationQuantity, vertNum, adjMatrix);
		population.sort();
		
		Random rand = new Random();
		int childNumb = populationQuantity;
			
		int gen = 0;
		while(true) {			
			Chromosome[] childTab = new Chromosome[childNumb];
			for(int i = 0; i < childNumb; i++) {
				int p = rand.nextInt(populationQuantity);
				childTab[i] = population.get(0).crossover(population.get(p));
				childTab[i].mutate(mutationProbability);
				childTab[i].fitness(adjMatrix);
				population.add(childTab[i]);
			}
			population.sort();
			
			for(int i = 0; i < childNumb; i++) {
				population.removeLast();
			}
			
			population.print(gen);
			gen++;
			
			int kk = population.get(0).fit;
			if (kk > k)	{
				k = kk;
				iterations = 0;
			}
			else {
				iterations++;
			}
			if (iterations >= (stopCon * vertNum) || gen >= 10 * vertNum) {
				break;
			}
		}
		Date stop_time = new Date();
		etime = (stop_time.getTime() - start_time.getTime())/1000.;
	}
	
	public void getGraph(String path) throws FileNotFoundException {
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
	}
	
	public void genGraph(int _vertNum, int _edgeNum) {
		vertNum = _vertNum;
		edgeNum = _edgeNum;
		Random rand = new Random();
		adjMatrix = new int[vertNum][vertNum];
		
		int n = 0; 	
		for(int i = 0; i < vertNum; i++) {
			for(int j = 0; j < vertNum; j++) {									
				adjMatrix[i][j] = 0;
			}
		}
		
		if(edgeNum > (vertNum * (vertNum -1))/2) { 
			edgeNum = (vertNum * (vertNum -1))/2;  
		}
			
		while(n < edgeNum) {
			int i = rand.nextInt(vertNum);
			int j = rand.nextInt(vertNum);
				
			if(i == j) {
				adjMatrix[i][j] = 0;
			} 
			else if(adjMatrix[i][j] == 0) {
				adjMatrix[i][j] = 1;
				adjMatrix[j][i] = 1;
					
				n++;
			}	
		}
	}
	
	int getEdgeNum() {
		return edgeNum;
	}
	
	int[][] getAdjMatrix() {
		return adjMatrix;
	}
	
	Population getPopulation() {
		return population;
	}
	
	double getEtime() {
		return etime;
	}
}

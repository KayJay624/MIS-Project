import java.io.*;
import java.util.*;

public class GenAlgorythm {
	private int vertNum = 0;
	private int[][] adjMatrix;
	public void run() throws FileNotFoundException {
		
		this.genGraph(8, 12);
		//this.getGraph("graf.txt");
		
		Population population = new Population(4, vertNum);
		population.adjMatrix = adjMatrix;
		population.sort();
		
		int gen = 0;
		while(gen < 20) {
			Chromosome newChild1 =  population.get(0).crossover(population.get(1));
			newChild1.mutate(0.1);			
			population.add(newChild1);
			
			Chromosome newChild2 =  population.get(1).crossover(population.get(0));
			newChild2.mutate(0.1);			
			population.add(newChild2);
			
			population.sort();
			
			population.removeLast();
			population.removeLast();
			
			population.print();
			
			gen++;
		}
	}
	
	private void getGraph(String path) throws FileNotFoundException {
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
	}
	
	private void genGraph(int verNum, int edgNum) {
		Random rand = new Random();
		adjMatrix = new int[verNum][verNum];
		vertNum = verNum;
		
		int n = 0; 	
		for(int i = 0; i < verNum; i++) {
			for(int j = 0; j < verNum; j++) {									
				adjMatrix[i][j] = 0;
			}
		}
			
		while(n < edgNum) {
			int i = rand.nextInt(verNum);
			int j = rand.nextInt(verNum);
				
			if(i == j) {
				adjMatrix[i][j] = 0;
			} else if(adjMatrix[i][j] == 0) {
				adjMatrix[i][j] = 1;
				adjMatrix[j][i] = 1;
					
				n++;
			}
			
		}
		 
		 System.out.println("Macierz S¹siedztwa: ");
		 for(int i = 0; i < vertNum; i++) {		  
		    	for(int j = 0; j < vertNum; j++) {
					 System.out.print(adjMatrix[i][j] + "  ");
				}
		    	System.out.println();

		 }
	    	System.out.println();
	    	System.out.println();
	}
	
}

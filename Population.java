package badania;

import java.util.*;

public class Population {
	private LinkedList<Chromosome> population = new LinkedList<Chromosome>();
	public int[][] adjMatrix;
	
	Population(int length, int chromNum) {
		for(int i = 0; i < length; i++) {
			Chromosome newChrom = new Chromosome(chromNum);
			newChrom.init();
			
			population.add(newChrom);
		}
	}
	
	public Chromosome get(int i) {
		return population.get(i);
	}
	
	public void add(Chromosome newChrom) {
		population.add(newChrom);
	}

	public void removeLast() {
		population.removeLast();
	}
	
	public void sort() {
		Collections.sort(population, new chromComp());
	}
	
	public void print() {
		for(int j = 0 ; j < population.size(); j++) {
   		  	System.out.print(j+1 + ". " + population.get(j).fitness(adjMatrix) + "  ");
	    	  for(int i = 0; i < population.get(j).size(); i++) {
	    		  System.out.print( population.get(j).get(i) + " ");	  
	    	  }
	    	  System.out.println();
   	  	}
   	  
   	  	System.out.println();
   	  	System.out.println();
	}
	
	private class chromComp implements Comparator<Chromosome>{
		
	    public int compare(Chromosome e1, Chromosome e2) {
	        if( e1.fitness(adjMatrix) < e2.fitness(adjMatrix)) {
	            return 1;
	        } else {
	            return -1;
	        }
	    }
	}
	
	}

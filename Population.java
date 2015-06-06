package badania;

import java.util.*;

public class Population {
	public ArrayList<Chromosome> population = new ArrayList<Chromosome>();
	//public int[][] adjMatrix;
	
	Population(int length, int chromNum) {
		for(int i = 0; i < length; i++) {
			//adjMatrix = GenAlgorythm.adjMatrix; //.clone();
			Chromosome newChrom = new Chromosome(chromNum);
			newChrom.init();
			newChrom.fitness();
			
			population.add(newChrom);
		}
	}
	
	public Chromosome get(int i) {
		return population.get(i);
	}
	
	public Chromosome getLast() {
		return population.get(population.size()-1);
	}
	
	public Chromosome getFirst() {
		return population.get(0);
	}
	
	public void add(Chromosome newChrom) {
		population.add(newChrom);
	}

	public void removeLast() {
		population.remove(population.size()-1);
	}
	
	public void sort() {
		Collections.sort(population, new chromComp());
	}
	
	public Chromosome[] getParentsTurniejowa1() {
		Random rand = new Random();
		Chromosome[] tabParents;
		tabParents = new Chromosome[2];
		int size = this.population.size();
		LinkedList<Chromosome> groupA = new LinkedList<Chromosome>();
		LinkedList<Chromosome> groupB = new LinkedList<Chromosome>();
		for(int i = 0; i < size - 1; i++) {
			if (rand.nextBoolean()) {
				groupA.add(this.get(i));
			}
			else {
				groupB.add(this.get(i));
			}
		}
		Collections.sort(groupA, new chromComp());
		Collections.sort(groupB, new chromComp());
		if (groupA.isEmpty()) {
			tabParents[0] = groupB.get(0);
			tabParents[1] = groupB.get(1);
		}
		else if (groupB.isEmpty()) {
			tabParents[0] = groupA.get(0);
			tabParents[1] = groupA.get(1);
		}
		else {
			tabParents[0] = groupA.get(0);
			tabParents[1] = groupB.get(0);
		}
		return tabParents;
	}
	
	public Chromosome[] getParentsTurniejowa2() {
		int size = this.population.size();
		Random rand = new Random();
		int groupAquantity = 0;
		int groupBquantity = 0;
		int maximalQuantity = size/2;
		Chromosome[] tabParents;
		tabParents = new Chromosome[2];
		LinkedList<Chromosome> groupA = new LinkedList<Chromosome>();
		LinkedList<Chromosome> groupB = new LinkedList<Chromosome>();
		for(int i = 0; i < size - 1; i++) {
			if (groupAquantity == maximalQuantity)
			{
				groupB.add(this.get(i));
				continue;
			}
			else if (groupBquantity == maximalQuantity)
			{
				groupA.add(this.get(i));
				continue;
			}
			else if (rand.nextInt(2) == 0) {
				groupA.add(this.get(i));
				groupAquantity++;
			}
			else {
				groupB.add(this.get(i));
				groupBquantity++;
			}
		}
		Collections.sort(groupA, new chromComp());
		Collections.sort(groupB, new chromComp());
		tabParents[0] = groupA.get(0);
		tabParents[1] = groupB.get(0);
		return tabParents;
	}
	
	public Chromosome[] getParentsNajlepszyPlusLosowy() {
		Chromosome[] tabParents;
		int size = this.population.size();
		this.sort();
		tabParents = new Chromosome[2];
		tabParents[0] = this.get(0); //najlepszy
		Random rand = new Random();
		int randomNum = rand.nextInt(size - 1) + 1;
		tabParents[1] = this.get(randomNum); //losowy
		return tabParents;
	}
	
	public void print(int gen) {
		synchronized(this) {
		String wynik = "Generacja: " + gen + "\n";
		wynik += "nr   fitness   chromosom \n";
		for(int j = 0 ; j < 4; j++) {
   		  	wynik += (j+1 + ".    " + population.get(j).fit + "          ");
	    	  for(int i = 0; i < population.get(j).size(); i++) {
	    		  wynik += ( population.get(j).get(i) + "  ");	  
	    	  }
	    	  wynik+="\n";
   	    }
		//wynik+="\n";
		Window.displayMessage(wynik);
		}
	}
	
	public void print2(int gen) {
		//String wynik = "Generacja: " + gen + "\n";
		System.out.println("nr   fitness   chromosom \n");
		for(int j = 0 ; j < population.size(); j++) {
			System.out.print(j+1 + ".    " + population.get(j).fit + "          ");
	    	  for(int i = 0; i < population.get(j).size(); i++) {
	    		  System.out.print( population.get(j).get(i) + "  ");	  
	    	  }
	    	  System.out.println();
   	    }
		//wynik+="\n\n";
		//Window.displayMessage(wynik);
	}
	
	public synchronized String print3(int gen) {
		String wynik = "Generacja: " + gen + "\n";
		wynik += "nr   fitness   chromosom \n";
		for(int j = 0 ; j < population.size(); j++) {
   		  	wynik += (j + ".    " + population.get(j).fit + "          ");
	    	  for(int i = 0; i < population.get(j).chromosome.length; i++) {
	    		  wynik += ( population.get(j).chromosome[i] + "  ");	  
	    	  }
	    	  wynik+="\n";
   	    }
		wynik+="\n\n";
		//Window.displayMessage(wynik);
		return wynik;
	}
	
	private class chromComp implements Comparator<Chromosome> {
		
	    public int compare(Chromosome e1, Chromosome e2) {
	        if(e1.fit == e2.fit) {
	        	return 0;
	        } else if( e1.fit < e2.fit) {
	            return 1;
	        } else {
	            return -1;
	        }
	    }
	}
}

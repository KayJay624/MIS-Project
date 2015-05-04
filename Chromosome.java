package badania;
import java.util.Random;

public class Chromosome {
	public int[] chromosome;
	private int length = 0;
	public int fit;
	
	Chromosome(int l) {
		chromosome = new int[l];
		this.length = l;
	}
	
	public void init() {
		Random rand = new Random();
		for(int i = 0; i < length; i++) {
			 if(rand.nextBoolean()) {
	    		  chromosome[i] = 1;	    		  
	    	  } else {
	    		  chromosome[i] = 0;
	    	  }
		}
	}

	public Chromosome crossover(Chromosome other) {
		Chromosome newChrom = new Chromosome(length);
		newChrom.chromosome = this.chromosome.clone();
		 
		for(int i = 0; i < this.length; i++) {
			if(i < (this.length / 2)) {
				newChrom.chromosome[i] = other.chromosome[i];				  
			} 
			  
		}
		
		return newChrom;
	}
	
	public void mutate(double probability) {
		Random rand = new Random();
		if(rand.nextDouble() > probability) {
			int a = rand.nextInt(this.length);
			if(chromosome[a] == 1) {
				chromosome[a] = 0;
			} else {
				chromosome[a] = 1;
			}			
		}
	}
	
	public void fitness(int [][] adjMatrix) {
		int sum = chromSum();
		int penalty = length * vectorXvector(vectorXmatrix(this.chromosome, adjMatrix), this.chromosome);
		
		fit = sum - penalty;
	}
	
	public int size() {
		return length;
	}
	
	public int get(int i) {
		return chromosome[i];
	}
	
	private int chromSum() {
		int sum = 0;
		for(int i = 0; i < this.length; i++) {
			sum += this.chromosome[i];
		}
		return sum;
	}
	
	private int[] vectorXmatrix(int[] vec, int[][] mat) {
		 int[] wynik = new int[vec.length];
			 for(int i = 0; i < vec.length; i++) {
				 for(int j = 0; j < i; j++) {
					 int temp = 0;
	                 for (int w = 0; w < mat.length; w++) { //ilosc wierszy tab2
	                	 temp += vec[w] * mat[w][j];
	                 }
	                 wynik[j] = temp;
				 }
			 }
		return wynik;
	 }
	
	private int vectorXvector(int[] vec1, int[] vec2) {
		 int wynik = 0;
		 for(int i = 0; i < vec1.length; i++) {
			 wynik += vec1[i] * vec2[i];
		 }
		 return wynik;
	 }
	

}

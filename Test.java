package badania;

import java.io.*;

public class Test {
	
	public static void displayMenu(int populationQuantity, int maxGen, double mutationProbability)
	{
		System.out.println("1. Wczytaj graf z pliku; 2. Generuj losowy graf; 3. Zmien liczebnosc populacji; 4. Zmien liczbe generacji; 5. Zmien prawdopodobienstwo mutacji; 6. Koniec");
		System.out.println("Obecnie liczebnosc populacji = "+populationQuantity+", liczba generacji = "+maxGen+", prawdopodobienstwo mutacji = "+mutationProbability);
	}
	
    public static String getString() {
		String text = null;
		try {
			InputStreamReader rd = new InputStreamReader(System.in);
			BufferedReader bfr = new BufferedReader(rd);	 
			text = bfr.readLine();
		} catch(IOException e) {e.printStackTrace();}
    	return text;	  
    }

	public static void main(String[] args) throws FileNotFoundException {
		int verNum, edgNum, choice, populationQuantity, maxGen;
		maxGen = 20;
		populationQuantity = 4;
		double mutationProbability = 0.1;
		String path;
		while(true) {
			GenAlgorythm alg = new GenAlgorythm();
			displayMenu(populationQuantity, maxGen, mutationProbability);
			try {
				choice = Integer.parseInt(getString());
			} catch(NumberFormatException e) { continue; }
			
			if(choice == 1) {
				System.out.println("Podaj sciezke:");
				path = getString();
				try {
					alg.run(populationQuantity, maxGen, mutationProbability, path, 0, 0,null,null);
				} catch(FileNotFoundException e) { System.out.println("Nie znaleziono pliku."); }
			}
			
			else if(choice == 2) {
				System.out.println("Podaj liczbe wierzcholkow:");
				try {
					verNum = Integer.parseInt(getString());
				} catch(NumberFormatException e) { continue; }
				System.out.println("Podaj liczbe krawedzi:");
				try {
					edgNum = Integer.parseInt(getString());
				} catch(NumberFormatException e) { continue; }

				if(verNum < 0 || edgNum < 0) {
					continue;
				}

			}
			
			else if(choice == 3) {
				System.out.println("Podaj liczebnosc populacji:");
				try {
					populationQuantity = Integer.parseInt(getString());
				} catch(NumberFormatException e) { continue; }
				if(populationQuantity < 1) {
					populationQuantity = 1;
				}
				continue;
			}
			
			else if(choice == 4) {
				System.out.println("Podaj liczbe generacji:");
				try {
					maxGen = Integer.parseInt(getString());
				} catch(NumberFormatException e) { continue; }
				if(maxGen < 1) {
					maxGen = 1;
				}
				continue;
			}
			
			else if(choice == 5) {
				System.out.println("Podaj nowe prawdopodobienstwo mutacji:");
				try {
					mutationProbability = Double.parseDouble(getString());
				} catch(NumberFormatException e) { continue; }
				if (mutationProbability < 0) {
					mutationProbability = 0;
				}
				else if(mutationProbability > 1) {
					mutationProbability = 1;
				}
				continue;	
			}
			
			else if(choice == 6) {
				System.exit(0);
			}
			
			else {
				continue;
			}
		}
	}

}

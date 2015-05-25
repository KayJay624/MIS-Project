package badania;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public class GenAlgorythm extends SwingWorker<Void, Void> {
	private int iterations = 0;
	private int k = 0;
	private int vertNum = 0;
	private int edgeNum = 0;
	private int populationQuantity = 0;
	private int barValue = 0;
	private double stopCon = 0;
	private double mutationProbability = 0;
	private String crossMethod = null;
	public int[][] adjMatrix;
	public Population population;
	JProgressBar progressBar = null;
	private Date start_time, stop_time;
	public JButton btnUruchom;
	public JTextArea taskOutput;
	public int gen =0;
	public Window window;
	public JFrame frame;
	
	public void setParams(int _populationQuantity, double _stopCon, double _mutationProbability, String _crossMethod, JProgressBar _progressBar) {
		populationQuantity = _populationQuantity;
		stopCon = _stopCon;
		mutationProbability = _mutationProbability;
		crossMethod = _crossMethod;
		progressBar = _progressBar;
	}
	
	
	
	@Override
    public Void doInBackground() {
		int progress = 0;
        //Initialize progress property.
        setProgress(0);
		start_time = new Date();
		
		population = new Population(populationQuantity, vertNum, adjMatrix);
		population.sort();
		
		Random rand = new Random();
		int childNumb = populationQuantity;	
		//int gen = 0;
				
		while(gen <= 5 * vertNum) {	
			
			Chromosome[] childTab = new Chromosome[childNumb];
			synchronized(population) {
			for(int i = 0; i < childNumb; i++) {
				int p = rand.nextInt(populationQuantity-1)+1;
				
				switch (crossMethod) {
					case "Jednopunktowy" : childTab[i] = population.get(0).jednopunktowyCrossover(population.get(p)); 
									       break;
					case "Jednorodny" : childTab[i] = population.get(0).jednorodnyCrossover(population.get(p));
										break;
					default : childTab[i] = population.get(0).wazonyCrossover(population.get(p));
							  break;
				}
				//childTab[i] = population.get(0).wazonyCrossover(population.get(p));
				childTab[i].mutate(mutationProbability);
				childTab[i].fitness(adjMatrix);
				//if(!childTab[i].isSame(population.get(0))) {
					population.add(childTab[i]);
				//}
			}
			population.sort();
			
			for(int i = 0; i < childNumb; i++) {
				if(population.population.size() > populationQuantity)
					population.removeLast();
			}
			}
			//population.print(gen);
			//population.print2(gen);
			gen++;
			firePropertyChange("alg",gen-1,gen);
			
			progress = 100 * gen / (5 * vertNum);
			
		
		
            setProgress(Math.min(progress, 100));
            Thread.yield();
				
		}
		
		stop_time = new Date();
		//Window.displayMessage("Elapsed time: " + this.getEtime() + " s");
		return null;
	}
	
	 @Override
     public void done() {
         
         //btnUruchom.setEnabled(true);
		 frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
         //taskOutput.append(population.print3(gen));
         window.displayGraph(610, 445, getAdjMatrix(), getPopulation(), false);
         taskOutput.append("Done!\n");
         taskOutput.append(String.valueOf( this.getEtime() ));
         Toolkit.getDefaultToolkit().beep();
         btnUruchom.setEnabled(true);
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
		return (stop_time.getTime() - start_time.getTime())/1000.;
	}
}

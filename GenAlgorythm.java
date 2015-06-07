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
	int vertNum = 0;
	private int edgeNum = 0;
	private int populationQuantity = 0;
	private double stopCon = 0;
	private double maxGen = 0;
	private double mutationProbability = 0;
	private int crossMethod = 0;
	public static int[][] adjMatrix;
	public Population population;
	JProgressBar progressBar = null;
	private Date start_time, stop_time;
	public JButton btnUruchom;
	public JTextArea taskOutput;
	public int gen =0;
	public Window window;
	public JFrame frame;
	String selekcja;
	
	public void setParams(int _populationQuantity, double _stopCon, double _maxGen, double _mutationProbability, int _crossMethod, JProgressBar _progressBar, String _selekcja) {
		populationQuantity = _populationQuantity;
		stopCon = _stopCon;
		maxGen = _maxGen;
		mutationProbability = _mutationProbability;
		crossMethod = _crossMethod;
		progressBar = _progressBar;
		selekcja = _selekcja;
	}
	
	private boolean cond() {
		if(stopCon != -1){
			return true;
		} else {
			return gen < maxGen; 
		}
	}
	
    public Void doInBackground() {
		int progress = 0;
		iterations = 0;
        setProgress(0);
		start_time = new Date();		
		population = new Population(populationQuantity, vertNum);
		population.sort();
		
		while(cond()) {				
			synchronized(population) {
				Chromosome child;
				Chromosome[] tabParents;
				if (selekcja.equals("turniejowa 1"))
				{
					tabParents = population.getParentsTurniejowa1();
					population.sort();
				}
				else if (selekcja.equals("turniejowa 2"))
				{
					tabParents = population.getParentsTurniejowa2();
					population.sort();
				}
				
				else if(selekcja.equals("ruletka")) {
					tabParents = population.getParentsKoloRuletki();
					population.sort();
				}
				else {
					tabParents = population.getParentsNajlepszyPlusLosowy();
					//sort niepotrzebny, bo juz jest w getParenstNajlepszyPlusLosowy()
				}
				population.removeLast();
				
				switch (crossMethod) {
					case 0 : 
						child = tabParents[0].jednopunktowyCrossover(tabParents[1]); 
				       break;
					case 1 : 
						child = tabParents[0].jednorodnyCrossover(tabParents[1]);
						break;
					default : 
						child = tabParents[0].wazonyCrossover(tabParents[1]);
					    break;
				}
				child.mutate(mutationProbability);
				child.fitness();
				population.add(child);
			}
			
			gen++;
			firePropertyChange("alg",gen-1,gen);	
			
			try {
				Thread.sleep(0, 1);
			} 
			catch (InterruptedException e) {
					e.printStackTrace();
			}
			if(stopCon != -1) {
				progress = (int) (100 * gen / (15* vertNum));
				setProgress(Math.min(progress, 100));
				int kk = population.get(0).fit;
				if (kk > k)	{
					k = kk;
					iterations = 0;
				}
				else {
					iterations++;
				}
				if (iterations >= (stopCon * vertNum) || gen >= 15*vertNum) {
					break;
				}
				//System.out.println(iterations + "    iter");
			} 
			else {
				progress = (int) (100 * gen / (maxGen));
				setProgress(Math.min(progress, 100));
			}
            Thread.yield();	
		}
		stop_time = new Date();
		return null;
	 }
	
     public void done() {
         
    	 progressBar.setValue(100);
    	 population.print(gen);
		 frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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

package badania;


import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
//import javax.xml.transform.Transformer;


import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

public class GenAlgorythm {
	private int iterations = 0;
	private int k = 0;
	private int vertNum = 0;
	private int[][] adjMatrix;
	public Population population;
	
	public void run(int populationQuantity, double stopCon, double mutationProbability, String path, int verNum, int edgNum, JTextArea pane, JProgressBar pbar) throws FileNotFoundException {
		
		if(path == "") {
			this.genGraph(verNum, edgNum);
			//writeToFile(edgNum);
		}
		else{
			this.getGraph(path);	
		}
		
		//displayAdjMatrix();
		//displayGraph(vertNum, pane);
		//population.adjMatrix = adjMatrix;
		population = new Population(populationQuantity, vertNum, adjMatrix);
		
		population.sort();
		
		
		//pbar.setMinimum(0);
		//pbar.setMaximum(maxGen);
		int gen = 0;
		while(true) {
			Chromosome newChild1 =  population.get(0).crossover(population.get(1));
			newChild1.mutate(mutationProbability);	
			newChild1.fitness(adjMatrix);
			population.add(newChild1);
			
			Chromosome newChild2 =  population.get(1).crossover(population.get(0));
			newChild2.mutate(mutationProbability);
			newChild2.fitness(adjMatrix);
			population.add(newChild2);
			
			population.sort();
			
			population.removeLast();
			population.removeLast();
			
			population.print(pane, gen);
			//population.print2(gen);
			gen++;
			//pbar.setValue(gen);
			//pbar.revalidate();
			//pbar.repaint();
			population.print2(gen);
			int kk = population.get(0).fit;
			if (kk > k)
			{
				k = kk;
				iterations = 0;
			}
			else
			{
				iterations++;
			}
			if (iterations >= (stopCon * vertNum) || gen >= 2 * vertNum)
			{
				break;
			}
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
	
	public void genGraph(int verNum, int edgNum) {
		Random rand = new Random();
		adjMatrix = new int[verNum][verNum];
		vertNum = verNum;
		
		int n = 0; 	
		for(int i = 0; i < verNum; i++) {
			for(int j = 0; j < verNum; j++) {									
				adjMatrix[i][j] = 0;
			}
		}
		
		if(edgNum > (verNum * (verNum -1))/2) { // Zeby nikt nie robil dowcipow typu dwa wierzcho³ki i milion krawedzi
			edgNum = (verNum * (verNum -1))/2;  // Zreszta to i tak konieczne, bo wtedy while moglby trwac wiecznie
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
	}
	
	private void displayAdjMatrix() {
		System.out.println("Macierz sasiedztwa: ");
		for(int i = 0; i < vertNum; i++) {		  
	    	for(int j = 0; j < vertNum; j++) {
				System.out.print(adjMatrix[i][j] + "  ");
			}
	    	System.out.println();
		}
    	System.out.println();
    	System.out.println();
	}
	
	public void displayGraph(int _x, int _y, JPanel panel) //nie trza przekazywac liczby wierzcholkow, bo przeciez jest pole vertNum
	{
		int x, y; //rozmiary okienka
		x = _x;
		y = _y;
		
		Vertexx[] tab = new Vertexx[vertNum];
		Graph<Vertexx, Edgee> g = new SparseMultigraph<Vertexx, Edgee>();
		for(int i = 0; i < vertNum; i++) {
			Vertexx v = new Vertexx(i);
			g.addVertex(v);
			tab[i] = v;
		}
		for(int i = 0; i < vertNum; i++) {
			for(int j = 0; j <= i; j++) {
				if(adjMatrix[i][j] == 1) {
					Edgee e = new Edgee(i, j);
					g.addEdge(e, tab[i], tab[j]);	
				}
			}
		}
		
       Transformer<Vertexx,Paint> vertexColor = new Transformer<Vertexx,Paint>() {
            public Paint transform(Vertexx i) {
            	int[] tab = population.get(0).chromosome.clone();
        		if(tab[i.id] == 1) {
        			return Color.GREEN;
        		} else {
        			return Color.ORANGE;
        		}              
            }
        };
	
        Layout<Vertexx, Edgee> layout = new KKLayout(g);
        layout.setSize(new Dimension(x, y)); 
        BasicVisualizationServer<Vertexx,Edgee> vv = new BasicVisualizationServer<Vertexx,Edgee>(layout);
        vv.setPreferredSize(new Dimension(x, y)); 
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        if (panel == null) {
        	JFrame frame = new JFrame("Graf");
        	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        	frame.getContentPane().add(vv); 
        	frame.pack();
        	frame.setVisible(true);  
            JPanel container = new JPanel();
            container.add(vv);
            JScrollPane jsp = new JScrollPane(container);
            frame.add(jsp);
        }
        else {
            panel.add(vv);
            panel.revalidate();  	
        } 
        

	}

	public void algNormal(int verNum) {
		//this.genGraph(verNum, edgNum);
		try {
			this.getGraph("graf_500-500_.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Vertexx[] tab = new Vertexx[verNum];
		Graph<Vertexx, Edgee> g = new SparseMultigraph<Vertexx, Edgee>();
		Graph<Vertexx, Edgee> S = new SparseMultigraph<Vertexx, Edgee>();
		
		for(int i = 0; i < verNum; i++) {
			Vertexx v = new Vertexx(i);
			g.addVertex(v);
			tab[i] = v;
		}
		for(int i = 0; i < verNum; i++) {
			for(int j = 0; j <= i; j++) {
				if(adjMatrix[i][j] == 1) {
					Edgee e = new Edgee(i, j);
					g.addEdge(e, tab[i], tab[j]);	
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
				 //System.out.println("Dodalem do S wierzcholek:" + lW.get(v));
				 
				 Collection<Edgee> cK = g.getIncidentEdges(lW.get(v));
				 LinkedList<Edgee> lK = new LinkedList<Edgee>(cK);
				 //System.out.println(lK.size());

				 Collection<Vertexx> cS = g.getNeighbors(lW.get(v));
				 LinkedList<Vertexx> lS = new LinkedList<Vertexx>(cS);
				 for(int i = 0; i < lS.size(); i++) {
					 g.removeVertex(lS.get(i));
					 //System.out.println("Usunolem sasiada :" + lS.get(i));
				 }
				 
				 g.removeVertex(lW.get(v));
				 
			 }
			 
			 
		 }
		
		System.out.println(S.getVertexCount());
	}
	
	 private String getDateTime() {
	        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");
	        Date date = new Date();
	        return dateFormat.format(date);
	    }
		
		public void writeToFile(int edgNum) {
			try {
				String fileName = "graf_"+vertNum+"-"+edgNum+"_"+".txt";
				PrintWriter writer = new PrintWriter(fileName, "UTF-8");
				for (int i = 0; i < vertNum; i++)
				{
					writer.write((i+1)+",");
				}
				writer.write("\n");
				for(int i = 0; i < vertNum; i++) {
					for(int j = 0; j <= i; j++) {
						if(adjMatrix[i][j] == 1) {
							writer.write((i+1)+","+(j+1)+"\n");
						}
					}
				}
				writer.close();
			}
			catch(Exception e) {e.printStackTrace();}
		}
	
}

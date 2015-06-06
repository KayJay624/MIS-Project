package badania;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JScrollPane;

import java.awt.Color;

import javax.swing.JProgressBar;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.apache.commons.collections15.Transformer;

import badania.ProgressBarDemo.Task;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;

public class Window implements 
								PropertyChangeListener {
	JFrame frame;
	static JTextArea textArea;
	private JTextField popul;
	private JTextField mutat;
	
	int pop;
	double warSt;
	double maxG;
	double mut;
	private JTextField wierz;
	private JTextField kraw;
	int wie, kra, chooseFileReturnVal;
	String path;
	JRadioButton rdbtnGenerujGraf;
	JRadioButton rdbtnWczytajZPliku;
	
	JRadioButton rdbtnprint1;
	JRadioButton rdbtnprint2;
	
	JPanel panel_1;
	private JProgressBar progressBar;
	
	private GenAlgorythm alg;
	private JButton btnZapisz;
	private JButton btnPowieksz;
	private JButton btnChart; 
	private JScrollPane scrollPane_1;
	private JComboBox<String> comboBox;
	private JButton btnUruchom; 
	
	//zeby latwiej bylo poprawiac wysypane polskie znaki
	private final String s1 = "Macierz s�siedztwa";
	private final String s2 = "Problem maksymalnego zbioru niezale�nego";
	private final String s3 = "Wska� plik";
	private final String s4 = "Nale�y wpisa� liczby.";
	private final String s5 = "Populacja";
	private final String s6 = "L. wierzcho�k�w";
	private final String s7 = "L. kraw�dzi";
	private final String s8 = "Powi�ksz";
	private final String s9 = "Krzy�owanie";
	private final String s10 = "wa�one";

	//---------------------------------------------------------
	private Chart chart = new Chart();
	private JTextField warStp;
	private JTextField maxGen;
	JRadioButton rdbtnWarStp;
	JRadioButton rdbtnMaxGen;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window() {
		initialize();
	}
	
	public void displayGraph(int x, int y, int[][] adjMatrix, Population population, boolean big)
	{	
		int vertNum = adjMatrix.length;
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
		
		Transformer<Vertexx, Paint> edgeStroke = new Transformer<Vertexx, Paint>() {
		    public Paint transform(Vertexx s) {
		    	
		    	new BasicStroke();
		        return Color.GREEN;
		    }
		};
	
       Transformer<Vertexx,Paint> vertexColor = new Transformer<Vertexx,Paint>() {
            public Paint transform(Vertexx i) {
            	//try {
	            	int[] tab = alg.population.get(0).chromosome;
	        		if(tab[i.id] == 1) {
	        			return Color.GREEN;
	        		} else {
	        			return Color.ORANGE;
	        		} 
            	//}
            	//catch (Exception e) {
	           //     return Color.RED;
            	//}
            }
        };
	
        Layout<Vertexx, Edgee> layout = new KKLayout<Vertexx, Edgee>(g);
        layout.setSize(new Dimension(x, y)); 
        BasicVisualizationServer<Vertexx,Edgee> vv = new BasicVisualizationServer<Vertexx,Edgee>(layout);
        vv.setPreferredSize(new Dimension(x, y)); 
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Vertexx>());
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        //vv.getRenderContext().setEdgeDrawPaintTransformer(edgeStroke);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        if (big) {
        	JFrame frame = new JFrame("Graf");
        	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        	frame.getContentPane().add(vv); 
        	frame.pack();
        	frame.setVisible(true);  
            JPanel container = new JPanel();
            container.add(vv);
            JScrollPane jsp = new JScrollPane(container);
            frame.getContentPane().add(jsp);
        }
        else {
            panel_1.add(vv);
            panel_1.revalidate();  	
        } 
	}

	private void displayAdjMatrix(int[][] adjMatrix) {
		System.out.println(s1);
		for(int i = 0; i < adjMatrix.length; i++) {		  
	    	for(int j = 0; j < adjMatrix.length; j++) {
				System.out.print(adjMatrix[i][j] + "  ");
			}
	    	System.out.println();
		}
    	System.out.println();
    	System.out.println();
	}
	
	public static void displayMessage(String s)
	{
		 textArea.append(s+"\n");
		 textArea.repaint();
		 textArea.revalidate();
	}
	
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
		
	public static void writeToFile(int[][] adjMatrix, int edgeNum) {
		try {
			String fileName = "graf_"+adjMatrix.length+"-"+edgeNum+"_"+getDateTime()+".txt";
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			
			for (int i = 0; i < adjMatrix.length; i++) {
				writer.write((i+1)+",");
			}
			writer.write("\n");
			for(int i = 0; i < adjMatrix.length; i++) {
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
	

 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("alg".equals(evt.getPropertyName()))   {
            int generacja = alg.gen;
            chart.series.add(generacja, alg.population.getFirst().fit);
            chart.series2.add(generacja, alg.population.getLast().fit);
            progressBar.setValue(alg.getProgress());
           // textArea.append(String.format(
             //       "Completed %d%% of task.\n", alg.getProgress()));
            //textArea.append(alg.population.print3(generacja));
            if(rdbtnprint1.isSelected()) {
            	alg.population.print(generacja);
            }
            
            if(alg.isDone()&&rdbtnprint2.isSelected()) {
				alg.population.print(alg.gen);
			}
        } 
    }
	
	
	
	private void initialize() {
		
		final JFileChooser fc = new JFileChooser();
		
		frame = new JFrame(s2);
		frame.setBounds(100, 100, 955, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JButton btnWskazPlik = new JButton(s3);
		btnWskazPlik.setBounds(30, 285, 110, 20);
		btnWskazPlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseFileReturnVal = fc.showOpenDialog(frame);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnWskazPlik);
		btnWskazPlik.setEnabled(false);

		progressBar = new JProgressBar(0,100);
		progressBar.setBounds(160, 470, 620, 25);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		frame.getContentPane().add(progressBar);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(5, 500, 929, 155);
		frame.getContentPane().add(scrollPane_1);
		
		textArea = new JTextArea(5,20);
		scrollPane_1.setViewportView(textArea);
		textArea.setEditable(false);
		//textArea.setVisible(false);
		
		panel_1 = new JPanel();
		panel_1.setBounds(160, 10, 620, 455);
		panel_1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		frame.getContentPane().add(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 10, 150, 485);
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		btnUruchom = new JButton("Uruchom");
		btnUruchom.setBounds(5, 440, 140, 40);
		panel.add(btnUruchom);
		btnUruchom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				btnUruchom.setEnabled(false);
		        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		        //Instances of javax.swing.SwingWorker are not reusuable, so
		        //we create new instances as needed.
		        chart = new Chart();
		        alg = new GenAlgorythm();
		        alg.window = Window.this;
		        alg.progressBar = progressBar;
		        alg.frame = frame;
		        alg.btnUruchom = btnUruchom;
		        alg.addPropertyChangeListener(Window.this);
		        alg.taskOutput = textArea;
		       
				try {
					pop = Integer.parseInt(popul.getText());
					if(pop < 1) {
						pop = 1;
					}
					maxG = Double.parseDouble(maxGen.getText());
					warSt = Double.parseDouble(warStp.getText());
					mut = Double.parseDouble(mutat.getText());
					if(mut < 0) {
						mut = 0;
					}
					else if(mut > 1) {
						mut = 1;
					}
					progressBar.setValue(0);
					textArea.setText("");
					if(rdbtnGenerujGraf.isSelected()) {		
						try {
							kra = Integer.parseInt(kraw.getText());
							wie = Integer.parseInt(wierz.getText());
							if(wie < 0) {
								wie = 1;
							}
							alg.genGraph(wie, kra);
						}
						catch(Exception e) {
							displayMessage(s4);
							e.printStackTrace();;
						}
					} 
					else if(rdbtnWczytajZPliku.isSelected()) {
						try {
							if (chooseFileReturnVal == JFileChooser.APPROVE_OPTION) {
					            File file = fc.getSelectedFile();
					            path = file.getPath();
					        } 
							alg.getGraph(path);
						}
						catch(Exception e){
							displayMessage("Problem z plikiem.");
						}
					}
					if(comboBox.getSelectedItem() == "jednopunktowe") {
						if(rdbtnWarStp.isSelected()){
							alg.setParams(pop, warSt, -1, mut, 0, progressBar);
						} else {
							alg.setParams(pop, -1, maxG, mut, 0, progressBar);
						}
						
					} else if(comboBox.getSelectedItem() == "jednorodne") {
						if(rdbtnWarStp.isSelected()){
							alg.setParams(pop, warSt, -1, mut, 0, progressBar);
						} else {
							alg.setParams(pop, -1, maxG, mut, 0, progressBar);
						}
					} else {
						if(rdbtnWarStp.isSelected()){
							alg.setParams(pop, warSt, -1, mut, 0, progressBar);
						} else {
							alg.setParams(pop, -1, maxG, mut, 0, progressBar);
						}
					}
					 alg.execute();
					 
					panel_1.removeAll();
					//progressBar.setValue(100);
					//displayGraph(610, 445, alg.getAdjMatrix(), alg.getPopulation(), false);
					btnPowieksz.setEnabled(true);
					btnZapisz.setEnabled(true);
					btnChart.setEnabled(true);
					panel_1.repaint();
					
				} 
				catch (Exception e) {
					e.printStackTrace();
				} 				
			}
		});
		//btnUruchom.setEnabled(false);
		
		popul = new JTextField();
		popul.setText("5");
		popul.setBounds(5, 20, 100, 20);
		panel.add(popul);
		popul.setColumns(10);
		
		JLabel lblLiczebnoPopulacji = new JLabel(s5);
		lblLiczebnoPopulacji.setBounds(5, 5, 150, 15);
		panel.add(lblLiczebnoPopulacji);
		
		JLabel lblMutacja = new JLabel("Mutacja");
		lblMutacja.setBounds(5, 62, 150, 15);
		panel.add(lblMutacja);
		
		mutat = new JTextField();
		mutat.setText("0.1");
		mutat.setBounds(5, 77, 100, 20);
		panel.add(mutat);
		mutat.setColumns(10);
		
		rdbtnGenerujGraf = new JRadioButton("Generuj graf");
		rdbtnGenerujGraf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnGenerujGraf.isSelected()) {						
					wierz.setEnabled(true);
					kraw.setEnabled(true);
					btnWskazPlik.setEnabled(false);
					btnUruchom.setEnabled(true);
				} 
				else {
					wierz.setEnabled(false);
					kraw.setEnabled(false);
				}
			}
		});
		rdbtnGenerujGraf.setBounds(5, 121, 140, 20);
		panel.add(rdbtnGenerujGraf);
		
		rdbtnWczytajZPliku = new JRadioButton("Wczytaj z pliku");
		rdbtnWczytajZPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnWczytajZPliku.isSelected()) {						
					wierz.setEnabled(false);
					kraw.setEnabled(false);
					btnWskazPlik.setEnabled(true);
					btnUruchom.setEnabled(true);
				} 
				else {		
					btnWskazPlik.setEnabled(false);
				}
			}
		});
		rdbtnWczytajZPliku.setBounds(5, 250, 140, 20);
		panel.add(rdbtnWczytajZPliku);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnGenerujGraf);
	    group.add(rdbtnWczytajZPliku);
		
		JLabel lblIlocWierzchokow = new JLabel(s6);
		lblIlocWierzchokow.setBounds(15, 146, 150, 15);
		panel.add(lblIlocWierzchokow);
		
		wierz = new JTextField();
		wierz.setBounds(15, 161, 90, 20);
		panel.add(wierz);
		wierz.setColumns(10);
		wierz.setEnabled(false);
		
		JLabel lblIloKrawdzi = new JLabel(s7);
		lblIloKrawdzi.setBounds(15, 186, 134, 14);
		panel.add(lblIloKrawdzi);
		
		kraw = new JTextField();
		kraw.setBounds(15, 201, 90, 20);
		panel.add(kraw);
		kraw.setColumns(10);
		kraw.setEnabled(false);
		
		btnPowieksz = new JButton(s8);
		btnPowieksz.setBounds(10, 385, 130, 20);
		panel.add(btnPowieksz);
		btnPowieksz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayGraph(2000, 2000, alg.getAdjMatrix(), alg.getPopulation(), true);
				//chart.display();
			}
		});
		btnPowieksz.setEnabled(false);
		
		btnZapisz = new JButton("Zapisz");
		btnZapisz.setBounds(10, 410, 130, 20);
		panel.add(btnZapisz);
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeToFile(alg.getAdjMatrix(), alg.getEdgeNum());
			}
		});
		btnZapisz.setEnabled(false);	
		
		JLabel lblNewLabel = new JLabel(s9);
		lblNewLabel.setBounds(5, 310, 150, 15);
		panel.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(5, 325, 140, 20);
		comboBox.addItem("jednopunktowe");
		comboBox.addItem("jednorodne");
		comboBox.addItem(s10);
		panel.add(comboBox);
		
		btnChart = new JButton("Wykres");
		btnChart.setBounds(10, 360, 130, 20);
		panel.add(btnChart);
		btnChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chart.display();
			}
		});
		btnChart.setEnabled(false);	
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		panel_2.setBounds(784, 10, 150, 485);
		frame.getContentPane().add(panel_2);
		
		JLabel label = new JLabel("Wy\u015Bwietlanie");
		label.setBounds(5, 5, 150, 15);
		panel_2.add(label);
		
		rdbtnprint1 = new JRadioButton("Wszystkie generacje");
		rdbtnprint1.setBounds(5, 25, 140, 20);
		rdbtnprint1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnprint1.isSelected()) {						
					//rdbtnprint2.setEnabled(false);
					rdbtnprint2.setSelected(false);
				} 
				else {
					//rdbtnprint2.setEnabled(false);
					rdbtnprint2.setSelected(true);
				}
			}
		});
		panel_2.add(rdbtnprint1);
		
		rdbtnprint2 = new JRadioButton("Tylko ostatnia");
		rdbtnprint2.setBounds(5, 47, 140, 20);
		rdbtnprint2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnprint2.isSelected()) {						
					//rdbtnprint1.setEnabled(false);
					rdbtnprint1.setSelected(false);
					
				} 
				else {
					//rdbtnprint1.setEnabled(false);
					rdbtnprint1.setSelected(true);
				}
			}
		});
		panel_2.add(rdbtnprint2);
		rdbtnprint2.setSelected(true);
		
		warStp = new JTextField();
		warStp.setText("5");
		warStp.setColumns(10);
		warStp.setBounds(15, 115, 100, 20);
		panel_2.add(warStp);
		
		rdbtnWarStp = new JRadioButton("Warunek stopu");
		rdbtnWarStp.setBounds(5, 90, 109, 23);
		rdbtnWarStp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnWarStp.isSelected()) {						
					warStp.setEnabled(true);
					maxGen.setEnabled(false);
					rdbtnMaxGen.setSelected(false);
				} 
				else {
					warStp.setEnabled(false);
					maxGen.setEnabled(true);
					rdbtnMaxGen.setSelected(true);
				}
			}
		});
		panel_2.add(rdbtnWarStp);
		rdbtnWarStp.setSelected(true);
		
		
		rdbtnMaxGen = new JRadioButton("Max generacji");
		rdbtnMaxGen.setBounds(5, 142, 109, 23);
		rdbtnMaxGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnMaxGen.isSelected()) {						
					warStp.setEnabled(false);
					maxGen.setEnabled(true);
					rdbtnWarStp.setSelected(false);
				} 
				else {
					warStp.setEnabled(true);
					maxGen.setEnabled(false);
					rdbtnWarStp.setSelected(true);
				}
			}
		});
		panel_2.add(rdbtnMaxGen);
		
		maxGen = new JTextField();
		maxGen.setText("5");
		maxGen.setColumns(10);
		maxGen.setBounds(15, 172, 100, 20);
		panel_2.add(maxGen);
		maxGen.setEnabled(false);
	}
}

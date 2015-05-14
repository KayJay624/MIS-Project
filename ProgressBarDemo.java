package badania;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.util.Date;
import java.util.Random;
 
public class ProgressBarDemo extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {
 
    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;
    private Population population;
    private int gen = 0;
    private double etime;
    
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            Date start_time = new Date();
            GenAlgorythm alg = new GenAlgorythm();
            alg.genGraph(500, 1000);
            int [][] adjMatrix = alg.adjMatrix.clone();
    		population = new Population(5, 500, adjMatrix);
    		population.sort();
    		
    		Random rand = new Random();
    		int populationQuantity = 5;
    		int childNumb = populationQuantity;
    			
    		gen = 0;
    		while(gen < 2000) {			
    			Chromosome[] childTab = new Chromosome[childNumb];
    			for(int i = 0; i < childNumb; i++) {
    				int p = rand.nextInt(populationQuantity-1)+1;
    				childTab[i] = population.get(0).wazonyCrossover(population.get(p));
    				childTab[i].mutate(0.1);
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
    			
    			gen++;
    			progress++;
    			
    			//try {
                    //Thread.sleep(1);
              //  } catch (InterruptedException ignore) {}
    		
    		//population.print(gen);
    		
    	
                setProgress(Math.min(progress/20, 100));
            }
    		Date stop_time = new Date();
    		etime = (stop_time.getTime() - start_time.getTime())/1000.;
    		return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            taskOutput.append("Done!\n");
           // int gen = 100;
            taskOutput.append(population.print3(gen));
            taskOutput.append(String.valueOf(etime));
        }
    }
 
    public ProgressBarDemo() {
        super(new BorderLayout());
 
        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
 
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
 
        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
 
        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(progressBar);
 
        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
    }
 
    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Completed %d%% of task.\n", task.getProgress()));
            //taskOutput.append(population.print3(gen);
        } 
    }
 
 
    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ProgressBarDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new ProgressBarDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
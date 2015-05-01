package badania;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.Color;

import javax.swing.JInternalFrame;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.Box;
import javax.swing.JRadioButton;

public class Window {

	private JFrame frame;
	private JTextArea textArea;
	private JTextField popul;
	private JTextField gener;
	private JTextField mutat;
	
	int pop;
	int gen;
	double mut;
	private JTextField wierz;
	private JTextField kraw;
	int wie;
	int kra;
	JRadioButton rdbtnGenerujGraf;
	JRadioButton rdbtnWczytajZPliku;
	
	JPanel panel_1;
	JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 789, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Uruchom");
		btnNewButton.setBounds(4, 314, 134, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenAlgorythm alg = new GenAlgorythm();
				try {
					pop = Integer.parseInt(popul.getText());
					gen = Integer.parseInt(gener.getText());
					mut = Double.parseDouble(mutat.getText());
					progressBar.setValue(0);
					textArea.setText("");
					if(rdbtnGenerujGraf.isSelected()) {		
						kra = Integer.parseInt(kraw.getText());
						wie = Integer.parseInt(wierz.getText());
						alg.run(pop, gen, mut, "", wie, kra, textArea, progressBar);
						panel_1.removeAll();
						alg.displayGraph(wie, panel_1);
						panel_1.repaint();
					} else if(rdbtnWczytajZPliku.isSelected()) {
						alg.run(pop, gen, mut, "graf.txt", 0, 0, textArea, progressBar);
						panel_1.removeAll();
						alg.displayGraph(8, panel_1);
						panel_1.repaint();
					}
					
					//textPane.setText(alg.population.print());;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);
		
		progressBar = new JProgressBar(0,100);
		progressBar.setBounds(148, 314, 615, 23);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		frame.getContentPane().add(progressBar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(4, 349, 759, 121);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 763, 300);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		popul = new JTextField();
		popul.setText("5");
		popul.setBounds(0, 20, 99, 20);
		panel.add(popul);
		popul.setColumns(10);
		
		JLabel lblLiczebnoPopulacji = new JLabel("Liczebno\u015B\u0107 populacji:");
		lblLiczebnoPopulacji.setBounds(0, 6, 135, 14);
		panel.add(lblLiczebnoPopulacji);
		
		JLabel lblLiczbaGeneracji = new JLabel("Liczba generacji:");
		lblLiczbaGeneracji.setBounds(0, 47, 99, 14);
		panel.add(lblLiczbaGeneracji);
		
		gener = new JTextField();
		gener.setText("20");
		gener.setBounds(0, 61, 99, 20);
		panel.add(gener);
		gener.setColumns(10);
		
		JLabel lblMutacja = new JLabel("Mutacja:");
		lblMutacja.setBounds(0, 88, 86, 14);
		panel.add(lblMutacja);
		
		mutat = new JTextField();
		mutat.setText("0.1");
		mutat.setBounds(0, 102, 99, 20);
		panel.add(mutat);
		mutat.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBounds(110, 0, 650, 300);
		panel.add(panel_1);
		
		rdbtnGenerujGraf = new JRadioButton("Generuj graf");
		rdbtnGenerujGraf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnGenerujGraf.isSelected()) {						
					wierz.setEnabled(true);
					kraw.setEnabled(true);
					rdbtnWczytajZPliku.setEnabled(false);
					//kra = Integer.parseInt(kraw.getText());
					//wie = Integer.parseInt(wierz.getText());
				} else {
					wierz.setEnabled(false);
					kraw.setEnabled(false);
					rdbtnWczytajZPliku.setEnabled(true);
				}
			}
		});
		rdbtnGenerujGraf.setBounds(0, 129, 109, 23);
		panel.add(rdbtnGenerujGraf);
		
		rdbtnWczytajZPliku = new JRadioButton("Wczytaj z pliku");
		rdbtnWczytajZPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnWczytajZPliku.isSelected()) {						
					rdbtnGenerujGraf.setEnabled(false);
				} else {				
					rdbtnGenerujGraf.setEnabled(true);
				}
			}
		});
		rdbtnWczytajZPliku.setBounds(0, 234, 109, 23);
		panel.add(rdbtnWczytajZPliku);
		
		JLabel lblIlocWierzchokow = new JLabel("Ilo\u015Bc wierzcho\u0142kow:");
		lblIlocWierzchokow.setBounds(10, 151, 99, 14);
		panel.add(lblIlocWierzchokow);
		
		wierz = new JTextField();
		wierz.setBounds(10, 165, 86, 20);
		panel.add(wierz);
		wierz.setColumns(10);
		wierz.setEnabled(false);
		
		JLabel lblIloKrawdzi = new JLabel("Ilo\u015B\u0107 kraw\u0119dzi:");
		lblIloKrawdzi.setBounds(10, 193, 99, 14);
		panel.add(lblIloKrawdzi);
		
		kraw = new JTextField();
		kraw.setBounds(10, 207, 86, 20);
		panel.add(kraw);
		kraw.setColumns(10);
		kraw.setEnabled(false);
		
		String t = "Elo ziomeczku df dsf asdf sd f sdf \n dsfsdfsdf fsdafasdffsdafasdfasdf";
		
	}
}

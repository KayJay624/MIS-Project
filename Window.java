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
import javax.swing.text.Caret;

import java.awt.Font;

import javax.swing.ButtonGroup;
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
	double gen;
	double mut;
	private JTextField wierz;
	private JTextField kraw;
	private JTextField sciezka;
	int wie;
	int kra;
	String sciez;
	JRadioButton rdbtnGenerujGraf;
	JRadioButton rdbtnWczytajZPliku;
	
	JPanel panel_1;
	JProgressBar progressBar;
	private JTextField textField;
	
	private GenAlgorythm alg;

	//---------------------------------------------------------
	
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

	public void displayMessage(String s)
	{
		 textArea.append(s+"\n");
		 textArea.repaint();
		 textArea.revalidate();
	}
	
	private void initialize() {
		frame = new JFrame("Problem maksymalnego zbioru niezależnego");
		frame.setBounds(100, 100, 789, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JButton btnPowieksz = new JButton("Powiększ");
		btnPowieksz.setBounds(630, 20, 134, 23);
		btnPowieksz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alg.displayGraph(2000, 2000, null);
			}
		});
		
		final JButton btnZapisz = new JButton("Zapisz graf");
		btnZapisz.setBounds(630, 50, 134, 23);
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alg.writeToFile();
			}
		});
		
		final JButton btnUruchom = new JButton("Uruchom");
		btnUruchom.setBounds(4, 314, 134, 23);
		btnUruchom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//gen to jest warunek stopu, nie zmieniam nazwy zmiennej
				
				alg = new GenAlgorythm();
				try {
					pop = Integer.parseInt(popul.getText());
					if(pop < 1) {
						pop = 1;
					}
					gen = Double.parseDouble(gener.getText());
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
						try{
							kra = Integer.parseInt(kraw.getText());
							wie = Integer.parseInt(wierz.getText());
							if(wie < 0) {
								wie = 1;
							}
							alg.run(pop, gen, mut, "", wie, kra, textArea, progressBar);
						}
						catch(Exception e) {
							displayMessage("Należy wpisać liczby.");
						}
					} else if(rdbtnWczytajZPliku.isSelected()) {
						sciez = sciezka.getText();
						try
						{
							alg.run(pop, gen, mut, sciez, 0, 0, textArea, progressBar);
						}
						catch(Exception e){
							displayMessage("Nie znaleziono pliku.");
						}
					}
					panel_1.removeAll();
					alg.displayGraph(650, 300, panel_1);
					btnPowieksz.setEnabled(true);
					btnZapisz.setEnabled(true);
					panel_1.repaint();
					
				} 
				catch (Exception e) {
					displayMessage("Należy wpisać liczby.");
				} 				
			}
		});
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnUruchom);
		frame.getContentPane().add(btnPowieksz);
		frame.getContentPane().add(btnZapisz);
		btnUruchom.setEnabled(false);
		btnPowieksz.setEnabled(false);
		btnZapisz.setEnabled(false);
		
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
		//textArea.setCaret((Caret) new Font("Arial", Font.PLAIN, 12));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 763, 300);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		popul = new JTextField();
		popul.setText("5");
		popul.setBounds(0, 20, 99, 20);
		panel.add(popul);
		popul.setColumns(10);
		
		JLabel lblLiczebnoPopulacji = new JLabel("Liczebno\u015B\u0107 populacji");
		lblLiczebnoPopulacji.setBounds(0, 6, 172, 14);
		panel.add(lblLiczebnoPopulacji);
		
		JLabel lblLiczbaGeneracji = new JLabel("Warunek stopu");
		lblLiczbaGeneracji.setBounds(0, 47, 144, 14);
		panel.add(lblLiczbaGeneracji);
		
		gener = new JTextField();
		gener.setText("1");
		gener.setBounds(0, 61, 99, 20);
		panel.add(gener);
		gener.setColumns(10);
		
		JLabel lblMutacja = new JLabel("Mutacja");
		lblMutacja.setBounds(0, 88, 172, 14);
		panel.add(lblMutacja);
		
		mutat = new JTextField();
		mutat.setText("0.1");
		mutat.setBounds(0, 102, 99, 20);
		panel.add(mutat);
		mutat.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBounds(162, 0, 598, 300);
		panel.add(panel_1);
		
		rdbtnGenerujGraf = new JRadioButton("Generuj graf");
		rdbtnGenerujGraf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnGenerujGraf.isSelected()) {						
					wierz.setEnabled(true);
					kraw.setEnabled(true);
					sciezka.setEnabled(false);
					btnUruchom.setEnabled(true);
				} else {
					wierz.setEnabled(false);
					kraw.setEnabled(false);
				}
			}
		});
		rdbtnGenerujGraf.setBounds(0, 129, 144, 23);
		panel.add(rdbtnGenerujGraf);
		
		rdbtnWczytajZPliku = new JRadioButton("Wczytaj z pliku");
		rdbtnWczytajZPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnWczytajZPliku.isSelected()) {						
					sciezka.setEnabled(true);
					wierz.setEnabled(false);
					kraw.setEnabled(false);
					btnUruchom.setEnabled(true);
				} else {		
					sciezka.setEnabled(false);
				}
			}
		});
		rdbtnWczytajZPliku.setBounds(0, 249, 144, 23);
		panel.add(rdbtnWczytajZPliku);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnGenerujGraf);
	    group.add(rdbtnWczytajZPliku);
		
		JLabel lblIlocWierzchokow = new JLabel("Liczba wierzchołków");
		lblIlocWierzchokow.setBounds(10, 157, 162, 14);
		panel.add(lblIlocWierzchokow);
		
		wierz = new JTextField();
		wierz.setBounds(20, 172, 86, 20);
		panel.add(wierz);
		wierz.setColumns(10);
		wierz.setEnabled(false);
		
		JLabel lblIloKrawdzi = new JLabel("Liczba krawędzi");
		lblIloKrawdzi.setBounds(10, 204, 134, 14);
		panel.add(lblIloKrawdzi);
		
		kraw = new JTextField();
		kraw.setBounds(20, 221, 86, 20);
		panel.add(kraw);
		kraw.setColumns(10);
		kraw.setEnabled(false);
		
		sciezka = new JTextField();
		sciezka.setBounds(20, 272, 86, 20);
		panel.add(sciezka);
		sciezka.setColumns(10);
		sciezka.setEnabled(false);		
	}
}

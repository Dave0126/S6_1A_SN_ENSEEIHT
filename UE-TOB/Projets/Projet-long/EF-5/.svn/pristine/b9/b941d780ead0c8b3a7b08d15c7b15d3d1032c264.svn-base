package simulation.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import simulation.Demarrage;
import simulation.reseau.Noeud;
import simulation.reseau.TypeNoeud;

import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JTabbedPane;

public class CreerEntreeSortie {

	private JDialog createEntreeSortie;
	private static Windows fenetre;	
	private JTextField nomEntree;	/*Zone où entrer la vitesse de limitation */
	private JTextField frqEntree;
	private JTextField xEntree;	/* Zone où entrer les coordonnées de l'entree */
	private JTextField yEntree;
	private JTextField nomSortie;
	private JTextField xSortie;	/* Zone où entrer les coordonnées de la sortie */
	private JTextField ySortie;
	private JPanel map;



	/**
	 * Create the application.
	 */
	public CreerEntreeSortie(Windows fenetre) {
		initialize(fenetre);
	}
		
	private void initialize(Windows fenetre) {
		createEntreeSortie = new JDialog(fenetre);
		createEntreeSortie.setVisible(true); 
		createEntreeSortie.setLocationRelativeTo(null);
		createEntreeSortie.setSize(400,240);
		Container contenu = createEntreeSortie.getContentPane();
		createEntreeSortie.setVisible(true);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		createEntreeSortie.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelEntree = new JPanel();
		tabbedPane.addTab("Entrée ", null, panelEntree, null);
		
		/* --------------------------------Entrée ------------------------------------------------------------------ */
		//panelEntree.setBorder(BorderFactory.createTitledBorder("Caractéristiques de l'entrée"));
		JPanel pnlNomEntree = new JPanel();
		pnlNomEntree.add(new JLabel("Nom de l'entrée : "));
		nomEntree = new JTextField();
		nomEntree.setColumns(10);
		pnlNomEntree.add(nomEntree);
		panelEntree.add(pnlNomEntree);
		
		/* Entrée les coordonnées de l'entrée */
		JPanel pnlCoordEntree = new JPanel();
		pnlCoordEntree.setBorder(BorderFactory.createTitledBorder("Les Coordonnées"));
		pnlCoordEntree.add(new JLabel("Coordonnées de l'entrée : "));
		pnlCoordEntree.add(new JLabel("x : "));
		xEntree = new JTextField();
		xEntree.setColumns(5);
		pnlCoordEntree.add(xEntree);
		pnlCoordEntree.add(new JLabel("y : "));
		yEntree = new JTextField();
		yEntree.setColumns(5);
		pnlCoordEntree.add(yEntree);
		panelEntree.add(pnlCoordEntree);
		
		/* Fréquence d'entrée */
		JPanel pnlFrqEntree = new JPanel();
		pnlFrqEntree.add(new JLabel("Fréquence d'entrée : "));
		frqEntree = new JTextField();
		frqEntree.setColumns(10);
		pnlFrqEntree.add(frqEntree);
		panelEntree.add(pnlFrqEntree);
		
		/* Bouton OK */
		JPanel okEntree = new JPanel();
		JButton btnOkEntree = new JButton("OK");
		btnOkEntree.addActionListener(this::validerEntree);
		//createEntreeSortie.getContentPane().setLayout(new BorderLayout(0, 0));
		okEntree.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		okEntree.add(btnOkEntree);
		panelEntree.add(okEntree, BorderLayout.SOUTH);
		
		/* Bouton annuler */
		JButton btnAnnulerEntree = new JButton("Annuler");
		okEntree.add(btnAnnulerEntree);
		btnAnnulerEntree.addActionListener(this::annulerEntree);
		panelEntree.add(okEntree, BorderLayout.SOUTH);
		
		
		/* --------------------------------Sortie ------------------------------------------------------------------ */
		JPanel panelSortie = new JPanel();
		tabbedPane.addTab("Sortie", null, panelSortie, null);
		
		//panelEntree.setBorder(BorderFactory.createTitledBorder("Caractéristiques de l'entrée"));
				JPanel pnlNomSortie = new JPanel();
				pnlNomSortie.add(new JLabel("Nom de la sortie : "));
				nomSortie = new JTextField();
				nomSortie.setColumns(10);
				pnlNomSortie.add(nomSortie);
				panelSortie.add(pnlNomSortie);
				
				/* Entrée les coordonnées de l'entrée */
				JPanel pnlCoordSortie = new JPanel();
				pnlCoordSortie.setBorder(BorderFactory.createTitledBorder("Les Coordonnées"));
				pnlCoordSortie.add(new JLabel("Coordonnées de l'entrée : "));
				pnlCoordSortie.add(new JLabel("x : "));
				xSortie = new JTextField();
				xSortie.setColumns(5);
				pnlCoordSortie.add(xSortie);
				pnlCoordSortie.add(new JLabel("y : "));
				ySortie = new JTextField();
				ySortie.setColumns(5);
				pnlCoordSortie.add(ySortie);
				panelSortie.add(pnlCoordSortie);
				
				/* Bouton OK */
				JPanel okSortie = new JPanel();
				JButton btnOkSortie = new JButton("OK");
				btnOkSortie.addActionListener(this::validerSortie);
				//createSortieSortie.getContentPane().setLayout(new BorderLayout(0, 0));
				okSortie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				okSortie.add(btnOkSortie);
				panelSortie.add(okSortie, BorderLayout.SOUTH);
				
				/* Bouton annuler */
				JButton btnAnnulerSortie = new JButton("Annuler");
				okSortie.add(btnAnnulerSortie);
				btnAnnulerSortie.addActionListener(this::annulerSortie);
				panelSortie.add(okSortie, BorderLayout.SOUTH);
				
				map = fenetre.getMap();
	}
	
	private void validerEntree(ActionEvent e) {
		try {
			int x_entree = Integer.parseInt(xEntree.getText());
			int y_entree = Integer.parseInt(yEntree.getText());
			double frequence = Double.parseDouble(frqEntree.getText());
			
			createEntreeSortie.setVisible(false);

			// Création de l'entrée dans le réseau
			String nom_entree = nomEntree.getText();
			Noeud noeudCree = Demarrage.reseau1.creerNoeud(x_entree, y_entree, nom_entree, TypeNoeud.ENTREE_SORTIE, frequence);
			// faudra vérifier si le noeud est bien créé
	
	
			//ImageIcon rond_P = new ImageIcon("D:\\eclipse-workspace\\Projet_Long\\src\\vue\\image0.png");
			//rond_P.setImage(rond_P.getImage().getScaledInstance(80, 80,  Image.SCALE_DEFAULT));
			JLabel labelEntree = new JLabel(nom_entree + "(E)",JLabel.CENTER);
			labelEntree.setBounds(x_entree,y_entree,80, 50);
			labelEntree.setOpaque(true);
			labelEntree.setBackground(Color.green);
			labelEntree.setBorder(new LineBorder(Color.black, 1));
			labelEntree.setVisible(true);
			map.add(labelEntree);
			map.revalidate();
			map.repaint();
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(createEntreeSortie, "Une erreur a été détectée, Veuillez recommencer.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void annulerEntree(ActionEvent e) {
		createEntreeSortie.setVisible(false);	
	}
	private void validerSortie(ActionEvent e) {
		try {
			int x_sortie = Integer.parseInt(xSortie.getText());
			int y_sortie = Integer.parseInt(ySortie.getText());

			createEntreeSortie.setVisible(false);
	
			// Création de la sortie dans le réseau
			String nom_sortie = nomSortie.getText();
			Noeud noeudCree = Demarrage.reseau1.creerNoeud(x_sortie, y_sortie, nom_sortie, TypeNoeud.ENTREE_SORTIE);
			// faudra vérifier si le noeud est bien créé
			
			//ImageIcon rond_P = new ImageIcon("D:\\eclipse-workspace\\Projet_Long\\src\\vue\\image0.png");
			//rond_P.setImage(rond_P.getImage().getScaledInstance(80, 80,  Image.SCALE_DEFAULT));
			JLabel labelSortie = new JLabel(nom_sortie + "(S)",JLabel.CENTER);
			labelSortie.setBounds(x_sortie,y_sortie,80, 50);
			labelSortie.setOpaque(true);
			labelSortie.setBackground(Color.green);
			labelSortie.setBorder(new LineBorder(Color.black, 1));
			labelSortie.setVisible(true);
			map.add(labelSortie);
			map.revalidate();
			map.repaint();
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(createEntreeSortie, "Une erreur a été détectée, Veuillez recommencer.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void annulerSortie(ActionEvent e) {
		createEntreeSortie.setVisible(false);				
	}

		
}
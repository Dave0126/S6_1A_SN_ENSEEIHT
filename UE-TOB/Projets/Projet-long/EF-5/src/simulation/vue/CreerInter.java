package simulation.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simulation.Demarrage;
import simulation.reseau.Noeud;
import simulation.reseau.TypeNoeud;



public class CreerInter {
	
	private JDialog createInter;
	private static Windows fenetre;	// fenêtre principale
	private JTextField x;	/* Zone où entrer les coordonnées du intersection */
	private JTextField y;
	private JPanel map;
	private JTextField nom;


	/**
	 * Create the application.
	 */
	public CreerInter(Windows fenetre) {
		initialize(fenetre);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Windows fenetre) {
		createInter = new JDialog(fenetre);
		createInter.setVisible(true);
		createInter.setLocationRelativeTo(null);
		createInter.setSize(400,200);
		Container contenu = createInter.getContentPane();
		
		JPanel entrerInter = new JPanel();
		entrerInter.setBorder(BorderFactory.createTitledBorder("Caractéristiques de l'intersection"));
		createInter.getContentPane().add(entrerInter, BorderLayout.CENTER);
		
		// Nom de la intersection
		JPanel pnlNom = new JPanel();
		pnlNom.setBorder(BorderFactory.createTitledBorder("Entrez le nom de l'intersection"));
		pnlNom.add(new JLabel("Nom de l'intersection : "));
		nom = new JTextField();
		nom.setColumns(10);
		pnlNom.add(nom);
		entrerInter.add(pnlNom);
		
		/* Entrée les coordonnées de la intersection */
		JPanel pnlCoord = new JPanel();
		pnlCoord.setBorder(BorderFactory.createTitledBorder("Entrez les coordonnées de l'intersection"));
		pnlCoord.add(new JLabel("Coordonnées de l'intersection : "));
		pnlCoord.add(new JLabel("x : "));
		x = new JTextField();
		x.setColumns(5);
		pnlCoord.add(x);
		pnlCoord.add(new JLabel("y : "));
		y = new JTextField();
		y.setColumns(5);
		pnlCoord.add(y);
		entrerInter.add(pnlCoord);
		contenu.add(entrerInter);
		
		// Bouton OK
		JPanel ok = new JPanel();
		ok.setLayout(new FlowLayout());
		JButton btnOk = new JButton("OK");
		ok.add(btnOk);
		btnOk.addActionListener(this::valider);
		
		// Bouton annuler
		JButton btnAnnuler = new JButton("Annuler");
		ok.add(btnAnnuler);
		btnAnnuler.addActionListener(this::annuler);
		contenu.add(ok, BorderLayout.SOUTH);
		
		map = fenetre.getMap();
	}
	
	private void valider(ActionEvent e) {
		createInter.setVisible(false);
		int x_interp = Integer.parseInt(x.getText());
		int y_interp = Integer.parseInt(y.getText());

		// Création de la sortie dans le réseau
		String nom_interp = nom.getText();
		Noeud noeudCree = Demarrage.reseau1.creerNoeud(x_interp, y_interp, nom_interp, TypeNoeud.DEFAULT);
		// faudra vérifier si le noeud est bien créé
		
		ImageIcon iconInter = new ImageIcon("Carrefour.jpg");
		iconInter.setImage(iconInter.getImage().getScaledInstance(80, 80,  Image.SCALE_DEFAULT));
		JLabel lbInter = new JLabel(iconInter);
		lbInter.setBounds(x_interp,y_interp,80, 80);
		lbInter.setVisible(true);
		map.add(lbInter);
		map.revalidate();
		map.repaint();
	}
	
	private void annuler(ActionEvent e) {
		createInter.setVisible(false);				
	}

}

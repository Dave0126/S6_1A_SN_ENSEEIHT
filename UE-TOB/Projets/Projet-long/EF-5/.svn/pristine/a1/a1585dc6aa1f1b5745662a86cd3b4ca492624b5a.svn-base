package simulation.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import simulation.Demarrage;
import simulation.reseau.Axe;
import simulation.reseau.Noeud;

public class CreerRoute {

	private JDialog createRoute;
	private static Windows fenetre;	/* fenêtre principale */
	private JTextField txtNoeud1;	/* Zone où entrer les noms des txtNoeuds */
	private JTextField txtNoeud2;
	private JTextField txtVitesse;	/*Zone où entrer la vitesse de limitation */
	private JTextField txtNbVoie; // zone où entrer le nombre de voie sur un axe
	private JCheckBox doubleSens; // indique si double sens ou nom
	private JTextField txtnom; // zone où entre le nom de la voie
	private JPanel map;

	/**
	 * Create the application.
	 */
	public CreerRoute(Windows fenetre) {
		initialize(fenetre);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Windows fenetre) {
		createRoute = new JDialog(fenetre);
		createRoute.setVisible(true); 
		createRoute.setLocationRelativeTo(null);
		createRoute.setSize(789,253);
		Container contenu = createRoute.getContentPane();
		createRoute.setVisible(true);
				
		/* Bouton OK */
		JPanel ok = new JPanel();
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(this::valider);
		createRoute.getContentPane().setLayout(new BorderLayout(0, 0));
		ok.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		ok.add(btnOk);
		contenu.add(ok, BorderLayout.SOUTH);
		
		/* Bouton annuler */
		JButton btnAnnuler = new JButton("Annuler");
		ok.add(btnAnnuler);
		btnAnnuler.addActionListener(this::annuler);
		contenu.add(ok, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		createRoute.getContentPane().add(panel);
		
		/* Etrer le nom de la voie */
		JPanel entrerNom = new JPanel();
		panel.add(entrerNom);
		entrerNom.setBorder(BorderFactory.createTitledBorder("Nom de l'axe"));
		entrerNom.add(new JLabel("Nom"));
		txtnom = new JTextField();
		txtnom.setColumns(20);
		entrerNom.add(txtnom);

		/* Entrée la vitesse de limitation de la route */
		JPanel entrerVitesse = new JPanel();
		panel.add(entrerVitesse);
		entrerVitesse.setBorder(BorderFactory.createTitledBorder("Vitesse de limitation"));
		entrerVitesse.add(new JLabel("Vitesse maximale autorisée : "));
		txtVitesse = new JTextField();
		txtVitesse.setColumns(8);
		entrerVitesse.add(txtVitesse);
		entrerVitesse.add(new JLabel(" km/h"));
		
		/* Entrée des caractéristiques des voies */
		JPanel entrerVoies = new JPanel();
		panel.add(entrerVoies);
		entrerVoies.setBorder(BorderFactory.createTitledBorder("Caractéristiques des voies"));
		entrerVoies.add(new JLabel("Nombre de voies : "));
		txtNbVoie = new JTextField();
		txtNbVoie.setColumns(10);
		entrerVoies.add(txtNbVoie);
		doubleSens = new JCheckBox("Voie à double sens");
		entrerVoies.add(doubleSens);
		
		/* Entrée des noeuds reliés par la route */
		JPanel entrertxtNoeuds = new JPanel();
		panel.add(entrertxtNoeuds);
		entrertxtNoeuds.setBorder(BorderFactory.createTitledBorder("Noms des noeuds de la route"));
		entrertxtNoeuds.add(new JLabel("Nom du 1er noeud : "));
		txtNoeud1 = new JTextField();
		txtNoeud1.setColumns(8);
		entrertxtNoeuds.add(txtNoeud1);
		entrertxtNoeuds.add(new JLabel("Nom du 2ème noeud : "));
		txtNoeud2 = new JTextField();
		txtNoeud2.setColumns(8);
		entrertxtNoeuds.add(txtNoeud2);
		
		map = fenetre.getMap();
		this.fenetre = fenetre;
	}
	
	private void valider(ActionEvent e) {
		createRoute.setVisible(false);	
		String noeud1 = txtNoeud1.getText();
		String noeud2 = txtNoeud2.getText();
		int vitesse =  Integer.parseInt(txtVitesse.getText());
		int nbVoies = Integer.parseInt(txtNbVoie.getText());
		Boolean sens = doubleSens.isSelected();
		Noeud noeudD = Demarrage.reseau1.getNoeud(noeud1);
		Noeud noeudA = Demarrage.reseau1.getNoeud(noeud2);
		String nomAxe = txtnom.getText();

		//Création de l'axe dans le réseau IL FAUDRA REMPLACER "nom" par le nom qu'on veut donner
		Axe axe1 = Demarrage.reseau1.creerAxe(noeudA, noeudD, nomAxe, vitesse, nbVoies);

		if (sens) {
			Axe axe2 = Demarrage.reseau1.creerAxe(noeudD, noeudA, nomAxe, vitesse, nbVoies);
		}
		
		// Ajouter la ligne représentant la route
		LigneRoute ligne = new LigneRoute((int)Math.round(noeudD.getAbscisse()), (int)Math.round(noeudD.getOrdonnee()),
			(int)Math.round(noeudA.getAbscisse()), (int)Math.round(noeudA.getOrdonnee()));
		GroupLayout gl_panel = new GroupLayout(map);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(66)
					.addComponent(ligne, GroupLayout.PREFERRED_SIZE, 616, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(502, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(ligne, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
		);
		map.setLayout(gl_panel);
		
		
	}
	
	private void annuler(ActionEvent e) {
		createRoute.setVisible(false);				
	}

}
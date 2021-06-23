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

public class CreerRondP {

	private JDialog createRondP;
	private static Windows fenetre;	/* Fenêtre principale */
	private JTextField x;	/* Zone où entrer les coordonnées du rond-point */
	private JTextField y;
	private JPanel map;

	/**
	 * Create the application.
	 */
	public CreerRondP(Windows fenetre) {
		initialize(fenetre);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Windows fenetre) {
		createRondP = new JDialog(fenetre);
		createRondP.setVisible(true);
		createRondP.setLocationRelativeTo(null);
		createRondP.setSize(400,200);
		Container contenu = createRondP.getContentPane();
		
		/* Entrée du nombre de sorties */
		JPanel entrerRondP = new JPanel();
		entrerRondP.setBorder(BorderFactory.createTitledBorder("Caractéristiques du rond-point"));
		JPanel pnlNbSorties = new JPanel();
		pnlNbSorties.add(new JLabel("Nombre de sorties : "));
		JTextField nbSorties = new JTextField();
		nbSorties.setColumns(10);
		pnlNbSorties.add(nbSorties);
		entrerRondP.add(pnlNbSorties);
		
		/* Entrée les coordonnées du rond-point */
		JPanel pnlCoord = new JPanel();
		pnlCoord.add(new JLabel("Coordonnées du rond-point : "));
		pnlCoord.add(new JLabel("x : "));
		x = new JTextField();
		x.setColumns(5);
		pnlCoord.add(x);
		pnlCoord.add(new JLabel("y : "));
		y = new JTextField();
		y.setColumns(5);
		pnlCoord.add(y);
		entrerRondP.add(pnlCoord);
		contenu.add(entrerRondP);
		
		/* Bouton OK */
		JPanel ok = new JPanel();
		ok.setLayout(new FlowLayout());
		JButton btnOk = new JButton("OK");
		ok.add(btnOk);
		btnOk.addActionListener(this::valider);
		
		/* Bouton annuler */
		JButton btnAnnuler = new JButton("Annuler");
		ok.add(btnAnnuler);
		btnAnnuler.addActionListener(this::annuler);
		contenu.add(ok, BorderLayout.SOUTH);
		
		map = fenetre.getMap();
	}
	
	private void valider(ActionEvent e) {
		createRondP.setVisible(false);
		int x_rondp = Integer.parseInt(x.getText());
		int y_rondp = Integer.parseInt(y.getText());
		ImageIcon rond_P = new ImageIcon("Rond_Point.jpg");
		rond_P.setImage(rond_P.getImage().getScaledInstance(80, 80,  Image.SCALE_DEFAULT));
		JLabel label = new JLabel(rond_P);
		label.setBounds(x_rondp,y_rondp,80, 80);
		label.setVisible(true);
		map.add(label);
		map.revalidate();
		map.repaint();
	}
	
	private void annuler(ActionEvent e) {
		createRondP.setVisible(false);				
	}

}

package simulation.vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class LigneRoute extends JPanel {
	
	/* Coordonnées des 2 noeuds reliés par la ligne */
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public LigneRoute(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	/* Dessiner une ligne entre les 2 noeuds définis */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;		
		g2.drawLine(this.x1, this.y1, this.x2, this.y2);
	}

}

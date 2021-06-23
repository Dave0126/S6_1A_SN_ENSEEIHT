package simulation.vue;
import javax.swing.*;
import java.awt.*;

/**
 * élément graphique représentant une source de véhicule
 * @version 1.0
 * @author Ahmed CHEGGAF
 */

public class Source extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Coordonnées et rayon de la source
	 */
	int x1, y1, rayon;
	
	/**
	 * True si le focus est sur la source
	 */
    boolean highlight = false;
    
    /**
     * Fréquence d'apparition des véhicules sur la source
     */
    double frequency;
    
    /**
     * True si la source est orientée horizontalement, False si verticalement
     */
    boolean horizontal;

    /**
     * Créé l'élement graphique représentant la source de véhicules
     * @param xun abscisse de la source
     * @param yun ordonnée de la source
     * @param freq fréquence de la source
     * @param orientation orientation de la source
     */
    public Source(int xun, int yun, double freq, boolean orientation){
        frequency = freq;
        x1 = xun;
        y1 = yun;
        rayon = 30;
        horizontal = orientation;
        this.setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Color myColor = new Color(0, 198, 141);
        g2D.setColor(myColor);
        if (highlight) g2D.setColor(Color.DARK_GRAY);
        if (!horizontal) g2D.fillRect(x1-5, y1-(rayon/2),10, rayon);
        else g2D.fillRect(x1-(rayon/2), y1-5,rayon, 10);

    }

    /**
     * Met le focus sur l'élement
     */
    public void Highlight(){
        highlight = true;
    }
    
    /**
     * Retire le focus sur l'élement
     */
    public void unHighlight(){
        highlight = false;
    }

    /**
     * Modifie la fréquence de la source
     * @param freq nouvelle valeur pour la fréquence
     */
    public void changeFreq(int freq){
        frequency = freq;
    }


}

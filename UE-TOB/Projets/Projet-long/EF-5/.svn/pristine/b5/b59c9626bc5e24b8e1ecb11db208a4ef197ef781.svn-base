package simulation;

import simulation.reseau.Reseau;
import simulation.vue.Windows;

import java.awt.EventQueue;

public class Demarrage {
    public static Reseau reseau1 = new Reseau();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Windows frame = new Windows();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

    /**
     * Initialise le réseau étudié
     * @param nouvReseau réseau étudié
    */
    public static void initialisationReseau(Reseau nouvReseau) {
        Demarrage.reseau1 = nouvReseau;
    }
    
}

package simulation.reseau;

import java.util.TreeSet;

import simulation.vehicules.Vehicule;

/**
 * Représente le réseau, fourni les moyens d'acceder aux éléments du réseau, et d'en créer.
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
public class Voie {

    /** Ensemble des vehicules sur la voie */
    private TreeSet<VehiculeSurVoie> ensVehicule;

    /**
     * Axe sur lequel se trouve la voie
     */
    private Axe axe;

    /**
     * Crée la voie
     */
    public Voie(Axe axe) {
        this.ensVehicule = new TreeSet<VehiculeSurVoie>();
    }
 
    /**
     * Ajoute un vehicule 
     * @param vehicule le vehicule à ajouter
     * @param position la position du vehicule
     */
    public void addVehicule(Vehicule vehicule, double position) {
        this.ensVehicule.add(new VehiculeSurVoie(vehicule, position));
    }

    /**
     * Supprime un vehicule
     * @param vehicule le vehicule à supprimer
     */
    public void removeVehicule(VehiculeSurVoie vehicule) {
        this.ensVehicule.remove(vehicule);
    }

    /**
	 * Met a jour la voie
     * @param t, le temps associe a la maj
	 */
	public void miseAJour(double t) {
        double positionMax = 0;
		for (VehiculeSurVoie vehicule : this.ensVehicule) {
            double deplacement = Double.min(vehicule.getVehicule().getVitesse() * t, positionMax - vehicule.getPosition());
            if (deplacement > 0) { //on ne prend en compte que les deplacements dans la bonne direction
                vehicule.setPosition(vehicule.getPosition() - deplacement);
            }
            positionMax = vehicule.getPosition() - vehicule.getVehicule().getlongueur(); // pas de distance de securite entre voitures ici
            
            // Si le vehicule a atteint la fin de la voie, on le transfert dans le noeud
            if (vehicule.getPosition() <= 0) {
            	this.removeVehicule(vehicule);
            	this.axe.getArrivee().addVehicule(vehicule);
            }
		}
    }

    /**
    * Renvoie TRUE si un véhicule se trouve à une portée temps de la fin
    */
    public boolean vehiculeProche(double temps) {
        //return ensVehicule.first().getPosition() < temps * ensVehicule.first().getVehicule().getVitesse();
        return true;
    }
    /**
    * Renvoie TRUE si un véhicule se trouve à une portée temps de la fin
    */
    public Vehicule getVehiculeProche() {
        return ensVehicule.first().getVehicule();
    }

}

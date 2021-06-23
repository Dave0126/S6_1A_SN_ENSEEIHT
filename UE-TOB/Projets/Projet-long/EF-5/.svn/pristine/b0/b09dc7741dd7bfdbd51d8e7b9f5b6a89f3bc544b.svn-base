package simulation.vehicules;

import simulation.reseau.Axe;
import simulation.reseau.Noeud;

public abstract class Vehicule {

    private double vitesse;
    private double acceleration;
    private double longueur;
    private Itineraire itineraire;

    public Vehicule(double vitesse, double longueur, double acceleration, Noeud depart, Noeud arrivee) {
        this.acceleration = acceleration;
        this.vitesse = vitesse;
        this.itineraire = new Itineraire(depart,arrivee);
    }

    //public void deplacement(Axe axe)

    /**
     * Renvoie la vitese instantannée de la voiture.
     * @return double de la vitesse
     */
    public double getVitesse() {
        return this.vitesse;
    }

    /**
     * Renvoie l'accélération instantannée du véhicule.
     * @return double de l'accélération
     */
    public double getacceleration() {
        return this.acceleration;
    }

    /**
     * Renvoie la longueur du véhicule.
     * @return double
     */
    public double getlongueur() {
        return this.longueur;
    }

    /**
     * Informe du prochain Axe à emprunter.
     * @return Axe
     */
    public Axe getNextItineraire() {
        return this.itineraire.nextAxe();
    }

    /**
     * Retire le prochain axe à emprunter à l'itinéraire.
     */
    public void enleverNextItineraire() {
        this.itineraire.defiler();
    }
}

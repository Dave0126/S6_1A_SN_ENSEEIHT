package simulation.vehicules;

import simulation.reseau.Noeud;

public class Voiture extends Vehicule {

    public Voiture(Noeud depart, Noeud arrivee) {
        super(0,4,0,depart,arrivee);
    }
}

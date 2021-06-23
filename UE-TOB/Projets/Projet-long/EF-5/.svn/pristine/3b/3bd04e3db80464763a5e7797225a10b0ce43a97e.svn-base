package simulation.reseau;

import java.util.ArrayList;

import simulation.vehicules.Vehicule;

/**
 * Chemin reliant 2 noeuds entre eux.
 * Les axes sont orientés
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
public class Axe {

	/** Nom de l'axe */
    private String nom;

    /** Noeud départ */
    private Noeud depart;

    /** Noeud arrivée */
    private Noeud arrivee;
    
    /** Vitesse sur l'axe */
    private int vitesse;

    /** Nombre de voies */
    private int nbVoies;

    private ArrayList<Voie> voies;
    
    /**
     * Crée un axe en fonction de ses extrémités, la vitesse limite de déplacement et son nombre de voie
     * @param depart Noeud du depart de l'axe
     * @param arrivee Noeud à la fin de l'axe
     * @param nom Nom du noeud
     * @param vitesse maximale autorisée sur l'axe
     * @param nbVoies nombre de voie sur l'axe
     */
    Axe(Noeud depart, Noeud arrivee, String nom, int vitesse, int nbVoies) {
		this.depart = depart;
		this.arrivee = arrivee;
        this.nom = nom;
		this.vitesse = vitesse;
        this.nbVoies = nbVoies;
        this.voies = new ArrayList<Voie>();
        for (int i = 0; i < nbVoies; i++) {
            voies.add(i, new Voie(this));
        }
	}

    /**
     * Crée un axe en fonction de ses extrémités, la vitesse limite de déplacement et avec une seule voie par défaut
     * @param depart Noeud du depart de l'axe
     * @param arrivee Noeud à la fin de l'axe
     * @param nom Nom du noeud
     * @param vitesse maximale autorisée sur l'axe
     */
    Axe(Noeud depart, Noeud arrivee, String nom, int vitesse) {
        this(depart, arrivee, nom, vitesse, 1);
    }

    /**
     * Obtient le nom de l'axe
     * @return nom de l'axe
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Obtient la longueur de l'axe
     * @return longueur de l'axe
     */
    public double getlongueur() {
        return Math.sqrt(Math.pow(this.depart.getAbscisse() - this.arrivee.getAbscisse(), 2)
        + Math.pow(this.depart.getOrdonnee() - this.arrivee.getOrdonnee(), 2));
    }

    /**
     * Obtient le noeud d'origine de l'axe
     * @return Début de l'axe
     */
    public Noeud getDepart() {
        return this.depart;
    }

    /**
     * Obtient le noeud d'arrivée de l'axe
     * @return fin de l'axe
     */
    public Noeud getArrivee() {
        return this.arrivee;
    }

    /**
     * Obtient la vitesse maximale autorisée sur l'axe
     * @return vitesse maximale autorisée sur l'axe
     */
    public int getVitesse() {
        return this.vitesse;
    }

    /**
     * Obtient le nombre de voies sur l'axe
     * @return nombre de voies sur l'axe
     */
    public int getNbVoies() {
        return this.nbVoies;
    }

    /**
	 * Met a jour les voies de l'axe
     * @param t, le temps associe a la maj
	 */
	public void miseAJour(double t) {
		for (Voie voie : this.voies) {
			voie.miseAJour(t);
		}
    }
	
	private int derniereVoie = 0;
	
	/**
	 * Ajoute un véhicule à l'une des voies (alterne à chaque fois)
	 * @param vehicule Le véhicule à ajouter
	 */
	void addVehicule(Vehicule vehicule) {
		this.voies.get(derniereVoie).addVehicule(vehicule, this.getlongueur());
		this.derniereVoie++;
		if (this.derniereVoie >= this.voies.size()) this.derniereVoie = 0;
	}

    public ArrayList<Voie> getVoies(){
        return this.voies;
    }

}

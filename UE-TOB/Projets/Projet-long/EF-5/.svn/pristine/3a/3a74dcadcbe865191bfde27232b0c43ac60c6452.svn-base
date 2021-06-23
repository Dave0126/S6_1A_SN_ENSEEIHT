package simulation.reseau;

import java.util.ArrayList;


/**
 * Points reliés par les axes, correspond à tout type d'intersection, ou simplement à un changement notable sur un axe
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
public abstract class Noeud {
	
	/**
	 * Reseau auquel appartient le noeud
	 */
	Reseau reseau;

	/**
	 * Nom du noeud
	 */
	private String nom;
	
	/**
	 * Abscisse du noeud
	 */
	private double abscisse;
	
	/**
	 * Ordonnée du noeud
	 */
	private double ordonnee;
	
	/**
	 * Axes entrants vers le noeud
	 */
	private ArrayList<Axe> axesEntrants;

	/**
	 * Axes sortants du noeud
	 */
	private ArrayList<Axe> axesSortants;

 

	/**
	 * Crée un noeud à partir de ses coordonnées
	 * @param reseau Réseau auquel appartient le noeud
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 */
	Noeud(Reseau reseau, double abscisse, double ordonnee, String nom) {
		this.reseau = reseau;
		this.abscisse = abscisse;
		this.ordonnee = ordonnee;
		this.nom = nom;
		this.axesEntrants = new ArrayList<Axe>();
		this.axesSortants = new ArrayList<Axe>();
	}
	
	/**
	 * Obtient le nom du noeud
	 * @return le nom du noeud
	 */
    public String getNom() {
        return this.nom;
    }

    /**
     * Obtient l'abscisse du noeud
     * @return l'abscisse du noeud
     */
    public double getAbscisse() {
        return this.abscisse;
    }

    /**
     * Obtient l'ordonnée du noeud
     * @return l'ordonnée du noeud
     */
    public double getOrdonnee() {
        return this.ordonnee;
    }
    
    /**
     * Obtient la liste des axes entrants
     * @return la liste des axes entrants
     */
    public ArrayList<Axe> getAxesEntrants() {
    	return this.axesEntrants;
    }
    
    /**
     * Obtient la liste des axes sortants
     * @return la liste des axes sortants
     */
    public ArrayList<Axe> getAxesSortants() {
    	return this.axesSortants;
    }

    /**
     * Ajouter un Axe entrant.
     * @param axeEntrant L'axe à ajouter en sortie
     */
    public void ajouterAxeEntrant(Axe axeEntrant) {
        this.axesEntrants.add(axeEntrant);
    }

    /**
     * Ajouter un Axe sortant.
     * @param axeSortant L'axe à ajouter en entrée
	 */
    public void ajouterAxeSortant(Axe axeSortant) {
        this.axesSortants.add(axeSortant);
    }

	/**
	 * Calculer la distance par rapport à un autre Noeud
	 * @param autre 
	 */
	public double distance(Noeud autre) {
		return Math.sqrt(Math.pow(this.abscisse - autre.getAbscisse(), 2)
		+ Math.pow(this.getOrdonnee() - autre.getOrdonnee(), 2));
	}

	public ArrayList<Noeud> getNoeudVoisins() {
		ArrayList<Noeud> voisins = new ArrayList<Noeud>();
		for (Axe i : this.axesSortants) {
			voisins.add(i.getArrivee());
		}
		return voisins;
	}


	/**
     * Met a jour le noeud
     * @param t, le temps associe a la maj
     */
	void miseAJour(double t) {
		System.out.println("Implementer la maj du noeud");
	}

	/**
	 * Fait entrer un véhicule sur le Noeud
	 * @param vehicule le véhicule à ajouter
	 */
	public abstract void addVehicule(VehiculeSurVoie vehicule);
	public void afficherTout(){}
	public void ajouterAxeEntrant(Axe axeEntrant,Position position, Priorite priorite){}
    public void ajouterAxeSortant(Axe axeSortant, Position positionDansAxe, Priorite prioriteAxe){}

}

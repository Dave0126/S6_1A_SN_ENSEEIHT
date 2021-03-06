package simulation.reseau;

import java.util.ArrayList;
import java.util.Random;

import simulation.vehicules.Vehicule;
import simulation.vehicules.Voiture;


/**
 * Représente le réseau, fourni les moyens d'acceder aux éléments du réseau, et d'en créer.
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
public class Reseau {
    
	/**
	 * Liste des noeuds du réseau
	 */
	private ArrayList<Noeud> noeuds;
	
	/**
	 * Matrice d'adjacence listant les noeuds reliés entre eux ainsi que les axes.
	 */
	private MatriceAdjacence matAdj;
	
	/**
	 * Créé et initialise le réseau.
	 */
	public Reseau() {
		noeuds = new ArrayList<Noeud>();
		matAdj = new MatriceAdjacence();
	}
	
	/**
	 * Créé et joute un noeud au réseau 
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @param type Type de noeud à créer
	 * @return le Noeud nouvellement créé
	 */
	public Noeud creerNoeud(double abscisse, double ordonnee, String nom, TypeNoeud type) {
		Noeud noeud = NoeudFactory.getInstance().creerNoeud(this, abscisse, ordonnee, nom, type);
		this.noeuds.add(noeud);
		return noeud;
	}
	
	/**
	 * Créé et joute un noeud au réseau 
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @param type Type de noeud à créer
	 * @param param Paramètre de création du noeud, si nécessaire
	 * @return le Noeud nouvellement créé
	 */
	public Noeud creerNoeud(double abscisse, double ordonnee, String nom, TypeNoeud type, double param) {
		Noeud noeud = NoeudFactory.getInstance().creerNoeud(this, abscisse, ordonnee, nom, type);
		this.noeuds.add(noeud);
		return noeud;
	}
	
	/**
	 * Créé un axe et l'ajoute au réseau
     * @param depart Noeud du depart de l'axe
     * @param arrivee Noeud à la fin de l'axe
     * @param nom Nom du noeud
     * @param vitesse maximale autorisée sur l'axe
     * @param nbVoies nombre de voie sur l'axe
	 * @return l'Axe nouvellement créé
	 */
	public Axe creerAxe(Noeud depart, Noeud arrivee, String nom, int vitesse, int nbVoies) {
		Axe axe = new Axe(depart, arrivee, nom, vitesse, nbVoies);
		this.matAdj.ajouterAxe(depart, arrivee, axe);
		depart.ajouterAxeSortant(axe);
		arrivee.ajouterAxeEntrant(axe);
		return axe;
	}
	
	/**
	 * Créé un axe et l'ajoute au réseau. Définit le nombre de voie à 1.
     * @param depart Noeud du depart de l'axe
     * @param arrivee Noeud à la fin de l'axe
     * @param nom Nom du noeud
     * @param vitesse maximale autorisée sur l'axe
	 * @return l'Axe nouvellement créé
	 */
	public Axe creerAxe(Noeud depart, Noeud arrivee, String nom, int vitesse) {
		return this.creerAxe(depart, arrivee, nom, vitesse, 1);
	}

	/**
	 * Obtient un axe en fonction de ses noeuds de départ et d'arrivée
	 * @param depart Le Noeud de départ
	 * @param arrivee Le Noeud d'arrivée
	 * @return l'Axe recherché s'il existe, null sinon.
	 */
	public Axe getAxe(Noeud depart, Noeud arrivee) {
		return this.matAdj.getAxe(depart, arrivee);
	}
	
	/**
	 * Obtient un Noeud à partir de son nom
	 * @param nom le Nom du Noeud
	 * @return le Noeud recherché s'il existe, null sinon.
	 */
	public Noeud getNoeud(String nom) {
		Noeud resultat = null;
		for (Noeud i: this.noeuds) {
			if (i.getNom().equals(nom)) {
				resultat = i;
			}
		}
		return resultat;
	}
	/**
	 * Obtient la liste de tous les noeuds du reseau
	 * @return la liste de tous les noeuds
	 */
	public ArrayList<Noeud> getListNoeud() {
		ArrayList<Noeud> tousLesNoeuds = new ArrayList<Noeud>();
		tousLesNoeuds.addAll(this.noeuds);
		return tousLesNoeuds;
	}

		/**
	 * Met a jour l'entiereté du reseau
	 * @param t le temps associe a la maj
	 */
	public void miseAJour(double t) {
		for (Noeud noeud : this.noeuds) {
			noeud.miseAJour(t);
		}
		for (Axe axe : this.matAdj.getAxes()) {
			axe.miseAJour(t);
		}
	}

	/**
	 * Lance la simulation du reseau
	 * @param duree la duree de la simulation
	 * @param pas le pas de la simulation
	 */
	public void simuler(double duree, double pas) {
		for (double temps = 0; temps <= duree; temps = temps + pas) {
			this.miseAJour(pas);
		}
	}
	
    /**
     * Créé un véhicule avec une arrivée aléatoire, pour un noeud de départ défini.
     */
    public Vehicule creerVehiculeArriveeAleatoire(Noeud depart) {
    	int total = this.noeuds.size();
    	Random rdm = new Random();
    	Noeud arrivee = this.noeuds.get(rdm.nextInt(total));
    	// TODO Ajoute uniquement des voitures, à modifier quand d'autres véhicules seront disponibles
    	return new Voiture(depart, arrivee);
    }
}

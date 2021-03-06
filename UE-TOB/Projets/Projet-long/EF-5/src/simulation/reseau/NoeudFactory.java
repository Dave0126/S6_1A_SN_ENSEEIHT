package simulation.reseau;

/**
 * Singleton chargé de créer les objets noeud, en fonction du type voulu.
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
class NoeudFactory {

	private NoeudFactory() {}
	
	private static NoeudFactory INSTANCE;
	
	/**
	 * Obtient l'instance unique de la classe
	 * @return l'instance
	 */
	static NoeudFactory getInstance() {
		if (INSTANCE == null) INSTANCE = new NoeudFactory();
		return INSTANCE;
	}
	
	/**
	 * Créé un noeud en fonction du type fourni
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @param type Type de noeud à créer
	 * @return le Noeud créé
	 */
	Noeud creerNoeud(Reseau reseau, double abscisse, double ordonnee, String nom, TypeNoeud type) {
		if (type == TypeNoeud.ENTREE_SORTIE) return this.creerEntreeSortie(reseau, abscisse, ordonnee, nom, NoeudEnSo.FREQUENCE_PAR_DEFAUT);
		else if (type == TypeNoeud.Intersection) return new Intersection(reseau, abscisse, ordonnee, nom);
		else if (type == TypeNoeud.DEFAULT) return this.creerNoeudDefault(reseau, abscisse, ordonnee, nom);
		else return null;
	}
	
	/**
	 * Créé un noeud en fonction du type fourni
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @param type Type de noeud à créer
	 * @param param Paramètre de création du noeud, si nécessaire
	 * @return le Noeud créé
	 */
	Noeud creerNoeud(Reseau reseau, double abscisse, double ordonnee, String nom, TypeNoeud type, double param) {
		if (type == TypeNoeud.ENTREE_SORTIE) return this.creerEntreeSortie(reseau, abscisse, ordonnee, nom, param);
		else if (type == TypeNoeud.DEFAULT) return this.creerNoeudDefault(reseau, abscisse, ordonnee, nom);
		else return null;
	}

	/**
	 * Créé un noeud classique
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @return le Noeud classique créé
	 */
	private Noeud creerNoeudDefault(Reseau reseau, double abscisse, double ordonnee, String nom) {
		return new NoeudDefault(reseau, abscisse, ordonnee, nom);
	}

	/**
	 * Créé un noeud d'entrée-sortie
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @param frequence Nombre moyen de véhicules entrant sur le noeud à chaque tick (entre 0 et 1) 
	 * @return le Noeud d'entrée-sortie créé
	 */
	private Noeud creerEntreeSortie(Reseau reseau, double abscisse, double ordonnee, String nom, double frequence) {
		return new NoeudEnSo(reseau, abscisse, ordonnee, nom, frequence);
	}
	
}

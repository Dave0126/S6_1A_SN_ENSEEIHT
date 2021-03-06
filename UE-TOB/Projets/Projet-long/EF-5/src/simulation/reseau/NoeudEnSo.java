package simulation.reseau;

import java.util.Random;

/**
 * Représente un noeud d'entrée sortie, servant uniquement à délimiter l'espace traité par la simulation
 * et s'occupant de faire entrer et sortir des véhicules de la simulation
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
public class NoeudEnSo extends Noeud {

	public static final double FREQUENCE_PAR_DEFAUT = 0;
	
	/**
	 * Nombre moyen de véhicules entrant sur le noeud à chaque tick (entre 0 et 1) 
	 */
	private double frequence;
	private Random rdm;

	/**
	 * Crée un noeud à partir de ses coordonnées
	 * @param reseau Réseau auquel appartient le noeud
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 * @param frequence Nombre moyen de véhicules entrant sur le noeud à chaque tick (entre 0 et 1) 
	 */
	NoeudEnSo(Reseau reseau, double abscisse, double ordonnee, String nom, double frequence) {
		super(reseau, abscisse, ordonnee, nom);
		this.frequence = frequence;
		if (this.frequence > 1) this.frequence = 1;
		if (this.frequence < 0) this.frequence = 0;
		this.rdm = new Random();
	}

	@Override
	public void addVehicule(VehiculeSurVoie vehicule) {
		//On ne fait rien, le véhicule sort de la simulation, On ne le prend plus en compte.
	}
	
	@Override
	void miseAJour(double t) {
		super.miseAJour(t);
		if (this.rdm.nextDouble() < this.frequence) {
			Axe axe = this.getAxesSortants().get(this.rdm.nextInt(this.getAxesSortants().size()));
			axe.addVehicule(this.reseau.creerVehiculeArriveeAleatoire(this));
		}
	}
    
}

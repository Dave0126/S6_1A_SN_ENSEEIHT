package allumettes;
/**
 * The class of player.
 * @author DAI Guohao.
 * @version 2.0
 */
public class Joueur {
	/** the name of player. */
	private String nom;
	/** the strategy of player. */
	Strategie strategie;
/**
 * The constructor of player class.
 * @param nom the name of player.
 * @param strategie the strategy of player.
 */
	public Joueur(String nom, Strategie strategie) {
		assert (nom != null);
		assert (strategie != null);
		this.nom = nom;
		this.strategie = strategie;
	}
/**
 * Modify the name of player.
 * @param nom a new name.
 */
	public void setNom(String nom) {
		assert (nom != null);
		this.nom = nom;
	}
/**
 * Get the name of player.
 * @return name of player.
 */
	public String getNom() {
		return this.nom;
	}
/**
 * Modify the strategy of player.
 * @param strategie a new strategy.
 */
	public void setStrategie(Strategie strategie) {
		assert (strategie != null);
		this.strategie = strategie;
	}
/**
 * Get the strategy of player.
 * @return the strategy of player.
 */
	public String getStrategie() {
		return this.strategie.getStrategie();
	}
/**
 * Get the prise from the game.
 * @param jeu current game.
 * @return the number of prise.
 */
	public int getPrise(Jeu jeu) {
		assert (jeu != null);
		return this.getPrise(jeu);
	}
}

package allumettes;
/**
 * the interface of strategy.
 * @author DAI Guohao.
 * @version 1.1
 */
public interface Strategie {
	/** To get the strategy of player.
	 * @return string of strategie. */
	String getStrategie();
	/** The number of matches that play wanna take.
	 * @param jeu1
	 * @return the number of prise
	 * */
	int prise(Jeu jeu1) throws CoupInvalideException;
}

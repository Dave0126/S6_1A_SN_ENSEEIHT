package allumettes;
/**
 * The proxy of class JeuReel.
 * this is inherited from Jeu.
 * @author DAI Guohao
 * @version 2.2
 */
public class JeuProxy implements Jeu {
	/** a object named Jeu form JeuReel. */
	private Jeu jeu;
/**
 * 	To proxy a class reality.
 * @param jeu a class jeu reality.
 */
	public JeuProxy(Jeu jeu) {
		assert (jeu != null);
		this.jeu = jeu;
	}

	@Override
	public int getNombreAllumettes() {
		// TODO Auto-generated method stub
		return this.jeu.getNombreAllumettes();
	}

	@Override
	public void retirer(int nbPrises) throws CoupInvalideException {
		// TODO Auto-generated method stub
		this.jeu.retirer(nbPrises);
	}

}

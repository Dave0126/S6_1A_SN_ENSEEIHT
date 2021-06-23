package allumettes;

/**
 * All of the games' rules.
 * @author DAI Guohao
 * @version 2.4
 */
final public class Arbitre {
	/** Player1 and Player2. */
	private Joueur j1, j2;
	/** flag of confiant. */
	private boolean confiant;
	/** Maximum number of matches. */
	static int MAX_NB_ALLUMTTES = 13;
/**
 * Constructor of Arbitre with confiant.
 * @param j1 play1.
 * @param j2 play2.
 * @param confiant flag of confiant.
 */
	public Arbitre(Joueur j1, Joueur j2, boolean confiant) {
		assert (j1 != null);
		assert (j2 != null);
		this.j1 = j1;
		this.j2 = j2;
		this.confiant = confiant;
	}
	/**
	 * Constructor of Arbitre without confiant.
	 * @param j1 play1
	 * @param j2 play2
	 */
	public Arbitre(Joueur j1, Joueur j2) {
		assert (j1 != null);
		assert (j2 != null);
		this.j1 = j1;
		this.j2 = j2;
	}


	/**
	 * @param jeu1     Current game. -confiant -> JeuReel
	 * the other case -> JeuProxy
	 * @throws CoupInvalideException Son of Exception.
	 */
	public void arbitrer(Jeu jeu1)
			throws CoupInvalideException {
		boolean turnToJ1 = true;
		int prise = 0;
		while (jeu1.getNombreAllumettes() > 0) {
			Joueur currentJ = j1;
			if (!turnToJ1) {
				currentJ = j2;
			}
			System.out.printf("\nNombre d'allumettes restantes : %d\n",
					jeu1.getNombreAllumettes());
			try {
				boolean isOver = false;
				while (!isOver) {
					if (currentJ.getStrategie().equals("humain")) {
						System.out.printf(currentJ.getNom() 
								+ ", combien d'allumettes ? ");
					}
					try {
						prise = currentJ.strategie.prise(jeu1);
						isOver = true;
					} catch (OperationInterditeException g) {
						System.out.println("Vous devez donner un entier.");
						continue;
					} catch (SomeoneCheatsException f) {
						if (!this.confiant) {
							System.out.println("Abandon de la partie car " 
						+ currentJ.getNom() + " triche !");
							Jouer.exit();
						} else {
							if (currentJ.getStrategie().equals("humain")) {
								jeu1.retirer(1);
								System.out.printf("[Une allumette en moins, plus que %d . Chut !]\n", 
										jeu1.getNombreAllumettes());
							} else {
								while (jeu1.getNombreAllumettes() > 2) {
									jeu1.retirer(1);
								}
								System.out.printf("[Allumettes restantes : %d]\n", 
										jeu1.getNombreAllumettes());
								prise = 1;
								break;
							}
						}
					}
				}
				jeu1.retirer(prise);
			} catch (CoupInvalideException e) {
				if (prise > 1) {
					System.out.printf(currentJ.getNom()
							+ " prend %d allumettes.\n", prise);
				} else {
					System.out.printf(currentJ.getNom()
							+ " prend %d allumette.\n", prise);
				}
				System.out.printf("Impossible ! ");
				System.out.println(e.getMessage());
				continue;
			}
			if (prise > 1) {
				System.out.printf(currentJ.getNom() + " prend %d allumettes.\n", prise);
			} else {
				System.out.printf(currentJ.getNom() + " prend %d allumette.\n", prise);
			}
			turnToJ1 = !turnToJ1;
		}
		Joueur winnerJ;
		Joueur loserJ;
		if (!turnToJ1) {
			winnerJ = j2;
			loserJ = j1;
		} else {
			winnerJ = j1;
			loserJ = j2;
		}
		System.out.printf("\n" + loserJ.getNom() + " perd !\n");
		System.out.printf(winnerJ.getNom() + " gagne !\n");
	}
}
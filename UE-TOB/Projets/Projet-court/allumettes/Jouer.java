package allumettes;

import java.util.Scanner;

/** Lance une partie des 13 allumettes en fonction des arguments fournis
 * sur la ligne de commande.
 * @author	DAI Guohao
 * @version	1.1
 */
public class Jouer {
	/** Player1 . */
	private static Joueur j1;
	/** Player2. */
	private static Joueur j2;
	/** The proxy of class Jeu. */
	private static Jeu jeu1;
	/** Maximum number of matches. */
	static int MAX_NB_ALLUMETTES = 13;
	/** Judgment of confiant.
	 *  false -> -confiant ;
	 *  true -> confiant */
	private static boolean confiant = false;

	/** Lancer une partie. En argument sont donnés les deux joueurs sous
	 * la forme nom@stratégie.
	 * @param args la description des deux joueurs
	 * @throws CoupInvalideException
	 */
	public static void main(String[] args) throws CoupInvalideException {
		try {
			verifierNombreArguments(args);
			Scanner sc = new Scanner(System.in);
			//System.out.println("\n\tà faire !\n");
			if (args[0].equals("-confiant")) {
				String[] nameAndStrategie1 = splitNameAndStrategie(args[1]);
				String[] nameAndStrategie2 = splitNameAndStrategie(args[2]);
				if (nameAndStrategie1.length != 2 || nameAndStrategie2.length != 2) {
					throw new ConfigurationException(
							"La forme de joueur n'est pas correcte");
				}
				confiant = true;
				jeu1 = new JeuReel(MAX_NB_ALLUMETTES);
				j1 = createPlayer(nameAndStrategie1, sc);
				j2 = createPlayer(nameAndStrategie2, sc);
			} else {
				String[] nameAndStrategie1 = splitNameAndStrategie(args[0]);
				String[] nameAndStrategie2 = splitNameAndStrategie(args[1]);
				if (nameAndStrategie1.length != 2 || nameAndStrategie1.length != 2) {
					throw new ConfigurationException(
							"La forme de joueur n'est pas correcte");
				}
				confiant = false;
				jeu1 = new JeuProxy(new JeuReel(MAX_NB_ALLUMETTES));
				j1 = createPlayer(nameAndStrategie1, sc);
				j2 = createPlayer(nameAndStrategie2, sc);
			}
			Arbitre arbitre = new Arbitre(j1, j2, confiant);
			arbitre.arbitrer(jeu1);
			sc.close();
		} catch (ConfigurationException e) {
			System.out.println();
			System.out.println("Erreur : " + e.getMessage());
			afficherUsage();
			exit();
		}
	}
	private static void verifierNombreArguments(String[] args) {
		final int nbJoueurs = 2;
		if (args.length < nbJoueurs) {
			throw new ConfigurationException("Trop peu d'arguments : "
					+ args.length);
			}
		if (args.length > nbJoueurs + 1) {
			throw new ConfigurationException("Trop d'arguments : "
					+ args.length);
		}
	}

	/** Afficher des indications sur la manière d'exécuter cette classe. */
	public static void afficherUsage() {
		System.out.println("\n" + "Usage :"
				+ "\n\t" + "java allumettes.Jouer joueur1 joueur2"
				+ "\n\t\t" + "joueur est de la forme nom@stratégie"
				+ "\n\t\t" + "strategie = naif | rapide | expert | humain | tricheur"
				+ "\n"
				+ "\n\t" + "Exemple :"
				+ "\n\t" + "	java allumettes.Jouer Xavier@humain "
					   + "Ordinateur@naif"
				+ "\n"
				);
	}
	/**
	 * The method of exiting program.
	 */
	public static void exit() {
		System.exit(0);
	}
	/**
	 * create a new player's strategy.
	 * @param strategie
	 * @param sc class of scanner
	 * @return a class of strategy
	 */
	public static Strategie getStrategie(String strategie, Scanner sc) {
		if (strategie.equals("rapide")) {
			return new StrategieRapide();
		} else if (strategie.equals("naif")) {
			return new StrategieNaif();
		} else if (strategie.equals("expert")) {
			return new StrategieExpert();
		} else if (strategie.equals("tricheur")) {
			return new StrategieTricheur();
		} else {
			return new StrategieHumain(sc);
		}
	}
	private static boolean isCorrectStrategie(String strategie) {
			return (strategie.equals("rapide") || strategie.equals("naif")
					|| strategie.equals("expert") || strategie.equals("humain")
					|| strategie.equals("tricheur"));
	}
	/**
	 * create a new player.
	 * @param input nameAndStrategie
	 * @param sc
	 * @return a class of Joueur.
	 */
	public static Joueur createPlayer(String[] input, Scanner sc) {
		Joueur currentJ;
		String[] nameAndStrategie = input;
		if (nameAndStrategie[0].equals("")) {
			throw new ConfigurationException("Le nom de joueur est null");
		}
		if (!isCorrectStrategie(nameAndStrategie[1])) {
			throw new ConfigurationException("La stratégie de joueur, '"
					+ nameAndStrategie[1] + "', n'est pas correcte");
		}
		currentJ = new Joueur(nameAndStrategie[0], getStrategie(nameAndStrategie[1], sc));
		return currentJ;
	}
	/**
	 * To separate the args.
	 * @param args
	 * @return nameAndStrategie
	 */
	public static String[] splitNameAndStrategie(String args) {
			if (!args.contains("@")) {
				throw new ConfigurationException(
						"La forme de joueur n'est pas correcte");
			}
		String[] nameAndStrategie = args.split("@");
		return nameAndStrategie;
	}
}

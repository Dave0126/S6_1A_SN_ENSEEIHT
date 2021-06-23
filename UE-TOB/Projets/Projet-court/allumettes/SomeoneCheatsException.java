package allumettes;

public class SomeoneCheatsException extends RuntimeException {
	/** Tp deal with Exceptions about someone's' cheating. */
	public SomeoneCheatsException() {
		super("Vous devez donner un entier.");
	}
}

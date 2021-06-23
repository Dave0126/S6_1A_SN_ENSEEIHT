package allumettes;

public class OperationInterditeException extends RuntimeException {
	/** Tp deal with Exceptions about typing. */
	public OperationInterditeException() {
		super("Vous devez donner un entier.");
	}

}

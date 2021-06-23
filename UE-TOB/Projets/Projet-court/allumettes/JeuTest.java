package allumettes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JeuTest {
	/** JeuReel named jeu. */
	private JeuReel jeu;
	/**
	 * Create a new JeuReel.
	 */
	@Before
	public void setUp() {
		this.jeu = new JeuReel(13);
	}
	/**
	 * Construct a new JeuReel with 0 match.
	 */
	@Test(expected = AssertionError.class)
	public void testConstructor1() {
		new JeuReel(0);
	}
	/**
	 * Construct a new JeuReel with -1 match.
	 */
	@Test(expected = AssertionError.class)
	public void testConstructor2() {
		new JeuReel(-1);
	}
	/**
	 * the tests of retirer.
	 * @throws Exception
	 */
	@Test
	public void testRetirer() throws Exception {
		assertEquals(this.jeu.getNombreAllumettes(), 13);
		this.jeu.retirer(3);
		assertEquals(this.jeu.getNombreAllumettes(), 10);
		this.jeu.retirer(2);
		assertEquals(this.jeu.getNombreAllumettes(), 8);
	}
}

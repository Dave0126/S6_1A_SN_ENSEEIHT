package allumettes;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class JeuProxyTest {
	/** JeuProxy named jeu. */
	private JeuProxy jeu;
	/**
	 * Create a new JeuProxy.
	 */
	@Before
	public void setUp() {
		this.jeu = new JeuProxy(new JeuReel(13));
	}
	/**
	 * Construct a new JeuProxy with 0 match.
	 */
	@Test(expected = AssertionError.class)
	public void testConstructor1() {
		new JeuProxy(new JeuReel(0));
	}
	/**
	 * Construct a new JeuProxy with -1 match.
	 */
	@Test(expected = AssertionError.class)
	public void testConstructor2() {
		new JeuProxy(new JeuReel(-1));
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

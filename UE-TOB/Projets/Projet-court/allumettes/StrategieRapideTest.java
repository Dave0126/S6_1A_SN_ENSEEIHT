package allumettes;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class StrategieRapideTest {
	/** the strategy. */
	private Strategie strategie;
	/**
	 * To create a new strategieRapide.
	 */
	@Before
	public void setUp() {
		this.strategie = new StrategieRapide();
	}
	/**
	 * To check the name of strategy is Rapide?
	 */
	@Test
	public void testGetStrategie() {
		assertEquals(this.strategie.getStrategie(), "rapide");
	}
	/**
	 * To check the StrategieRapide will take
	 * 3 matches in one step?
	 * @throws CoupInvalideException
	 */
	@Test
	public void testPrise() throws CoupInvalideException {
		assertEquals(this.strategie.prise(new JeuReel(13)), 3);
	}
}

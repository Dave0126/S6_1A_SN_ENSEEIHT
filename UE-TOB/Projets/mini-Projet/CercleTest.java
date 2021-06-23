
import static org.junit.Assert.assertEquals;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

public class CercleTest {
		/** Précision pour les comparaisons réelle. */
		public final static double EPSILON = 0.001;

		/** Les points du sujet. */
		private Point A, B, C, D, E, F;

		/** Les cercles du sujet. */
		private Cercle C1, C2, C3, C4, C5, C6, C7, C8, C9;

		@Before public void setUp() {
			// Construire les points
			A = new Point(0, 0);
			B = new Point(0, 4);
			C = new Point(4, 1);
			D = new Point(8, 1);
			E = new Point(8, 2);
			F = new Point(8, 2.5);

			// Construire les cercles
			C1 = new Cercle(A, B);			//E12
			C2 = new Cercle(A, B, Color.green);	//E13
			C3 = Cercle.creerCercle(A, B);		//E14
			C4 = new Cercle(C, D);			//E12
			C5 = new Cercle(C, D, Color.red);	//E13
			C6 = Cercle.creerCercle(C, D);		//E14
			C7 = new Cercle(D, E);			//E12
			C8 = new Cercle(D, E, Color.yellow);	//E13
			C9 = Cercle.creerCercle(D, F);		//E14
			
		}

		/** Vérifier si deux points ont mêmes coordonnées.
		 * @param message le message de failure
		 * @param p1 le premier point
		 * @param p2 le deuxième point
		 */
		static void memesCoordonnees(String message, Point p1, Point p2) {
			assertEquals(message + " (x)", p1.getX(), p2.getX(), EPSILON);
			assertEquals(message + " (y)", p1.getY(), p2.getY(), EPSILON);
		}
	/**
	 * E12:
	 * We will create the new circles as below:
	 * C2 by A(0,0) and B(0,4). Center is (0,2). R = 2.
	 * C5 by C(4,1) and D(8,1). Center is (6,1). R = 2.
	 * C8 by D(8,1) and E(8,2). Center is (8,1.5). R = 0.5.
	 * And set color of the new circle to GREEN.
	 */
	@Test
	public void testerE12_1() {
		memesCoordonnees("E12 : Centre de C1 incorrect", new Point(0, 2), C1.getCentre());
		assertEquals("E12 : Rayon de C1 incorrect", 2, C1.getRayon(), EPSILON);
		assertEquals("E12 : Couleur de C1 incorrect", Color.blue, C1.getCouleur());
	}
	@Test(expected = AssertionError.class)
	public void testerE12_2() {
		this.C1 = new Cercle(null,null);
	}
	@Test(expected = AssertionError.class)
	public void testerE12_3() {
		this.C1 = new Cercle(B, B);
	}
	@Test
	public void testerE12_4() {
		memesCoordonnees("E12 : Centre de C4 incorrect", new Point(6, 1), C4.getCentre());
		assertEquals("E12 : Rayon de C4 incorrect", 2, C4.getRayon(), EPSILON);
		assertEquals("E12 : Couleur de C4 incorrect", Color.blue, C1.getCouleur());
	}
	@Test
	public void testerE12_5() {
		memesCoordonnees("E12 : Centre de C7 incorrect", new Point(8, 1.5), C7.getCentre());
		assertEquals("E12 : Rayon de C7 incorrect", 0.5, C7.getRayon(), EPSILON);
		assertEquals("E12 : Couleur de C7 incorrect", Color.blue, C7.getCouleur());
	}
	/**
	 * E13:
	 * We will create the new circles as below:
	 * C2 by A(0,0) and B(0,4). Center is (0,2). R = 2. GREEN
	 * C5 by C(4,1) and D(8,1). Center is (6,1). R = 2. RED
	 * C8 by D(8,1) and E(8,2). Center is (8,1.5). R = 0.5. YELLOW
	 */
	@Test
	public void testerE13_1() {
		memesCoordonnees("E13 : Centre de C2 incorrect", new Point(0, 2), C2.getCentre());
		assertEquals("E13 : Rayon de C2 incorrect", 2, C2.getRayon(), EPSILON);
		assertEquals("E13 : Couleur de C2 incorrect", Color.green, C2.getCouleur());
	}
	@Test(expected = AssertionError.class)
	public void testerE13_2() {
		this.C2 = new Cercle(A, B, null);
	}
	@Test
	public void testerE13_3() {
		memesCoordonnees("E13 : Centre de C5 incorrect", new Point(6, 1), C5.getCentre());
		assertEquals("E13 : Rayon de C5 incorrect", 2, C5.getRayon(), EPSILON);
		assertEquals("E13 : Couleur de C5 incorrect", Color.red, C5.getCouleur());
	}
	@Test
	public void testerE13_4() {
		memesCoordonnees("E13 : Centre de C8 incorrect", new Point(8, 1.5), C8.getCentre());
		assertEquals("E13 : Rayon de C8 incorrect", 0.5, C8.getRayon(), EPSILON);
		assertEquals("E13 : Couleur de C8 incorrect", Color.yellow, C8.getCouleur());
	}
	/**
	 * E14:
	 * We will create the new circles as below:
	 * C3 by A(0,0) and B(0,4). Center is A. R = 4
	 * C6 by C(4,1) and D(8,1). Center is C. R = 4
	 * C9 by D(8,1) and F(8,2.5). Center is D. R = 1.5
	 * And set color of all the circle to BLUE.
	 */
	@Test
	public void testerE14_1() {
		memesCoordonnees("E14 : Centre de C3 incorrect", A, C3.getCentre());
		assertEquals("E14 : Rayon de C3 incorrect", 4, C3.getRayon(), EPSILON);
		assertEquals("E14 : Couleur de C3 incorrect", Color.blue, C3.getCouleur());
	}
	@Test(expected = AssertionError.class)
	public void testerE14_2() {
		this.C2 = Cercle.creerCercle(E, E);
	}
	@Test
	public void testerE14_3() {
		memesCoordonnees("E14 : Centre de C6 incorrect", C, C6.getCentre());
		assertEquals("E14 : Rayon de C6 incorrect", 4, C6.getRayon(), EPSILON);
		assertEquals("E14 : Couleur de C6 incorrect", Color.blue, C6.getCouleur());
	}
	@Test
	public void testerE14_4() {
		memesCoordonnees("E14 : Centre de C9 incorrect", D, C9.getCentre());
		assertEquals("E14 : Rayon de C9 incorrect", 1.5, C9.getRayon(), EPSILON);
		assertEquals("E14 : Couleur de C9 incorrect", Color.blue, C9.getCouleur());
	}
}

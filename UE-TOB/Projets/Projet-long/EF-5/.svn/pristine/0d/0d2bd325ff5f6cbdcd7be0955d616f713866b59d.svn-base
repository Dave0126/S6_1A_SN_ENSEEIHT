package simulation.reseau;
import org.junit.*;
import static org.junit.Assert.*;

public class TestReseau {

    /**
     *
     */
    private static final String ROUTE = "route";
    private Reseau reseau1;
    private Noeud noeud1;
    private Noeud noeud2;
    private Axe axe1;
    public static final double EPSILON = 1e-6;

    @Before
    public void initialiserReseau() {
        reseau1 = new Reseau();
    }

    @Test
    public void testCreerNoeud() {
        String nom = "Intersection";
        reseau1.creerNoeud(3, 4, nom, TypeNoeud.DEFAULT);
        
        noeud1 = reseau1.getNoeud(nom);
        assertTrue(noeud1 instanceof NoeudDefault);
        assertEquals(nom, noeud1.getNom());
        assertEquals(3, noeud1.getAbscisse(), EPSILON);
        assertEquals(4, noeud1.getOrdonnee(), EPSILON);
    }

    @Test
    public void testCreerAxe() {
        noeud1 = reseau1.creerNoeud(3, 4, "start", TypeNoeud.DEFAULT);
        noeud2 = reseau1.creerNoeud(4, 5, "end", TypeNoeud.DEFAULT);
        axe1 = reseau1.creerAxe(noeud1, noeud2, ROUTE, 70, 1);
        assertEquals(Math.sqrt(2),axe1.getlongueur(), EPSILON);
        assertEquals(ROUTE, axe1.getNom());
        assertEquals(noeud1, axe1.getDepart());
        assertEquals(noeud2, axe1.getArrivee());
        assertEquals(70, axe1.getVitesse());
    }

    @Test
    public void testMatriceAdjacence() {
        Axe axe2;
        noeud1 = reseau1.creerNoeud(3, 4, "start", TypeNoeud.DEFAULT);
        noeud2 = reseau1.creerNoeud(4, 5, "end", TypeNoeud.DEFAULT);
        axe1 = reseau1.creerAxe(noeud1, noeud2, ROUTE, 70, 1);
        axe2 = reseau1.getAxe(noeud1, noeud2);
        assertEquals(Math.sqrt(2),axe2.getlongueur(), EPSILON);
        assertEquals(ROUTE, axe2.getNom());
        assertEquals(noeud1, axe2.getDepart());
        assertEquals(noeud2, axe2.getArrivee());
        assertEquals(70, axe2.getVitesse());
    }
}

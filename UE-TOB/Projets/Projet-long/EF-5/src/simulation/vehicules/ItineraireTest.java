package simulation.vehicules;

import org.junit.*;

import simulation.Demarrage;
import simulation.reseau.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ItineraireTest {

    Reseau reseau;
    Itineraire i06;
    LinkedList<Noeud> I05;
    Noeud n0,n1,n2,n3,n4,n5,n6,n7;
    Axe a01, a02, a13, a24, a34, a36, a45, a46, a67, a75, a10,a20, a31, a42, a43, a54, a64, a76, a57;

    @Before
    public void initialiserReseau() {
        reseau = new Reseau();

         n0 = reseau.creerNoeud( 0.0, 0.0 , "0", TypeNoeud.DEFAULT);
         n1 = reseau.creerNoeud( 2.0, 0.0, "1", TypeNoeud.DEFAULT);
         n2 = reseau.creerNoeud( 0.0, 2.0, "2", TypeNoeud.DEFAULT);
         n3 = reseau.creerNoeud( 2.0, 2.0, "3", TypeNoeud.DEFAULT);
         n4 = reseau.creerNoeud( 1.0, 4.0, "4", TypeNoeud.DEFAULT);
         n5 = reseau.creerNoeud( 0.0, 6.0 , "5", TypeNoeud.DEFAULT);
         n6 = reseau.creerNoeud( 4.0, 4.0, "6", TypeNoeud.DEFAULT);
         n7 = reseau.creerNoeud( 2.0, 6.0, "7", TypeNoeud.DEFAULT);

         a01 = reseau.creerAxe(n0,n1,"01",50,2);
         a36 = reseau.creerAxe(n3,n6, "36",50,2);
         a13 = reseau.creerAxe(n1,n3,"13",50,2);
         a24 = reseau.creerAxe(n2,n4,"24",50,2);
         a34 = reseau.creerAxe(n3,n4,"34",50,2);
         a45 = reseau.creerAxe(n4,n5,"45",50,2);
         a46 = reseau.creerAxe(n4,n6,"46",50,2);
         a67 = reseau.creerAxe(n6,n7,"67",50,2);
         a75 = reseau.creerAxe(n7,n5,"75",50,2);

         /*
         a10 = reseau.creerAxe(n0,n1,"10",50,2);
         a20 = reseau.creerAxe(n0,n2,"20",50,2);
         a31 = reseau.creerAxe(n1,n3,"31",50,2);
         a42 = reseau.creerAxe(n2,n4,"42",50,2);
         a43 = reseau.creerAxe(n3,n4,"43",50,2);
         a54 = reseau.creerAxe(n4,n5,"54",50,2);
         a64 = reseau.creerAxe(n4,n6,"64",50,2);
         a76 = reseau.creerAxe(n6,n7,"76",50,2);
         a57 = reseau.creerAxe(n7,n5,"57",50,2);
        */

        Demarrage.initialisationReseau(reseau);
    }

    @Test
    public void testItiniraire05_1() {
        Itineraire i06 = new Itineraire(n0,n6);
        Axe j = i06.nextAxe();
        i06.defiler();
        assertEquals(j, a01);
        j = i06.nextAxe();
        i06.defiler();
        assertEquals(j, a13);
        j = i06.nextAxe();
        i06.defiler();
        assertEquals(j, a36);
        j = i06.nextAxe();
        i06.defiler();
        assertEquals(j, null);
    }

    @Test
    public void testItiniraire05_2() {
        Itineraire i06 = new Itineraire(n0,n6);
        Axe j = i06.nextAxe();
        i06.defiler();
        assertEquals(j, a01);
        j = i06.nextAxe();
        j = a01;
        assertEquals(i06.nextAxe(), a13);
    }

}

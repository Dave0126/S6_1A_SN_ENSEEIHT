package simulation.reseau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Matrice qui indique quels noeuds sont liés par un axe
 * @version 0.1
 * @author Adrien, Clément, Pierre-Louis
 */
public class MatriceAdjacence {

	/**
	 * HashMap permettant de stocker la liste des noeuds reliés, et l'axe les reliant
	 */
    private HashMap<List<Noeud>,Axe> matriceAdjacence = new HashMap<List<Noeud>,Axe>();
    
    /**
     * Ajout d'un axe à la matrice
     * @param depart noeud de départ de l'axe
     * @param arrivee noeud d'arrivée de l'axe
     * @param axe axe ajouté
     */
    void ajouterAxe(Noeud depart, Noeud arrivee, Axe axe) {
    	List<Noeud> tab = Collections.unmodifiableList(Arrays.asList(depart, arrivee));
    	this.matriceAdjacence.put(tab, axe);
    }

    /**
     * Obtenir l'axe reliant deux points
     * @param depart noeud de départ de l'axe recherché
     * @param arrivee noeud d'arrivée de l'axe recherché
     * @return l'axe recherhé s'il a été trouvé, sinon null
     */
    Axe getAxe(Noeud depart, Noeud arrivee) {
    	List<Noeud> tab = Collections.unmodifiableList(Arrays.asList(depart, arrivee));
        return this.matriceAdjacence.get(tab);
    }
    
    ArrayList<Axe> getAxes() {
    	return new ArrayList<Axe>(this.matriceAdjacence.values());
    }
    
}

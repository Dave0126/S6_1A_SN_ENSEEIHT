package simulation.vehicules;
import java.util.*;

import simulation.Demarrage;
import simulation.reseau.Axe;
import simulation.reseau.Noeud;
import simulation.reseau.Reseau;

/**
 * Itineraire donne par l'algorithme de Dijkstra l'itineraire à suivre en lui donnant les points de départ et d'arrivée.
 * Avant de pouvoir calculer les itinéraires il faut cependant avoir initialisé le réseau avec : initialisationReseau(nouvReseau).
 */
public class Itineraire {


    private static final double INF = Double.POSITIVE_INFINITY;

    Noeud debut;
    Noeud fin;
    LinkedList<Noeud> itineraire;

    public Itineraire(Noeud deb, Noeud arr){
        debut = deb;
        fin = arr;
        dijkstra(deb, arr);
    }

    /**
     * Enlève à l'itinéraire le noeud parcouru
     */
    public void defiler(){
        itineraire.remove(0);
    }

    /**
     * Renvoie le prochain axe à emprunter.
     * @return le prochain axe si il existe, Null si il n'y en a plus
     */
    public Axe nextAxe(){
        if (this.itineraire.size()<2) {
            return null;
        }
        else {
            return Demarrage.reseau1.getAxe(itineraire.get(0), itineraire.get(1));
        }
    }

    /**
     * L'ensemble de noeuds qui constitue le chemin le plus court
     * @return Chemin le plus court pour arrivée vers le destination
     */
    public LinkedList<Noeud> getPath(Map<Noeud, Noeud> predecesseurs) {

        LinkedList<Noeud> chemin = new LinkedList<Noeud>();
        Noeud step = fin;
        if (predecesseurs.get(step) == null) {
            return null;
        }
        chemin.add(step);
        while (predecesseurs.get(step) != null) {
            step = predecesseurs.get(step);
            chemin.add(step);
        }
        Collections.reverse(chemin);
        return chemin;
    }

    /**
     * l'algorithm de dijkstra principale
     * @param start le noeud du debut
     * @param Dest le noeud d'arrivee
     */
    public void dijkstra(Noeud start, Noeud Dest) {
        Set<Noeud> noeudTraite = new HashSet<Noeud>();
        Set<Noeud> noeudPasencoreTraite = new HashSet<Noeud>();
        Map<Noeud, Double> distance = new HashMap<Noeud, Double>();
        Map<Noeud, Noeud> predecesseurs = new HashMap<Noeud, Noeud>();

        init(distance, start);
        noeudPasencoreTraite.add(start);

        //tant qu'on n'a pas tout traité, on traite les noeuds restant
        while (noeudPasencoreTraite.size() > 0) {
            Noeud noeud = getMinimum(noeudPasencoreTraite, distance);
            noeudTraite.add(noeud);
            noeudPasencoreTraite.remove(noeud);

            //on cherche le noeud avec la plus petite distance du noeud a traite

            List<Noeud> adjacentNoeuds = noeud.getNoeudVoisins();
            for (Noeud target : adjacentNoeuds) {
                if (distance.get(target) > distance.get(noeud) + noeud.distance(target)) {
                    distance.put(target, distance.get(noeud) + noeud.distance(target));
                    predecesseurs.put(target, noeud);
                    noeudPasencoreTraite.add(target);
                }
            }
        }
        this.itineraire=getPath(predecesseurs);
    }

    /**
     * Renvoie le noeud le plus proche
     * @param ensembleNoeud
     * @param distance
     * @return
     */
    private Noeud getMinimum(Set<Noeud> ensembleNoeud, Map<Noeud, Double> distance) {
        Noeud minimum = null;
        for (Noeud n : ensembleNoeud) {
            if (minimum == null) {
                minimum = n;
            } else {
                if (distance.get(n) < distance.get(minimum)) {
                    minimum = n;
                }
            }
        }
        return minimum;
    }

    /**
     * Initialise les distances à +inf
     * @param distance tableau des distances
     * @param deb noeud de départ
     */
    private static void init(Map<Noeud, Double> distance, Noeud deb) {
        for (Noeud noeudAInit : Demarrage.reseau1.getListNoeud()) {
            distance.put(noeudAInit, INF);
        }
        distance.put(deb, 0.0);
    }


    
}

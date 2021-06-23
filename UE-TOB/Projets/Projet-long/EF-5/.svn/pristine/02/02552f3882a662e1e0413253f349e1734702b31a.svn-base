package simulation.reseau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import simulation.vehicules.Vehicule;

/**choix de conception rappel 
 * - les voies sont positionnées sur les côtés extérieures en premier (ça paraissait plus imple)
 * - implantation du quadrillage comme une matrice (un peu contre intuitif car quand on va vers le haut
 *   on se retrouve à descendre dans les indices des lignes à modifier éventuellement mais pas nécessaire)
 */

/**
 * Représente une intersection (avec max 3 voies sur un axe ou du moins on ne peut gérer le déplacement que de 3 voies en même temps)
 * Une fois l'intersection créée, il faut ajouter les axes entrants et sortants
 * @version 0.1
 * @author Sylvain
 */

public class Intersection extends Noeud { // à extends

    private Map<Axe,Position> axe2Position;
    private Map<Axe,Priorite> axe2Priorite;
	private boolean intersectionInitialisee;

    private Quadrilage quadrillageIntersection;

	/**
	 * Crée un noeud à partir de ses coordonnées
	 * @param reseau Réseau auquel appartient le noeud
	 * @param abscisse Abscisse du noeud
	 * @param ordonnee Ordonnée du noeud
	 * @param nom Nom du noeud
	 */
	Intersection(Reseau reseau, double abscisse, double ordonnee, String nom) {
		super(reseau, abscisse, ordonnee, nom);
        this.quadrillageIntersection = new Quadrilage();
        this.intersectionInitialisee=false;
        this.axe2Position = new HashMap<>();
        this.axe2Priorite = new HashMap<>();
	}


    public void deplacementIntersection(double temps) {
        ArrayList<Trajectoire> listeTrajectoire = new ArrayList<>();
        //pour chaque axe en entrees
        for (Axe unAxeEntrant : super.getAxesEntrants()) {
            //pour chaque voie de l'axe
            for (Voie voie : unAxeEntrant.getVoies()) {
                //si un véhicule peut s'engager on enregistre sa trajectoire dans l'intersection
                if (voie.vehiculeProche(temps)) {
                    listeTrajectoire.add(calculTrajectoire(voie.getVehiculeProche(),unAxeEntrant));
                }
            }
        }
        //ajout des trajectoires des véhicules déjà dans l'intersection)
        listeTrajectoire.addAll(quadrillageIntersection.getTrajectoiresEngagees());
        //gestion des conflits entre trajectoires et priorités
        ArrayList<Trajectoire> listeConflit;
        //pour chaque trajectoire
        for (Trajectoire trajectoire : listeTrajectoire) {
            //on regarde si elle a des conflits
            listeConflit = trajectoire.conflit(listeTrajectoire);
            //si il y a conflit, pour chaque conflit
            while (!listeConflit.isEmpty()) {
                //si elle n'est pas prioritaire
                if (trajectoire.pasPrioritaire(listeConflit.get(0))) {
                    //on l'enlève des trajectoires possibles
                    listeTrajectoire.remove(trajectoire);
                    listeConflit.clear();
                }
                //si prioritaire on l'enlève des conflits à vérifier
                else {listeConflit.remove(0);}
            }
        }
        //déplacement des véhicules prioritaires sur le quadrillage puis des nouveax dans listeTrajectoire
        quadrillageIntersection.deplacer(listeTrajectoire);
    }

    /**
     * @param vehicule sur voie
     * @param axe l'axe dont vient le véhicule
     * @return la Trajectoire que suit le
     */
    Trajectoire calculTrajectoire(Vehicule vehicule, Axe axe) {
        Axe axeCible = vehicule.getNextItineraire();
        int diff=axe2Position.get(axe).ordinal() - axe2Position.get(axeCible).ordinal();
        //cf propriété avec ces valeurs pour les positions
        boolean tourneAGauche = diff==-1 | diff==3;
        boolean tourneADroite = diff==1 | diff==-3;
        Trajectoire nouvelleTraj = new Trajectoire(axe2Priorite.get(axe), vehicule, tourneAGauche, axe2Position.get(axe));
        Case CaseAlignement = quadrillageIntersection.getCaseAlignement(axe,axeCible,tourneADroite,tourneAGauche);
        /* désormais on a la position du point à partir duquel on doit rouner */
        Case caseVehicule = new Case(axe, axeCible,tourneADroite,tourneAGauche);
        //un véhicule occupe les 2 cases quand il se déplace d'une case à l'autre
        int tick=0;
        int tousLes2=0;
        Position direction;
        switch (axe2Position.get(axe)){
            case Nord:direction=Position.Sud;break;
            case Sud:direction=Position.Nord;break;
            case Est:direction=Position.Ouest;break;
            case Ouest:direction=Position.Est;break;
            default:direction=Position.Nord;
        }
        while ( (caseVehicule.dansLimite()) && !CaseAlignement.isEqual(caseVehicule)) {
            nouvelleTraj.put(caseVehicule, tick);
            tousLes2+=1;
            tick+=tousLes2%2;
            caseVehicule.avancer(direction);
        }
        while (caseVehicule.dansLimite()) {
            tousLes2+=1;
            tick+=tousLes2%2;
            caseVehicule.avancer(axe2Position.get(axeCible));
            nouvelleTraj.put(caseVehicule, tick);
        }
        return nouvelleTraj;
    }
    @Override
    void miseAJour(double t) {
        if (!this.intersectionInitialisee){
            this.quadrillageIntersection.initQuadrillage();
        }
        this.deplacementIntersection(t);
    }

    private class Trajectoire {
        //vehicule associé à la trajectoire
        private Vehicule vehicule;
        //position de l'axe de départ dans l'intersection
        private Position positionDepart;
        private Priorite priorite;
        private Map<Case, Integer> trajectoirevehicule;

        Trajectoire(Priorite nvPriorite, Vehicule nvVehicule, boolean tourneGauche, Position axeDeDepart) {
            this.trajectoirevehicule = new HashMap<>();
            this.priorite = nvPriorite;
            this.vehicule = nvVehicule;
            this.positionDepart = axeDeDepart;
        }
        /**
         * Permet d'évaluer si une
         * @param listeTrajectoire
         * @return True si une trajectoire de la liste est plus prioritaire
         */
        boolean pasPrioritaire(Trajectoire trajectoireAVerifier) {
            if (trajectoireAVerifier.priorite.ordinal()>this.priorite.ordinal()) {
                return true;
            }
            else if (trajectoireAVerifier.priorite.ordinal()==this.priorite.ordinal()) {
                int posRelative;
                posRelative=this.positionDepart.ordinal()-positionDepart.ordinal();
                return posRelative==1 || posRelative == -3;
            }
            else return false;
        }
        
        /**
         * Permet dévaluer des conflits entre une trajectoire et une liste de trajectoire
         * @param listeTrajectoire
         * @return Renvoie la liste des trafectoires avec laquelle elle est en conflit, la liste est vide si il n'est en conflit avec personne
         */
        ArrayList<Trajectoire> conflit(ArrayList<Trajectoire> listeTrajectoire) {
            ArrayList<Trajectoire> listeARetourner = new ArrayList<>();
            for (Case uneCase : this.keySet()) {
                for (Trajectoire trajectoire : listeARetourner) {
                    if (trajectoire.containsKey(uneCase)) {
                        if (this.trajectoirevehicule.get(uneCase) == trajectoire.get(uneCase)){
                            listeARetourner.add(trajectoire);
                        }
                    }
                }
            }
            return listeARetourner;
        }
        void soustraireTick() {
            for (Case case1 : trajectoirevehicule.keySet()) {
                this.trajectoirevehicule.replace(case1, trajectoirevehicule.get(case1)-1);
            }
        }
        void enlever0() {
            for (Case case1 : trajectoirevehicule.keySet()) {
                if (trajectoirevehicule.get(case1)==0){
                    trajectoirevehicule.remove(case1);
                }
            }
        }
        /* fonction qui permettent d'étendre les méthodes utiles de Hashmap */
        void put(Case nouvelleCase, Integer tick) { this.trajectoirevehicule.put(nouvelleCase, tick);}
        Vehicule getVehicule() {return this.vehicule;}
        Set<Case> keySet() {return this.trajectoirevehicule.keySet();}
        Integer get(Case uneCase) {return this.trajectoirevehicule.get(uneCase);}
        boolean containsKey(Case uneCase) {return this.containsKey(uneCase);}
    }

    private class Case {
        private int x;
        private int y;
        public int getX() {return x;}
        public int getY() {return y;}
        boolean isEqual(Case autreCase) {
            return (this.x == autreCase.getX()) && (this.y == autreCase.getY());
        }
        /**
         * cré la case au bon endroit pour pouvoir s'engager dans l'intersection
         * (si quelqu'un va à droite il sera vers la case de droite)
         * @param axe
         * @param axeCible
         * @param tourneADroite
         * @param tourneAGauche
         */
        Case (Axe axe, Axe axeCible, boolean tourneADroite, boolean tourneAGauche) {
            Position origine = axe2Position.get(axe);
            int nbVoieDepart = axe.getNbVoies();
            int positiondansvoie;
            if (nbVoieDepart>1) {
                if (tourneADroite) {
                    positiondansvoie=nbVoieDepart-1;
                } else if (tourneAGauche) {
                    positiondansvoie=0;
                } else {
                    /*divisé par 2 puis arrondi au supérieur (plus suscpetible d'aller tout droit à partir de la voie de droite)
                    logique du point de vue route étant donnée que quand on tourne à droite on est tjrs prioritaire*/
                    positiondansvoie=nbVoieDepart-nbVoieDepart/2;
                }
            }
            else {positiondansvoie=0;}
            //maintenant on a une place par rapport au premier
            switch (origine) {
                case Nord:
                this.y=nbVoieDepart-positiondansvoie;
                this.x=0;
                break;
                case Sud:
                this.y=quadrillageIntersection.nbColonne - nbVoieDepart + positiondansvoie;
                this.x=quadrillageIntersection.nbLigne-1;
                break;
                case Est:
                this.x=nbVoieDepart-positiondansvoie;
                this.y=quadrillageIntersection.nbColonne-1;
                break;
                case Ouest:
                this.y=0;
                this.x=quadrillageIntersection.nbLigne - nbVoieDepart + positiondansvoie;
                break;
            }
        }

        boolean dansLimite() {
            boolean bonX=(this.x<quadrillageIntersection.nbLigne) & (this.x>=0);
            boolean bonY=(this.y<quadrillageIntersection.nbColonne) & (this.y>=0);
            return bonX & bonY;
        }
        void avancer(Position direction) {
            int vecteurX=0;
            int vecteurY=0;
            switch (direction) {
                case Nord:vecteurX=-1;vecteurX=-1;break;
                case Sud:vecteurX=0;vecteurX=1;break;
                case Est:vecteurX=1;vecteurX=0;break;
                case Ouest:vecteurX=-1;vecteurX=0;break;
            }
            this.x+=vecteurX;
            this.y+=vecteurY;
        }
    }

    /**
     * Classe du quadrillage
     */
    private class Quadrilage {
        ArrayList<Trajectoire> trajectoiresDejaEngagees;
        int nbLigne,nbColonne;

        Quadrilage(){
            this.trajectoiresDejaEngagees = new ArrayList<>();
        }
        /**
         * on initialise le quadrillage avec les axes Nord-Sud sur les colonnes et Est-Ouest sur les lignes.
         * Le nombre de quadrillage sur un axe Nord-Sud Est-Ouest prend le nombre maximal de voie qu'il y a sur
         * cette voies donc exemple si Nord=4 voies (2 axes à 2 voies) et Sud est de 2 voies (2 axes d'une voie)
         * on aura un quadrillage avec 4 colonnes
         */
        void initQuadrillage (){
            int nbVoiesNord=0;
            int nbVoiesSud=0;
            int nbVoiesEst=0;
            int nbVoiesOuest=0;
            ArrayList<Axe> toutaxes = getAxesEntrants();
            toutaxes.addAll(getAxesSortants());
            for (Axe unAxeSortant : toutaxes) {
                switch (axe2Position.get(unAxeSortant)) {
                    case Nord:
                    nbVoiesNord+=unAxeSortant.getNbVoies();
                    break;
                    case Sud:
                    nbVoiesSud+=unAxeSortant.getNbVoies();
                    break;
                    case Est:
                    nbVoiesEst+=unAxeSortant.getNbVoies();
                    break;
                    case Ouest:
                    nbVoiesOuest+=unAxeSortant.getNbVoies();
                    break;
                }
            }
            int a,b;
            if (nbVoiesNord<nbVoiesSud) {a = nbVoiesSud;}
            else {a=nbVoiesNord;}
            if (nbVoiesEst<nbVoiesOuest) {b = nbVoiesOuest;}
            else {b=nbVoiesEst;}
            this.nbLigne = b;
            this. nbColonne = a;
            intersectionInitialisee = true;

        }
        ArrayList<Trajectoire> getTrajectoiresEngagees () {return this.trajectoiresDejaEngagees;}

        /**
         * Cettefonction déplace sur le quadrillage de l'intersection les véhicules et enregistre les véhicules qui n'ont pas fini leur
         * déplacement sur l'intersection
         * @param vehicule
         */
        void deplacer(ArrayList<Trajectoire> listeTrajectoires){
            //il faut d'abord déplacer les véhicules dans l'intersection puis ajouter les nouveaux véhicules de dehors et leur donner la priorité Engage.
            //il met également à jour sa liste des trajectoires  des véhicules dans l'intersection
            /* déplacer <==> soustraire tous les tick de 1, enlever les ticks à 0 et si liste des cases vide alors mettre la voiture sur l'axe */
            for (Trajectoire trajectoire1 : listeTrajectoires) {
                this.trajectoiresDejaEngagees.clear();
                trajectoire1.soustraireTick();
                trajectoire1.enlever0();
                if (trajectoire1.trajectoirevehicule.isEmpty()){
                    trajectoire1.getVehicule().getNextItineraire().addVehicule(trajectoire1.getVehicule());
                    trajectoire1.getVehicule().enleverNextItineraire();
                } else {
                    this.trajectoiresDejaEngagees.add(trajectoire1);
                }
            }
        }
        Case getCaseAlignement(Axe axe, Axe axeCible, boolean tourneADroite, boolean tourneAGauche) {
            int nbArrivee = axeCible.getNbVoies();
            int decalageDepuisExterieur;
            //là on a la case dans laquelle on arrive
            Case caseAlignement = new Case(axe, axeCible, tourneADroite, tourneAGauche);
            //maitenant on la déplace pour qu'elle soit alignée avec la bonne sortie
            if (tourneADroite) {decalageDepuisExterieur=0;}
            else if (tourneAGauche) {decalageDepuisExterieur=nbArrivee-1;}
            else {decalageDepuisExterieur=nbArrivee-nbArrivee/2;}

            switch (axe2Position.get(axeCible)) {
                case Nord:
                caseAlignement.x = decalageDepuisExterieur;
                break;
                case Sud:
                caseAlignement.x = quadrillageIntersection.nbColonne - decalageDepuisExterieur;
                break;
                case Est:
                caseAlignement.y = decalageDepuisExterieur;
                break;
                case Ouest:
                caseAlignement.y = quadrillageIntersection.nbLigne - decalageDepuisExterieur;
                break;
            }
            return caseAlignement;
        }
    }

    @Override
    public void addVehicule(VehiculeSurVoie vehicule) {
        // TODO Auto-generated method stub
        
    }

    public void ajouterAxeEntrant(Axe axeEntrant, Position positionDansAxe, Priorite prioriteAxe) {
        super.ajouterAxeEntrant(axeEntrant);
        this.axe2Position.put(axeEntrant, positionDansAxe);
        this.axe2Priorite.put(axeEntrant, prioriteAxe);
        this.intersectionInitialisee=false;
    }

    public void ajouterAxeSortant(Axe axeSortant, Position positionDansAxe, Priorite prioriteAxe) {
        super.ajouterAxeSortant(axeSortant);
        this.axe2Position.put(axeSortant, positionDansAxe);
        this.axe2Priorite.put(axeSortant,prioriteAxe);
        this.intersectionInitialisee=false;
    }
}

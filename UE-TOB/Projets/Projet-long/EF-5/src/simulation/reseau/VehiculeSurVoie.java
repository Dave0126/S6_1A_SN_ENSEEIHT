package simulation.reseau;

import simulation.vehicules.Vehicule;

/** 
 * Représente un véhiule, associé à sa position sur sa voie.
 * @author Clément, Adrien
 */
public class VehiculeSurVoie implements Comparable<VehiculeSurVoie> {
    /** Vehicule */
    private Vehicule vehicule;

    /** Position du vehicule sur l'axe */
    private double position; // la position va de longeur axe à zero au fur et a mesure que la voiture avance vers la fin de l'axe

    /**
     * Construire VehiculeSurVoie
     * @param vehicule le vehicule
     * @param position la position du vehicule sur la voie
     */
    public VehiculeSurVoie(Vehicule vehicule, double position) {
        this.vehicule = vehicule;
        this.position = position;
    }
    /**
     * Obtenir la position du vehicule
     * @return la position du vehicule
     */
    public double getPosition() {
        return this.position;
    }
    
    /**
     * Obtenir le véhicule
     * @return le véhicule
     */
    public Vehicule getVehicule() {
    	return this.vehicule;
    }

    /**
     * Définir la position du vehicule
     * @param newPosition
     */
    public void setPosition(double newPosition) {
       this.position = newPosition;
    }

    @Override
    public int compareTo(VehiculeSurVoie autreVehicule) {
        return Double.compare(this.position, autreVehicule.getPosition());
        }

    @Override 
    public boolean equals(Object object) {
        return (object instanceof VehiculeSurVoie) && (this.position == ((VehiculeSurVoie) object).getPosition());
    }
}

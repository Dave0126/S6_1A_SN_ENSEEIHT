
import java.awt.Color;

/**
 * @version 2.0
 * @author DAI Guohao /SN1 /Groupe E
 *	Last edit time: 26/04/2021
 */

public class Cercle implements Mesurable2D {
	/**  Math.PI. */
	public static final double PI = Math.PI;
	/** the center of the circle. */
	private Point centerPoint;			//center of the circle
	/** the radius of the circle. */
	private double cr;					//radius
	/** the color of the circle. */
	private Color cCouleur;				//color of the circle
	/**
	 * This constructor will input the center and radius of a circle,
	 * and finally return a circle.
	 * @param centerPoint The center of circle, it must be non-null.
	 * @param cr The radius of circle, it must be non-null.
	 * And set the color of the circle to blue.
	 */
	public Cercle(Point centerPoint, double cr) {
		assert (centerPoint != null);
		assert (cr > 0);
		this.centerPoint = new Point(centerPoint.getX(), centerPoint.getY());
        this.cr = cr;
        this.cCouleur = Color.blue;
	}
	/**
	 * This constructor creates a circle by 2 points,
	 * and take a half of the distance between them as the radius,
	 * and we can decide the color of circle,
	 * and finally return a circle.
	 * And the two points must not be equal!
	 * @param p1 A point on the circle, it must be non-null.
	 * @param p2 Another point on the circle, it must be non-null.
	 * @param couleur The color of circle, it must be non-null.
	 */
	public Cercle(Point p1, Point p2, Color couleur) {
		assert (p1 != null);
		assert (p2 != null);
		assert (couleur != null);
		assert ((p1.getX() != p2.getX()) || (p1.getY() != p2.getY()));
		this.centerPoint = new Point((p1.getX() + p2.getX()) / 2,
				(p1.getY() + p2.getY()) / 2);
		this.cr = p1.distance(p2) / 2;
        this.cCouleur = couleur;
	}
	/**
	 * This constructor calls last constructor,
	 * and input 2 points,
	 * and take a half of the distance between them as the radius,
	 * and finally return a circle.
	 * And the two points must not be equal!
	 * @param p1 A point on the circle, it must be non-null.
	 * @param p2 Another point on the circle, it must be non-null.
	 */
	public Cercle(Point p1, Point p2) {
		this(p1, p2, Color.blue);
	}

	/**
	 * This method returns the center of circle.
	 * @return centerPoint
	 */
	public Point getCentre() {
		Point pt = new Point(this.centerPoint.getX(),
				this.centerPoint.getY());
		return pt;
	}

	/**
	 * This method gets the radius of the circle.
	 * @return this.cr
	 */
	public double getRayon() {
		return this.cr;
	}

	/**
	 * This method gets the diameter of the circle.
	 * diameter = 2 * radius
	 * @return 2*this.cr
	 */
	public double getDiametre() {
		return 2 * this.cr;
	}

	/**
	 * This method gets the color of the circle.
	 * @return cCouleur
	 */
	public Color getCouleur() {
		return this.cCouleur;
	}

	/**
	 * This method sets the radius of the circle.
	 * @param rayon The radius rayon must be > 0.
	 */
	public void setRayon(double rayon) {
		assert (rayon > 0);
		this.cr = rayon;
	}

	/**
	 * This method sets the diameter of the circle.
	 * radius = diameter / 2.
	 * @param cd The diameter cd must be > 0!
	 */
	public void setDiametre(double cd) {
		assert (cd > 0);
		this.cr = cd / 2;
	}

	/**
	 * This method sets the color of the circle.
	 * @param newcCouleur The color of circle newcCouleur must be non-null!
	 */
	public void setCouleur(Color newcCouleur) {
		assert (newcCouleur != null);
		this.cCouleur = newcCouleur;
	}

	 /**
	  * This method is made for E14.
	  * This method creates a new circle by point1 and point2.
	  * Among them, the center of the circle is point1,
	  * and the radius is the distance from point1 to point2.
	  * And set color of the new circle to BLUE.
	  * @param p1 Point1
	  * @param p2 Point2
	  * @return c1 The new circle
	  */
	public static Cercle creerCercle(Point p1, Point p2) {
		assert (p1 != null);
		assert (p2 != null);
		assert ((p1.getX() != p2.getX()) || (p1.getY() != p2.getY()));
		double cercleR;
		cercleR = p1.distance(p2);
		Cercle c1 = new Cercle(p1, cercleR);
		c1.setCouleur(Color.blue);
		return c1;
	 }

	/**
	 * This method is made for E5.
	 * This method can judge whether a point is inside the circle.
	 * if (radius >= the distance from the point to the center of the circle)
	 * 		Point1 is inside the circle
	 * else: Point is not inside the circle
	 * @param p1 Point1
	 * @return boolean True or False.
	 */
	public boolean contient(Point p1) {
		assert (p1 != null);
		double distanceToPoint;
		double r;
		Point centerpoint;
		centerpoint = this.centerPoint;
		r = this.getRayon();
		distanceToPoint = centerpoint.distance(p1);
		return (r >= distanceToPoint);
	}

	/**
	 * This method is made for E15.
	 * This method outputs the circle information in the following form:
	 * C(double radius) @ (centerPoint.x , centerPoint.y)
	 * @return the message of circle.
	 */
	public String toString() { //OUTPUT：“Cr@(x, y)”
		String messageCercle;
		messageCercle = "C" + String.valueOf(this.getRayon())
			+ "@" + this.centerPoint.toString();
		return messageCercle;
	}

	/**
	 * This method calculates the circumference of a circle.
	 * Circumference = 2 * radius * PI
	 * PI = Math.PI
	 * @return perimetre
	 */
	public double perimetre() {
		double perimetre;
		perimetre = this.getRayon() * 2 * PI;
		return perimetre;
	}

	/**
	 * This method calculates the area of the circle.
	 * Area = PI * radius^2
	 * @return superficie
	 */
	public double aire() {
		double superficie;
		superficie = Math.pow(this.getRayon(), 2) * PI;
		return superficie;
	}

	/**
	 * This method translates this circle.
	 * @param dx The X-axis coordinate you want to move.
	 * @param dy The Y-axis coordinate you want to move
	 */
	public void translater(double dx, double dy) {
		 this.centerPoint.translater(dx, dy);
	}
}

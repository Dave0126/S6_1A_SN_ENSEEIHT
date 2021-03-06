package simulation.vue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;

/**
 * élément graphique représentant une route
 * @version 2.0
 * @author Ahmed CHEGGAF
 */

public class Route extends JComponent {
    int x1, x2, y1, y2, strk, largeurRoute;
    int w,h;
    int centreX, centreY;
    int traitW, traitH;
    int centreTraitX, centreTraitY;
    boolean highlight = false;
    boolean routeX;
    int vitesse = 50;


    public Route(int xun, int yun, int xdeux, int ydeux,
          int strokeThickNess, int largeur,int speed){
        vitesse = speed;
        if (xun<xdeux){
            x1 = xun;
            x2 = xdeux;
        }
        else {
            x1 = xdeux;
            x2 = xun;
        }
        if (yun<ydeux){
            y1 = yun;
            y2 = ydeux;
        }
        else{
            y1 = ydeux;
            y2 = yun;
        }
        routeX = java.lang.Math.abs(x2-x1)>=java.lang.Math.abs(y2-y1);

        w = (routeX) ? java.lang.Math.abs(x2-x1) : largeur;
        h = (routeX) ? largeur : java.lang.Math.abs(y2-y1) ;
        centreX = (routeX) ? 0 : largeur/2;
        centreY = routeX ? largeur/2 : 0;
        traitW = routeX ? 8 : 2;
        traitH = routeX ? 2 : 8;
        centreTraitX = routeX ? 0 : 1;
        centreTraitY = routeX ? 1 : 0;

        strk = strokeThickNess;
        largeurRoute = largeur;
        this.setVisible(true);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        AffineTransform transform = new AffineTransform();
        if (highlight) g2D.setColor(Color.darkGray);
        else g2D.setColor(Color.black);
        g2D.setStroke(new BasicStroke(strk));
        g.fillRect(x1-centreX,y1-centreY,w,h);
        g2D.setColor(Color.white);
        int taille = (routeX) ? w : h;
        int traitLen = (routeX) ? traitW : traitH;
        for (int i = 0 ; i< taille ; i++){
            if (i%(2*traitLen)==0){
                if (routeX)g.fillRect(x1-centreTraitX+i,y1-centreTraitY,traitW,traitH);
                else g.fillRect(x1-centreTraitX,y1-centreTraitY+i,traitW,traitH);
            }
        }

    }

    public void changeColor(boolean state){
        highlight = state;
    }

    public void changeSpeed(int speed){
        vitesse = speed;

    }
}

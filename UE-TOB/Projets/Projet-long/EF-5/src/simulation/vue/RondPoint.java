package simulation.vue;
import javax.swing.*;
import java.awt.*;

/**
 * élément graphique représentant un rond point
 * @version 2.0
 * @author Ahmed CHEGGAF
 */

public class RondPoint extends JComponent {
    int x1, y1, rayon;
    boolean lh = false;
    boolean uh = false;
    boolean dh = false;
    boolean rh = false;

    public RondPoint(int xun, int yun){
        x1 = xun;
        y1 = yun;
        rayon = 2*20;
        this.setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.BLACK);
        int rayonNoir = rayon;
        g2D.fillOval(x1-(rayonNoir/2),y1-(rayonNoir/2),rayonNoir,rayonNoir);
        g2D.setColor(Color.GRAY);
        if(lh) g2D.fillArc(x1-(rayonNoir/2), y1-(rayonNoir/2), rayonNoir, rayonNoir, 135, 90);
        else if(rh) g2D.fillArc(x1-(rayonNoir/2), y1-(rayonNoir/2), rayonNoir, rayonNoir, -45, 90);
        else if(uh) g2D.fillArc(x1-(rayonNoir/2), y1-(rayonNoir/2), rayonNoir, rayonNoir, 45, 90);
        else if(dh) g2D.fillArc(x1-(rayonNoir/2), y1-(rayonNoir/2), rayonNoir, rayonNoir, -135, 90);
        g2D.setColor(Color.white);
        int rayonRouge = (int)(0.2*rayon);
        g2D.fillOval(x1-(rayonRouge/2),y1-(rayonRouge/2),rayonRouge,rayonRouge);

    }


    public void resetHighlight(){
        lh = rh = uh = dh = false;
    }

    public void whichSideToHighlight(int x, int y){
        double angle = Math.toDegrees(Math.atan2(y1 - y, x - x1));
        angle =  angle>= 0? angle: angle + 360;
        rh = (angle>0 && angle<45) || (angle<360 && angle>315);
        dh = (angle>225 && angle<315) ;
        lh = (angle>135 && angle<225) ;
        uh = (angle>45 && angle<135) ;
    }
}

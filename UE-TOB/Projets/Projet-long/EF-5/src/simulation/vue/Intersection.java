package simulation.vue;

import javax.swing.*;
import java.awt.*;

public class Intersection extends JComponent {
    int x1, y1, rayon;
    boolean highlight = false;
    boolean lr = true, ur = true, rr =true, dr = true;

    public Intersection(int xun, int yun, boolean up, boolean down, boolean right, boolean left){
        ur = up;
        lr = left;
        rr = right;
        dr = down;
        x1 = xun;
        y1 = yun;
        rayon = 30;
        this.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.black);
        if (highlight) g2D.setColor(Color.DARK_GRAY);
        g2D.fillRect(x1-(rayon/2), y1-(rayon/2),rayon, rayon);
        g2D.setColor(Color.white);
        if (ur) g2D.fillRect(x1-3,y1-(rayon/2),6,2);
        if (dr) g2D.fillRect(x1-3,y1+(rayon/2)-2,6,2);
        if (rr) g2D.fillRect(x1-(rayon/2),y1-1,2,6);
        if (lr) g2D.fillRect(x1+(rayon/2)-2,y1-1,2,6);
    }

    public void Highlight(){
        highlight = true;
    }
    public void unHighlight(){
        highlight = false;
    }
}

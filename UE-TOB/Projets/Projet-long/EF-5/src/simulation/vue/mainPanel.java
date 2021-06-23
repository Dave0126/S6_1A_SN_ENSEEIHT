package simulation.vue;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class mainPanel {

     public static void main(String[] argc){
         JFrame mainFrame = new JFrame();
         mainFrame.setLayout(null);
         JPanel mainPanel = PanelDrawing.getPanel(0,0,500,400);
         mainFrame.setBounds(0,0,500,400);
         mainFrame.setResizable(false);
         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         mainFrame.add(mainPanel);
         mainFrame.setVisible(true);
     }


}

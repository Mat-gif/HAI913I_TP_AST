package ui.template;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import ui.controller.SelectProjectController;
import ui.paramater.MyViewParameter;
import ui.template.CustomJPanel.InitialPanel;
import ui.template.CustomJPanel.ResultsPanel;


public class MainFrame {
	
	private MyViewParameter myParam = new MyViewParameter();
    private JFrame frame  = new JFrame();
    private SelectProjectController controller;
  
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame window = new MainFrame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        
        frame.setBounds(myParam.getxFenetre(), myParam.getyFenetre(), myParam.getLargeurFenetre(), myParam.getHauteurFenetre());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créez un JPanel contenant un CardLayout
        JPanel cardPanel = new JPanel(new CardLayout());
        
        InitialPanel  panel1 = new InitialPanel(frame);
        ResultsPanel  panel2 = new ResultsPanel(frame);
       
        //Ajoutez les panneaux au conteneur principal avec des noms d'identification
        cardPanel.add(panel1, "Panel1");
        cardPanel.add(panel2, "Panel2");

        // Ajoutez le conteneur principal au frame
        frame.add(cardPanel);

   
        //CardLayout pour basculer entre les panneaux
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "Panel1"); // Affichez initialement le panel1
        
        controller = new SelectProjectController(frame,panel1,panel2,cardLayout,cardPanel);
        panel1.addAllListener(controller);
        panel2.addAllListener(controller);

    }
}

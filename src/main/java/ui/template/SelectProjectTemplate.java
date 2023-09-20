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
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import ui.controller.SelectProjectController;
import ui.paramater.MyViewParameter;

import javax.swing.ButtonGroup;
import javax.swing.Icon;

public class SelectProjectTemplate {
	
	
	
	private MyViewParameter myParam = new MyViewParameter();

     JFrame frame  = new JFrame();
     JPanel panel = new JPanel();
    private ButtonGroup buttonGroup = new ButtonGroup(); // Créez un groupe de boutons radio
    private SelectProjectController myController = new SelectProjectController(frame,panel);
    
    

    
    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectProjectTemplate window = new SelectProjectTemplate();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SelectProjectTemplate() {
        initialize();
    }

    private void initialize() {
        
        frame.setBounds(myParam.getxFenetre(), myParam.getyFenetre(), myParam.getLargeurFenetre(), myParam.getHauteurFenetre());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
       


        // Créez un bouton pour ouvrir la boîte de dialogue de sélection de dossier
        JButton btnSelectFolder = new JButton("Sélectionner un projet Java");
        btnSelectFolder.setBounds(myParam.getxBouton(), myParam.getyBouton(), myParam.getLargeurBouton(), myParam.getHauteurBouton());
        btnSelectFolder.setFont(MyViewParameter.getMyFontStyle());
        panel.add(btnSelectFolder);
        
        JLabel lblSelectionnerUnType = new JLabel("Selectionner un type d'analyse :");
        lblSelectionnerUnType.setBounds(myParam.getxBouton(), myParam.getyBouton()*2, myParam.getLargeurBouton(), myParam.getHauteurBouton());
        lblSelectionnerUnType.setFont(MyViewParameter.getMyFontStyle());
        panel.add(lblSelectionnerUnType);
        
        JRadioButton rdbtnAnalyse_1 = new JRadioButton("Analyse de base");
        rdbtnAnalyse_1.setBounds(myParam.getxBouton(), myParam.getyBouton()*3, myParam.getLargeurBouton(), myParam.getHauteurBouton());
        rdbtnAnalyse_1.setFont(MyViewParameter.getMyFontStyle());
        panel.add(rdbtnAnalyse_1);
        buttonGroup.add(rdbtnAnalyse_1); // Ajoutez le bouton radio au groupe
        

        
        JRadioButton rdbtnAnalyse_2 = new JRadioButton("Analyse complémentaire");
        rdbtnAnalyse_2.setBounds(myParam.getxBouton()+myParam.getLargeurBouton(), myParam.getyBouton()*3, myParam.getLargeurBouton(), myParam.getHauteurBouton());
        rdbtnAnalyse_2.setFont(MyViewParameter.getMyFontStyle());
        panel.add(rdbtnAnalyse_2);
        buttonGroup.add(rdbtnAnalyse_2); // Ajoutez le bouton radio au groupe
        
        JRadioButton rdbtnAnalyse_3 = new JRadioButton("Obtenir le graphe");
        rdbtnAnalyse_3.setBounds(myParam.getxBouton()+myParam.getLargeurBouton()*2, myParam.getyBouton()*3, myParam.getLargeurBouton(), myParam.getHauteurBouton());
        rdbtnAnalyse_3.setFont(MyViewParameter.getMyFontStyle());
        panel.add(rdbtnAnalyse_3);
        buttonGroup.add(rdbtnAnalyse_3); // Ajoutez le bouton radio au groupe
        
        JButton btnValider = new JButton("Lancer l'analyse");
        btnValider.setBounds(myParam.getLargeurFenetre()-myParam.getxBouton()-myParam.getLargeurBouton(), myParam.getyBouton(), myParam.getLargeurBouton(), myParam.getHauteurBouton());
        btnValider.setFont(MyViewParameter.getMyFontStyle());
        btnValider.setBackground(Color.lightGray);
        panel.add(btnValider);

   
        btnSelectFolder.addActionListener(myController.btnSelectFoldeListener);
        btnValider.addActionListener(myController.buttonListener);
        rdbtnAnalyse_1.addActionListener(myController.radioListener);
        rdbtnAnalyse_2.addActionListener(myController.radioListener);
        rdbtnAnalyse_3.addActionListener(myController.radioListener);
    }
}

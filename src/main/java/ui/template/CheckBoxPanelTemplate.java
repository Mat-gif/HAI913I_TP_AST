package ui.template;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import ui.paramater.MyViewParameter;
public class CheckBoxPanelTemplate {
    private MyViewParameter myParam = new MyViewParameter();
    private ButtonGroup buttonGroup = new ButtonGroup();
    private String[] myList;
    private String id;
    List<JCheckBox> checkBoxList = new ArrayList<>();


    public CheckBoxPanelTemplate(JFrame frame, JPanel panel, String[] myList, String typeAnalyse) {
        super();
this.id = typeAnalyse;
        this.myList = myList;

        int y = 0; // Position verticale initiale

        for (int i = 1; i <= myList.length; i++) {
        	JCheckBox checkBox = new JCheckBox("Analyse " + i);
        	

            switch (typeAnalyse) {
                case "Analyse de base":
                    checkBox.setBounds(myParam.getxBouton(), myParam.getyBouton() * 4 + y, myParam.getLargeurBouton(),
                            myParam.getHauteurBouton());
                    checkBox.setActionCommand(""+i);
                    break;

                case "Analyse complémentaire":
                    checkBox.setBounds(myParam.getxBouton() + myParam.getLargeurBouton(),
                            myParam.getyBouton() * 4 + y, myParam.getLargeurBouton(), myParam.getHauteurBouton());
                    checkBox.setActionCommand(""+(i+7));
                    break;
            }

            checkBox.setFont(new Font("Arial", Font.PLAIN, 16)); // Police personnalisée
            checkBox.setVisible(false);
            checkBoxList.add(checkBox);
            panel.add(checkBox);
            //buttonGroup.add(checkBox); // Ajouter la case à cocher au groupe

            y += 60; // Incrémentation de la position verticale pour la case suivante
        }
        panel.revalidate(); // Actualisez le panneau pour refléter les changements
        panel.repaint(); // Redessinez le panneau

    }

    public void showButtonGroup(JPanel panel,boolean isShow ) {

    	 switch (id) {
         case "Analyse de base":
        	 for (int i = 0; i < 8; i++) {
        		 String myId = i+"";
        		 for (JCheckBox checkBox : checkBoxList) {
                     if (myId.equals(checkBox.getActionCommand())) {
                         checkBox.setVisible(isShow);
                     }
                     else if(!isShow) {
                    	 checkBox.setSelected(false);
                     }
                 }
             }
             break;

         case "Analyse complémentaire":
        	 for (int i = 8; i < 14; i++) {
        		 String myId = i+"";
        		 for (JCheckBox checkBox : checkBoxList) {
                     if (myId.equals(checkBox.getActionCommand())) {
                         checkBox.setVisible(isShow);
                     }
                     else if(!isShow) {
                    	 checkBox.setSelected(false);
                     }
                 }
             }
             break;
     }
    	
        panel.revalidate(); // Actualisez le panneau pour refléter les changements
        panel.repaint(); // Redessinez le panneau
    }
}



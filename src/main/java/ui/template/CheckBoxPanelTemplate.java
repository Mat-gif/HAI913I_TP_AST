package ui.template;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.paramater.MyViewParameter;
public class CheckBoxPanelTemplate {
    private MyViewParameter myParam = new MyViewParameter();
    private ButtonGroup buttonGroup = new ButtonGroup();
    private String id;
    List<JCheckBox> checkBoxList = new ArrayList<>();
    private int y = 0; // Position verticale initiale


    public CheckBoxPanelTemplate(JFrame frame, JPanel panel, Map<String,String> labels, String typeAnalyse) 
    {
        super();
        this.id = typeAnalyse;
        
        labels.forEach((k,v)->
        {
        	JCheckBox checkBox = new JCheckBox(v);
            switch (typeAnalyse) 
            {
                case "Analyse de base":
                    checkBox.setBounds(myParam.getxBouton(), myParam.getyBouton() * 4 + y, myParam.getLargeurBouton(), myParam.getHauteurBouton());
                    checkBox.setActionCommand(k);
                    break;

                case "Analyse complémentaire":
                    checkBox.setBounds(myParam.getxBouton() + myParam.getLargeurBouton(),myParam.getyBouton() * 4 + y, myParam.getLargeurBouton(), myParam.getHauteurBouton());
                    checkBox.setActionCommand(k);
                    break;
            }
            checkBox.setFont(new Font("Arial", Font.PLAIN, 16)); // Police personnalisée
            checkBox.setVisible(false);
            checkBoxList.add(checkBox);
            panel.add(checkBox);
            //buttonGroup.add(checkBox); // Ajouter la case à cocher au groupe

            y += 60; 
        });
        panel.revalidate();
        panel.repaint(); 

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



package ui.template;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
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
    private Map<String,String> labels;
	HashSet<String> methodsForProcessor = new HashSet<>();


    public CheckBoxPanelTemplate(JFrame frame, JPanel panel, Map<String,String> labels, String typeAnalyse) 
    {
        super();
        this.id = typeAnalyse;
        this.labels= labels;
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
    
        // Ajouter un ItemListener pour chaque case à cocher
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    methodsForProcessor.add(checkBox.getActionCommand());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                	 methodsForProcessor.remove(checkBox.getActionCommand());
                }
            }
        });
    });
        panel.revalidate();
        panel.repaint(); 

    }
    
    
    
    public HashSet<String>  getMethodsForProcessor(){
		return methodsForProcessor;
    }

    public void showButtonGroup(JPanel panel,boolean isShow ) {

        	 labels.forEach((k,v)->{
        		 for (JCheckBox checkBox : checkBoxList) {
        			 if (k.equals(checkBox.getActionCommand())) {
                         checkBox.setVisible(isShow);
                     }
                     else if(!isShow) {
                    	 checkBox.setSelected(false);
                     }
        		 }
        		 
        	 });

        panel.revalidate(); 
        panel.repaint(); 
    }
}



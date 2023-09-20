package ui.controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ui.template.CheckBoxPanelTemplate;
import ui.template.FolderChooserTemplate;

public class SelectProjectController  {
	

	private String my_path = "";
	private String my_analyse = "";
	 JFrame frame;
	 JPanel panel;
	 private CheckBoxPanelTemplate checkBoxPanelBasique;
	 private CheckBoxPanelTemplate checkBoxPanelComplementaire;
	 private String[] listAnalyseBasique = new String[7];
	 private String[] listAnalyseComplementaire = new String[6];



	
	
	public SelectProjectController(JFrame frame, JPanel panel) {
		super();
		this.frame = frame;
		this.panel = panel;
		
		 listAnalyseBasique[0] = "1";
	        listAnalyseBasique[1] = "2";
	        listAnalyseBasique[2] = "3";
	        listAnalyseBasique[3] = "4";
	        listAnalyseBasique[4] = "5";
	        listAnalyseBasique[5] = "6";
	        listAnalyseBasique[6] = "7";
	        
	        
			
	        listAnalyseComplementaire[0] = "8";
	        listAnalyseComplementaire[1] = "9";
		        listAnalyseComplementaire[2] = "10";
		        listAnalyseComplementaire[3] = "11";
		        listAnalyseComplementaire[4] = "12";
		        listAnalyseComplementaire[5] = "13";
		        
		        
		        checkBoxPanelBasique = new CheckBoxPanelTemplate(frame,panel,listAnalyseBasique,"Analyse de base");
	        	checkBoxPanelComplementaire = new CheckBoxPanelTemplate(frame,panel,listAnalyseComplementaire,"Analyse complémentaire");
	

	}



	// Ajoutez un gestionnaire d'événements aux boutons radio
    public ActionListener radioListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
        	

            
            if (selectedRadioButton.isSelected()){
                switch (selectedRadioButton.getText()) {
	                case "Analyse de base":
	                	checkBoxPanelBasique.showButtonGroup(panel,true);
	                	checkBoxPanelComplementaire.showButtonGroup(panel,false);
	                	my_analyse= selectedRadioButton.getText();	                    
	                	break; 
	
	                case "Analyse complémentaire":
	                	checkBoxPanelComplementaire.showButtonGroup(panel,true);
	                	checkBoxPanelBasique.showButtonGroup(panel,false);
	                	my_analyse= selectedRadioButton.getText();	                    
	                	break;
	
	                default:
	                	checkBoxPanelBasique.showButtonGroup(panel,false);
	                	checkBoxPanelComplementaire.showButtonGroup(panel,false);                
                }

            }

           
        }
    };
    
 // Ajoutez un gestionnaire d'événements au bouton
    public ActionListener buttonListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	if(my_path != "" && my_analyse != "") {
                System.out.println("chemin : " + my_path + " ; analyse : "+my_analyse);
                
        	}
        }
    };
    

    public ActionListener btnSelectFoldeListener = new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
            JFileChooser folderChooser = new FolderChooserTemplate();
            int returnValue = folderChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Obtenez le dossier sélectionné
                java.io.File selectedFolder = folderChooser.getSelectedFile();

                // Faites quelque chose avec le dossier sélectionné
                //System.out.println("Dossier sélectionné : " + selectedFolder.getAbsolutePath());
                my_path = selectedFolder.getAbsolutePath();
            }
        }
    };

}

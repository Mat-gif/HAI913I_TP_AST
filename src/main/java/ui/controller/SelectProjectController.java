package ui.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import processor.MyProcessor;
import ui.template.CheckBoxPanelTemplate;
import ui.template.FolderChooserTemplate;
import ui.template.CustomJPanel.InitialPanel;
import ui.template.CustomJPanel.ResultsPanel;

public class SelectProjectController  {
	

	private String my_path = "";
	private String my_analyse = "";
	 JFrame frame;
	 private InitialPanel panel1;
	 private ResultsPanel panel2;
	 private JPanel cardPanel;
	 private CheckBoxPanelTemplate checkBoxPanelBasique;
	 private CheckBoxPanelTemplate checkBoxPanelComplementaire;
	 private String[] listAnalyseBasique = new String[7];
	 private String[] listAnalyseComplementaire = new String[6];
	 private MyProcessor myProcessor;
	 private CardLayout cardLayout;
	    
	 


	
	
	public SelectProjectController(JFrame frame, InitialPanel panel1,ResultsPanel panel2,CardLayout cardLayout,JPanel cardPanel) {
		super();
		this.frame = frame;
		this.panel1 = panel1;
		this.panel2 = panel2;
		this.cardLayout = cardLayout;
		this.cardPanel = cardPanel;
		
		
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
		        
		        
		        checkBoxPanelBasique = new CheckBoxPanelTemplate(frame,panel1,listAnalyseBasique,"Analyse de base");
	        	checkBoxPanelComplementaire = new CheckBoxPanelTemplate(frame,panel1,listAnalyseComplementaire,"Analyse complémentaire");
	
	        	
	           
	}



	// Ajoutez un gestionnaire d'événements aux boutons radio
    public ActionListener radioListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
        	

            
            if (selectedRadioButton.isSelected()){
                switch (selectedRadioButton.getText()) {
	                case "Analyse de base":
	                	checkBoxPanelBasique.showButtonGroup(panel1,true);
	                	checkBoxPanelComplementaire.showButtonGroup(panel1,false);
	                	my_analyse= selectedRadioButton.getText();	                    
	                	break; 
	
	                case "Analyse complémentaire":
	                	checkBoxPanelComplementaire.showButtonGroup(panel1,true);
	                	checkBoxPanelBasique.showButtonGroup(panel1,false);
	                	my_analyse= selectedRadioButton.getText();	                    
	                	break;
	
	                default:
	                	checkBoxPanelBasique.showButtonGroup(panel1,false);
	                	checkBoxPanelComplementaire.showButtonGroup(panel1,false);                
                }

            }

           
        }
    };
    
 // Ajoutez un gestionnaire d'événements au bouton
    public ActionListener buttonListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	 cardLayout.show(cardPanel, "Panel2"); // Affichez initialement le panel1
        	 
        	 
        	 if(my_path != "" ) {
                System.out.println("chemin : " + my_path + " ; analyse : "+my_analyse);
                myProcessor= new MyProcessor( my_path);
               /* try {
					myProcessor.getParser().parseProject();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
                
        	}
        }
    };
    
    
    // Ajoutez un gestionnaire d'événements au bouton
    public ActionListener buttonQuitListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	cardLayout.show(cardPanel, "Panel1");
        	
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

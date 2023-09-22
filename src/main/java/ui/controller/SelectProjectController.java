package ui.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	private JFrame frame;
	private InitialPanel panel1;
	private ResultsPanel panel2;
	private JPanel cardPanel;
	private CheckBoxPanelTemplate checkBoxPanelBasique;
	private CheckBoxPanelTemplate checkBoxPanelComplementaire;
	private MyProcessor myProcessor;
	private CardLayout cardLayout;
	private LabelMap labels = new LabelMap();
	HashSet<String> methodsForProcessor = new HashSet<>();

	public SelectProjectController(JFrame frame, InitialPanel panel1,ResultsPanel panel2,CardLayout cardLayout,JPanel cardPanel) 
	{
		super();
		this.frame = frame;
		this.panel1 = panel1;
		this.panel2 = panel2;
		this.cardLayout = cardLayout;
		this.cardPanel = cardPanel;
	    checkBoxPanelBasique = new CheckBoxPanelTemplate(frame,panel1,labels.getAnalyseDeBase(),"Analyse de base");
	    checkBoxPanelComplementaire = new CheckBoxPanelTemplate(frame,panel1,labels.getAnalyseComplementaire(),"Analyse complémentaire");
	}



	// Ajoutez un gestionnaire d'événements aux boutons radio
    public ActionListener radioListener = new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) {
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
            if (selectedRadioButton.isSelected()){
                switch (selectedRadioButton.getText()) 
                {
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
    

    /**
     * Gestinnaire d'évévements 
     * Permet de lancer l'analyse du projet
     * Necessite d'avoir selectioner:
     * 		- un path valide 
     * 		- choisi des analyses a réaliser 
     */
    public ActionListener buttonListener = new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	if(!checkBoxPanelBasique.getMethodsForProcessor().isEmpty()) methodsForProcessor=checkBoxPanelBasique.getMethodsForProcessor();
        	else if(!checkBoxPanelComplementaire.getMethodsForProcessor().isEmpty()) methodsForProcessor=checkBoxPanelComplementaire.getMethodsForProcessor();
        	 cardLayout.show(cardPanel, "Panel2"); // Affichez  le panel2
        	 if(my_path != "" ) {
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
    public ActionListener buttonQuitListener = new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	cardLayout.show(cardPanel, "Panel1");
        }
    };
    
    /**
     * Gestinnaire d'évévements 
     * Permet d'afficher de choisir un projet pour l'analyse
     */
    public ActionListener btnSelectFoldeListener = new ActionListener() 
    {
    	public void actionPerformed(ActionEvent e) 
    	{
            JFileChooser folderChooser = new FolderChooserTemplate();
            int returnValue = folderChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) 
            {
                // Obtenez le dossier sélectionné
                java.io.File selectedFolder = folderChooser.getSelectedFile();
                // Je stock le path dans une valiable pour la suite
                my_path = selectedFolder.getAbsolutePath();
            }
        }
    };

}

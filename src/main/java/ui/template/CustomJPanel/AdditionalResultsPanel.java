package ui.template.CustomJPanel;

import java.awt.BorderLayout;
import java.awt.Color;
<<<<<<< HEAD
import java.awt.Dimension;
import java.awt.FlowLayout;
=======
import java.util.HashMap;
import java.util.HashSet;
>>>>>>> 478daac54191a0aeb79806e8215aa1ab863e6164
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ui.controller.LabelMap;
import ui.controller.Resultat;
import ui.controller.SelectProjectController;
import ui.paramater.MyViewParameter;

<<<<<<< HEAD
public class AdditionalResultsPanel extends JPanel {
    private MyViewParameter myParam = new MyViewParameter();
    private JButton btnTerminer;
    private LabelMap labels = new LabelMap();
    private JPanel contentPanel; // Panel pour le contenu
    private int i = 2;
    private int j = 0;
    private int myY = myParam.getyBouton() * i;
    private JLabel valueLabel;

    public JButton getBtnTerminer() {
        return btnTerminer;
    }

    public AdditionalResultsPanel(JFrame frame) {
        frame.getContentPane().add(this, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        
        valueLabel = new JLabel();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("--> Resultats :");
        titleLabel.setFont(MyViewParameter.getMyFontStyleTitle());
        topPanel.add(titleLabel);
        btnTerminer = new JButton("Terminer");
        btnTerminer.setFont(MyViewParameter.getMyFontStyle());
        btnTerminer.setBackground(Color.lightGray);
        topPanel.add(btnTerminer);

        // Panel pour le contenu avec layout null
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        // Ajoutez le contenuPanel au JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Ajustez la vitesse de défilement si nécessaire

        // Ajoutez les composants au panneau de contenu
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void printResults(Map<String, Resultat> results, String myType, int n) {
        labels.setAdditionalAnalysis11(n);

        results.forEach((k, res) -> {
            JLabel keyLabel = new JLabel(labels.getAdditionalAnalysisByID(k) + " : ");
            keyLabel.setBounds(myParam.getxBouton(), (int) Math.round(myY), myParam.getLargeurBouton() * 2,
                    myParam.getHauteurBouton());
            keyLabel.setFont(MyViewParameter.getMyFontStyle());

            // Calcul de la position x pour valueLabel en fonction de keyLabel
            int valueLabelX = keyLabel.getX() + keyLabel.getWidth();

            j = 0;
            res.getResultats().forEach((kk, vv) -> {
                System.out.println(kk);
                if (vv != 0) {
                    valueLabel = new JLabel(kk + " -> " + vv);
                } else {
                    valueLabel = new JLabel(kk);
                }

                valueLabel.setBounds(valueLabelX, keyLabel.getY() + j, myParam.getLargeurBouton(),
                        myParam.getHauteurBouton());
                // valueLabel.setFont(MyViewParameter.getMyFontStyle());

                contentPanel.add(valueLabel);
                myY = valueLabel.getY() + 40;
                j += 20;
            });
            contentPanel.add(keyLabel);
            i++;
        });

        // Assurez-vous que le panneau de contenu a une taille appropriée
        int width = myParam.getLargeurFenetre(); // Ajustez cela en fonction de vos besoins
        int height = myY + myParam.getHauteurBouton(); // Ajustez cela en fonction de vos besoins
        contentPanel.setPreferredSize(new Dimension(width, height));
    }

    public void addAllListener(SelectProjectController myController) {
        btnTerminer.addActionListener(myController.buttonQuitListener);
    }
}
=======

public class AdditionalResultsPanel extends JPanel{
    private JScrollPane scrollPane;

	   private MyViewParameter myParam = new MyViewParameter();
	   private JButton btnTerminer;
	   private LabelMap labels = new LabelMap();
	   private JLabel valueLabel;
	   private int i = 2;
	   private int j = 0;
	   private int myY =myParam.getyBouton()*i;
	   
	public JButton getBtnTerminer() {
		return btnTerminer;
	}


	public void setBtnTerminer(JButton btnTerminer) {
		this.btnTerminer = btnTerminer;
	}


	public AdditionalResultsPanel( JFrame frame ) {
		 frame.getContentPane().add(this, BorderLayout.CENTER);

	     		this.setLayout(null);


	    JLabel titleLabel = new JLabel( "--> Resultats :");
	    titleLabel.setBounds(myParam.getxBouton(), (int) Math.round((myParam.getyBouton()/2)), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	    titleLabel.setFont(MyViewParameter.getMyFontStyleTitle());
	    this.add(titleLabel);
	       
	    btnTerminer = new JButton("Terminer");
	    btnTerminer.setBounds(myParam.getLargeurFenetre()-myParam.getxBouton()-myParam.getLargeurBouton(), (int) Math.round((myParam.getyBouton()/2)), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	    btnTerminer.setFont(MyViewParameter.getMyFontStyle());
	    btnTerminer.setBackground(Color.lightGray);
	       this.add(btnTerminer);
	       


	}
	
	public void printResults(Map<String,Resultat> results, String myType, int n)
	{
		
	       labels.setAdditionalAnalysis11(n);

	       results.forEach((k,res) ->{
	    	   JLabel keyLabel = new JLabel(labels.getAdditionalAnalysisByID(k)+ " : ");
	           keyLabel.setBounds(myParam.getxBouton(), (int) Math.round(myY), myParam.getLargeurBouton()*2, myParam.getHauteurBouton());
	           keyLabel.setFont(MyViewParameter.getMyFontStyle());
	           
	        // Calcul de la position x pour valueLabel en fonction de keyLabel
	           int valueLabelX = keyLabel.getX() + keyLabel.getWidth();

	           
	           j=0;
	    	   res.getResultats().forEach((kk,vv)->{
	    		   System.out.println(kk);
	    		   if(vv!=0)  {valueLabel = new JLabel(kk + " -> " + vv);}
	    		   else  {valueLabel = new JLabel(kk);}
	    		   
		           valueLabel.setBounds(valueLabelX, keyLabel.getY()+j, myParam.getLargeurBouton(), myParam.getHauteurBouton());
		           //valueLabel.setFont(MyViewParameter.getMyFontStyle());
		           
		           this.add(valueLabel);
	    		   myY=valueLabel.getY()+40;
		           j+=20;
	    	   });
	    	   this.add(keyLabel);
	    	   i++;
	       });
	   
	 	  
	    	   
	    	   
	
	       }
	
	
	
	
   	
   	public void addAllListener(SelectProjectController myController) 
   	{
        btnTerminer.addActionListener(myController.buttonQuitListener);
   	}

}
>>>>>>> 478daac54191a0aeb79806e8215aa1ab863e6164

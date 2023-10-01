package ui.template.CustomJPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
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


public class AdditionalResultsPanel extends JPanel{
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
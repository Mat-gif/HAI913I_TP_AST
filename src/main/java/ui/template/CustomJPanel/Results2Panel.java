package ui.template.CustomJPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.controller.LabelMap;
import ui.controller.SelectProjectController;
import ui.paramater.MyViewParameter;


public class Results2Panel extends JPanel{
	   private MyViewParameter myParam = new MyViewParameter();
	   private JButton btnTerminer;
	   private LabelMap labels = new LabelMap();
	   private int i = 2;
	   private int j = 0;
	   private int l = 0;
	   
	public JButton getBtnTerminer() {
		return btnTerminer;
	}


	public void setBtnTerminer(JButton btnTerminer) {
		this.btnTerminer = btnTerminer;
	}


	public Results2Panel( JFrame frame ) {
		
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
	
	public void printResults(Map<String,Map<String, Integer>> results, String myType)
	{

	       
		
	       results.forEach((k,v) ->{
	    	   JLabel keyLabel = new JLabel(labels.get(myType).get(k)+ " : ");
	           keyLabel.setBounds(myParam.getxBouton(), (int) Math.round((myParam.getyBouton()-(myParam.getyBouton()*0.25))*i)+l*40, myParam.getLargeurBouton()*2, myParam.getHauteurBouton());
	           keyLabel.setFont(MyViewParameter.getMyFontStyle());
	           
	        // Calcul de la position x pour valueLabel en fonction de keyLabel
	           int valueLabelX = keyLabel.getX() + keyLabel.getWidth();

	           l=0;
	    	   v.forEach((kk,vv)->{
	    		   System.out.println(kk);
	    		   JLabel valueLabel = new JLabel(kk + " -> " + vv);
		           valueLabel.setBounds(valueLabelX, keyLabel.getY()+j, myParam.getLargeurBouton(), myParam.getHauteurBouton());
		           keyLabel.setFont(MyViewParameter.getMyFontStyle());
		           this.add(keyLabel);
		           this.add(valueLabel);
	    		   
		           j+=40;
		           l++;
	    	   });
	    	  
	           
	           j=0;
	           i++;
	       });
	    	   
	    	   
	
	       }
		
	
	
	
   	
   	public void addAllListener(SelectProjectController myController) {

   		  
        btnTerminer.addActionListener(myController.buttonQuitListener);
   	}

}
package ui.template.CustomJPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.controller.SelectProjectController;
import ui.paramater.MyViewParameter;


public class ResultsPanel extends JPanel{
	   private MyViewParameter myParam = new MyViewParameter();
	   private static  Map<String, Integer> results = new HashMap<>();
	   private JButton btnTerminer;
	
	public ResultsPanel( JFrame frame) {
		
		frame.getContentPane().add(this, BorderLayout.CENTER);
		this.setLayout(null);
	    
		   results.put("Alice", 25);
		   results.put("Bob", 30);
		   results.put("Charlie", 28);
		   results.put("David", 22);
		   results.put("Alice1", 25);
		   results.put("Bob1", 30);
		   results.put("Charlie1", 28);
	       

	       JLabel titleLabel = new JLabel( "--> Resultats :");
	       titleLabel.setBounds(myParam.getxBouton(), (int) Math.round((myParam.getyBouton()/2)), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	       titleLabel.setFont(MyViewParameter.getMyFontStyleTitle());
	       this.add(titleLabel);
	       
	       btnTerminer = new JButton("Terminer");
	       btnTerminer.setBounds(myParam.getLargeurFenetre()-myParam.getxBouton()-myParam.getLargeurBouton(), (int) Math.round((myParam.getyBouton()/2)), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	       btnTerminer.setFont(MyViewParameter.getMyFontStyle());
	       btnTerminer.setBackground(Color.lightGray);
	       this.add(btnTerminer);
	       
	       int i =2;
	       for (Map.Entry<String, Integer> entry : results.entrySet()) {
	    	 
	           JLabel keyLabel = new JLabel( entry.getKey() + " : ");
	           keyLabel.setBounds(myParam.getxBouton(), (int) Math.round((myParam.getyBouton()-(myParam.getyBouton()*0.25))*i), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	           keyLabel.setFont(MyViewParameter.getMyFontStyle());
	           JLabel valueLabel = new JLabel( entry.getValue()+ "");
	           valueLabel.setBounds(myParam.getxBouton()*2, (int) Math.round((myParam.getyBouton()-(myParam.getyBouton()*0.25))*i), myParam.getLargeurBouton(), myParam.getHauteurBouton());
	           keyLabel.setFont(MyViewParameter.getMyFontStyle());
	           this.add(keyLabel);
	           this.add(valueLabel);
	           i++;
	       }


	}
	
   	
   	public void addAllListener(SelectProjectController myController) {

   		  
        btnTerminer.addActionListener(myController.buttonQuitListener);
   	}

}
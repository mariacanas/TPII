package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver{

	private JLabel etiquetatiempo;
	private JLabel numgrupos;
	
	StatusBar(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);
		
	}
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		//Crear una etiqueta de tiempo y añadirla al panel
		//Crear la etiqueta de número de grupos y añadirla al panel
		etiquetatiempo = new JLabel();
		numgrupos = new JLabel ("Groups: ");
		this.add(etiquetatiempo);
		
		//Utilizar el sigiente código para añadir un separador vertical
		
			JSeparator s = new JSeparator(JSeparator.VERTICAL);
			s.setPreferredSize(new Dimension(10,20));
			this.add(s);
			
		this.add(numgrupos);
		
		
	}
	
	//Resto de métodos
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		etiquetatiempo.setText("Tiempo sim:" + time + " segundos");
		numgrupos.setText("Num grupos: " + groups.size());
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		etiquetatiempo.setText("Tiempo sim: " + time);
		numgrupos.setText("Num grupos: " + groups.size());
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		numgrupos.setText("Num grupos: " + groups.size());
		etiquetatiempo.setText("Tiempo: " +time);
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		numgrupos.setText("Num grupos: " + groups.size());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	

}

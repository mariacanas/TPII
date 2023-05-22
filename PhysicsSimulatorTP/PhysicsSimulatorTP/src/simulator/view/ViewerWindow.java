package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ViewerWindow extends JFrame implements SimulatorObserver{

	private Controller _ctrl;
	private SimulationViewer _viewer;
	private JFrame _parent;
	
	ViewerWindow(JFrame jFrame, Controller ctrl){
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = jFrame;
		initGUI();
		
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		
		//Poner contentPanel como mainPanel con scrollbarsa (JScrollPane)
		
		//Crear el viewer y añadirlo a mainPanel (En el centro)
		setContentPane(new JScrollPane(mainPanel));
		_viewer = new Viewer();
		mainPanel.add(_viewer, BorderLayout.CENTER);
		
		
		//En el método windowClosing, eliminar this de los observadores
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				_ctrl.removeObserver(ViewerWindow.this);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		pack();
		if(_parent != null)
			setLocation(_parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2,
					_parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
		setVisible(true);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	        _viewer.update();
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_viewer.reset();;
		
		for(BodiesGroup g: groups.values()) {
			_viewer.addGroup(g);
			for(Body b: g) {
				_viewer.addBody(b);
			}
		}
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			_viewer.addGroup(bg);
			for(Body b: bg) {
				_viewer.addBody(b);
			}
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_viewer.addGroup(g);
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		_viewer.addBody(b);
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

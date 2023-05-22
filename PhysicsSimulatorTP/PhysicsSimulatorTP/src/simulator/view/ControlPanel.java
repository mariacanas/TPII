package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private  boolean _stopped = true;
	private JButton _quitButton, _openButton, _runButton, _stopButton, _viewerButton, _physicsButton;
	private JTextField _dtField;
	
	private ForceLawsDialog _forceLawsDialog;
	private ViewerWindow _viewerWindows;
	private JSpinner _stepsSpinner;
	private JSpinner _delaySpinner;
	
	
	ControlPanel (Controller ctrl){
		_ctrl = ctrl;
		initGUI();
		
		ctrl.addObserver(this);
		
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar,BorderLayout.PAGE_START);
		
		//Crear los botones/atributos y añadirlos a _toolaBar.
			//Con su correspondiente tooltip. Utillizar _toolarBar.addSeparator() para
			//añadir la línea de separación vertical entre las componentes que lo necesiten
		
		//Botones
		
		//Open Button
		_openButton = new JButton();
		_openButton.setToolTipText("Open");
		_openButton.setIcon(new ImageIcon("resources/icons/open.png"));
		_openButton.addActionListener((e) -> {
			_fc = new JFileChooser("C:\\Users\\maryb\\OneDrive\\Escritorio\\UNIVERSIDAD\\2023\\2 CUATRIMESTRE\\TP2\\PRACTICAS\\1\\PhysicsSimulatorTP\\PhysicsSimulatorTP\\resources\\examples\\input");
	    int returnVal = _fc.showOpenDialog(Utils.getWindow(this));
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	File file = _fc.getSelectedFile();
	         try {
	              InputStream in = new FileInputStream(file);
	              _ctrl.reset();
	              _ctrl.loadBodies(in);
	          } catch (Exception ex) {
	              Utils.showErrorMsg("Error while loading the file: " + ex.getMessage());
	          }
	      }
	    });
		_toolaBar.add(_openButton);
		
		
		//Run Button
		_toolaBar.addSeparator();
		_runButton = new JButton();
		_runButton.setToolTipText("Run");
		_runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		_runButton.addActionListener((e) ->{
			
			_stopped = false;
			_openButton.setEnabled(false);
			_runButton.setEnabled(false);
			_physicsButton.setEnabled(false);
			_quitButton.setEnabled(false);
			_viewerButton.setEnabled(false);
			
			_ctrl.setDeltaTime(Double.parseDouble(_dtField.getText()));
			run_sim((Integer) _stepsSpinner.getValue());
	
		});
		_toolaBar.add(_runButton);
		
		
		
		//Stop Button
		//_toolaBar.addSeparator();
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop");
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {

				_stopped = true;
			}
			
		});
		_toolaBar.add(_stopButton);
		
		//Viewer Button
		_toolaBar.addSeparator();
		_viewerButton = new JButton();
		_viewerButton.setToolTipText("Viewer");
		_viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		_viewerButton.addActionListener((e) -> {
			_viewerWindows  = new ViewerWindow((JFrame)SwingUtilities.getWindowAncestor(this), _ctrl);
		});
		_toolaBar.add(_viewerButton);
		
		//Physics Button
		//_toolaBar.addSeparator();
		_physicsButton = new JButton();
		_physicsButton.setToolTipText("Physics");
		_physicsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		_physicsButton.addActionListener((e) -> {
		    if (_forceLawsDialog == null) {
		        _forceLawsDialog = new ForceLawsDialog(Utils.getWindow(this), _ctrl);
		    }
		    _forceLawsDialog.open();
		});
		_toolaBar.add(_physicsButton);
		
		//Spinner
		_toolaBar.addSeparator();
		_toolaBar.add(new JLabel(" Steps:"));
		_stepsSpinner = new JSpinner(new SpinnerNumberModel(500, 1, 1000, 100));
		
		_toolaBar.add(_stepsSpinner);
		
		
		//Delta-Time
		_toolaBar.addSeparator();
		_toolaBar.add(new JLabel("Delta Time:"));
		_dtField = new JTextField(8);
		_dtField.setMaximumSize(new Dimension(80,40));
		_toolaBar.add(_dtField);
		
		//Quit Button
		_toolaBar.add(Box.createGlue());
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
	}
	
	//Resto de métodos
	
	
	private void run_sim(int n) {
		if(n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			}  catch (Exception e) {
				Utils.showErrorMsg(e.getMessage());
				_openButton.setEnabled(true);
				_runButton.setEnabled(true);
				_physicsButton.setEnabled(true);
				_quitButton.setEnabled(true);
				_viewerButton.setEnabled(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
			
		}
		else {
			_openButton.setEnabled(true);
			_runButton.setEnabled(true);
			_physicsButton.setEnabled(true);
			_quitButton.setEnabled(true);
			_viewerButton.setEnabled(true);
			_stopped = true;
			
		}
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_dtField.setText(String.valueOf(dt));
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_dtField.setText(String.valueOf(dt));
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		this._dtField.setText(String.valueOf(dt));
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
}

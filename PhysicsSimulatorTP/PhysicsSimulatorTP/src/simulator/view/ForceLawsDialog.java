package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog implements SimulatorObserver {
	
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = {"Key", "Value", "Description"};
	
	private int _status;
	private int _selectedLawsIndex;
	
	ForceLawsDialog(Frame parent, Controller ctrl){
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setTitle("Force Laws Selection");
		
		JPanel forcePanel = new JPanel();
		
		JPanel mainPanel = new JPanel();
		JPanel buttons = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		_status=0;
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		_dataTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return column==1;
				
				//Hacer editable solo la columna 1
			}
			
		};
		
		_dataTableModel.setColumnIdentifiers(_headers);
		
		JTable table = new JTable(_dataTableModel);
	    mainPanel.add(new JScrollPane(table));
	 
		
		_lawsModel = new DefaultComboBoxModel<>();
		
		//Añadir la desc de todas las leyes de fuerza a _lawsModel
		for(int i =0; i < _forceLawsInfo.size();i++) {
			_lawsModel.addElement(_forceLawsInfo.get(i).getString("desc"));
		}
		
		//Crear un combobox que use _lawsModel y añadirlo al panel
		
		JComboBox<String> _laws = new JComboBox<>(_lawsModel);
		mainPanel.add(_laws);
		
		
		 
	        _selectedLawsIndex = _laws.getSelectedIndex();;
	        _dataTableModel.setRowCount(0);
	         JSONObject info = _forceLawsInfo.get(_selectedLawsIndex);
	         JSONObject data = info.getJSONObject("data");
		    for (String key : data.keySet()) {
		        _dataTableModel.addRow(new Object[]{key, "", data.get(key)});
		    }
		
		_laws.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 _selectedLawsIndex = _laws.getSelectedIndex();
			        
			        _dataTableModel.setRowCount(0);
			         JSONObject info = _forceLawsInfo.get(_selectedLawsIndex);
			         JSONObject data = info.getJSONObject("data");
				    for (String key : data.keySet()) {
				        _dataTableModel.addRow(new Object[]{key, "", data.get(key)});
				    }
			}
		});
		       
		        

		
		_groupsModel = new DefaultComboBoxModel<>();
		
		//Crear un combobox que use _grouosModel y añadirlo al panel
		
		JComboBox<String> _groups = new JComboBox<String>(_groupsModel);
		mainPanel.add(_groups);
		
		forcePanel.add(_laws);
		forcePanel.add(_groups);
		
		mainPanel.add(forcePanel);
		
		//Crear los botones OK y Cancel y añadirlos al panel
		
		JButton Bok = new JButton("OK");
		JButton Bcancel = new JButton("Cancel");
		
		Bok.addActionListener(new ActionListener() {
		   
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//Convertir la informacion en la tabla en un JSONObject
				String s = "{";
	            for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
	                String key = (String) _dataTableModel.getValueAt(i, 0);
	                String value = (String)_dataTableModel.getValueAt(i, 1);
	                s+=key+":"+value;
	                if(i<_dataTableModel.getRowCount()-1) {
	                	s+=",";
	                }
	            }
				s+="}";
	            JSONObject data = new JSONObject(s);
				//Crear un JSONObject que tiene una clave data y type
	            JSONObject info = new JSONObject();
				
	            info.put("data",data );
	            info.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));
	      
	            //Llama al controlador fijando esta ley en el grupo seleccionado
	            try {
		            _ctrl.setForceLaws(_groupsModel.getSelectedItem().toString(), info);
		            _status = 1;
		            setVisible(false);
	            }catch(Exception er) {
	            	Utils.showErrorMsg("Error force law " + er.getMessage());
	            }
			}
		});
		
		Bcancel.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Cerrar la ventana de diálogo
		    	_status = 0;
		        setVisible(false);
		    }
		});
		
		
		buttons.add(Bok);
		buttons.add(Bcancel);
		
		mainPanel.add(buttons);
		
		setPreferredSize(new Dimension(700,400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public int open() {
		if(_groupsModel.getSize() == 0) {
			return _status;
		}
		
		//Establecer la pos de la ventana de dialogo de tal manera que se 
		//abra en el centro de la ventana principal
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
		return _status;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groupsModel.removeAllElements();
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg : groups.values()) {
			_groupsModel.addElement(bg.getId());
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_groupsModel.addElement(g.getId());;
		
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

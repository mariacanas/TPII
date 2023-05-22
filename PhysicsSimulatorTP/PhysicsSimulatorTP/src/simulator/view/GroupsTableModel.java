package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver{
	
	String[] _header = {"Id", "Force Laws", "Bodies"};
	List<BodiesGroup> _groups;
	
	GroupsTableModel(Controller ctrl){
		_groups = new ArrayList<>();
		ctrl.addObserver(this);
		
	}
	
	//Resto de métodos

	@Override
	public int getRowCount() {
		return _groups.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}
	
	@Override
	public String getColumnName(int index) {
		return _header[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BodiesGroup bgroup = _groups.get(rowIndex);
		switch(columnIndex) {
		case 0: return bgroup.getId(); 
		case 1: return bgroup.getForceLawsInfo(); 
		case 2: 
			String s = "";
			for(Body b: _groups.get(rowIndex)) {
				s += b.getId() + " " ;
			}
			return s;
		}
		return null;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
		
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groups = new ArrayList<>();
		
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			if(!_groups.contains(bg)) {
				_groups.add(bg);
			}
		}
		
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			if(!_groups.contains(bg)) {
				_groups.add(bg);
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			if(!_groups.contains(bg)) {
				_groups.add(bg);
			}
		}
		
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

}

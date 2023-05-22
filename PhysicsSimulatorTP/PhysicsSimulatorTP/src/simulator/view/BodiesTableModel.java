package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{

	String[] _header = {"Id", "gId", "Mass", "Velocity", "Position", "Force"};
	List<Body> _bodies;
	List<BodiesGroup> _bg;
	
	BodiesTableModel(Controller ctrl){
		_bodies = new ArrayList<>();
		_bg = new ArrayList<>();
		ctrl.addObserver(this);
		
	}
	
	//Resto de métodos

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public String getColumnName(int idx) {
		return _header[idx];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = _bodies.get(rowIndex);
		switch(columnIndex) {
		case 0: return b.getId(); 
		case 1: return b.getgId(); 
		case 2: return b.getMass();
		case 3: return b.getVelocity();
		case 4: return b.getPosition();
		case 5: return b.getForce();
		default: throw new IllegalArgumentException("Algumento no valido");
		}
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_bodies = new ArrayList<>();
		
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			if(!_bg.contains(bg)) {
				_bg.add(bg);
			}
			for(Body b: bg) {
				if(!_bodies.contains(b)) {
					_bodies.add(b);
				}
			}
		}
		
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			if(!_bg.contains(bg)) {
				_bg.add(bg);
			}
			for(Body b: bg) {
				if(!_bodies.contains(b)) {
					_bodies.add(b);
				}
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg: groups.values()) {
			if(!_bg.contains(bg)) {
				_bg.add(bg);
			}
			for(Body bb: bg) {
				if(!_bodies.contains(bb)) {
					_bodies.add(bb);
				}
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
		
	}
	
	
	
	
	
}

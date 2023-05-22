package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver> {

	private double tr;
	private ForceLaws force;
	private Map<String,BodiesGroup> mapa;
	private double ta;
	private List<BodiesGroup> lboodies;
	
	private List<SimulatorObserver> lobserver;
	private Map<String, BodiesGroup> _groupsR0;
	
	
	public PhysicsSimulator(ForceLaws force, double tr) {
		if(tr < 0) {
			throw new IllegalArgumentException("El valor del tiempo debe ser válido");
		}
		if(force == null) {
			throw new IllegalArgumentException("No puede haber un valor nulo");
		}
		this.tr = tr;
		this.force = force;
		this.mapa = new HashMap<String,BodiesGroup>();
		this.ta = 0;
		this.lboodies = new LinkedList<BodiesGroup>();
		
		this.lobserver = new ArrayList<>();
		this._groupsR0 = Collections.unmodifiableMap(mapa);
	}
	
	public void advance() {
		for(BodiesGroup i: mapa.values()) {
			i.advance(tr);;
		}
		ta += tr;
		
		for(int i = 0; i < lobserver.size();i++) {
			lobserver.get(i).onAdvance(mapa, ta);
		}
	}
	
	
	public void addGroup(String id) {
		if(mapa.containsKey(id)) {
			throw new IllegalArgumentException("Ya existe un grupo en el simulador con este mismo id");
		}
		BodiesGroup b = new BodiesGroup(id,force);
		lboodies.add(b);
		mapa.put(id,b);
		
		for(int i = 0; i < lobserver.size();i++) {
			lobserver.get(i).onGroupAdded(_groupsR0, b);
		}
		
	}
	
	
	public void setForceLaws(String id, ForceLaws fl) {
		 if (!mapa.containsKey(id)) {
	            throw new IllegalArgumentException("No existe ningún grupo con el identificador " + id);
	        }
	        mapa.get(id).setForceLaws(fl);
	        for(int i = 0; i < lobserver.size();i++) {
				lobserver.get(i).onForceLawsChanged(mapa.get(id));
			}
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		
		json.put("time", ta);

		JSONArray ja = new JSONArray();
		
		for(BodiesGroup bg: lboodies) {
			ja.put(bg.getState());
		}
		json.put("groups", ja);
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}

	public void addBody(Body bod) {

		  String groupId = bod.getgId();
	        if (!mapa.containsKey(groupId)) {
	            throw new IllegalArgumentException("No existe ningún grupo con el identificador " + groupId);
	        }
	        mapa.get(groupId).addBody(bod);
	        
	        for(int i = 0; i < lobserver.size();i++) {
				lobserver.get(i).onBodyAdded(_groupsR0, bod);
			}
	}
	
	
	//Nuevo Practica2
	
	public void reset() {
		lboodies.clear();
		mapa.clear();
		ta = 0.0;
		for(int i = 0; i < lobserver.size();i++) {
			lobserver.get(i).onReset(_groupsR0, ta,tr);
		}
		
	}
	
	public void setDeltaTime(double dt) {
		if(dt<0) {
			throw new IllegalArgumentException("dt no es positivo");
		}
		this.tr = dt;
		
		for(int i = 0; i < lobserver.size();i++) {
			lobserver.get(i).onDeltaTimeChanged(tr);;
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		
		if(!lobserver.contains(o)) {
			lobserver.add(o);

			
		}
		o.onRegister(_groupsR0, ta, tr);
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		lobserver.remove(o);
		
	}
	
	
}

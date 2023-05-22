package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body> {

	private String id;
	private List<Body> bodies;
	private ForceLaws force;
	
	private List<Body> _bodiesR0;
	
	public BodiesGroup(String id, ForceLaws f) {
		
		if(id == null || f == null) {
			throw new IllegalArgumentException("No puede haber parámetros nulos");
		}
		if(id.trim().length()==0) {
			throw new IllegalArgumentException("Id no incluye al menos un caracter que no sea espacio en blanco");
		}
		
		this.id=id;
		this.force = f;
		this.bodies = new ArrayList<Body>();
		
		this._bodiesR0 = Collections.unmodifiableList(bodies); 
	}
	
	
	
	public String getId() {
		return this.id;
	}
	
	void setForceLaws(ForceLaws fl) {
		if(fl == null) {
			throw new IllegalArgumentException("No puede haber parametros nulos");
		}
		this.force = fl;
	}
	
	void addBody(Body b) {
		if(b == null) {
			throw new IllegalArgumentException("No puede haber parametros nulos");
		}
		
		for(Body bo: bodies){
			if(bo.equals(b)) {
				throw new IllegalArgumentException("Ya existe este cuerpo en nuestra lista");
			}
		}
		bodies.add(b);
	}
	
	
	void advance(double dt) {
		if(dt <= 0) {
			throw new IllegalArgumentException("El tiempo debe tener un valor positivo");
		}
		
		for(Body b : bodies) {
			b.resetForce();
		}
		
		force.apply(bodies);
		
		for(Body b: bodies){
			b.advance(dt);
		}
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : id.hashCode());
		return result;
	}
	
	public boolean equeals(Object o) {
		return this == o;
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());

		JSONArray aBodies = new JSONArray();
		
		for(Body b: bodies) {
			aBodies.put(b.getState());
		}
		
		json.put("bodies",aBodies);
		
		return json;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	//Nuevo Practica 2
	
	public String getForceLawsInfo() {
		return force.toString();
	}



	@Override
	public Iterator<Body> iterator() {
		return _bodiesR0.iterator();
	}



	
}


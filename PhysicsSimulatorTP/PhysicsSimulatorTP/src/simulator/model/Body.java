package simulator.model;

import java.util.Objects;
import java.util.Vector;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract  class Body {

	protected String id;
	protected String gid;
	protected Vector2D v = new Vector2D();
	protected Vector2D f = new Vector2D();
	protected Vector2D p =new Vector2D();
	protected Double m;
	
	//Constructor por defecto más excepción
	public Body(String id, String gid, Vector2D p, Vector2D v, Double m) {
		
		if(id == null || gid == null || v == null || p == null || m == null) {
			throw new IllegalArgumentException("Alguno de los parámetros es nulo");
		}
		if(id.trim().length() == 0 || gid.trim().length() == 0) {
			throw new IllegalArgumentException("Id o Gid no incluyen al menos un caracter que no sea espacio en blanco");
		}
		if(m <= 0) {
			throw new IllegalArgumentException("La masa es negativa");
		}
		
		
		this.id = id;
		this.gid = gid;
		this.v = v;
		this.p = p;
		this.m = m;
		this.f = new Vector2D();
	}
	
	//Metodos set
	
	public String getId() {
		return id;
	}
	public String getgId() {
		return gid;
	}
	public Vector2D getVelocity() {
		return v;
	}
	public Vector2D getForce() {
		return f;
	}
	public Vector2D getPosition() {
		return p;
	}
	public double getMass() {
		return m;
	}
	
	
	void addForce(Vector2D v) {
		 double x = f.getX() + v.getX();
	     double y = f.getY() + v.getY();
	     f = new Vector2D(x,y);
	}
	void resetForce() {
		this.f = new Vector2D(0,0);
	}
	
	abstract void advance(double dt);
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("p",getPosition().asJSONArray());
		json.put("v",getVelocity().asJSONArray());
		json.put("f",getForce().asJSONArray());
		json.put("m",getMass());
		return json;
		
	}
	
	public String toString() {
		return getState().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		Body other = (Body) obj;
		return Objects.equals(id, other.id);
	}
}

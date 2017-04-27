package visitor;

import java.util.Enumeration;
import java.util.Hashtable;

import syntaxtree.IdentifierType;
import syntaxtree.Type;


public class Class {

	String id;
	Hashtable<String, Method> methods;
	Hashtable<String, Variable> globals;
	String parent;
	Type type;

	public Class(String id, String p) {
		this.id = id;
		parent = p;
		type = new IdentifierType(id);
		methods = new Hashtable<String, Method>();
		globals = new Hashtable<String, Variable>();
	}

	public Class() {}

	public String getId(){ return id; }

	public Type type(){ return type; }

	public boolean addMethod(String id, Type type) {
		if(containsMethod(id)) 
			return false;       
		else {
			methods.put(id, new Method(id, type));
			return true;
		}
	}

	public Enumeration<String> getMethods(){
		return methods.keys();
	}

	public Method getMethod(String id) {
		if(containsMethod(id)) 
			return (Method)methods.get(id);
		else 
			return null;
	}

	public boolean addVar(String id, Type type) {
		if(globals.containsKey(id)) 
			return false;
		else{
			globals.put(id, new Variable(id, type));
			return true;
		}
	}

	public Variable getVar(String id) {
		if(containsVar(id)) 
			return (Variable)globals.get(id);
		else 
			return null;
	}

	public boolean containsVar(String id) {
		return globals.containsKey(id);
	}

	public boolean containsMethod(String id) {
		return methods.containsKey(id);
	}

	public String parent() {
		return parent;
	}	    
} // Class
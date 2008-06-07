package pl747.tabelaSimbolos;
/**
 * Descreve um tipo estrutura (an�nimo).
 */

import java.util.*;

public class StructTypeSymb extends PrimTypeSymb {
	
	private LinkedList<FieldSymb> fieldList; // lista de campos
	private HashMap<String,FieldSymb> fieldMap; // mapa c/ os campos
	
	/** 
	 * Construtor
	 * @param name nome da estrutura (anonimo)
	 */
	public StructTypeSymb(){
		super(null); // o tipo estrutura � sempre an�nimo!
		this.kind = STRUCT_TYPE;
		fieldList = new LinkedList<FieldSymb>();
		fieldMap = new HashMap<String,FieldSymb>();
	}
	
	/** getters & setters **/
	public void setFieldList(LinkedList<FieldSymb> list) { 
		fieldList = list;
		fieldMap = new HashMap<String,FieldSymb>();
		for(FieldSymb s : list) {
		  fieldMap.put(s.getName(),s);	
		}
	}
	
	public LinkedList<FieldSymb> getFieldList(){ return fieldList; }
	
	/**
	 * Insere novo campo
	 * @param field
	 * @return true se a inser��o for bem sucedida (false: nome j� presente)
	 */
	public boolean addField(FieldSymb field){
		if(fieldMap.get(field.getName())== null){
			fieldList.add(field);
			fieldMap.put(field.getName(), field);
			field.setStruct(this);
			return true;
		}
		return false;
	}
	
	/**
	 * Busca por um campo numa estrutura a partir do seu nome.
	 * @param name nome do campo.
	 * @return campo da estrutura.
	 */
	public FieldSymb getField(String name){
		return fieldMap.get(name);
	}

    @Override
    public String toString(){
    	String fs = "";
    	for(FieldSymb f: fieldList){
    	  fs +=f.getName()+":"+f.getType().getName()+" ";	
    	}
    	return "[struct name:"+name+" level:"+level+"{"+fs+"}]";
    }
    
	
}

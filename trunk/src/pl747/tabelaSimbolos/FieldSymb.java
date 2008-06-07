package pl747.tabelaSimbolos;
/**
 * Representa um campo de uma estrutura
 *
 */

public class FieldSymb extends VarSymb {
	
	private StructTypeSymb struct; // estrutura que cont�m este campo
	
	/**
	 * Construtor
	 * @param name - nome do campo
	 * @param level - n�vel de encaixamento
	 * @param type - tipo do campo
	 */
	public FieldSymb(String name, int level, PrimTypeSymb type){
		super(name,level,type);
		this.kind = FIELD_SYMB;
		struct = null;
	}
	
	
	/** getter & setters **/
	public void setStruct(StructTypeSymb struct) { this.struct = struct; }
	public StructTypeSymb getStruct() { return struct; }
	
    @Override
    public String toString(){
    	return "[field name:"+name+" level:"+level+" type:"+type.toString()+"]";
    }
    
	

}

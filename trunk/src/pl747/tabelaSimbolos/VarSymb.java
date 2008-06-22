package pl747.tabelaSimbolos;
/**
 * Descreve uma vari�vel declarada no programa ou numa fun��o.
 *
 */

public class VarSymb extends Symbol {
	
	/* Variáel adicionada */
	private int address;
	
	PrimTypeSymb type; // tipo da vari�vel descrita por este s�mbolo
	
	/**
	 * devolve o tipo da vari�vel descrita por este s�mbolo
	 * @return tipo da vari�vel descrita por este s�mbolo
	 */
	public PrimTypeSymb getType() { return type; }
	
	/**
	 * Construtor 
	 * @param name - nome da vari�vel
	 * @param level - n�vel da vari�vel
	 * @param type - tipo da vari�vel
	 */
	public VarSymb(String name, int level, PrimTypeSymb type){
		super(name,level,VAR_SYMB);
		this.type = type; 
	}
	
    @Override
    public String toString(){
    	return "[var name:"+name+" level:"+level+" type:"+type.toString()+"]";
    }
    
    /* Código adicionado */
    public void setAddress(int address) {
		this.address = address;
	}
	
	public int getAddress() {
		return this.address;
	}

}

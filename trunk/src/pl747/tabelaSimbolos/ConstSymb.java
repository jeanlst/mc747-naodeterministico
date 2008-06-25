package pl747.tabelaSimbolos;

/**
 * Representa uma constante da linguagen
 */

import pl747.semantico.AbsNode;

public class ConstSymb extends Symbol{
	
    private AbsNode value;  // valor da constante (string ou tupla)
    
    private int address;
    
    /**
     * Construtor
     * @param name - nome da constante
     * @param level - n�vel de encaixamento
     * @param value - valor da constante
     */
    public ConstSymb(String name, int level, AbsNode value){
    	super(name,level,CONST_SYMB);
    	this.value = value;
    }
    
    /** getters & setters **/
    public AbsNode getValue() { return value; }
    public void setValue(AbsNode value) { this.value = value; }
    
    @Override
    public String toString(){
    	return "[const name:"+name+" level:"+level+" value:"+value+"]";
    }
    
    /* Código adicionado */
    public void setAddress(int address) {
		this.address = address;
	}
	
	public int getAddress() {
		return this.address;
	}
    
}

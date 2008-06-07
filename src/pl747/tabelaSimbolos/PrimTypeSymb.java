package pl747.tabelaSimbolos;
/**
 * Descreve um tipo primitivo da linguagem
 *
 */

public class PrimTypeSymb extends Symbol{
	
	public PrimTypeSymb(String name){
		super(name,0,PRIM_TYPE);
	}
    @Override
    public String toString(){
    	return "[primType name:"+name+" level:"+level+"]";
    }
    
}

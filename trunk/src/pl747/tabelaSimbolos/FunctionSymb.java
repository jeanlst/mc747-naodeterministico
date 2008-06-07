package pl747.tabelaSimbolos;
/**
 *   Descreve uma fun��o
 */
import java.util.*;

public class FunctionSymb extends VarSymb{
	
	List<ParmSymb> parmList;
	boolean isProtype;
	
	/**
	 * Construtor
	 * @param name - nome da fun��o
	 * @param type - tipo do valor de retorno
	 */
	public FunctionSymb(String name, PrimTypeSymb type){
	  super(name,0,type);
	  kind = FUNCTION_SYMB;
	  parmList = new ArrayList<ParmSymb>();
	}
	public FunctionSymb(String name, PrimTypeSymb type, boolean isProtype){
		  super(name,0,type);
		  kind = FUNCTION_SYMB;
		  parmList = new ArrayList<ParmSymb>();
		  this.isProtype = isProtype;		  
		}
	
	public boolean isPrototype(){
		return this.isProtype;		
	}
	
	public void setPrototype(boolean isPrototype){
		this.isProtype = isPrototype;
	}
	
	
	/** getters & setters **/
	public void setParmList(List<ParmSymb> list) { parmList = list; }
	public List<ParmSymb> getParmList() { return parmList; }

    @Override
    public String toString(){
    	return "[function name:"+name+" level:"+level+" type:"+type.toString()+"]";
    }
    
	
}

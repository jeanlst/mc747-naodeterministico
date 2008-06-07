package pl747.tabelaSimbolos;
/**
 * Descreve um par�metro de fun��o
 *
 */

public class ParmSymb extends VarSymb {

     private boolean refMode; // modo de passagem de par�metros
     
     /**
      * Construtor
      * @param name nome do par�metro
      * @param type tipo do par�metro
      * @param mode modo de passagem(true => refer�ncia, false => valor)
      */
	 public ParmSymb(String name, PrimTypeSymb type, boolean mode){
		 super(name,2,type);
		 this.kind = PARM_SYMB;
		 this.refMode = mode;
	 }
	 
	 /** getters & setters **/
	 public boolean getRefMode() { return refMode; }
	 public void setRefMode(boolean mode) { refMode = mode; }

	    @Override
	    public String toString(){
	    	return "[parm name:"+name+" level:"+level+" type:"+type.toString()+"]";
	    }
	    
	 
}

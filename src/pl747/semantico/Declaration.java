package pl747.semantico;

import pl747.tabelaSimbolos.*;

/**
 * Classe abstrata a partir da qual sao derivadas as classes que representam as declara��es da linguagem.
 */
public abstract class Declaration extends AbsNode {

	protected String name;
				
	/**
	 * Construtor de Declaration
	 * @param name
	 */
	public Declaration(String name) {
		this.name = name;
	}
	
	/**
	 * Retorna o nome do objeto declarado
	 * @return name : String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Preenche o nome do objeto declarado
	 * @param name : String
	 */
	public void setName(String name) {
		this.name = name;
	}	
	
	public PrimTypeSymb getSymbType(){		
		return new  PrimTypeSymb(this.name);		
	}
}	
	
	


package pl747.semantico;

import pl747.tabelaSimbolos.*;

/**
 * Classe abstrata que representa um comando da linguagem. 
 */
public abstract class StatOp extends Expression {

	/**
	 * Retorna o objeto Scope (tabela de simbolos) que contem as declaracoes feitas neste comando (se for o caso).
	 */
	public Scope getScope() {
		return SymbolTable.getCurScope();
	}
	
}

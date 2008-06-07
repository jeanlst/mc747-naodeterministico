package pl747.tabelaSimbolos;
/**
 *  Cont�m as declara��es feitas num 'escopo': programa principal ou fun��o.
 */

import java.util.*;
/**
 * Define o escopo, na tabela de s�mbolos, onde est�o contidas
 * as defini��es dos s�mbolos declarados localmente a uma fun��o ou comando.
 * 
 */
public class Scope {
	
	private Scope upperScope; // escopo que cont�m este escopo
	private int level;        // n�vel de encaixamento
	private LinkedList<Symbol> symbolList; // lista c/ os s�mbolos locais a este escopo
	private HashMap<String,Symbol> symbolMap; // mapa c/ os s�mbolos locais a este escopo
	

	
	/**
	 * construtor
	 */
	public Scope(Scope upper){
		upperScope = upper;
		if(upper == null) level = 0; else level = upper.getLevel()+1;
		symbolList = new LinkedList<Symbol>();
		symbolMap = new HashMap<String,Symbol>();
	}

	/**
	 * Retorna uma lista contendo os s�mbolos associados a este escopo.
	 * @return lista de s�mbolos
	 */
	public List<Symbol> getSymbList() { return symbolList; }

	/**
	 * Devolve o escopo onde o escopo atual est� contido.
	 * @return o escopo
	 */
	public Scope getUpperScope() { return upperScope; }

	/**
	 * Procura por um s�mbolo com o nome indicado, devolvendo sua refer�ncia ou null em caso contr�rio. A procura � feita a partir do escopo atual e se estende ao escopo mais externo, se este existir.
	 * @param name nome do s�mbolo a ser procurado
	 * @return s�mbolo procurado ou null se n�o encontrado.
	 */
	public Symbol getSymbol( String name ) {
	  return symbolMap.get(name); 
	}
	
	/**
	 * Insere s�mbolo na tabela
	 * @param symb - simbolo a ser inserido
	 * @return true se inser��o ok (false se j� existe simbolo c/ mesmo nome).
	 */
	public boolean addSymbol(Symbol symb){
	  if(symbolMap.get(symb.getName()) != null) return false;
	  symbolMap.put(symb.getName(), symb);
	  symbolList.add(symb);
	  return true;
	}
	
	/**
	 * Devolve o n�vel de encaixamento deste escopo
	 * @return n�vel de encaixamento.
	 */
	public int getLevel(){ return level; }

}

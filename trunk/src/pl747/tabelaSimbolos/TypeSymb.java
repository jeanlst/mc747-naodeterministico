package pl747.tabelaSimbolos;

import java.util.List;


public class TypeSymb extends Symbol {

	
	private int kind;
	
	/**
	 * Construtor para si�mbolos de tipos
	 * @param name nome do simbolo
	 * @param level nivel do simbolo
	 * @param type tipo do simbolo
	 */
	public TypeSymb(String name, int level, int kind) {
		super(name, level,kind);
	}
	
		
	public int getKind() {
		return this.kind;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
}

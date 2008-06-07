package pl747.semantico;

import java.util.List;

import pl747.TreeNode;

/**
 * Representa um tipo primitivo da linguagem. Os tipos primitivos definidos na linguagem, int, boolean e char serao representadas por instancias desta classe.
 */
public class Type extends Declaration {
	

	public Type(String name) {
		super(name);
    }
   	
	
	public void addChild(TreeNode child) throws Exception {
			
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

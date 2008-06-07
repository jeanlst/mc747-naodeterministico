package pl747.semantico;
import java.util.*;

import pl747.TreeNode;
import pl747.Visitor;
/**
 * Representa um comando composto.
 */
public class CompoundStat extends StatOp {

		
	private List<TreeNode> statList;
	
	/**
	 * Construtor de CompoundStat
	 */	
	public CompoundStat() {
		this.statList = new ArrayList<TreeNode>();
		this.type = new Type("void");
	}
	
	/**
	 * Adiciona no filho ao comando composto
	 */
	public void addChild(TreeNode child) {	
		this.statList.add((TreeNode)child);	
	}

	/**
	 * Devolve a lista de comandos que constituem o comando composto representado por este objeto.
	 */
	@SuppressWarnings("unchecked")
	public List<Expression> getStatList() {			
		return (List) this.statList;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * (Verifica se todos os comandos internos ao comando composto estao corretos)
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		
		boolean result = true;		
		
		// Verificando todos os comandos internos
		for (TreeNode stat : this.statList) {
			if (!stat.check(errorList))
				result = false;
		}
		
		return result;
	}
	
}

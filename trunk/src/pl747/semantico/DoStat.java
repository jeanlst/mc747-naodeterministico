package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;

/**
 * Representa um comando do tipo 'do-while'.
 */
public class DoStat extends StatOp {

	
	private Expression cond;
	private CompoundStat stat;
	
	/**
	 * Construtor de DoStat
	 * @param cond
	 * @param stat
	 */
	public DoStat(TreeNode cond, TreeNode stat) {
		this.cond = (Expression)cond;
		this.stat = (CompoundStat)stat;
	}

	public void addChild(TreeNode child) throws Exception {		
		this.stat.addChild(child);
	}
		
	/**
	 * Retorna a condicao associada ao comando.
	 */
	public Expression getCondition() {
		return this.cond;
	}

	/**
	 * Devolve o comando a ser repetido.
	 */
	public Expression getStat() {
		return this.stat;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		
		boolean r1 = true;
		boolean r2 = true;
		
		if (!((Type)this.cond.getType()).getName().equals("boolean")){
			errorList.add("Condicao invalida - a expressao deve retornar um valor do tipo boolean");	
			r1 = false;
		}
		
		r2 = this.stat.check(errorList);
		
		return r1 && r2;
		
	}

	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
}

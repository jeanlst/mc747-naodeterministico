package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;

/**
 * Representa um comando repetitivo do tipo 'while'.
 */
public class WhileStat extends StatOp {

	private Expression cond;
	private CompoundStat stat;	
	
	public WhileStat(TreeNode cond, TreeNode stat) {
		this.cond = (Expression) cond;
		this.stat = (CompoundStat) stat;
	}

	/**
	 * Devolve a condicao associada ao comando while.
	 */
	public Expression getCondition() {
		return cond;
	}

	/**
	 * Devolve o comando que sera repetido enquanto a condicao for verdadeira.
	 */
	public Expression getStat() {
		return stat;
	}

	public void addChild(TreeNode child) throws Exception {	
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		boolean r1 = true;
		boolean r2;
		
		if (!(((Type)this.cond.getType()).getName().equals("boolean"))){
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

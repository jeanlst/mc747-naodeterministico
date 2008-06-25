package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;

/**
 * Representa um comando condicional.
 */
public class IfStat extends StatOp {

	private Expression cond;
	private TreeNode stat1;
	private TreeNode stat2;
	
	public IfStat(TreeNode cond, TreeNode stat1, TreeNode stat2) {
		this.cond = (Expression) cond;
		this.stat1 = stat1;
		this.stat2 = stat2;
	}

	public void addChild(TreeNode child) throws Exception {
				
	}
	
	/**
	 * Devolve a condicao associada ao comando condicional representado por este objeto.
	 */
	public Expression getCondition() {

		return this.cond;
	}

	
	/**
	 * Devolve o comando a ser executado caso a condicao seja verdadeira.
	 */
	public Expression getThenPart() {

		return (Expression) stat1;
	}

	/**
	 * Devolve o comando a ser executado caso a condicao seja falsa.
	 */
	public Expression getElsePart() {

		return (Expression) stat2;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		
		boolean r0 = this.cond.check(errorList);
		boolean r1 = true;
		boolean r2 = true;
		boolean r3 = true;
		
		
		
		if (!((Type)this.cond.getType()).getName().equals("boolean"))
		{
			errorList.add("Condicao invalida - a expressao deve retornar um valor do tipo boolean");	
			r1 = false;
		}
		
		r2 = this.stat1.check(errorList);
		
		if (this.stat2 != null)
			r3 = this.stat2.check(errorList);		
		
		return r0 && r1 && r2 && r3;
	}

	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
}

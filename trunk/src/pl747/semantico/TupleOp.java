package pl747.semantico;

import java.util.*;

import pl747.TreeNode;
import pl747.Visitor;
/**
 * Descreve uma tupla.
 */
public class TupleOp extends Expression {

	private List<Expression> elementList;
		
	public TupleOp(){
		this.elementList = new ArrayList<Expression>();	
		this.type = new StructType();
	}
	
	public void addChild(TreeNode child) throws Exception {
		this.elementList.add((Expression)child);		
	}
	
	/**
	 * Devolve a lista de valores associada a tupla representada por este objeto.
	 */
	public List<Expression> getElementList() {
		return elementList;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		return true;	
	}

	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
}

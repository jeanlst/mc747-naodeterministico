package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;

/**
 * Representa um string.
 */
public class StringOp extends Expression {

	
	private String value;
	
	
	public StringOp(String value) {
		this.value = value;
		this.type = new VectorType(String.valueOf(this.value.length()),(TreeNode)new Type("char")); 
		
	}

	public void addChild(TreeNode child) throws Exception {}
	
	/**
	 * Devolve o string representado por este objeto.
	 * @return TODO
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		
		if (this.type instanceof VectorType) {
			VectorType t = (VectorType) this.type;		
			if(Integer.parseInt(t.getSize()) == this.value.length())
				return true;
		}		
		return false;
	}

	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
}

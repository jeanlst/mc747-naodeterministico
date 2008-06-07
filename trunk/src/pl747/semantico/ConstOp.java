package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;
/**
 * Representa uma constante usada numa expressao.
 */
public class ConstOp extends Expression {

	private String value;
	
	/**
	 * Construtor de ConstOp
	 * @param value
	 * @param type
	 */
	public ConstOp(String value, TreeNode type) {
		this.value = value;
		this.type = (Type)type;
	}
	
	/**
	 * Implementacao do addChild, herdado de AbsNode
	 */
	public void addChild(TreeNode child) throws Exception {
		Exception e = new Exception("Nao eh possi�vel adicionar filhos a uma constante");
		throw e;
	}

	/**
	 * Preenche o valor do Operador com o valor passado
	 * @param value : String
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Devolve o valor da constante representada por este objeto, como um string.
	 * @return value : String
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
		
		// Verificando se a constante possui tipo definido
		if (this.getType() == null){
			errorList.add("Constante sem tipo definido");
			return false;
		}				
		return true;
	}

}

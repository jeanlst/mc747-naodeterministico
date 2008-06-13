package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;
import pl747.tabelaSimbolos.*;

/**
 * Representa uma declaracao de constante da linguagem.
 */
public class ConstDeclaration extends Declaration {

	private Expression value;		
	
	
	/**
	 * Construtor de ConstDeclaration
	 * @param name : String
	 * @param value : TreeNode
	 */
	public ConstDeclaration(String name, TreeNode value) {
		super(name);
		this.type = ((Expression) value).type;
		this.value = (Expression) value;
	}
		
	/**
	 * Implementacao do addChild, herdado de AbsNode
	 */
	public void addChild(TreeNode child) throws Exception {
		Exception e = new Exception("Nao e possi�vel adicionar filhos a uma constante");
		throw e;
	}

	/**
	 * Devolve o objeto Expression que representa o valor (constante) relativo a este objeto ConstDeclaration.
	 */
	public Expression getValue() {
		return this.value;
	}
	
	/**
	 * Preenche o atributo value da Contante com o valor passado 
	 * @param value
	 */
	public void setValue(Expression value) {
		this.value = value;
	}

	/**
	 * Retorna o tipo da Constante
	 * @return Type
	 */
	public Type getTipo() {
		return type;
	}

	/**
	 * Preenche o atributo type da Constante
	 * @param type
	 */
	public void setTipo(TreeNode type) {
		this.type = (Type) type;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check( List<String> errorList ) {

		boolean result = true;
		
		// Verificando se a constante ja foi declarada no escopo
		if (SymbolTable.search(this.name) != null) {
			errorList.add("Constante " + this.name + "ja foi declarada.");
			result =  false;
		}
		else{
			// Verificando se o valor da constante eh um inteiro, caracter,
			// booleano, tupla, string ou nome de uma constante
			if ((this.value instanceof ConstOp)	 || 
				(this.value instanceof StringOp) || 
				(this.value instanceof TupleOp)){

				// Adicionando Constante na tabela de Simbolos
				Symbol symbol = new ConstSymb(this.name, SymbolTable
						.getCurLevel(), this.value);
				SymbolTable.getCurScope().addSymbol(symbol);
			} 
			else{
				errorList.add("Declaracao de Constante Invalida");
				result = false;
			}
		}
		return result;
	}
	
	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
}

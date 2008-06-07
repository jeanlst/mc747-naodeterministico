package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;

public class VarOp extends Expression {

	
	private String name;	
	
	public VarOp(String name) {
		this.name = name;
	}

	/**
	 * Implementacao do addChild, herdado de AbsNode
	 */
	public void addChild(TreeNode child) throws Exception {
		Exception e = new Exception("Nao eh possivel adicionar filhos a uma constante");
		throw e;
	}
	
	/**
	 * Devolve o nome da variavel (ou constante) representada por este objeto.
	 */
	public String getName() {		
		return this.name;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList){
		VarSymb symb = (VarSymb) SymbolTable.search(this.name);
		if (symb == null){			
			errorList.add("Variavel "+ this.name +" nao declarada ou nao acessivel neste escopo");
			return false;
		}
		else
		{
			if (symb.getType() instanceof VectorTypeSymb) {
				
				String tipoElem = ((VectorTypeSymb) symb.getType()).getElementType().getName();			
				String tamVetor = Integer.toString(((VectorTypeSymb) symb.getType()).getSize());					
				this.type = new VectorType(tamVetor, new Type(tipoElem));				
			}
			else
			{
				String tname = symb.getType().getName();
				this.type = new Type(tname);
			} 
			return true;
		}
		
	}

}

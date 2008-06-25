package pl747.semantico;

import java.util.LinkedList;
import java.util.List;

import pl747.TreeNode;
import pl747.Visitor;
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

	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
	
	
	public static Type findType(Symbol symb) throws Exception
	{
		if (symb instanceof VectorTypeSymb) {
			VectorTypeSymb vector = (VectorTypeSymb) symb;
			
			Symbol element = vector.getElementType();
			Type elementType = findType(element);
			
			String size = String.valueOf(vector.getSize());
			VectorType type = new VectorType(size,elementType);
			return type;
			
		}
		
		if (symb instanceof StructTypeSymb) {
			StructTypeSymb struct = (StructTypeSymb) symb;
			
			StructType type = new StructType();
			
			for (FieldSymb field : struct.getFieldList()) {
								
				String fieldName = field.getName();
				Symbol fieldTypeSymb = field.getType();
				
				Type fieldType = findType(fieldTypeSymb);			
				
				VarDeclaration addField = new VarDeclaration(false,fieldType,fieldName);
				
				type.addChild((TreeNode)addField);				
			}
			return type;			
		}
		if(symb instanceof PrimTypeSymb)
		{
			
			Type tipo = new Type(symb.getName());
			return tipo;
		}
		
		return null;
	}
	
	
	
	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto.
	 * Retorna true se nao houver erros e false em caso contrario. A lista
	 * errorList acumula os erros encontrados.
	 * 
	 * @param errorList
	 *            lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 * @throws Exception
	 */
	public boolean check(List<String> errorList) throws Exception {

		// Obtendo símbolo da variável no escopo
		Object tSymb = SymbolTable.search(this.name);

		// Se a variável não foi declarada, a busca retornará null, lançar erro
		if (tSymb == null) {
			errorList.add("Variavel " + this.name + " nao declarada ou nao acessivel neste escopo");
			return false;
		}

		// Verificando se o identificador é uma constante
		if (tSymb instanceof ConstSymb) {
			ConstSymb symb = (ConstSymb) tSymb;
			this.type = (Type) symb.getValue().getType();
			return true;
		}
		
		// Verificando se o identificador é uma variável e atribuindo valor de retorno
		else if (tSymb instanceof VarSymb) {

			VarSymb symb = (VarSymb) tSymb;

			// Verificação para estrutura
			if (symb.getType() instanceof StructTypeSymb) {
				this.type = findType(symb);
				return true;
			}
			// Verificação para Vetor
			else if (symb.getType() instanceof VectorTypeSymb) {
				VectorTypeSymb vetor = (VectorTypeSymb) symb.getType();
				this.type = (Type) findType(vetor);
				return true;
			} 
			// 
			else {
				String tname = symb.getType().getName();
				this.type = new Type(tname);
				return true;
			}
			
		}
		errorList.add("Variavel " + this.name + " nao declarada ou nao acessivel neste escopo");
		return false;
	}
		
}



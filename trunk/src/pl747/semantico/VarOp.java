package pl747.semantico;

import java.util.LinkedList;
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
	 * @throws Exception 
	 */
	public boolean check(List<String> errorList) throws Exception {

		Object tSymb = SymbolTable.search(this.name);

		if (tSymb instanceof VarSymb) {

			VarSymb symb = (VarSymb) tSymb;

			if (symb == null) 
			{
				errorList.add("Variavel " + this.name + " nao declarada ou nao acessivel neste escopo");
				return false;
			} 
			else 
			{
				if (symb.getType() instanceof StructTypeSymb) {
					StructTypeSymb stSymb = (StructTypeSymb) symb.getType();
					LinkedList<FieldSymb> campos = stSymb.getFieldList();
					StructType struct = new StructType();
					for (FieldSymb symb2 : campos) {
						Type tipo_campo = new Type(symb2.getType().getName());
						String nome = symb2.getName();
						VarDeclaration decl = new VarDeclaration(false,
								tipo_campo, nome);
						struct.addChild(decl);
					}
					this.type = struct;

				} else if (symb.getType() instanceof VectorTypeSymb) {

					String tipoElem = ((VectorTypeSymb) symb.getType())
							.getElementType().getName();
					String tamVetor = Integer.toString(((VectorTypeSymb) symb
							.getType()).getSize());
					this.type = new VectorType(tamVetor, new Type(tipoElem));
				} else {
					String tname = symb.getType().getName();
					this.type = new Type(tname);
				}
				return true;
			}
		}
		else
		{
			if (tSymb instanceof ConstSymb) {
				ConstSymb symb = (ConstSymb) tSymb;	
				this.type = (Type) symb.getValue().getType();
				return true;
			}
		}
		
		errorList.add("Variavel " + this.name + " nao declarada ou nao acessivel neste escopo");
		return false;		
	}

}

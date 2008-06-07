package pl747.semantico;

import java.util.List;

import pl747.TreeNode;

public class UnaryOp extends Expression {

	private int kind;
	private Expression opn1;	
		
	public UnaryOp(int kind,TreeNode opn1) {
		this.opn1 = (Expression)opn1;
		this.kind = kind;
	}
	
	/**
	 * Devolve o operando (unico) da operacao representada por este objeto.
	 */
	public Expression getOperand() {		
		return this.opn1;
	}

	/**
	 * Devolve o 'codigo' desta operacao (os codigos estao definidos em PL747Consts.java).
	 */
	public int getKind() {
		return kind;
	}

	public void addChild(TreeNode child) throws Exception {
		this.opn1 = (Expression) child; 		
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		boolean r1 = opn1.check(errorList);
		boolean r2 = true;
		
		// Chamada de funcao
		if (kind == CALL_OP){
			this.type = (((VectorType)((VarOp)this.opn1).getType()).getElementType());
		}

		// Menos unario
		if (kind == MINUS_OP){
			
			this.type = ((Expression)this.opn1).getType();
			
			if(((Type)this.opn1.getType()).getName() != "int"){				
				errorList.add("O operador - (\"menos\" unario) soh pode ser aplicado em operandos de tipo inteiro (\"int\")");
				r2 = false;
			}
		}
		
		// Negacao logica
		if (kind == NOT_OP){
			
			this.type = new Type("boolean");
			
			if(((Type)this.opn1.getType()).getName() != "boolean"){				
				errorList.add("O operador ! (negacao logica) soh pode ser aplicado em operandos de tipo booleano (\"boolean\")");
				r2 = false;
			}
		}	
		
		return r1 && r2;
	}
	
}

package pl747.semantico;

import java.util.List;

import pl747.TreeNode;

/**
 * Representa um comando 'return'.
 */
public class ReturnStat extends StatOp {

	private Expression value;	
	
	public ReturnStat(Expression value) {
		this.value = value;
		if (value == null)
			this.setType(new Type("void"));
		else
			this.setType(value.getType());
	}

	public void addChild(TreeNode child) throws Exception {		
	}
	
	/**
	 * Retorna a representacao da expressao associada ao comando return.
	 */
	public Expression getValue() {
		return value;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		
		
		boolean r2 = true;
		
		/* boolean r1 = true;
		String returnType =  this.type.getName();
		String valueType = null;				
			 
		 // Encontrando tipo da Expressao value
		if (value instanceof VarOp) {
			VarOp vStart = (VarOp) value;
			valueType = ((VarSymb) SymbolTable.search(vStart.getName())).getType().getName();
		} else if (value instanceof ConstOp) {
			ConstOp cStart = (ConstOp) value;
			valueType = ((Type) cStart.getType()).getName();
		} else {
			valueType = ((Type) value.getType()).getName();
		}
		 		 
		try{
		// Verificando se a expressao de retorno respeita o tipo de retorno
		if (!returnType.equals(valueType)){
			errorList.add("Tipo de retorno incorreto ");
			r1 = false;
		}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(SymbolTable.getCurScope());	
			System.out.println(this.value);			
		}*/
		
		// Verificando se a expressao de retorno eh valida
		if (value != null)
			r2 = this.value.check(errorList);
		
		return /*r1 &&*/ r2;
	}

}

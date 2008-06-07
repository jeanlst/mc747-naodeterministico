package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;


public class DiadOp extends Expression {

	private int kind;
	private Expression op1;
	private Expression op2;
	
	public DiadOp(int kind, TreeNode opn1, TreeNode  opn2) {
		this.kind = kind;
		this.op1 = (Expression) opn1;
		this.op2 = (Expression) opn2;
		
		
		if (kind == 0){
			try {
				
				//String tname = ((VectorTypeSymb)SymbolTable.search(((VarOp)this.op1).getName())).getElementType().getName();
				//this.type = new Type(tname);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		if (kind == 1 || kind == 17){
			this.type = ((Expression)this.op2).getType();
		}
		if (kind >=2 && kind <= 7){
			this.type = ((Expression)this.op1).getType();
		}
		if (kind >= 8 && kind <= 16){
			this.type = new Type("boolean");			
		}
		
	}

	public void addChild(TreeNode child) throws Exception {				

	}
	
	/**
	 * Devolve o primeiro operando da operacao representada por este objeto.
	 */
	public Expression getFirstOperand() {

		return (Expression) this.op1;
	}

	/**
	 * Devolve o segundo operando da operacao representada por este objeto.
	 */
	public Expression getSecondOperand() {

		return (Expression) this.op2;
	}

	/**
	 * Devolve o 'codigo' da operacao representada por este objeto (definido em PL747Consts.java)
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		
		// Verificando os dois operadores
		boolean r1 = op1.check(errorList);
		boolean r2 = op2.check(errorList);
		boolean r3 = true;
		
		if(!r1 || !r2)
		{
			return false;
		}
		
		// Indexacao de vetor
		if (kind == 0){

			// Verificando se o primeiro operador eh um vetor
			if (op1.getType() instanceof VectorType) {
				errorList.add("Tipo nao indexavel");
				r3 = false;					
			}
			
			// Verificando se o segundo operador eh um inteiro
			if(!op2.getType().getName().equals("int")){
				errorList.add("Indice invalido");
				r3 = false;				
			}
			
			// Encontrando o tipo armazenado pelo vetor
			Symbol symbol = SymbolTable.search(((VarOp) this.op1).getName());
			VarSymb  vSymb = (VarSymb) symbol;
			PrimTypeSymb type = vSymb.getType();
			String tName = ((VectorTypeSymb) type).getElementType().getName();
			
			this.type = new Type(tName);			
		}
		
		// Selecao de campo 
		if (kind == 1){
			// Verificando se o primeiro operador eh uma estrutura
			if (op1.getType() instanceof StructType) {
				errorList.add("Tipo nao indexavel");
				r3 = false;
			}
			this.type = ((Expression)this.op2).getType();	
		}
		
		
		// Comandos Aritmeticos
		if (kind >=2 && kind <= 7){
			
			// Resultado da expressao eh um inteiro
			this.type = ((Expression)this.op1).getType();
			
			// Verificando se os dois operadores sao inteiros
			if( (!((Type)this.op1.getType()).getName().equals("int")    ||
			     !((Type)this.op2.getType()).getName().equals("int"))) {				
				errorList.add("Tipos incompativeis para a operacao, inteiro esperado");
				r3 = false;
			}
		}
		
		//	Comandos de comparacao
		if (kind >= 8 && kind <= 13){
			
			// Resultado da expressao eh um boolean
			this.type = new Type("boolean");		
			
			// Verificando se os dois operandos do mesmo tipo		
			if( (((Type)this.op1.getType()).getName() != ((Type)this.op2.getType()).getName())
				        || !(((Type)this.op1.getType()).getName().equals("int")
						||   ((Type)this.op1.getType()).getName().equals("char")
						||   ((Type)this.op1.getType()).getName().equals("boolean")
					)
				) {			
					errorList.add("Tipos incompativeis para a operacao.");
					r3 = false;
			}
		}	
		
		// Comandos logicos
		if (kind >= 14 && kind <= 16){
			
			// Resultado da expressao eh um boolean
			this.type = new Type("boolean");		
			
			// Verificando se o operador 1 eh um tipo primitivo
			if(!((Type)this.op1.getType() instanceof Type)){
				errorList.add("Tipo incompativel para o operador 1");
				r3 = false;
			}
				
			//	Verificando se o operador 2 eh um tipo primitivo
			if(!((Type)this.op2.getType() instanceof Type)){
				errorList.add("Tipo incompativel para o operador 2");
				r3 = false;
			}
			
			// Verificando se os dois operadores sao boolean
			if (!((Type)this.op1.getType()).getName().equals(((Type)this.op2.getType()).getName())) {				
				errorList.add("Tipos incompativeis para a operacao, boolean esperado");
				r3 = false;
			}
		}	
		
		// Atribuicao
		if (kind == 17){
			
			// Verificação de Vetores
			if (op1.getType() instanceof VectorType) 
			{
				VectorType v1 = ((VectorType) op1.getType());
				
				int s1 = 0;
				try{
					s1 = Integer.parseInt(v1.getSize());
				}
				catch(Exception e){	

					Symbol size = SymbolTable.search(v1.getSize());
					if (size instanceof ConstSymb) {
						s1 = Integer.parseInt((((ConstOp)((ConstSymb) size).getValue()).getValue()));						
					}				
				}
				
				
				if (op2.getType() instanceof VectorType) 
				{
					VectorType v2 = ((VectorType) op2.getType());
					
					int s2 = 0;
					try{
						s2 = Integer.parseInt(v1.getSize());
					}
					catch(Exception e2){	

						Symbol size = SymbolTable.search(v1.getSize());
						if (size instanceof ConstSymb) {
							s2 = Integer.parseInt((((ConstOp)((ConstSymb) size).getValue()).getValue()));						
						}	
					}
					
					if(s1 != s2)
					{
						errorList.add("Tipos incompativeis para a atribuicao");
						r3 = false;							
					}
				}
				else
				{
					errorList.add("Tipos incompativeis para a atribuicao");
					r3 = false;	
				}
				
			}
			else // Verificação de variáveis de tipo primário				
			{	
				if (!((Type)this.op1.getType()).getName().equals(((Type)this.op2.getType()).getName())) {
					errorList.add("Tipos incompativeis para a atribuicao");
					r3 = false;
				}
			}
			
			
			
			//TODO verificar se op1 e identificador de variavel simples (int,char,boolean), selecao de campo ou indexacao
			//Se eh que entendi direito, tudo isso é resumido em VarOp
			if ( !(this.op1 instanceof VarOp)) {
				errorList.add("O primeiro operando da atribuicao nao pode receber valores, pois nao eh variavel de tipo primitivo, selecao de campo, nem indexacao de vetor");
				r3 = false;
			}
			
			
			this.type = ((Expression)this.op2).getType();
		}
		
		return r1 && r2 && r3;
	}
}

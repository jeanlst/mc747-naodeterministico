package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;


public class DiadOp extends Expression {

/* Atributos da Classe */	
	
	/**
	 * Tipo da operacao
	 */	
	private int kind;
	
	/**
	 * Operador 1 
	 */
	private Expression op1;
	
	/**
	 * Operador 2
	 */
	private Expression op2;
	
		
/* Metodos publicos */	
	
	/**
	 * Construtor de DiadOp
	 */
	public DiadOp(int kind, TreeNode opn1, TreeNode  opn2) 
	{
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

	/**
	 * Método herdado (sem funcionalidade nesta classe)
	 */
	public void addChild(TreeNode child) throws Exception 
	{	
		Exception e = new Exception("Método nao se aplica a objetos desta classe");
		throw e;
	}
	
	/**
	 * Devolve o primeiro operando da operacao representada por este objeto.
	 */
	public Expression getFirstOperand() 
	{
		return (Expression) this.op1;
	}

	/**
	 * Devolve o segundo operando da operacao representada por este objeto.
	 */
	public Expression getSecondOperand() 
	{

		return (Expression) this.op2;
	}

	/**
	 * Devolve o 'codigo' da operacao representada por este objeto (definido em PL747Consts.java)
	 */
	public int getKind() 
	{
		return kind;
	}
	
	
/* Verificacoes por tipo de Operacao*/
	
	/**
	 * Indexacao de vetor
	 */
	private boolean VerificaIndexacao(List<String> errorList) 
	{
				
			boolean r = true;
						
			// Verificando se o primeiro operador eh um vetor
			if (!(op1.getType() instanceof VectorType)) {
				errorList.add("Tipo nao indexavel");
				r = false;
			}

			// Verificando se o segundo operador eh um inteiro
			if (!op2.getType().getName().equals("int")) {
				errorList.add("Indice invalido");
				r = false;
			}

			// Encontrando o tipo armazenado pelo vetor
			Symbol symbol = SymbolTable.search(((VarOp) this.op1).getName());
			VarSymb vSymb = (VarSymb) symbol;
			PrimTypeSymb type = vSymb.getType();
			String tName = ((VectorTypeSymb) type).getElementType().getName();

			this.type = new Type(tName);
			return r;
		
	}
	
	/**  
	 * Selecao de campo
	 * @param errorList
	 * @return
	 */
	private boolean VerificaSelecao(List<String> errorList) 
	{
		boolean r = true;
		
		String var1Name = ((VarOp)this.op1).getName();
		VarSymb var1Symb = ((VarSymb)SymbolTable.search(var1Name));
		StructTypeSymb Tsymb = (StructTypeSymb) var1Symb.getType();			
		String var2Name = ((VarOp)this.op2).getName();			
		FieldSymb field = Tsymb.getField(var2Name);
		String Tname = field.getType().getName();
		
		
		this.type = new Type(Tname) ;
		
		// Verificando se o primeiro operador eh uma estrutura
		if (!(op1.getType() instanceof StructType)) {
			errorList.add("Nao eh um struct");
			r = false;
		}
		return r;
	}
			
	/** 
	 * Comandos aritmeticos
	 * @param errorList
	 * @return
	 */
	private boolean VerificaAritmeticos(List<String> errorList)
	{		
		boolean r = true;
		
		// Resultado da expressao eh um inteiro
		this.type = ((Expression)this.op1).getType();
		
		// Verificando se os dois operadores sao inteiros
		if( (!((Type)this.op1.getType()).getName().equals("int")    ||
		     !((Type)this.op2.getType()).getName().equals("int"))) {				
			errorList.add("Tipos incompativeis para a operacao, inteiro esperado");
			r = false;
		}
		return r;
	}
	
	/**
	 * Comandos de comparacao
	 */
	private boolean VerificaComparacao(List<String> errorList)
	{
		boolean r = true;
		
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
				r = false;
		}
		return r;
	}
	
	/**
	 * Comandos logicos
	 */
	private boolean VerificaComandoLogico(List<String> errorList)
	{

		boolean r = true;
		
		// Resultado da expressao eh um boolean
		this.type = new Type("boolean");		
		
		// Verificando se o operador 1 eh um tipo primitivo
		if(!((Type)this.op1.getType() instanceof Type)){
			errorList.add("Tipo incompativel para o operador 1");
			r = false;
		}
			
		//	Verificando se o operador 2 eh um tipo primitivo
		if(!((Type)this.op2.getType() instanceof Type)){
			errorList.add("Tipo incompativel para o operador 2");
			r = false;
		}
		
		// Verificando se os dois operadores sao boolean
		if (!((Type)this.op1.getType()).getName().equals(((Type)this.op2.getType()).getName())) {				
			errorList.add("Tipos incompativeis para a operacao, boolean esperado");
			r = false;
		}
		
		return r;
	}	
			
	/** 
	 * Atribuicao de estrutura
	 */
	private boolean VerificaAtribuicaoEstrutura(List<String> errorList) 
	{		
		boolean r = true;
		
		if (this.op2 instanceof TupleOp) {

			StructType struct = (StructType) this.op1.getType();

			List<VarDeclaration> campos = struct.getElementList();
			List<Expression> valores = ((TupleOp) this.op2).getElementList();

			int tam_c = campos.size();
			int tam_v = valores.size();

			if (tam_c == tam_v) {
				for (int i = 0; i < tam_c; i++) {
					try {
						r = r && campos.get(i).check(errorList);
						r = r && valores.get(i).check(errorList);
					} catch (Exception e) {
						r = false;
					}
					if (r) {
						if (campos.get(i).getType().getName() != valores.get(i)
								.getType().getName()) {
							errorList
									.add("Tipos incompatíveis na inicialização da estrutura");
							r = false;
						}
					}
				}

			} else {
				errorList
						.add("Tupla e estrutura não possuem mesmo número de campos");
				r = false;
			}

		}
		return r;

	}
	
	/**
	 * Atribuicao de vetor
	 */	
	private boolean VerificaAtribuicaoVetor(List<String> errorList)
	{
		
	boolean r = true;

		VectorType v1 = ((VectorType) op1.getType());
		VectorType v2 = null;

		int s1 = 0;
		try {
			s1 = Integer.parseInt(v1.getSize());
		} catch (Exception e) {

			Symbol size = SymbolTable.search(v1.getSize());
			if (size instanceof ConstSymb) {
				s1 = Integer.parseInt((((ConstOp) ((ConstSymb) size)
						.getValue()).getValue()));
			}
		}

		if (op2.getType() instanceof VectorType) {
			v2 = ((VectorType) op2.getType());

			int s2 = 0;
			try {
				s2 = Integer.parseInt(v2.getSize());
			} catch (Exception e2) {

				Symbol size = SymbolTable.search(v1.getSize());
				if (size instanceof ConstSymb) {
					s2 = Integer.parseInt((((ConstOp) ((ConstSymb) size)
							.getValue()).getValue()));
				}
			}

			if (s1 != s2) {
				if (r == true) {
					errorList.add("Tipos incompativeis para a atribuicao");
					r = false;
				}
			}
		} else {
			errorList.add("Tipos incompativeis para a atribuicao");
			r = false;
		}
		String tipo1 = ((Type) v1.getType()).getName();
		String tipo2 = ((Type) v2.getType()).getName();
		if (!tipo1.equals(tipo2)) {
			if (r == true) {
				errorList.add("Tipos incompativeis para a atribuicao");
				r = false;
			}
		}
		return r;
	}
		
	/**
	 * Atribuicao de tipos primitivos
	 */
	private boolean VerificaAtribuicaoPrimarios(List<String> errorList)
	{
		boolean r = true;
		
		if (!((Type) this.op1.getType()).getName().equals(
				((Type) this.op2.getType()).getName())) {
			errorList.add("Tipos incompativeis para a atribuicao");
			r = false;
		}
		return r;
	}
	
	/** 
	 * Atribuicao
	 * @param errorList
	 * @return
	 */
	private boolean VerificaAtribuicao(List<String> errorList) 
	{
		boolean r = true;
		
		/* Verificando se a variavel foi inicializada */				
		if (this.op1.getType() instanceof StructType) 
			r = r && VerificaAtribuicaoEstrutura(errorList);
		
		// Verificação de Vetores
		else if (op1.getType() instanceof VectorType) 
			r = r && VerificaAtribuicaoVetor(errorList);

		// Verificação de variáveis de tipo primário
		else 		
			r = r && VerificaAtribuicaoPrimarios(errorList);
		

		// TODO verificar se op1 e identificador de variavel simples
		// (int,char,boolean), selecao de campo ou indexacao
		// Se eh que entendi direito, tudo isso é resumido em VarOp
		if (!(this.op1 instanceof VarOp)) {
			if (this.op1 instanceof DiadOp) {
				DiadOp op = (DiadOp) this.op1;
				if ((op.getKind() != INDEX_OP) && (op.getKind() != SEL_OP)) {
					errorList
							.add("O primeiro operando da atribuicao nao pode receber valores, pois nao eh variavel de tipo primitivo, selecao de campo, nem indexacao de vetor");
					r = false;
				}
			} else {
				errorList
						.add("O primeiro operando da atribuicao nao pode receber valores, pois nao eh variavel de tipo primitivo, selecao de campo, nem indexacao de vetor");
				r = false;
			}
		}

		this.type = ((Expression) this.op2).getType();
		
		return r;
	}

	
/* Verificacao do Noh DiadOp*/
	
	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto.
	 * Retorna true se nao houver erros e false em caso contrario. A lista
	 * errorList acumula os erros encontrados.
	 * 
	 * @param errorList
	 *            lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception 
	{		
		// variavel de retorno
		boolean r = true;
			
		// Verificando o primeiro operando
		r = r && op1.check(errorList);
		
		// Verificando o segundo operando
		if(kind != 1)
			 r = r && op2.check(errorList);		
		
		// Verificando a operacao, caso os 2 operandos estejam corretos
		if (r) {
			
			// Verificando indexacao			
			if (kind == INDEX_OP)
				r = r && VerificaIndexacao(errorList);
			
			// Verificando selecao de campo
			else if (kind == SEL_OP)
				r = r && VerificaSelecao(errorList);

			// Verificando comandos aritmeticos
			else if (kind >= ADD_OP && kind <= MOD_OP)
				r = r && VerificaAritmeticos(errorList);

			// Verificando comandos de comparacao
			else if (kind >= EQ_OP && kind <= LE_OP)
				r = r && VerificaComparacao(errorList);

			// Verificando comandos logicos
			else if (kind >= AND_OP && kind <= OR_OP)
				r = r && VerificaComandoLogico(errorList);
			
			// Verificando atribuicao
			else if (kind == ASSIGN_OP)
				r = r && VerificaAtribuicao(errorList);
		}

		return r;
	}
}

package pl747.semantico;

import java.util.*;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;


public class FunctionDeclaration extends Declaration {

	private boolean isPrototype;
	private CompoundStat body;
	private List<VarDeclaration> params;
	
	
	public void addChild(TreeNode child) throws Exception {		
		this.params.add((VarDeclaration)child);
	}
	
	/**
	 * Construtor de FunctionDeclaration
	 * @param name
	 * @param type
	 * @param body
	 */
	public FunctionDeclaration(String name, Type type, TreeNode body){
		super(name);
		this.type = type;
		this.params = new ArrayList<VarDeclaration>();	
		if (body != null){
			this.body = (CompoundStat) body;
			this.isPrototype = false;
		}
		else
			this.isPrototype = true;
		/*else{
			System.err.println("LALA");
			System.exit(1);
		}*/
	}
	
	/**
	 * Devolve a lista de objetos AbsNode que constitui o 'corpo' da funcao.
	 * @return lista de objetos do corpo da funcao
	 */
	public TreeNode getBody() {
		return body;
		
	}

	/**
	 * Devolve a lista de parametros da funcao.
	 * @return lista de declaracoes de variaveis que constituem os parametros da funcao
	 */
	public List<VarDeclaration> getParmList() {		
		return params;
	}


	/**
	 * Devolve true se este objeto representa um prototipo de funcao.
	 * @return true se a funcao eh prototipo, e false caso contrario
	 */
	public boolean isPrototype() {

		return this.isPrototype;
	}

	/**
	 * Devolve o escopo na tabela de simbolos onde estao os objetos declarados na funcao representada por este objeto.
	 * @return TODO
	 */
	public Scope getScope() {

		//TODO
		return null;
	}
	
	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 * @throws Exception 
	 */
	public boolean check(List<String> errorList) throws Exception{
	
		/* 
		 * Verificar o tipo de retorno da funcao e o comando return
		 */ 		
		
		boolean result = true;
		FunctionSymb fSymb = null;
		
		FunctionSymb tempSymb = (FunctionSymb)SymbolTable.getCurScope().getSymbol(this.name); 
		
		// Verificando se a funcao ja foi declarada/prototipada
		if (tempSymb != null && (this.isPrototype() || (!tempSymb.isPrototype()))) {
			errorList.add("Redeclaracao da funcao " + this.name +" nao eh possivel");
			result = false;
		}		
		else
		{
		    // Adicionando o simbolo da funcao no escopo atual, ou atualizando o que era prototipo
			if (tempSymb == null) {
				fSymb = new FunctionSymb(this.name,new PrimTypeSymb(this.type.name),this.isPrototype);		
				SymbolTable.getCurScope().addSymbol(fSymb);
			}
			else {
				tempSymb.setPrototype(this.isPrototype);
			}
		}
		
		// Definindo escopo da funcao		
		List<ParmSymb> paramList = new ArrayList<ParmSymb>();
		Scope escopo = SymbolTable.newScope();
		
		for (VarDeclaration no: this.params) {
			if (SymbolTable.localSearch(no.getName()) == null){		
				// Checando no
				result = result && no.check(errorList);
				
				// Adicionando si�mbolo ao escopo
				Symbol symbol = new VarSymb(no.getName(),escopo.getLevel(),(PrimTypeSymb)SymbolTable.search(no.getType().getName()));
				escopo.addSymbol(symbol);							
				
				// Adicionando parametro a� lista de parametros da funcao
				String name = no.getName();
				PrimTypeSymb //type = ((PrimTypeSymb)SymbolTable.search(no.getType().getName()));
				type = new PrimTypeSymb(no.getType().getName());
				ParmSymb param = new ParmSymb(name,type,no.isRef());				
				paramList.add(param);				
			}
			else{
				errorList.add("Nome "+this.name+" invalido para o parametro" + no.getName() + ". Ja existe outro parametro com esse nome");
				result = false;				
			}
		}
		
		// Adicionando lista de parametros ao si�mbolo
		fSymb.setParmList(paramList);				
		
		
		// Cehcando o corpo da função
		if (body!= null)
			result = result && this.body.check(errorList);
		
		
		// Verificando o tipo retornado
		for (TreeNode no : this.body.getStatList()) {
			if (no instanceof ReturnStat) {
				ReturnStat noRet = (ReturnStat) no;
				
				if(noRet.getValue() == null){
					if (!this.type.getName().equals("void")){
						errorList.add("Tipo invalido no retorno da funcao "	+ this.name);
						result = false;
					}
				}
				else{
				if (!noRet.getValue().getType().getName().equals(this.type.getName())) {
						errorList.add("Tipo invalido no retorno da funcao "	+ this.name);
						result = false;
					}
				}
					
			}
		}
		
				
		// Saindo do escopo da funcao
		SymbolTable.closeScope();
		
		return result;	
		
	}

}

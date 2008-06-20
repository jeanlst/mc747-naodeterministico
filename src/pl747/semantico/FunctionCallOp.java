package pl747.semantico;

import java.util.*;
import pl747.Visitor;
import pl747.TreeNode;
import pl747.tabelaSimbolos.*;

/**
 * Representa uma chamada de funcao.
 */
public class FunctionCallOp extends Expression {


	private String name;
	private List<Expression> childs; 
	
	
	public FunctionCallOp(String fname) {
	   this.name = fname;
	   childs = new ArrayList<Expression>();
	}
		
	/**
	 * Agrega um 'no filho' a este objeto.
	 * @param child - objeto a ser agregado como 'no filho'
	 * @throws Exception - disparada em caso de erro, tipicamente quando o objeto nao admite (mais) filhos.
	 */
	public void addChild(TreeNode child) throws Exception {
		childs.add((Expression)child);
	}

	/**
	 * Devolve o nome da funcao sendo chamada.
	 */
	public String getFunctionName() {

		return name;
	}

	/**
	 * Devolve a lista de parametros associados a esta chamada de funcao.
	 */
	public List<Expression> getParmList() {
		return (List<Expression>) childs;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
		boolean result = true;
				
		/* Verificando se a funcao ja foi declarada */		
		FunctionSymb funcSymb = (FunctionSymb)SymbolTable.search(this.name);
		if (funcSymb == null) {
						
			errorList.add("Funcao " + this.name +" nao declarada");
			result = false;	
		}
		else {
			
			// Verificando se o prototipo foi implementado
			
			/*if(funcSymb.isPrototype())
			{
				result = false;
				errorList.add("Protótipo não implementado");		
			}*/
			
			
			// Setando tipo de retorno do metodo de acordo com a declaracao da funcao
			this.type = new Type(funcSymb.getType().getName());
			
			// Encontrando tamanho da lista de parametros passada
			int thisTam = this.childs.size();
			
			// Encontrando tamanho da lista de parametros definida para a funcao 
			int otherTam = funcSymb.getParmList().size();
			
			
			// Verificando se a chamada da funcao esta de acordo com os parametros que deveria receber
			if (thisTam == otherTam) {					
				
				boolean resultados = true;
			
				//verificando se os parametros passados na chamada da funcao sao compativeis com os tipos declarados na declaracao da funcao
				
				for (int i = 0; i < thisTam; i++) {
				
					Expression no = this.childs.get(i);
					VarSymb var = funcSymb.getParmList().get(i);
					result = result && no.check(errorList);
													 
					
					String declName; 
					try {
						declName = no.getType().getName();
					} catch (Exception e) {									
						declName = ((VarSymb)SymbolTable.search(((VarOp)no).getName())).getType().getName();
					}
					
					
					String paramName; 
					try {
						paramName = var.getType().getName();
					} catch (Exception e) {
						
						paramName = ((VarSymb)SymbolTable.search(var.getName())).getType().getName();
					}
					
					//teste especial para type castings
					if (this.name.equals("int") || this.name.equals("char") || this.name.equals("boolean") ) {
						if (!((declName.equals("int") || declName.equals("char") || declName.equals("boolean"))) ) {
						
							errorList.add("Type castings, como o " + this.name + " soh podem receber valores do tipo int, char ou boolean, e nao " + declName);
							result = false;
						}
					}
					else if (!declName.equals(paramName)){
						errorList.add("O argumento " + (i+1) + "  da funcao " + this.name + " deve ser " + paramName +", e nao " + declName + "");
						result = false;
					
					}
					
					if (funcSymb.getParmList().get(i).getRefMode())
					{
						if (no instanceof VarOp) {
							VarOp varRef = (VarOp) no;
											
							Symbol symbRef = SymbolTable.search(varRef.getName());
														
							//TODO isso eh suficiente? Porque ele acaba pegando qualquer coisa que nao seja
							//vetor ou estrutura e fala que estah errado,mas uma variavel primitiva pode ser passada
							if (    !(((VarSymb)symbRef).getType().getName().equals("vector") ||
									 ((VarSymb)symbRef).getType().getName().equals("struct"))       ){
								errorList.add("Variável não pode ser passada por referência");
								result = false;								
							} 							
						}				
					}
					
					
					// Faz a verificacao de todas as expressoes que derivam os parametros da chamada
					resultados = resultados && no.check(errorList);									
					result = result && resultados;		

				}				
				
			}
			else {
				
				//Verificando funções print e println
				if(this.name.equals("print") || this.name.equals("println")) {
					for (Expression no : this.childs) {						
						no.check(errorList);
					}
					
				}
				else if (this.name.equals("read")
						|| this.name.equals("readln")) {
					for (Expression no : this.childs) {
						if (no instanceof VarOp) {
							no.check(errorList);
						} else if (no instanceof DiadOp) {
							DiadOp noDiad = (DiadOp) no;
							if (!(noDiad.getKind() == INDEX_OP)
									&& !(noDiad.getKind() == SEL_OP)) {
								errorList
										.add("As funções reads e readln aceitam apenas variáveis como parâmetro");
								result = false;
							}
						}
					}
				} 
				else {
					errorList.add("A funcao " + this.name + " necessita "
							+ otherTam + " parametros, e nao " + thisTam);
					result = false;
				}
			}
			
			// Verificando funções inc e dec
			if (this.name.equals("inc") || this.name.equals("dec")){
				for (Expression no : this.childs) {						
					if (!((no.getType().getName().equals("int")) ||
							(no.getType().getName().equals("char")) ||
							(no.getType().getName().equals("boolean")))){
						errorList.add("Tipo incompatível para a função " + this.name);
						result = false;					
					}
				}				
			}
		}
		
		return result;
	}
	
	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
}

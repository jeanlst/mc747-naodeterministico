package pl747.semantico;

import java.util.*;

import pl747.TreeNode;


/**
 * Representa uma declaracao de variavel da linguagem.
 */
public class VarDeclList extends AbsNode {
	
	/**
	 * Devolve true se a variavel representada por este objeto eh um parametro passado por referencia.
	 */

	private List<Declaration> declList;
	
	
	public VarDeclList(Type type) {
		declList = new ArrayList<Declaration>();
		this.type = type;
	}	
		
	/**
	 * Devolve o type da variavel representada por este objeto.
	 */
	public Type getType() {
		return this.type;
	}
	
	@SuppressWarnings("unchecked")
	public List<VarDeclaration> getElements(){		
		return (List) this.declList;		
	}
	
	public List<Declaration> getDeclarations(){
		return  this.declList;		
	}
	

	public void addChild(TreeNode child){		
		//if (((VarDeclaration)child).getType() == null);
			//{
				((VarDeclaration)child).setType(this.type);
			//}
		this.declList.add((Declaration)child);
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
				
		boolean result = true;
		for (TreeNode no: this.declList) {
			try {
				if (no.check(errorList) == false){
					result = false;
				}
			} catch (Exception e) {
				result = false;
				e.printStackTrace();
			}				
		}
		return result;
	}



}

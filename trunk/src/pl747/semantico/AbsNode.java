/*teste*/
package pl747.semantico;

import pl747.TreeNode;
import pl747.PL747Consts;
import pl747.Visitor;


/**
 * Classe abstrata a partir da qual sao derivadas as classes que representam os nos da arvore de programa. Preve os principais metodos necessarios a verificacao semantica.
 */
public abstract class AbsNode implements Visitable, TreeNode, PL747Consts {

	
	public Type type;
	
	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	/*public boolean check( List<String> errorList ) {
		return true;
	}*/

	/**
	 * Devolve o tipo do valor correspondente a subarvore representada por este objeto. 
	 * @return tipo do valor correspondente a subarvore
	 */
	public AbsNode getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	

	/**
	 * Atraves deste metodo o objeto 'visitor' solicita a visita a um objeto da arvore de programa.
	 * @param v TODO
	 * @return TODO
	 */
	public Object accept( Visitor v ) {
		//TODO
		return null;
	}

	/**
	 * Agrega um 'no filho' a este objeto.
	 * @param child - objeto a ser agregado como 'no filho'
	 * @throws Exception - disparada em caso de erro, tipicamente quando o objeto nao admite (mais) filhos.
	 */
	public void addChild(TreeNode child) throws Exception {	}
	
}

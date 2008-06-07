package pl747;


import java.util.List;

/**
 * MC 747 - Projeto de curso
 * Interface TreeNode: 
 * 				Descreve a interface a ser implementada pelas classes
 * 		        da hierarquia que constituira a representacao intermediaria
 * 	            do programa, a partir da qual sera feita a analise semantica
 * 	            e a geracao de codigo.
 *
 */
public interface TreeNode {
	
	/**
	 * Agrega um 'no filho' a este objeto.
	 * @param child - objeto a ser agregado como 'no filho'
	 * @throws Exception - disparada em caso de erro, tipicamente
	 * 					   quando o objeto nao admite (mais) filhos.
	 */
	void addChild(TreeNode child) throws Exception;
	
	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	boolean check(List<String> errorList) throws Exception;

}

package pl747.semantico;

/**
 * Classe abstrata a partir da qual sao derivadas os objetos que representam as expressoes e comandos da linguagem.
 */
public abstract class Expression extends AbsNode {

	/**
	 * Retorna o Tipo da Expressao
	 */
	public Type getType(){
		return (Type) this.type;		
	}
		
}

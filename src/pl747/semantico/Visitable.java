package pl747.semantico;

import pl747.Visitor;

/**
 * Interface a ser implementada pelos nos da arvore de programa, para permitir 'visitas' a mesma, se acordo com o 'design pattern'  visitor.
 * O pattern visitor sera usado no projeto para a geracao de codigo.
 */
public interface Visitable {

	/**
	 * Atraves deste metodo o objeto 'visitor' solicita a visita a um objeto da arvore de programa.
	 * @param v TODO
	 * @return TODO
	 */
	Object accept( Visitor v );

}

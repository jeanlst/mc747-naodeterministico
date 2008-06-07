package pl747.semantico;

import pl747.TreeNode;
import pl747.TreeNodeFactory;

/**
 * Interface TreeNodeFactory que deve ser implementada pela classe
 *           responsavel pela construcao da representacao intermediaria
 *           do programa, e sera usada na analise semantica e geracao de
 *           codigo.
 *
 */


public class AbsNodeFactory implements TreeNodeFactory{
	
	
	/**
	 * Constroi a representacao de um tipo primitivo.
	 * @param name - nome do tipo.
	 * @return o objeto TreeNode que descreve esse tipo.
	 */
	public TreeNode makePrimType(String name){ 
		
		TreeNode tipoPrimario = new Type(name);
		return tipoPrimario;
		
	}
	
	/**
	 * Constroi a representacao de um tipo 'struct', anonimo. Os campos 
	 * devem ser acrescentados posteriormente atraves de chamadas ao metodo
	 * addChild() aplicado ao objeto TreeNode retornado por este metodo.
	 * @return o objeto TreeNode que descreve esse tipo
	 */
	public TreeNode makeStructType(){
		
		StructType no = new StructType();
		return no;
		
	}
	
	/**
	 * Constroi a representacao de um tipo vetor, anonimo.
	 * @param size - tamanho do vetor.
	 * @param elementType - tipo dos elementos do vetor (pode ser o nome de uma constante)
	 * @return  o objeto TreeNode que descreve esse tipo
	 */
	public TreeNode makeVectorType(String size, TreeNode elementType){
		TreeNode no = new VectorType(size,elementType);
		return no;		
	}
	
	/**
	 * Constroi a representacao de uma lista (vazia) de declaracoes de variaveis de
	 * um mesmo tipo. Cada declaracao deve ser agregada a lista atraves do metodo
	 * addChild() aplicado ao objeto retornado por esta chamda. 
	 * @param type - tipo das variaveis declaradas na lista.
	 * @return a representacao da lista 
	 */
	public TreeNode makeVarDeclList(TreeNode type){	
			VarDeclList no = new VarDeclList((Type)type);			
			return no;			
	}
	
	/**
	 * Constroi a representacao de uma declaracao de variavel.
	 * @param name - nome da variavel
	 * @param value - valor inicial (ou null)
	 * @return  - o objeto TreeNode que representa a declaracao
	 */
	public TreeNode makeVarDecl(String name, TreeNode value){
		
		Type tipoVar;
		
		try {
			tipoVar = (Type) ((Expression) value).getType();
		} catch (Exception e) {
			tipoVar = null;
		}			
		
		VarDeclaration no = new VarDeclaration(false,tipoVar,name);	
		
		no.addChild(value);
		//TODO
		return no;
	}
	
	/**
	 * Constroi a representacao de uma declaracao de um parametro de funcao. 
	 * @param name - nome do parametro
	 * @param type - tipo do parametro.
	 * @param refMode - modo de passagem (false => valor, true => referencia).
	 * @return- o objeto TreNode que representa o parametro.
	 */
	public TreeNode makeParmDecl(String name, TreeNode type, boolean refMode){
		
		VarDeclaration no = new VarDeclaration(refMode,(Type) type, name);
		return (TreeNode) no;		
	}
	
	/**
	 * Constroi a representacao de uma declaracao de funcao, com a lista de
	 * parametros vazia. A lista de parametros deve ser preenchida atraves de
	 * chamadas ao metodo TreeNode.addChild(), onde cada parametro eh representado
	 * como uma declaracao de parametro.
	 * @param name - nome da funcao
	 * @param type - tipo de retorno da funcao
	 * @param body - objeto TreeNode que representa o comando composto correspondente
	 * 				 ao corpo da funcao.
	 * @return  - o objeto TreNode que representa a declaracao
	 */
	public TreeNode makeFunctionDecl(String name, TreeNode type, TreeNode body){			
		TreeNode no = new FunctionDeclaration(name,(Type) type, body);				
		return no;		
	}
	
	/**
	 * Constroi a representacao de uma constante inteira.
	 * @param value - string que representa o valor da constante.
	 * @return - o objeto TreNode que representa a constante.
	 */
	public TreeNode makeIntConstOp(String value){	
		TreeNode no = new ConstOp(value, makePrimType("int"));
		return no;	
	}
	
	/**
	 * Constroi a representacao de uma constante do tipo 'char'.
	 * @param value - string que representa o valor da constante.
	 * @return - o objeto TreNode que representa a constante.
	 */
	public TreeNode makeCharConstOp(String value){
		TreeNode no = new ConstOp(value,makePrimType("char"));		
		return no;
	}
	
	/**
	 * Constroi a representacao de uma constante do tipo 'boolean'.
	 * @param value - string que representa o valor da constante.
	 * @return - o objeto TreNode que representa a constante.
	 */
	public TreeNode makeBoolConstOp(String value){
		TreeNode no = new ConstOp(value,makePrimType("string"));		
		return no;
	}
	
	/**
	 * Constroi a representacao de uma variavel (usada numa expressao).
	 * @param name - nome da variavel
	 * @return - o objeto TreeNode que representa a variavel.
	 */
	public TreeNode makeVarOp(String name){		
		VarOp no = new VarOp(name);
		return no;
	}
	
	/**
	 * Constroi a representacao de uma definicao de constante.
	 * @param name - nome da constante
	 * @param value - valor da constante
	 * @return - o objeto TreNode que representa a constante
	 */
	public TreeNode makeConstDef(String name, TreeNode value){
		TreeNode no = new ConstDeclaration(name,value);
		return no;
	}
	
	/**
	 * Constroi a representacao de uma tupla com a lista de valores vazia.
	 * A lista de valores deve ser preenchida atraves de
	 * chamadas ao metodo TreeNode.addChild().
	 * @return - - o objeto TreNode que representa a tupla
	 */
	public TreeNode makeTuple(){
		TreeNode no = new TupleOp();
		return no;
	}
	
	/**
	 * Constroi a representacao de uma constante do tipo String.
	 * @param value - valor do string a ser criado.
	 * @return - o objeto TreNode que representa o string.
	 */
	public TreeNode makeString(String value){
		TreeNode no = new StringOp(value);
		return no;
	}
	
	/**
	 * Constroi a representacao intermediaria de uma operacao (numa expressao)
	 * @param oper - constante inteira que define o tipo da operacao
	 * @param opn1 - representacao do primeiro operando
	 * @param opn2 - representacao do segundo operando
	 * @return- o objeto TreNode que representa a operacao
	 */
	public TreeNode makeOperation(int oper, TreeNode opn1, TreeNode opn2){
		
			TreeNode no;
					
			if (opn2 != null)
			{
				no = new DiadOp(oper, opn1,opn2);				
				
			}else{
				no = new UnaryOp(oper,opn1);		
			}		
			return no;
	}
	
	/**
	 * Constroi a representacao intermediaria de um comando condicional.
	 * @param cond - representacao da condicao associada ao comando.
	 * @param stat1 - representacao do comando a ser executado caso a condicao seja verdadeira.
	 * @param stat2 - representacao do comando a ser executado caso a condicao seja falsa.
	 * @retrn - o objeto TreNode que representa o comando.
	 */
	public TreeNode makeIfStat(TreeNode cond, TreeNode stat1, TreeNode stat2){
		
		TreeNode no = new IfStat(cond, stat1, stat2);		
		return no;
		}
	
	/**
	 * Constroi a representacao de um comando composto vazio. Os comandos que
	 * fazem parte do mesmo devem ser agregados um a um atraves de chamadas ao
	 * metodo addChild() aplicado ao objeto TreeNode retornado por este metodo.
	 * @return -- o objeto TreNode que representa o comando.
	 */
	public TreeNode makeCompoundStat(){
		
		TreeNode no = new CompoundStat();
		return no;
	}
	
	/**
	 * Constroi a representacao de um comando 'while'.
	 * @param cond - condicao associada ao comando.
	 * @param stat - comando a ser repetido enquanto a condicao for verdadeira.
	 * @return- o objeto TreNode que representa o comando.
	 */
	public TreeNode makeWhileStat(TreeNode cond, TreeNode stat){
		
		TreeNode no = new WhileStat(cond,stat);
		return no;
		}
	
	/**
	 * Constroi a representacao de um comando 'do-while'.
	 * @param cond - condicao associada ao comando.
	 * @param stat - comando a ser repetido ate que a condicao seja verdadeira.
	 * @return - o objeto TreNode que representa o comando.
	 */
	public TreeNode makeDoStat(TreeNode cond, TreeNode stat){
		TreeNode no = new DoStat(cond,stat); 
		return no;
		}
	
	/**
	 * Constroi a representacao intermediaria de um comando 'for'
	 * @param var - variavel associada ao comando.
	 * @param start - representacao da expressao que define o valor inicial da variavel.
	 * @param finish - representacao da expressao que define o valor final da variavel.
	 * @param dir - sentido da contagem (true => crescente, false => decrescente).
	 * @return
	 */
	public TreeNode makeForStat(TreeNode var, TreeNode start, TreeNode finish, boolean dir){
		
		TreeNode no = new ForStat(var, start, finish, dir);
		return no;
		
	}
	
	/**
	 * Constroi a representacao intermediaria de um comando 'return'.
	 * @param value - representacao da expressao cujo valor deve ser retornado 
	 * 				  (ou null caso a funcao seja 'void')
	 * @return
	 */
	public TreeNode makeReturnStat(TreeNode value){
		TreeNode no = new ReturnStat((Expression) value); 
		return no;
		
	}
	
	/**
	 * Constroi a representacao intermediaria de uma chamada de funcao. A lista 
	 * de valores associados aos parametros eh vazia e deve ser preenchida atraves de
	 * chamadas ao metodo addChild() aplicado ao objeto retornado por esta chamada.
	 * @param fName - nome da funcao.
	 * @return - o objeto TreNode que representa a operacao.
	 */
	public TreeNode makeCallOp(String fName){
	
		TreeNode no = new FunctionCallOp(fName);		
		return no;
		
	}
	
	/**
	 * Constroi a representacao intermediaria de um programa vazio.
	 * As declaracoes do programa devem ser agregadas atraves de chamadas
	 * ao metodo addChild() aplicado ao objeto retornado por esta chamada. 
	 */
	public TreeNode makeProgram(){
		TreeNode no = new Program();		
		return no;
	}
}
package pl747.tabelaSimbolos;

/**
 * Representa um s�mbolo usado num programa (pode ser um pr�-definido ou declarado pelo programador).
 */
public abstract class Symbol {
	
	String name; // nome do s�mbolo
	int level;   // n�vel de encaixamento do s�mbolo (coment�rio em getLevel() );
	int kind;    // categoria do simbolo (ver coment�rios nas constantes abaixo)
	
	public static final int CONST_SYMB   = 0;     // constante
	public static final int PRIM_TYPE    = 1;     // tipo primitivo
	public static final int STRUCT_TYPE   = 2;    // tipo estrutura
	public static final int VECTOR_TYPE   = 3;    // tipo vetor 
	public static final int VAR_SYMB      = 4;    // vari�vel
	public static final int FIELD_SYMB    = 5;    // campo de estrutura
	public static final int PARM_SYMB     = 6;    // par�metro
	public static final int FUNCTION_SYMB = 7;    // fun��o
	
	private static int nameCounter = 0; // contador de 'nomes' p/ os s�mbolos an�nimos

	
	/**
	 * Devolve o nome associado ao s�mbolo.
	 * @return TODO
	 */
	public String getName() { return name; }
	
	/**
	 * Retorna o 'n�vel' em que o s�mbolo � declarado no programa. 
	 * Valores retornados:
	 * 0 - s�mbolo pr�-definido
	 * 1 - s�mbolo global (declarado no programa)
	 * 2 - s�mbolo local (declarado numa fun��o).
	 * @return n�vel associado ao campo
	 */
	public int getLevel() { return level; }
	
	/**
	 * retorna a categoria do s�mbolo;
	 * @return categoria do s�mbolo
	 */
	public int getKind() { return kind; }
	
	/**
	 * Construtor
	 */
	public Symbol(String name, int level,int kind ){
	  if(name == null) name = makeName(); // 'inventa' um nome
	  this.name = name;
	  this.level = level;
	  this.kind = kind; 
	}
	
	/**
	 * 'inventa' um nome para os tipos an�nimos
	 * @return
	 */
	private String makeName(){
	  return "#"+(nameCounter++);	
	}
	
	//public abstract String toString();
}


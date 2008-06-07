package pl747;

public interface PL747Consts {
	/**
	 * Constantes associadas às construções da linguagem (makeOperation(int,... ) )
	 */
	public static final int INDEX_OP  =  0;   /** indexação            **/
	public static final int SEL_OP    =  1;   /** seleção de campo     **/
	public static final int ADD_OP    =  2;   /** '+'                  **/
	public static final int SUB_OP    =  3;   /** '-'                  **/
	public static final int MINUS_OP  =  4;   /** '-' unário           **/
	public static final int MULT_OP   =  5;   /** '*'                  **/
	public static final int DIV_OP    =  6;   /** '/'                  **/
	public static final int MOD_OP    =  7;   /** '%'                  **/
	public static final int EQ_OP     =  8;   /** '=='                 **/
	public static final int NE_OP     =  9;   /** '!='                 **/
	public static final int GT_OP     = 10;   /** '>'                  **/
	public static final int LT_OP     = 11;   /** '<'                  **/
	public static final int GE_OP     = 12;   /** '>='                 **/
	public static final int LE_OP     = 13;   /** '<='                 **/
	public static final int AND_OP    = 14;   /** '&&'                 **/
	public static final int OR_OP     = 15;   /** '||'                 **/
	public static final int NOT_OP    = 16;   /** '!' (unário)         **/
	public static final int ASSIGN_OP = 17;   /** '='                  **/
	public static final int CALL_OP   = 18;   /** function call op     **/

	public static final int PRIM_TYPE = 19;   /** primitive type       **/
	public static final int STRUCT_TYPE = 20; /** struct type          **/
	public static final int VECTOR_TYPE = 21; /** vector type          **/
	public static final int VAR_DECL_LIST = 22; /** var decl. list     **/
	public static final int VAR_DECL    = 23; /** var decl.            **/
	public static final int FUNCTION_DECL = 24; /** function decl.     **/
	public static final int INTCONST_OP    = 25; /** constant             **/
	public static final int BOOLCONST_OP  = 26;
	public static final int CHARCONST_OP  = 27;
	public static final int VAR_OP      = 28; /** operation            **/
	public static final int CONST_DEF   = 29; /** const. def.          **/
	public static final int TUPLE       = 30; /** tuple                **/
	public static final int STRING      = 31; /** string const.        **/
	
	public static final int IF_STAT     = 32; /** if statement         **/
	public static final int COMPOUND_STAT    = 33; /** compound stat.  **/
	public static final int WHILE_STAT  = 34; /** while stat.          **/
	public static final int DO_STAT     = 35; /** do stat.             **/
	public static final int FOR_STAT    = 36; /** for statement        **/
	public static final int RETURN_STAT = 37; /** return stat.         **/
	public static final int VALUE_PARM  = 38; /** 'by value' parameter **/
	public static final int REF_PARM    = 39; /** 'by ref'   parameter **/
	public static final int PROGRAM     = 40; /** program              **/
	
	/* usado na reconstrução do código fonte */
	public static final String[] OP_STR = { 
		                                    "[",".","+","-","-","*","/","%",
		                                    "==","!=",">","<",">=","<=",
		                                    "&&","||","!","="
	                                      };
	
}

package pl747.tabelaSimbolos;
/**
 * Implementa as opera��es de manipula��o da tabela de s�mbolos usada no
 * compilador (como m�todos est�ticos).
 *
 */

import java.util.*;
import pl747.*;
import pl747.semantico.Declaration;
import pl747.semantico.StructType;
import pl747.semantico.VectorType;

public class SymbolTable {

	/** tipos pr�-declarados (primitivos e auxiliares) **/
	public static final PrimTypeSymb intType    = new PrimTypeSymb("int");      
	public static final PrimTypeSymb boolType   = new PrimTypeSymb("boolean");
	public static final PrimTypeSymb charType   = new PrimTypeSymb("char");
	public static final PrimTypeSymb voidType   = new PrimTypeSymb("void");
	public static final PrimTypeSymb stringType = new PrimTypeSymb("~String");
	public static final PrimTypeSymb tupleType  = new PrimTypeSymb("~tupleType");
	public static final PrimTypeSymb wrongType  = new PrimTypeSymb("~wrongType");

	/** mapa contendo os tipos previamente declarados **/
	private static HashMap<Declaration,PrimTypeSymb> typeMap = new HashMap<Declaration,PrimTypeSymb>();
	
	private static Scope curScope = null; // escopo atual
	
	/** (re)inicia a tabela (ainda sem uso) **/
	public static void init(){
		
	}
	
	/**
	 * Cria um novo escopo.
	 * @return escopo criado, que passa a ser o escopo atual.
	 */
	public static Scope newScope(){
		curScope = new Scope(curScope);
		return curScope;
	}
	
	/**
	 * 'fecha' o escopo corrente.
	 * @return o novo escopo atual.
	 */
	public static Scope closeScope(){
		curScope = curScope.getUpperScope();
		return curScope;
	}
	
	/**
	 * Retorna o escopo atual.
	 * @return escopo atual.
	 */
	public static Scope getCurScope() {
		return curScope;
	}

	/**
	 * Devolve o n�vel de encaixamento corrente
	 * @return n�vel de encaixamento atual
	 */
	public static int getCurLevel() {
		return curScope.getLevel();
	}
	
	/**
	 * Muda o escopo atual.
	 * @param scope novo escopo atual.
	 * @return novo escopo atual.
	 */
	public static Scope setCurScope(Scope scope){
		curScope = scope;
		return curScope;
	}
	
	/**
	 * Busca por um s�mbolo a partir do nome, no escopo atual.
	 * @param name nome do s�mbolo
	 * @return s�mbolo (ou null se n�o encontra).
	 */
	public static Symbol localSearch(String name){
		return curScope.getSymbol(name); 
	}
	
	/**
	 * Busca por um s�mbolo a partir do nome, a partir do escopo atual.
	 * @param name nome do s�mbolo
	 * @return s�mbolo (ou null se n�o encontra)
	 */
	 public static Symbol search(String name){
		 Scope sc = curScope;
		 while(sc != null){
			 Symbol symb = sc.getSymbol(name);
			 if(symb != null) return symb;
			 sc = sc.getUpperScope();
		 }
		 return null;
	 }
	 
	/**
	 * Insere s�mbolo na tabela.
	 * @param symb s�mbolo a ser inserido.
	 * @return true se inser��o OK (false => s�mbolo j� presente).
	 */
	public static boolean insertSymb(Symbol symb){
		//System.out.println("inserting "+symb.getName());
		return curScope.addSymbol(symb);
	}
	
	/**
	 * Verifica se dois tipos primitivos s�o compat�veis.
	 * @param s1 - s�mbolo que descreve o primeiro tipo
	 * @param s2 - s�mbolo que descreve o segundo tipo
	 * @return true se os tipos s�o compat�veis (false em caso contr�rio)
	 */
	public static boolean primTypeCompatible(Symbol s1, Symbol s2){
		if((s1 == s2)&&
		   ((s1 == intType)|| (s1 == boolType)|| (s1 == charType))) return true;
		return false;
	}
	
	/**
	 * Verifica se dois tipos s�o compat�veis
	 * @param t - primeiro tipo
	 * @param s - segundo tipo
	 * @return true se os tipos s�o compat�veis (false em caso contr�rio)
	 */
	public static boolean typeCompatible(Symbol t, Symbol s){
		  if(s instanceof PrimTypeSymb) return SymbolTable.primTypeCompatible(t, s);
		  if(s instanceof VectorTypeSymb){
			  if(!(t instanceof VectorTypeSymb)) return false;
			  if(((VectorTypeSymb)s).getSize() != ((VectorTypeSymb)t).getSize()) return false;
			  Symbol s1 = ((VectorTypeSymb)s).getElementType();
			  Symbol s2 = ((VectorTypeSymb)t).getElementType();
			  return SymbolTable.primTypeCompatible(s1, s2);
		  }
		  if(s instanceof StructTypeSymb) return (s==t);
		  return false;
	}
	
	/**
	 * Devolve o s�mbolo correspondente a um tipo passado como AbsNode<-Declaration
	 * @param type AbsNode que descreve o tipo
	 * @return o tipo passado como par�metro
	 */
	public static PrimTypeSymb getTypeAsSymbol(Declaration type){
		LinkedList<String>errors = new LinkedList<String>();
		if(type != null)
			try {
				type.check(errors);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		for(String s:errors) System.out.println("******getTypeAs => erro:"+s);
		PrimTypeSymb res = typeMap.get(type);
		if(res != null) return res;
		if("int".equals(type.getName()))    { typeMap.put(type,intType);  return intType; }
		if("char".equals(type.getName()))   { typeMap.put(type,charType); return charType; }
		if("boolean".equals(type.getName())){ typeMap.put(type,boolType); return boolType; }
		//if(type instanceof VectorType) System.out.println("getTypeAs... size:"+((VectorType)type).getSize());
		if(type instanceof VectorType) res = (PrimTypeSymb)((VectorType)type).getSymbType(); 
		else if (type instanceof StructType) res = (PrimTypeSymb)((StructType)type).getSymbType();
		//if(res == null)System.out.println("getTypeAs... res == null - type:"+type.getName());
        typeMap.put(type, res);
        //System.out.println("getTypeAsSymb- returning:"+res.getName());
        //System.out.println("  => "+res.toString());
		return res;
	}
}

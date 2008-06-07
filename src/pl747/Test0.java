package pl747;

import java.util.*;
import pl747.semantico.*;

/**
 * M�dulo de testes exemplo:
 * 		- monta as chamadas � uma classe que implementa TreeNodeFactory correspondentes
 * 		  ao seguinte programa em PL747:
 * 
 *   int a,b=1, c;
 *   boolean b1 = false;
 *   char[4] nome = "JOSE";
 *   struct{ int x,y; } p1,p2 = {10,5};
 *   int[10] tab = { 2,3,5,7,11,13,17,19,23, 29 };
 *   
 *   const c1 = 10;
 *   const c2 = true;
 *   const c3 = 'a';
 *   const c4 = "string";
 *   const c5 = {1,2,3};
 * 
 * 	int mdc(int a, int b){
 *     if(a == b) return a;
 *     if(a < b) return mdc(b,a);
 *     return mdc(b,a % b);
 *  }
 *  
 *  int fat(int n) { 
 *     if( n == 0) return 1; 
 *     else return  n*fat(n-1);  
 *  }  
 *
 * int seqSearch(int k, int i, int j){
 *   int s;
 *   for s = i to j do 
 *     if(tab[s] == k) return s;
 *   return -1;
 * }
 *
 * void main(){
 *     int x = 18;
 *     int y = 24;
 *     int z = mdc(x,y);
 *     int f = fat(z);
 * }
 *  
 */

public class Test0 implements PL747Consts{
	
	/**
	 *   tipos primitivos
	 */
	static TreeNode intType;
	static TreeNode boolType;
	static TreeNode charType;
	static TreeNode voidType;
	
	/**
	 * Monta a �rvore de programa para declara��es de vari�veis e constantes:
	 * 
	 *   int a,b=1, c;
	 *   boolean b1 = false;
	 *   char[4] nome = "JOSE";
	 *   struct{ int x,y; } p1,p2 = {10,5};
     *   int[10] tab = { 2,3,5,7,11,13,17,19,23, 29 };
     *   int[2][2] m = { {1,2}, {3,4} }; 
     *   struct{ int x, int y }[10] v,w;
	 *   const c1 = 10;
	 *   const c2 = true;
	 *   const c3 = 'a';
	 *   const c4 = "string";
	 *   const c5 = {1,2,3};
	 * 
	 * @param tnf
	 * @return
	 * @throws Exception
	 */
	static void makeVarDeclarations1(TreeNodeFactory tnf,TreeNode prog) throws Exception{
	    TreeNode decl1 = tnf.makeVarDeclList(intType);
		TreeNode decl2 = tnf.makeVarDeclList(boolType);
		TreeNode decl3 = tnf.makeVarDeclList(tnf.makeVectorType("4",charType));
		TreeNode struct4 = tnf.makeStructType();
		TreeNode tuple4 = tnf.makeTuple();
		TreeNode decl4 = tnf.makeVarDeclList(struct4);
		TreeNode fieldList4 = tnf.makeVarDeclList(intType);
		TreeNode decl5 = tnf.makeVarDeclList(tnf.makeVectorType("10", intType));
		TreeNode tuple5 = tnf.makeTuple();
		TreeNode decl6 = tnf.makeVarDeclList(tnf.makeVectorType("2", tnf.makeVectorType("2",intType)));
		TreeNode tuple6 = tnf.makeTuple();
		TreeNode tuple6_1 = tnf.makeTuple();
		TreeNode tuple6_2 = tnf.makeTuple();
		TreeNode struct7 = tnf.makeStructType();
		TreeNode fieldList7_1 = tnf.makeVarDeclList(intType);
		TreeNode fieldList7_2 = tnf.makeVarDeclList(intType);
		TreeNode decl7 = tnf.makeVarDeclList(tnf.makeVectorType("10",struct7));
		try{
			/*   int a,b=1, c; */
			decl1.addChild(tnf.makeVarDecl("a", null));
			decl1.addChild(tnf.makeVarDecl("b", tnf.makeIntConstOp("1")));
			decl1.addChild(tnf.makeVarDecl("c", null));
			prog.addChild(decl1);
			
			/* boolean b1 = false; */
			decl2.addChild(tnf.makeVarDecl("b1", tnf.makeBoolConstOp("false")));
			prog.addChild(decl2);
			
			/* char[4] nome = "JOSE"; */
			decl3.addChild(tnf.makeVarDecl("nome", tnf.makeString("JOSE")));
			prog.addChild(decl3);
			
			/* struct{ int x,y; } p1,p2 = {10,5}; */
			fieldList4.addChild(tnf.makeVarDecl("x",null));
			fieldList4.addChild(tnf.makeVarDecl("y",null));
			struct4.addChild(fieldList4);
			tuple4.addChild(tnf.makeIntConstOp("10"));
			tuple4.addChild(tnf.makeIntConstOp("5"));
			decl4.addChild(tnf.makeVarDecl("p1",null));
			decl4.addChild(tnf.makeVarDecl("p2",tuple4));
			prog.addChild(decl4);
			
			/* int[10] tab = { 2,3,5,7,11,13,17,19,23, 29 }; */
			tuple5.addChild(tnf.makeIntConstOp("2"));
			tuple5.addChild(tnf.makeIntConstOp("3"));
			tuple5.addChild(tnf.makeIntConstOp("5"));
			tuple5.addChild(tnf.makeIntConstOp("7"));
			tuple5.addChild(tnf.makeIntConstOp("11"));
			tuple5.addChild(tnf.makeIntConstOp("13"));
			tuple5.addChild(tnf.makeIntConstOp("17"));
			tuple5.addChild(tnf.makeIntConstOp("19"));
			tuple5.addChild(tnf.makeIntConstOp("23"));
			tuple5.addChild(tnf.makeIntConstOp("29"));
			decl5.addChild(tnf.makeVarDecl("tab", tuple5));
			prog.addChild(decl5);
			
			/* int[2][2] m = { {1,2}, {3,4} };  */
			tuple6_1.addChild(tnf.makeIntConstOp("1"));
			tuple6_1.addChild(tnf.makeIntConstOp("2"));
			tuple6.addChild(tuple6_1);
			tuple6_2.addChild(tnf.makeIntConstOp("3"));
			tuple6_2.addChild(tnf.makeIntConstOp("4"));
			tuple6.addChild(tuple6_2);
			decl6.addChild(tnf.makeVarDecl("m", tuple6));
			prog.addChild(decl6);
			
			/* struct{ int x, int y }[10] v,w; */
			struct7.addChild(fieldList7_1);
			fieldList7_1.addChild(tnf.makeVarDecl("x", null));
			fieldList7_2.addChild(tnf.makeVarDecl("y", null));
			struct7.addChild(fieldList7_2);
			decl7.addChild(tnf.makeVarDecl("v", null));
			decl7.addChild(tnf.makeVarDecl("w", null));
			prog.addChild(decl7);
			
			/*  const c1 = 10; */
			prog.addChild(tnf.makeConstDef("c1",tnf.makeIntConstOp("10")));
			
			/* const c2 = true; */
			prog.addChild(tnf.makeConstDef("c2",tnf.makeBoolConstOp("true")));
			
			/* const c3 = 'a'; */
			prog.addChild(tnf.makeConstDef("c3",tnf.makeCharConstOp("\'a\'")));
			
			/* const c4 = "string"; */
			prog.addChild(tnf.makeConstDef("c4",tnf.makeString("string")));
			
			/* const c5 = {1,2,3}; */
			TreeNode tuplac5 = tnf.makeTuple();
			tuplac5.addChild(tnf.makeIntConstOp("1"));
			tuplac5.addChild(tnf.makeIntConstOp("2"));
			tuplac5.addChild(tnf.makeIntConstOp("3"));
			prog.addChild(tnf.makeConstDef("c5",tuplac5));
		}catch(Exception ee){ ee.printStackTrace(); }
	}
		
		
	/**
	 * Monta a �rvore de programa para a fun��o
	 * 
	 * 	int mdc(int a, int b){
	 *     if(a == b) return a;
	 *     if(a < b) return mdc(b,a);
	 *     return mdc(b,a % b);
	 *  }
	 *  
	 * @param tnf
	 * @return
	 * @throws Exception
	 */
	static TreeNode makeMdcFunction(TreeNodeFactory tnf)throws Exception {
		TreeNode mdcBody = tnf.makeCompoundStat();
		TreeNode mdc = tnf.makeFunctionDecl("mdc",intType, mdcBody);
		/** par�metros **/
		mdc.addChild(tnf.makeParmDecl("a", intType, false));
		mdc.addChild(tnf.makeParmDecl("b", intType, false));
		
		/** corpo da fun��o **/
		  	/** if(a==b) return a; **/
			TreeNode var_a = tnf.makeVarOp("a");
			TreeNode var_b = tnf.makeVarOp("b");
			TreeNode cond1 = tnf.makeOperation(EQ_OP,var_a, var_b);
			TreeNode ret1 = tnf.makeReturnStat(var_a); // return a;
			TreeNode ifStat1 = tnf.makeIfStat(cond1, ret1, null);       // if(a == b) return a;
			mdcBody.addChild(ifStat1);
			
			/** if(a < b) return mdc(b,a); **/
			TreeNode cond2 = tnf.makeOperation(LT_OP,var_a, var_b); // (a < b) 
			TreeNode call1 = tnf.makeCallOp("mdc"); 
			call1.addChild(var_b);
			call1.addChild(var_a);
			TreeNode ret2 = tnf.makeReturnStat(call1); // return mdc(b,a);
			TreeNode ifStat2 = tnf.makeIfStat(cond2, ret2, null); // if (a < b) return mdc(b,a);
			mdcBody.addChild(ifStat2);
		
			/** return mdc(b,a%b); **/
			TreeNode call2 = tnf.makeCallOp("mdc");
			call2.addChild(var_b);
			call2.addChild(tnf.makeOperation(MOD_OP, var_a, var_b)); 
			TreeNode ret3 = tnf.makeReturnStat(call2);
			mdcBody.addChild(ret3);
		
		return mdc;
	}

	/**
	 *    Monta a �rvore de programa para a fun��o
	 *    
	 *    int fat(int n) { if( n == 0) return 1; else return  n*fat(n-1);  }
	 *    
	 * @param tnf
	 * @return
	 */
	static TreeNode makeFatFunction(TreeNodeFactory tnf) throws Exception{
	    TreeNode fatBody = tnf.makeCompoundStat();  
	    
	    /** int fat();  **/
	    TreeNode fat = tnf.makeFunctionDecl("fat", intType,fatBody); 
	    
	    /** lista de Par�metros **/
	    fat.addChild(tnf.makeParmDecl("n",intType,false));            // par�metro n
	    
	    /** auxiliares **/
	    TreeNode var_n = tnf.makeVarOp("n");                         // vari�vel local n
	    TreeNode zero = tnf.makeIntConstOp("0");                        // constante "0"
	    TreeNode one = tnf.makeIntConstOp("1");                         // constante "1"
	    
	    /** primeiro comando do if **/
	    TreeNode ret1 = tnf.makeReturnStat(one);                     // return 1;
	    
	    /** segundo comando do if **/
	    TreeNode call = tnf.makeCallOp("fat");                       // fat()                 
	    TreeNode sub = tnf.makeOperation(SUB_OP,var_n,one);          // (n-1)
	    call.addChild(sub);	                                         // fat(n-1);
	    TreeNode times = tnf.makeOperation(MULT_OP,var_n,call);      // n*fat(n-1)
	    TreeNode ret2 = tnf.makeReturnStat(times);                   // return n*fat(n-1)
	    
	    /** condi��o **/
	    TreeNode cond = tnf.makeOperation(EQ_OP, var_n,zero);        // (n == 0)
	    
	    /** comando if agregado ao corpo da fun��o **/
	    fatBody.addChild(tnf.makeIfStat(cond,ret1,ret2));
	    return fat; 
	}
	
	/**
	 * Constr�i a representa��o intermedi�ria da fun��o
	 * 
	 * 	void troca(ref int a, ref int b){
	 *    int t = a;
	 *    a = b;
	 *    b = t;
	 *  }
	 * @param tnf
	 * @return
	 */
	static TreeNode makeTrocaFunction(TreeNodeFactory tnf){
		TreeNode body = tnf.makeCompoundStat();
		TreeNode trocaFunction = tnf.makeFunctionDecl("troca", voidType, body);
		try{
		  trocaFunction.addChild(tnf.makeParmDecl("a", intType, true));
		  trocaFunction.addChild(tnf.makeParmDecl("b", intType, true));
		  TreeNode decl =tnf.makeVarDeclList(intType);
		  decl.addChild(tnf.makeVarDecl("t",tnf.makeVarOp("a")));
		  body.addChild(decl);
		  body.addChild(tnf.makeOperation(ASSIGN_OP, tnf.makeVarOp("a"), tnf.makeVarOp("b")));
		  body.addChild(tnf.makeOperation(ASSIGN_OP, tnf.makeVarOp("b"), tnf.makeVarOp("t")));
		}catch(Exception ee) { ee.printStackTrace(); }
		return trocaFunction;
	}
	/**
	 * Monta a �rvore de programa para a fun��o
	 * 
	 * void reverse(int i, int j) {
	 *   while(i < j){
	 *     troca(tab[i],tab[j];
	 *     inc(i); dec(j);
	 *   }
	 * }
	 * 
	 * @param tnf
	 * @return
	 * @throws Exception
	 */
	static TreeNode makeReverseFunction(TreeNodeFactory tnf) throws Exception{
		TreeNode body = tnf.makeCompoundStat();
		TreeNode rev = tnf.makeFunctionDecl("reverse", voidType, body);
		TreeNode var_i = tnf.makeVarOp("i");
		TreeNode var_j = tnf.makeVarOp("j");
		TreeNode var_tab = tnf.makeVarOp("tab");
		try{
			rev.addChild(tnf.makeParmDecl("i", intType, false));
			rev.addChild(tnf.makeParmDecl("j", intType, false));
			TreeNode stat = tnf.makeCompoundStat();
			TreeNode call1 = tnf.makeCallOp("troca");
			call1.addChild(tnf.makeOperation(INDEX_OP, var_tab, var_i));
			call1.addChild(tnf.makeOperation(INDEX_OP, var_tab, var_j));
			stat.addChild(call1);
			TreeNode call2 = tnf.makeCallOp("inc");
			call2.addChild(var_i);
			stat.addChild(call2);
			TreeNode call3 = tnf.makeCallOp("dec");
			call3.addChild(var_j);
			stat.addChild(call3);
			body.addChild(tnf.makeWhileStat(tnf.makeOperation(LT_OP, var_i, var_j), stat));
		}catch(Exception ee) { ee.printStackTrace(); }
		return rev;
	}
	
	/**
	 * Constr�i a �rvore de programa para a fun��o
	 * int seqSearch(int k, int i, int j){
	 *   int s;
	 *   for s = i to j do 
	 *     if(tab[s] == k) return s;
	 *   return -1;
	 * }
	 * @param tnf
	 * @return
	 * @throws Exception
	 */
	static TreeNode makeSeqSearchFunction(TreeNodeFactory tnf) throws Exception{
		TreeNode body = tnf.makeCompoundStat();
		TreeNode seq = tnf.makeFunctionDecl("seqSearch", intType, body);
		TreeNode var_k = tnf.makeVarOp("k");
		TreeNode var_i = tnf.makeVarOp("i");
		TreeNode var_j = tnf.makeVarOp("j");
		TreeNode var_s = tnf.makeVarOp("s");
		TreeNode var_tab = tnf.makeVarOp("tab");
		TreeNode decl = tnf.makeVarDeclList(intType);
		body.addChild(decl);
		TreeNode ret1 = tnf.makeReturnStat(var_s);
		TreeNode ret2 = tnf.makeReturnStat(tnf.makeIntConstOp("-1"));
		TreeNode cond = tnf.makeOperation(EQ_OP,tnf.makeOperation(INDEX_OP, var_tab, var_s), var_k);
		TreeNode ifSt = tnf.makeIfStat(cond, ret1, null);
		try{
			 seq.addChild(tnf.makeParmDecl("k", intType, false));
			 seq.addChild(tnf.makeParmDecl("i", intType, false));
			 seq.addChild(tnf.makeParmDecl("j", intType, false));
			 decl.addChild(tnf.makeVarDecl("s", null));			 
			 TreeNode forStat = tnf.makeForStat(var_s, var_i, var_j, true);
			 forStat.addChild(ifSt);
  	 		 body.addChild(forStat);
			 body.addChild(ret2);
		}catch(Exception ee) { ee.printStackTrace(); }
		return seq;
	}

	/**
	 * Monta a �rvore de programa para a fun��o
	 * 
	 * void main(){
	 *     int x = 18, y = 24;
	 *     int z = mdc(x,y);
	 *     println("fat(",z,"):",fat(z));
	 *     p1.x = p2.x;
	 *     p1.y = p2.y;
	 *     do{
	 *       x = x+y;
	 *     }while(x < 100);
	 * }
	 * 
	 * @param tnf
	 * @return
	 * @throws Exception
	 */
	static TreeNode makeMainFunction(TreeNodeFactory tnf) throws Exception{
	   
		TreeNode mainBody = tnf.makeCompoundStat();  

	     /** void main();  **/
	    TreeNode mainFunc = tnf.makeFunctionDecl("main", voidType,mainBody);
	    TreeNode intList1 = tnf.makeVarDeclList(intType);
	    intList1.addChild(tnf.makeVarDecl("x",tnf.makeIntConstOp("18")));  // int x = 18;
	    intList1.addChild(tnf.makeVarDecl("y",tnf.makeIntConstOp("24")));  // int y = 24;
	    mainBody.addChild(intList1);

	    TreeNode call1 = tnf.makeCallOp("mdc");          // mdc()
	    call1.addChild(tnf.makeVarOp("x")); 
	    call1.addChild(tnf.makeVarOp("y"));              // mdc(x,y)
	   
	    TreeNode intList2 = tnf.makeVarDeclList(intType);
	    intList2.addChild(tnf.makeVarDecl("z", call1));
	    mainBody.addChild(intList2);

	    /** println("fat(",z,"):",fat(z)) **/
	    TreeNode call2 = tnf.makeCallOp("println");
	    call2.addChild(tnf.makeString("fat("));
	    call2.addChild(tnf.makeVarOp("z"));
	    call2.addChild(tnf.makeString("):"));
	      TreeNode call3 = tnf.makeCallOp("fat");          // fat()
	      call3.addChild(tnf.makeVarOp("z"));              // fat(z);
	    call2.addChild(call3);
	    mainBody.addChild(call2);
	    TreeNode var_p1 = tnf.makeVarOp("p1");
	    TreeNode var_p2 = tnf.makeVarOp("p2");
	    TreeNode var_x  = tnf.makeVarOp("x");
	    TreeNode var_y  = tnf.makeVarOp("y");
	    TreeNode sel_p1_x = tnf.makeOperation(SEL_OP,var_p1,var_x);
	    TreeNode sel_p1_y = tnf.makeOperation(SEL_OP,var_p1,var_y);
	    TreeNode sel_p2_x = tnf.makeOperation(SEL_OP,var_p2,var_x);
	    TreeNode sel_p2_y = tnf.makeOperation(SEL_OP,var_p2,var_y);
	    mainBody.addChild(tnf.makeOperation(ASSIGN_OP, sel_p1_x, sel_p2_x));
	    mainBody.addChild(tnf.makeOperation(ASSIGN_OP, sel_p1_y, sel_p2_y));
	    TreeNode cond = tnf.makeOperation(LT_OP, var_x, tnf.makeIntConstOp("100"));
	    TreeNode stat = tnf.makeCompoundStat();
	    stat.addChild(tnf.makeOperation(ASSIGN_OP, var_x, tnf.makeOperation(ADD_OP, var_x, var_y)));
	    mainBody.addChild(tnf.makeDoStat(cond, stat));
		return mainFunc;
	}
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		AbsNodeFactory nodeFactory = new AbsNodeFactory();
		
		/** inicializa��o dos tipos primitivos **/
		intType = nodeFactory.makePrimType("int");
		boolType = nodeFactory.makePrimType("boolean");
		charType = nodeFactory.makePrimType("char");
		voidType = nodeFactory.makePrimType("void");
		
		
		/** cria��o do programa **/
		TreeNode program = nodeFactory.makeProgram();	  		
		
		try{
			makeVarDeclarations1(nodeFactory,program);
			program.addChild(makeMdcFunction(nodeFactory)); 
			program.addChild(makeFatFunction(nodeFactory));
			program.addChild(makeTrocaFunction(nodeFactory));
			program.addChild(makeReverseFunction(nodeFactory));
			program.addChild(makeSeqSearchFunction(nodeFactory));
			program.addChild(makeMainFunction(nodeFactory));
			//System.out.println(((AbsNode)program).toSourceCode());			
			
		}catch(Exception ee) {
			ee.printStackTrace(); 
		}
		
		
		ArrayList<String> errorList = new ArrayList<String>();		
		boolean checagem = ((AbsNode)program).check(errorList);
		
		
		if (!checagem){
			for (String string : errorList) {
				System.out.println(string);
			}			
		}
		else
		{
			System.out.println("Nenhum erro encontrado");
		}
	}
		
	
	

}

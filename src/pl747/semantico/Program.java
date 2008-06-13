package pl747.semantico;

import java.util.*;

import pl747.TreeNode;
import pl747.Visitor;
import pl747.tabelaSimbolos.*;

public class Program extends CompoundStat{

	private Scope scope; 
	
	public Program() {
		super();			
	}	
	
 	public Scope getScope() {
		return scope;
	}


	public void setScope(Scope scope) {
		this.scope = scope;
	}


	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check( List<String> errorList ) {
		
		// Inicializando a Tabela de Simbolos
		SymbolTable.init();
		this.scope = SymbolTable.newScope();
		
		/*
		 * Insercao dos simbolos das Funcoes predefinicidas na tabela de simbolos
		 */
		
		//funcao inc inteira
		FunctionSymb incInt = new FunctionSymb("inc",new PrimTypeSymb("int"));
		SymbolTable.getCurScope().addSymbol(incInt);
		List<ParmSymb> incIntParamList = new ArrayList<ParmSymb>();
		ParmSymb incIntParam = new ParmSymb(null,new PrimTypeSymb("int"),false);				
		incIntParamList.add(incIntParam);
		incInt.setParmList(incIntParamList);
		
		//funcao inc de caracteres
		FunctionSymb incChar = new FunctionSymb("inc",new PrimTypeSymb("char"));
		SymbolTable.getCurScope().addSymbol(incChar);
		List<ParmSymb> incCharParamList = new ArrayList<ParmSymb>();
		ParmSymb incCharParam = new ParmSymb(null,new PrimTypeSymb("char"),false);				
		incCharParamList.add(incCharParam);
		incChar.setParmList(incCharParamList);
		
		//funcao inc booleana
		FunctionSymb incBool = new FunctionSymb("inc",new PrimTypeSymb("boolean"));
		SymbolTable.getCurScope().addSymbol(incBool);
		List<ParmSymb> incBoolParamList = new ArrayList<ParmSymb>();
		ParmSymb incBoolParam = new ParmSymb(null,new PrimTypeSymb("boolean"),false);				
		incBoolParamList.add(incBoolParam);
		incBool.setParmList(incBoolParamList);
		
		//funcao dec inteira
		FunctionSymb decInt = new FunctionSymb("dec",new PrimTypeSymb("int"));
		SymbolTable.getCurScope().addSymbol(decInt);
		List<ParmSymb> decIntParamList = new ArrayList<ParmSymb>();
		ParmSymb decIntParam = new ParmSymb(null,new PrimTypeSymb("int"),false);				
		decIntParamList.add(decIntParam);
		decInt.setParmList(decIntParamList);
		
		//funcao dec de caracteres
		FunctionSymb decChar = new FunctionSymb("dec",new PrimTypeSymb("char"));
		SymbolTable.getCurScope().addSymbol(decChar);
		List<ParmSymb> decCharParamList = new ArrayList<ParmSymb>();
		ParmSymb decCharParam = new ParmSymb(null,new PrimTypeSymb("char"),false);				
		decCharParamList.add(decCharParam);
		decChar.setParmList(decCharParamList);
		
		//funcao dec booleana
		FunctionSymb decBool = new FunctionSymb("dec",new PrimTypeSymb("boolean"));
		SymbolTable.getCurScope().addSymbol(decBool);
		List<ParmSymb> decBoolParamList = new ArrayList<ParmSymb>();
		ParmSymb decBoolParam = new ParmSymb(null,new PrimTypeSymb("boolean"),false);				
		decBoolParamList.add(decBoolParam);
		decBool.setParmList(decBoolParamList);

		//funcao read
		FunctionSymb readSymb = new FunctionSymb("read",new PrimTypeSymb("void"));
		SymbolTable.getCurScope().addSymbol(readSymb);
		List<ParmSymb> readParamList = new ArrayList<ParmSymb>();
		readSymb.setParmList(readParamList);
		
		//funcao readln
		FunctionSymb readlnSymb = new FunctionSymb("readln",new PrimTypeSymb("void"));
		SymbolTable.getCurScope().addSymbol(readlnSymb);
		List<ParmSymb> readlnParamList = new ArrayList<ParmSymb>();
		readlnSymb.setParmList(readlnParamList);

		//funcao print
		FunctionSymb printSymb = new FunctionSymb("print",new PrimTypeSymb("void"));
		SymbolTable.getCurScope().addSymbol(printSymb);
		List<ParmSymb> printParamList = new ArrayList<ParmSymb>();
		printSymb.setParmList(printParamList);
		
		//funcao println
		FunctionSymb printlnSymb = new FunctionSymb("println",new PrimTypeSymb("void"));
		SymbolTable.getCurScope().addSymbol(printlnSymb);
		List<ParmSymb> printlnParamList = new ArrayList<ParmSymb>();
		printlnSymb.setParmList(printlnParamList);

		//type casting inteiro (int -> int)
		FunctionSymb castInt = new FunctionSymb("int",new PrimTypeSymb("int"),false);
		SymbolTable.getCurScope().addSymbol(castInt);
		List<ParmSymb> castIntParamList = new ArrayList<ParmSymb>();
		ParmSymb castIntParam = new ParmSymb(null,new PrimTypeSymb("int"),false);				
		castIntParamList.add(castIntParam);
		castInt.setParmList(castIntParamList);
		
		//type casting inteiro (char -> int)
		FunctionSymb castChar2Int = new FunctionSymb("int",new PrimTypeSymb("int"),false);
		SymbolTable.getCurScope().addSymbol(castChar2Int);
		List<ParmSymb> castChar2IntParamList = new ArrayList<ParmSymb>();
		ParmSymb castChar2IntParam = new ParmSymb(null,new PrimTypeSymb("char"),false);				
		castChar2IntParamList.add(castChar2IntParam);
		castChar2Int.setParmList(castChar2IntParamList);
		
		//type casting inteiro (boolean -> int)
		FunctionSymb castBool2Int = new FunctionSymb("int",new PrimTypeSymb("int"),false);
		SymbolTable.getCurScope().addSymbol(castBool2Int);
		List<ParmSymb> castBool2IntParamList = new ArrayList<ParmSymb>();
		ParmSymb castBool2IntParam = new ParmSymb(null,new PrimTypeSymb("boolean"),false);				
		castBool2IntParamList.add(castBool2IntParam);
		castBool2Int.setParmList(castBool2IntParamList);

		//type casting de caracteres (char -> char)
		FunctionSymb castChar = new FunctionSymb("char",new PrimTypeSymb("char"),false);
		SymbolTable.getCurScope().addSymbol(castChar);
		List<ParmSymb> castCharParamList = new ArrayList<ParmSymb>();
		ParmSymb castCharParam = new ParmSymb(null,new PrimTypeSymb("char"),false);				
		castCharParamList.add(castCharParam);
		castChar.setParmList(castCharParamList);
		
		//type casting de caracteres (int -> char)
		FunctionSymb castInt2Char = new FunctionSymb("char",new PrimTypeSymb("char"),false);
		SymbolTable.getCurScope().addSymbol(castInt2Char);
		List<ParmSymb> castInt2CharParamList = new ArrayList<ParmSymb>();
		ParmSymb castInt2CharParam = new ParmSymb(null,new PrimTypeSymb("int"),false);				
		castInt2CharParamList.add(castInt2CharParam);
		castInt2Char.setParmList(castInt2CharParamList);
		
		//type casting de caracteres (boolean -> char)
		FunctionSymb castBool2Char = new FunctionSymb("char",new PrimTypeSymb("char"),false);
		SymbolTable.getCurScope().addSymbol(castBool2Char);
		List<ParmSymb> castBool2CharParamList = new ArrayList<ParmSymb>();
		ParmSymb castBool2CharParam = new ParmSymb(null,new PrimTypeSymb("boolean"),false);				
		castBool2CharParamList.add(castBool2CharParam);
		castBool2Char.setParmList(castBool2CharParamList);
				
		//type casting booleano (bool -> bool)
		FunctionSymb castBool = new FunctionSymb("boolean",new PrimTypeSymb("boolean"),false);
		SymbolTable.getCurScope().addSymbol(castBool);
		List<ParmSymb> castBoolParamList = new ArrayList<ParmSymb>();
		ParmSymb castBoolParam = new ParmSymb(null,new PrimTypeSymb("boolean"),false);				
		castBoolParamList.add(castBoolParam);
		castBool.setParmList(castBoolParamList);
		
		//type casting booleano (int -> bool)
		FunctionSymb castInt2Bool = new FunctionSymb("boolean",new PrimTypeSymb("boolean"),false);
		SymbolTable.getCurScope().addSymbol(castInt2Bool);
		List<ParmSymb> castInt2BoolParamList = new ArrayList<ParmSymb>();
		ParmSymb castInt2BoolParam = new ParmSymb(null,new PrimTypeSymb("int"),false);				
		castInt2BoolParamList.add(castInt2BoolParam);
		castInt2Bool.setParmList(castInt2BoolParamList);
		
		//type casting booleano (char -> bool)
		FunctionSymb castChar2Bool = new FunctionSymb("boolean",new PrimTypeSymb("boolean"),false);
		SymbolTable.getCurScope().addSymbol(castChar2Bool);
		List<ParmSymb> castChar2BoolParamList = new ArrayList<ParmSymb>();
		ParmSymb castChar2BoolParam = new ParmSymb(null,new PrimTypeSymb("char"),false);				
		castChar2BoolParamList.add(castChar2BoolParam);
		castChar2Bool.setParmList(castChar2BoolParamList);
		
		
		// Variavel de retorno da funcao		
		boolean result = true;
		  
		// Verificando todos os nos da arvore de programa
		if(this.getStatList() != null)
		{
			List<Expression> statList = this.getStatList();
			for (TreeNode no: statList) {
				try {
					if (no.check(errorList) == false){
						result = false;						
					}
				//} catch (Exception e) {}
				} catch (Exception e) {e.printStackTrace();}
			}
		}
		// Verificando se a funcao main foi declarada
		if(SymbolTable.search("main") == null){
			errorList.add("Funcao main() nao declarada");
			result = false;
		}

		
		FunctionSymb funcSymb = null;
		for(Symbol no: SymbolTable.getCurScope().getSymbList()) {
			
			if (no instanceof FunctionSymb) {
				// Verificando se o prototipo foi implementado
				
				funcSymb = (FunctionSymb) no;
				if(funcSymb.isPrototype())	{
					result = false;
					errorList.add("Prototipo da funcao " + funcSymb.getName() + " nao implementado");		
				}
			}
			
		}
		
		return result;
	}

	public Object accept( Visitor v ) {
		v.visit(this);
		return null;
	}
} 
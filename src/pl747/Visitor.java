package pl747;

import java.util.List;
import java.util.Vector;

import com.sun.org.apache.xpath.internal.operations.Variable;

import pl747.codigo.gerador;
import pl747.semantico.*;
import pl747.tabelaSimbolos.*;
import pl747.codigo.VarSymbTbl;

public class Visitor {
	
	private int Label = 0;
	
	public void visit(Program node) {
		int i, n;
		List<TreeNode> L = node.getStatList();		
		Scope scope = node.getScope();
		List<Symbol> sList;
		Symbol symb;
		
		System.out.println("visit(Program " + node + " )");
		
		SymbolTable.setCurScope(scope);
		sList = scope.getSymbList();
		n = sList.size();
		
		gerador.pWriter.println("INIT");
		
		System.out.println("n is " + n);
		
		for ( i = 0; i < n; i++ )
		{
			symb = sList.get(i);
			if ( symb.getKind() == Symbol.VAR_SYMB )
				alloc((VarSymb)symb);
			else if ( symb.getKind() == Symbol.CONST_SYMB )
				alloc((ConstSymb)symb);
		}
		
		gerador.pWriter.println("LOADR T");
		gerador.pWriter.println("STORER B");
		gerador.pWriter.println("LOADR B");
		gerador.pWriter.println("STORER BM");
		gerador.pWriter.println("CTE 0");
		gerador.pWriter.println("LOADR PC");
		gerador.pWriter.println("CTE 3");
		gerador.pWriter.println("ADD");
		gerador.pWriter.println("CALL :MAIN");
		gerador.pWriter.println("STOP");		
		
		if(L != null)
			for (TreeNode exp: L)
			{
    			if ( exp instanceof VarDeclaration )
    				((VarDeclaration)exp).accept(gerador.v);
    			else if ( exp instanceof FunctionDeclaration )
    				((FunctionDeclaration)exp).accept(gerador.v);
    			else if ( exp instanceof ConstDeclaration )
    				((ConstDeclaration)exp).accept(gerador.v);
			}
		
		gerador.addr += 2;
		
	//	SymbolTable.closeScope();
	}
	
	public void alloc(VarSymb s) {
		int n;
		
		n = 0;
		n = n + 1;
		
		if ( s.getType() instanceof VectorTypeSymb )
		{
			
		}
		else if ( s.getType() instanceof StructTypeSymb )
		{
			
		}
		else
		{
			gerador.pWriter.println("ALLOC 1");
			((VarSymb)SymbolTable.search(s.getName())).setAddress(gerador.addr);
			gerador.addr++;
		}
	}
	
	public void alloc(ConstSymb s) {
		
	}
	
	public void visit(VarDeclList node) {
		int i, n;
		List<VarDeclaration> vList = node.getElements();
		
		System.out.println("visit(VarDeclList " + node + " )");
		
		for (VarDeclaration var: vList)
			var.accept(gerador.v);				
	}
	
	public void visit(Declaration node) {
		System.out.println("visit(Declaration " + node + " )");
	}
	
    public void visit(VarDeclaration node) {
    	int size, j;
    	Type type;
    	List varList;
    	Expression exp;    	
    	
    	System.out.println("visit(VarDeclaration " + node + " )");
    	
    	if ( 1 == 1)
    		return;
    	
    	try {
    		type = node.getType();
    		    		
    		if ( type instanceof VectorType )
    		{    			
    			size = Integer.parseInt(((VectorType)type).getSize());
    			gerador.pWriter.println("ALLOC " + size);
    		}
    		else if ( type instanceof StructType )
    		{
    			varList = ((StructType)type).getElementList();    			
    			size = GetTotalSize(varList);
    			gerador.pWriter.println("ALLOC " + size);
    		}
    		else
    		{
    			size = 1;
    			gerador.pWriter.println("ALLOC 1");
    			
    			((VarSymb)SymbolTable.search(node.getName())).setAddress(gerador.addr);
    			// ((VarSymbTbl)SymbolTable.search(node.getName())).setAddress(gerador.addr);
    			
    			exp = node.getInitialValue();
    			
    			if ( exp instanceof DiadOp )
    				((DiadOp)exp).accept(gerador.v);
    			else if ( exp instanceof VarOp )
    				((VarOp)exp).accept(gerador.v);
    			else if ( exp instanceof FunctionCallOp )
    				((FunctionCallOp)exp).accept(gerador.v);
    			else if ( exp instanceof ConstOp )
    				((ConstOp)exp).accept(gerador.v);
    			else if ( exp instanceof UnaryOp )
    				((UnaryOp)exp).accept(gerador.v);
    			
    			gerador.pWriter.println("STORE B " + SymbolTable.search(node.getName()));
			}
    		
    		// ((VarSymbTbl)SymbolTable.search(node.getName())).setAddress(gerador.addr);
    		gerador.addr += size;    		
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    		System.err.print("VarDeclarition node exception.\n");
    		System.exit(1);
    	}
    }
    
    private int GetTotalSize(List varList) {
    	int tSize = 0;
    	int i;
    	int listSize;
    	Type type;
    	Object curObj;
    	
    	listSize = varList.size();
    	
    	i = 0;
    	while ( i < listSize )
    	{
    		curObj = varList.get(i);
    		type = ((VarDeclaration)curObj).getType();
    		
    		if ( type instanceof VectorType )
    			tSize = tSize + Integer.parseInt(((VectorType)type).getSize());
    		else if ( type instanceof StructType )
    			tSize = tSize + GetTotalSize(((StructType)type).getElementList());
    		else
    			tSize = tSize + 1;
    			
    		i++;
    	}
    	
    	return tSize;
    }

    public void visit(FunctionDeclaration node) {
    	CompoundStat body;
    	List<TreeNode> sList;
    	List param;
    	boolean isPrototype;
    	Scope scope;
    	int i, n;    	
    	String oldLabel = gerador.rLabel;
    	Symbol symb;
    	List<Symbol> syList;
    	
    	System.out.println("visit(FunctionDeclaration " + node + " )");
    	
    	body = (CompoundStat)node.getBody();
    	param = node.getParmList();
    	isPrototype = node.isPrototype();
    	scope = node.getScope();
    	
    	SymbolTable.setCurScope(scope);
    	syList = scope.getSymbList();
    	n = syList.size();		
    	
    	if ( !isPrototype ) {
    		System.out.println("  >> not prototype");
    		try {
    			gerador.rLabel = ":RET_" + node.getName().toUpperCase();
    			
    			gerador.pWriter.println(":" + node.getName().toUpperCase());
    			gerador.pWriter.println("ENTER");
    			
    			gerador.addr = 2;
    			    			
    			for ( i = 0; i < n; i++ )
    			{
    				symb = syList.get(i);
    				if ( symb.getKind() == Symbol.VAR_SYMB )
    					alloc((VarSymb)symb);
    				else if ( symb.getKind() == Symbol.CONST_SYMB )
    					alloc((ConstSymb)symb);
    			}
    			
    			sList = body.getStatList();
    			for (TreeNode exp: sList)
    			{
    				
    				exp.getClass();
    				
    				if ( exp instanceof VarDeclaration )
        				((VarDeclaration)exp).accept(gerador.v);
        			else if ( exp instanceof FunctionDeclaration )
        				((FunctionDeclaration)exp).accept(gerador.v);
        			else if ( exp instanceof ConstDeclaration )
        				((ConstDeclaration)exp).accept(gerador.v);
        			else if ( exp instanceof DiadOp )
        				((DiadOp)exp).accept(gerador.v);
        			else if ( exp instanceof VarOp )
        				((VarOp)exp).accept(gerador.v);
        			else if ( exp instanceof FunctionCallOp )
        				((FunctionCallOp)exp).accept(gerador.v);
        			else if ( exp instanceof ConstOp )
        				((ConstOp)exp).accept(gerador.v);
        			else if ( exp instanceof TupleOp )
        				((TupleOp)exp).accept(gerador.v);
        			else if ( exp instanceof UnaryOp )
        				((UnaryOp)exp).accept(gerador.v);
        			else if ( exp instanceof StringOp )
        				((StringOp)exp).accept(gerador.v);
        			else if ( exp instanceof IfStat )
        				((IfStat)exp).accept(gerador.v);
        			else if ( exp instanceof WhileStat )
        				((WhileStat)exp).accept(gerador.v);
        			else if ( exp instanceof DoStat )
        				((DoStat)exp).accept(gerador.v);
        			else if ( exp instanceof ForStat )
        				((ForStat)exp).accept(gerador.v);
        			else if ( exp instanceof ReturnStat )
        				((ReturnStat)exp).accept(gerador.v);        			
        			else if ( exp instanceof VarDeclList )
        				((VarDeclList)exp).accept(gerador.v);
        			else 
        				System.out.println("  >> exp not found");
    			}
    			
    			n = GetTotalSize(param);
    			
    			gerador.pWriter.println(":RET_" + node.getName().toUpperCase());
    			if ( node.getName().compareTo("main") != 0 )
    				gerador.pWriter.println("STORE B -" + (n+1));
    			gerador.pWriter.println("LOADR B");
    			gerador.pWriter.println("CTE -3");
    			gerador.pWriter.println("ADD");
    			gerador.pWriter.println("RET");
    			
    			gerador.rLabel = oldLabel;

    		}
    		catch (Exception ex) {
    			ex.printStackTrace();
    			System.err.print("Erro ao escrever no arquivo.\n");
    			System.exit(1);
    		}
    	}
    	
    	// SymbolTable.closeScope();
    }

    public void visit(ConstDeclaration node) {
    	int tSize = 0;
    	Type lsType = node.getTipo();
    	Type rsType = node.getValue().getType();
    	List varList;
    	
    	System.out.println("visit(ConstDeclaration " + node + " )");
    	
    /*	if ( rsType instanceof VectorType )
			tSize = Integer.parseInt(((VectorType)lsType).getSize());
		else if ( lsType instanceof )
		{
			tSize = GetTotalSize(varList);
		}
		else
			tSize = 1;
    	*/
    }
    
    public void visitPrint(FunctionCallOp node) {
    	int i, j, n;
    	Expression exp;
    	List<Expression> param = node.getParmList();
    	String s;
    	
    	System.out.println("visitPrint(FunctionCallOp " + node + " )");
    	
    	n = param.size();
    	for ( i = 0; i < n; i++ )
    		{
    			exp = param.get(i);
    		
    			if ( exp instanceof DiadOp )
    				((DiadOp)exp).accept(gerador.v);
    			else if ( exp instanceof VarOp ) {
    				((VarOp)exp).accept(gerador.v);
    				s = ((VarOp)exp).getType().getName();
    				if ( s.compareTo("int") == 0 )
    					gerador.pWriter.println("PRINT INT");
    				else if ( s.compareTo("char") == 0 )
    					gerador.pWriter.println("PRINT CHAR");
    				else if ( s.compareTo("boolean") == 0 )
    					gerador.pWriter.println("PRINT BOOLEAN");
    			}
    			else if ( exp instanceof FunctionCallOp )
    				((FunctionCallOp)exp).accept(gerador.v);
    			else if ( exp instanceof ConstOp )
    				((ConstOp)exp).accept(gerador.v);
    			else if ( exp instanceof TupleOp )
    				((TupleOp)exp).accept(gerador.v);
    			else if ( exp instanceof UnaryOp )
    				((UnaryOp)exp).accept(gerador.v);
    			else if ( exp instanceof StringOp ) {    				
    				((StringOp)exp).accept(gerador.v);    				
    				for ( j = 0; j < ((StringOp)exp).getValue().length(); j++ )
    					gerador.pWriter.println("PRINT CHAR");
    			}
    			
    			
    		}
    }
    
    public void visitRead(FunctionCallOp node) {
    	System.out.println("visitRead(FunctionCallOp " + node + " )");
    }

    public void visit(FunctionCallOp node) {
    	int i, n;
    	Expression exp;
    	List<Expression> param = node.getParmList();
    	
    	System.out.println("visit(FunctionCallOp " + node + " )");
    	    	
    	if ( node.getFunctionName().compareTo("print") == 0 ) {
    		visitPrint(node);
    		return;
    	}
    	
    	n = param.size();
    	for ( i = 0; i < n; i++ )
    		{
    			exp = param.get(i);
    		
    			if ( exp instanceof DiadOp )
    				((DiadOp)exp).accept(gerador.v);
    			else if ( exp instanceof VarOp )
    				((VarOp)exp).accept(gerador.v);
    			else if ( exp instanceof FunctionCallOp )
    				((FunctionCallOp)exp).accept(gerador.v);
    			else if ( exp instanceof ConstOp )
    				((ConstOp)exp).accept(gerador.v);
    			else if ( exp instanceof TupleOp )
    				((TupleOp)exp).accept(gerador.v);
    			else if ( exp instanceof UnaryOp )
    				((UnaryOp)exp).accept(gerador.v);
    			else if ( exp instanceof StringOp )
    				((StringOp)exp).accept(gerador.v);
    		}
    	    	
    	gerador.pWriter.println("LOADR B");
    	gerador.pWriter.println("LOADR T");
    	gerador.pWriter.println("CTE 1");
    	gerador.pWriter.println("SUB");
    	gerador.pWriter.println("STORER B");
    	gerador.pWriter.println("LOADR PC");
    	gerador.pWriter.println("CTE 3");
    	gerador.pWriter.println("ADD");
    	gerador.pWriter.println("CALL :" + node.getFunctionName().toUpperCase());
    }

    public void visit(ConstOp node) {
    	int i, n;    
    	String type;
    	String s = node.getValue();
    	
    	System.out.println("visit(ConstOp " + node + " )");
    	
    	type = node.getType().getName();
    	
    	if ( type.compareTo("int") == 0 )
    		gerador.pWriter.println("CTE " + Integer.parseInt(s));
    	else if ( type.compareTo("boolean") == 0 )
    		if ( s.charAt(0) == 't' )
    			gerador.pWriter.println("CTE 1");
    		else
    			gerador.pWriter.println("CTE 0");
    	else if ( type.compareTo("char") == 0 )
    		gerador.pWriter.println("CTE " + (int)(s.charAt(0)-'\0'));
    }

    public void visit(StringOp node) {
    	int i;
    	int n;
    	String s;
    	
    	System.out.println("visit(StringOp " + node + " )");
    	
    	s = node.getValue();
    	n = s.length();
    	
    	for ( i = n-1; i >= 0; i-- )
    		gerador.pWriter.println("CTE " + (int)(s.charAt(i)-'\0'));    		
    }

    public void visit(TupleOp node) {
    	System.out.println("visit(TupleOp " + node + " )");
    }

    public void visit(VarOp node) {
    	System.out.println("visit(VarOp " + node + " )");    	
    	if ( SymbolTable.search(node.getName()).getLevel() == 0 )
    		gerador.pWriter.println("LOAD 0 " + ((VarSymb)SymbolTable.search(node.getName())).getAddress());
    	else
    		gerador.pWriter.println("LOAD B " + ((VarSymb)SymbolTable.search(node.getName())).getAddress());
    }

    public void visit(DiadOp node) {
    	int OP;
    	Expression expr;
    	
    	System.out.println("visit(DiadOp " + node + " )");
    	    	
    	OP = node.getKind();
    	
    	switch (OP) {
    	
    	case PL747Consts.INDEX_OP:
    		break;
    		
    	case PL747Consts.SEL_OP:
    		break;
    		
    	case PL747Consts.ADD_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("ADD");
    		break;
    		
    	case PL747Consts.SUB_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("SUB");
    		break;
    		    		
    	case PL747Consts.MULT_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("MUL");
    		break;
    		
    	case PL747Consts.DIV_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("DIV");
    		break;
    		
    	case PL747Consts.MOD_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("MOD");
    		break;
    		
    	case PL747Consts.EQ_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("EQL");
    		break;
    		
    	case PL747Consts.NE_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("NEQ");
    		break;
    		
    	case PL747Consts.GT_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("GT");
    		break;
    		
    	case PL747Consts.LT_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("LT");
    		break;
    		
    	case PL747Consts.GE_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("GTE");
    		break;
    		
    	case PL747Consts.LE_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("LTE");
    		break;
    		
    	case PL747Consts.AND_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);
    		gerador.pWriter.println("AND");
    		break;
    		
    	case PL747Consts.OR_OP:
    		((DiadOp)node.getFirstOperand()).accept(gerador.v);
    		((DiadOp)node.getSecondOperand()).accept(gerador.v);    		
    		gerador.pWriter.println("OR");
    		break;
    		
    	case PL747Consts.ASSIGN_OP:
    		expr = node.getSecondOperand();
    		if ( expr instanceof FunctionCallOp )
    			((FunctionCallOp)expr).accept(gerador.v);
    		else if ( expr instanceof ConstOp )
    			((ConstOp)expr).accept(gerador.v);
    		else if ( expr instanceof StringOp )
    			((StringOp)expr).accept(gerador.v);
    		else if ( expr instanceof TupleOp )
    			((TupleOp)expr).accept(gerador.v);
    		else if ( expr instanceof VarOp )
    			((VarOp)expr).accept(gerador.v);
    		else if ( expr instanceof DiadOp )
    			((DiadOp)expr).accept(gerador.v);
    		else if ( expr instanceof UnaryOp )
    			((UnaryOp)expr).accept(gerador.v);
    		
    		expr = node.getFirstOperand();
    		
    		if ( expr instanceof VarOp )
    		{
    			if ( SymbolTable.search(((VarOp)expr).getName()).getLevel() == 0 )
    				gerador.pWriter.println("STORE 0 " + ((VarSymb)SymbolTable.search(((VarOp)expr).getName())).getAddress());
    			else
    				gerador.pWriter.println("STORE B " + ((VarSymb)SymbolTable.search(((VarOp)expr).getName())).getAddress());
    		}
    		else if ( expr instanceof DiadOp )
    		{
    			OP = ((DiadOp)(expr)).getKind();
    			if ( OP == PL747Consts.INDEX_OP ) {
    				// accept(node.getSecondOperand());
    				/* Usar indireção para acessar a posição
    				 * do vetor na pilha.
    				 * Tem que fazer uma soma.
    				 */
    			}
    			
    		}
    		break;
    		
    	}
    	
    }

    public void visit(UnaryOp node) {
    	int OP = node.getKind();
    	
    	System.out.println("visit(UnaryOp " + node + " )");
    	
    	switch (OP) {
    	case PL747Consts.MINUS_OP:
    		// accept(node.getOperand());
    		gerador.pWriter.println("NEG");
    		break;
    		
    	case PL747Consts.NOT_OP:
    		// accept(node.getOperand());
    		gerador.pWriter.println("NOT");
    		break;
    	}
    }

    public void visit(CompoundStat node) {
    	System.out.println("visit(CompoundStat " + node + " )");
    }

    public void visit(IfStat node) {
    	System.out.println("visit(IfStat " + node + " )");
    	// accept(node.getCondition());
    	gerador.pWriter.println("JMPF :cond_" + Label);
    	// accept(node.getThenPart());
    	gerador.pWriter.println("JMP :end_" + Label);
    	gerador.pWriter.println(":cond_" + Label);
    	// accept(node.getElsePart());
    	gerador.pWriter.println(":end_" + Label);
    	Label++;
    }

    public void visit(WhileStat node) {
    	System.out.println("visit(WhileStat " + node + " )");
    	gerador.pWriter.println(":cond_" + Label);
    	// accept(node.getCondition());
    	gerador.pWriter.println("JMPF :end_" + Label);
    	// accept(node.getStat());
    	gerador.pWriter.println("JMP :cond_" + Label);
    	gerador.pWriter.println(":end_" + Label);
    	Label++;
    }

    public void visit(DoStat node) {
    	System.out.println("visit(DoStat " + node + " )");
    	gerador.pWriter.println(":cond_" + Label);
    	// accept(node.getStat());
    	// accept(node.getCondition());
    	gerador.pWriter.println("JMPT :cond_" + Label);    	    	
    }

    public void visit(ForStat node) {
    	String var = node.getVariable();
    	
    	System.out.println("visit(ForStat " + node + " )");
    	
    	// accept(node.getStartValue());
    	gerador.pWriter.println("STORE B " + "" /* getAdress */);
    	
    	gerador.pWriter.println(":cond_" + Label);
    	// accept(node.getFinalValue());
    	gerador.pWriter.println("JMPF :end_" + Label);    	
    	// accept(node.getStat());
    	if ( node.getDirection() == true )
    		gerador.pWriter.println("INC B " + "" /* getAdress */);
    	else
    		gerador.pWriter.println("DEC B " + "" /* getAdress */);
    	gerador.pWriter.println("JMP cond_" + Label);
    	gerador.pWriter.println(":end_" + Label);
    	Label++;
    }

    public void visit(ReturnStat node) {
    	System.out.println("visit(ReturnStat " + node + " )");
    	// accept(node.getValue());
    	gerador.pWriter.println("JMP " + gerador.rLabel);
    }
}

package pl747;

import java.util.List;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.impl.dv.xs.FullDVFactory;
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
		int size;
		Type type;
		
		n = 0;
		n = n + 1;
		
		if ( s.getType() instanceof VectorTypeSymb )
		{			
			size = ((VectorTypeSymb)(s.getType())).getSize();			
			gerador.pWriter.println("ALLOC " + size);
			
			((VarSymb)SymbolTable.search(s.getName())).setAddress(gerador.addr);
			gerador.addr += size;
		}
		else if ( s.getType() instanceof StructTypeSymb )
		{	
			/*
			LinkedList<FieldSymb> lList = ((StructTypeSymb)(s.getType())).getF
			varList = ((StructType)type).getElementList();    			
			size = GetTotalSize(varList);
			gerador.pWriter.println("ALLOC " + size);
			*/
		}
		else
		{
			gerador.pWriter.println("ALLOC 1");
			((VarSymb)SymbolTable.search(s.getName())).setAddress(gerador.addr);
			gerador.addr++;
		}
	}
	
	public void alloc(ConstSymb s) {
		int i, size, addr;
		AbsNode n = s.getValue();
		
		if ( n instanceof ConstOp ) {
			gerador.pWriter.println("ALLOC 1");
			((ConstSymb)SymbolTable.search(s.getName())).setAddress(gerador.addr);
			gerador.addr++;
			((ConstOp)n).accept(gerador.v);
			gerador.pWriter.println("STORE B " + ((ConstSymb)SymbolTable.search(s.getName())).getAddress());
		}
		else if ( n instanceof StringOp ) {
			size = ((StringOp)n).getValue().length();
			gerador.pWriter.println("ALLOC " + size);
			((ConstSymb)SymbolTable.search(s.getName())).setAddress(gerador.addr);
			gerador.addr += size;
		
			addr = ((ConstSymb)SymbolTable.search(s.getName())).getAddress();
			
			((StringOp)n).accept(gerador.v);
			for ( i = 0; i < size; i++ )
				gerador.pWriter.println("STORE B " + (addr + i));
		}
		else if ( n instanceof TupleOp )
			;
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
    	int i, size, j, n;
    	Type type;
    	List varList;
    	Expression exp;    	
    	
    	System.out.println("visit(VarDeclaration " + node + " )");
    	
    	try {
    		type = node.getType();
    		    		
    		if ( type instanceof VectorType )
    		{    			
    			exp = node.getInitialValue();
    			
    			if ( exp != null )
    			{
    				if ( exp instanceof TupleOp )    					
    					((TupleOp)exp).accept(gerador.v);
    				else if ( exp instanceof StringOp )
    					((StringOp)exp).accept(gerador.v);
    				
    				n = Integer.parseInt(((VectorType)type).getSize());
    				
    				for ( i = 0; i < n; i++ )
    					gerador.pWriter.println("STORE B " + ( ((VarSymb)SymbolTable.search(node.getName())).getAddress() + i));
    			}
    		}
    		else if ( type instanceof StructType )
    		{
    			// TODO
    		}
    		else
    		{
    			size = 1;
    			
    			exp = node.getInitialValue();
    			
    			if ( exp != null ) {
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
    				
    				gerador.pWriter.println("STORE B " + ((VarSymb)SymbolTable.search(node.getName())).getAddress());    				
    			}
			}
    				
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
    		try {
    			gerador.rLabel = ":RET_" + node.getName().toUpperCase();
    			
    			gerador.pWriter.println(":" + node.getName().toUpperCase());
    			gerador.pWriter.println("ENTER");
    			
    			gerador.addr = 2;
    			 //################################################################################################################################   			
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
    			if ( ((Declaration)node.getType()).getName().compareTo("void") != 0 && (node.getName()).compareTo("main") != 0 )
    				gerador.pWriter.println("STORE B -" + (n+1));
    			gerador.pWriter.println("LOADR T");
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
    			{
    				
    				((DiadOp)exp).accept(gerador.v);
    				
    				s = ((DiadOp)exp).getType().getName();
    				if ( s.compareTo("int") == 0 )
    					gerador.pWriter.println("PRINT INT");
    				else if ( s.compareTo("char") == 0 )
    					gerador.pWriter.println("PRINT CHAR");
    				else if ( s.compareTo("boolean") == 0 )
    					gerador.pWriter.println("PRINT BOOLEAN");
    			}
    			else if ( exp instanceof ConstOp ) {
    				((ConstOp)exp).accept(gerador.v);
    				s = ((ConstOp)exp).getType().getName();
    				if ( s.compareTo("int") == 0 )
    					gerador.pWriter.println("PRINT INT");
    				else if ( s.compareTo("char") == 0 )
    					gerador.pWriter.println("PRINT CHAR");
    				else if ( s.compareTo("boolean") == 0 )
    					gerador.pWriter.println("PRINT BOOLEAN");
    			}
    			else if ( exp instanceof FunctionCallOp ) {
    				((FunctionCallOp)exp).accept(gerador.v);
    				s = ((FunctionCallOp)exp).getType().getName();
    				if ( s.compareTo("int") == 0 )
    					gerador.pWriter.println("PRINT INT");
    				else if ( s.compareTo("char") == 0 )
    					gerador.pWriter.println("PRINT CHAR");
    				else if ( s.compareTo("boolean") == 0 )
    					gerador.pWriter.println("PRINT BOOLEAN");
    			}
    			else if ( exp instanceof VarOp ) {
     				if ( ((VarOp)exp).getType() instanceof VectorType ) {
     					// TODO HERE     					
     				}
     				else if ( ((VarOp)exp).getType() instanceof StructType ) {
     					// TODO
     				}
     				else {
    					((VarOp)exp).accept(gerador.v);
    					s = ((VarOp)exp).getType().getName();
    					if ( s.compareTo("int") == 0 )
    						gerador.pWriter.println("PRINT INT");
    					else if ( s.compareTo("char") == 0 )
    						gerador.pWriter.println("PRINT CHAR");
    					else if ( s.compareTo("boolean") == 0 )
    						gerador.pWriter.println("PRINT BOOLEAN");
    				}
    			}
    			else if ( exp instanceof TupleOp ) {
    				((TupleOp)exp).accept(gerador.v);
    				System.out.println("Wrong argument for print ...");
    				System.exit(1);
    			}
    			else if ( exp instanceof UnaryOp ) {
    				((UnaryOp)exp).accept(gerador.v);
    				s = ((UnaryOp)exp).getType().getName();
    				if ( s.compareTo("int") == 0 )
    					gerador.pWriter.println("PRINT INT");
    				else if ( s.compareTo("char") == 0 )
    					gerador.pWriter.println("PRINT CHAR");
    				else if ( s.compareTo("boolean") == 0 )
    					gerador.pWriter.println("PRINT BOOLEAN");
    			}
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
    	
    	if ( node.getFunctionName().compareTo("println") == 0 ) {
    		visitPrint(node);
    		gerador.pWriter.println("CTE 10");
    		gerador.pWriter.println("PRINT CHAR");
    		return;
    	}
    	
    	if ( (node.getType()).getName().compareTo("void") != 0 )
    		gerador.pWriter.println("ALLOC 1");
    	
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
    		gerador.pWriter.println("CTE " + (int)(s.charAt(1)-'\0'));
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
    	int i, n;
    	Expression exp;
    	List<Expression> eList = node.getElementList();
    	
    	System.out.println("visit(TupleOp " + node + " )");
    	
    	n = eList.size();
    	for ( i = n - 1; i >= 0; i-- ) {
    		exp = eList.get(i);
    		if ( exp instanceof ConstOp )
    			((ConstOp)exp).accept(gerador.v);
    		else if ( exp instanceof VarOp )
    			((VarOp)exp).accept(gerador.v);
    		else if ( exp instanceof TupleOp )
    			((TupleOp)exp).accept(gerador.v);
    		else if ( exp instanceof StringOp )
    			((StringOp)exp).accept(gerador.v);
    	}
    		
    }

    public void visit(VarOp node) {
    	System.out.println("visit(VarOp " + node + " )");
    	
    	if ( SymbolTable.search(node.getName()) instanceof ConstSymb )
    		if ( SymbolTable.search(node.getName()).getLevel() == 0 )
        		gerador.pWriter.println("LOAD 0 " + ((ConstSymb)SymbolTable.search(node.getName())).getAddress());
        	else			
        		gerador.pWriter.println("LOAD B " + ((ConstSymb)SymbolTable.search(node.getName())).getAddress());
    	else
    		if ( SymbolTable.search(node.getName()).getLevel() == 0 )
    			gerador.pWriter.println("LOAD 0 " + ((VarSymb)SymbolTable.search(node.getName())).getAddress());
    		else			
    			gerador.pWriter.println("LOAD B " + ((VarSymb)SymbolTable.search(node.getName())).getAddress());
    }
    
    public void visitExp(Expression exp) {
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

    public void visit(DiadOp node) {
    	int OP;
    	Expression expr;    	
    	
    	System.out.println("visit(DiadOp " + node + " )");
    	    	
    	OP = node.getKind();
    	
    	switch (OP) {
    	
    	case PL747Consts.INDEX_OP:    		
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("CTE " + ((VarSymb)SymbolTable.search(((VarOp)node.getFirstOperand()).getName())).getAddress());
    		gerador.pWriter.println("LOADR B");
    		gerador.pWriter.println("ADD");
    		gerador.pWriter.println("ADD");    		
    		gerador.pWriter.println("LDX");
    		break;
    		
    	case PL747Consts.SEL_OP:
    		break;
    		
    	case PL747Consts.ADD_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("ADD");
    		break;
    		
    	case PL747Consts.SUB_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("SUB");
    		break;
    		    		
    	case PL747Consts.MULT_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("MUL");
    		break;
    		
    	case PL747Consts.DIV_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("DIV");
    		break;
    		
    	case PL747Consts.MOD_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("MOD");
    		break;
    		
    	case PL747Consts.EQ_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("EQL");
    		break;
    		
    	case PL747Consts.NE_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("NEQ");
    		break;
    		
    	case PL747Consts.GT_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("GT");
    		break;
    		
    	case PL747Consts.LT_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("LT");
    		break;
    		
    	case PL747Consts.GE_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("GTE");
    		break;
    		
    	case PL747Consts.LE_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("LTE");
    		break;
    		
    	case PL747Consts.AND_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());
    		gerador.pWriter.println("AND");
    		break;
    		
    	case PL747Consts.OR_OP:
    		visitExp(node.getFirstOperand());
    		visitExp(node.getSecondOperand());    		
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
    				visitExp(((DiadOp)expr).getSecondOperand());
    				gerador.pWriter.println("CTE " + ((VarSymb)SymbolTable.search(((VarOp)((DiadOp)expr).getFirstOperand()).getName())).getAddress());
    	    		gerador.pWriter.println("LOADR B");
    	    		gerador.pWriter.println("ADD");
    	    		gerador.pWriter.println("ADD");
    	    		
    	    		gerador.pWriter.println("STX");
    				
    				
    			}
    			
    		}
    		/*!!!!!POG!!!!!!  PASSIVEL DE INSUCESSO!!!!!!*/
    		gerador.pWriter.println("LOAD B " + ((VarSymb)SymbolTable.search(((VarOp)expr).getName())).getAddress());
    		break;
    		
    	}
    	
    }

    public void visit(UnaryOp node) {
    	int OP = node.getKind();
    	
    	System.out.println("visit(UnaryOp " + node + " )");
    	
    	switch (OP) {
    	case PL747Consts.MINUS_OP:
    		visitExp(node.getOperand()); 
    		gerador.pWriter.println("NEG");
    		break;
    		
    	case PL747Consts.NOT_OP:
    		visitExp(node.getOperand());
    		gerador.pWriter.println("NOT");
    		break;
    	}
    }

    public void visit(CompoundStat node) {
    	int i, n;
    	List<TreeNode> tnList;
    	TreeNode tn;
    	
    	System.out.println("visit(CompoundStat " + node + " )");
    	
    	tnList = node.getStatList();
    	
    	n = tnList.size();
    	
    	for ( i = 0; i < n; i++ )
    	{
    		tn = tnList.get(i);
    		
        	if ( tn instanceof DiadOp )
    			((DiadOp)tn).accept(gerador.v);
    		else if ( tn instanceof VarOp )
    			((VarOp)tn).accept(gerador.v);
    		else if ( tn instanceof FunctionCallOp )
    			((FunctionCallOp)tn).accept(gerador.v);
    		else if ( tn instanceof ConstOp )
    			((ConstOp)tn).accept(gerador.v);
    		else if ( tn instanceof TupleOp )
    			((TupleOp)tn).accept(gerador.v);
    		else if ( tn instanceof UnaryOp )
    			((UnaryOp)tn).accept(gerador.v);
    		else if ( tn instanceof StringOp )
    			((StringOp)tn).accept(gerador.v);
    		else if ( tn instanceof IfStat )
    			((IfStat)tn).accept(gerador.v);
    		else if ( tn instanceof WhileStat )
    			((WhileStat)tn).accept(gerador.v);
    		else if ( tn instanceof DoStat )
    			((DoStat)tn).accept(gerador.v);
    		else if ( tn instanceof ForStat )
    			((ForStat)tn).accept(gerador.v);
    		else if ( tn instanceof ReturnStat )
    			((ReturnStat)tn).accept(gerador.v);
    		else if ( tn instanceof CompoundStat )
    			((CompoundStat)tn).accept(gerador.v);
    	}
    	
    }

    public void visit(IfStat node) {
    	Expression exp;
    	int localL = Label;
    	Label++;
    	System.out.println("visit(IfStat " + node + " )");
    	// accept(node.getCondition());
    	exp = node.getCondition();
    	if ( exp instanceof DiadOp )
    		((DiadOp)exp).accept(gerador.v);
    	else if ( exp instanceof UnaryOp )
    		((UnaryOp)exp).accept(gerador.v);
    	else if ( exp instanceof VarOp )
    		((VarOp)exp).accept(gerador.v);
    	else if ( exp instanceof ConstOp )
    		((ConstOp)exp).accept(gerador.v);
    	else if ( exp instanceof FunctionCallOp )
    		((FunctionCallOp)exp).accept(gerador.v);
    	
    	gerador.pWriter.println("JMPF :cond_" + localL);
    	// accept(node.getThenPart());
    	// Mudar isso ! ... tem que coloca um IF gigante
    	exp = node.getThenPart();    	
    	((CompoundStat)exp).accept(gerador.v);
    	
    	gerador.pWriter.println("JMP :end_" + localL);
    	gerador.pWriter.println(":cond_" + localL);
    	// accept(node.getElsePart());
    	exp = node.getElsePart();
    	((CompoundStat)exp).accept(gerador.v);
    	
    	gerador.pWriter.println(":end_" + localL);
    	
    }

    public void visit(WhileStat node) {
    	Expression exp;
    	int localL = Label;
    	Label++;
    	System.out.println("visit(WhileStat " + node + " )");
    	gerador.pWriter.println(":cond_" + localL);
    	// accept(node.getCondition());
    	exp = node.getCondition();
    	visitExp(exp);    	
    	
    	gerador.pWriter.println("JMPF :end_" + localL);
    	// accept(node.getStat());
    	exp = node.getStat();
    	
    	((CompoundStat)exp).accept(gerador.v);
    	
    	gerador.pWriter.println("JMP :cond_" + localL);
    	gerador.pWriter.println(":end_" + localL);
    	
    }

    public void visit(DoStat node) {
    	Expression exp;
    	int localL = Label;
    	Label++;
    	System.out.println("visit(DoStat " + node + " )");
    	gerador.pWriter.println(":cond_" + localL); 
    	exp = node.getStat();
    	((CompoundStat)exp).accept(gerador.v);    	
 
    	exp = node.getCondition();
    	visitExp(exp);    	
    	
    	gerador.pWriter.println("JMPT :cond_" + localL);    	    	
    }

    public void visit(ForStat node) {
    	Expression exp;
    	String var = node.getVariable();
    	int localL = Label;
    	Label++;
    	System.out.println("visit(ForStat " + node + " )");
    	
    	exp = node.getStartValue();
    	visitExp(exp);
    	gerador.pWriter.println("STORE B " + ((VarSymb)SymbolTable.search(var)).getAddress());
    	
    	gerador.pWriter.println(":cond_" + localL);
    	// accept(node.getFinalValue());
    	gerador.pWriter.println("LOAD B " + ((VarSymb)SymbolTable.search(var)).getAddress());
    	exp = node.getFinalValue();
    	visitExp(exp);
    	gerador.pWriter.println("EQL");
    	
    	gerador.pWriter.println("JMPT :end_" + localL);    	
    	// accept(node.getStat());
    	exp = node.getStat();
    	((CompoundStat)exp).accept(gerador.v);
    	
    	if ( node.getDirection() == true )
    		gerador.pWriter.println("INC B " + ((VarSymb)SymbolTable.search(var)).getAddress());
    	else
    		gerador.pWriter.println("DEC B " + ((VarSymb)SymbolTable.search(var)).getAddress());
    	gerador.pWriter.println("JMP :cond_" + localL);
    	gerador.pWriter.println(":end_" + localL);
    	
    }

    public void visit(ReturnStat node) {
    	System.out.println("visit(ReturnStat " + node + " )");
    	// accept(node.getValue());
    	visitExp(node.getValue());
    	gerador.pWriter.println("JMP " + gerador.rLabel);
    }
}

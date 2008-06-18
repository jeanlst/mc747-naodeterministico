package pl747;

import java.util.List;

import com.sun.corba.se.impl.orbutil.graph.Node;

import pl747.codigo.gerador;
import pl747.semantico.*;
import pl747.tabelaSimbolos.*;

public class Visitor {
	
	private int Label = 0;
	
    public void visit(VarDeclaration node) {
    	int size;
    	Type type;
    	List varList;    	    
    	
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
			}
    		    	
    		// atualiza a tabela de simbolos 
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
    	TreeNode body;
    	List param;
    	boolean isPrototype;
    	Scope scope;
    	int i, n;
    	
    	body = node.getBody();
    	param = node.getParmList();
    	isPrototype = node.isPrototype();
    	scope = node.getScope();
    	
    	Symbol s;
    	s = scope.getSymbol(node.getName());    	
    	
    	if ( !isPrototype ) {    		
    		try {
    			gerador.pWriter.println(":" + node.getName());
    			gerador.pWriter.println("ENTER");
    			
    			// accept(body);
    			
    			n = GetTotalSize(param);
    			
    			gerador.pWriter.println("" /* Label de return */);
    			gerador.pWriter.println("STORE B -" + (n+1)); // tomara que isso seja valido
    			gerador.pWriter.println("LOADR B");
    			gerador.pWriter.println("CTE -3");
    			gerador.pWriter.println("ADD");
    			gerador.pWriter.println("RET");

    		}
    		catch (Exception ex) {
    			ex.printStackTrace();
    			System.err.print("Erro ao escrever no arquivo.\n");
    			System.exit(1);
    		}    		
    	}    	
    }

    public void visit(ConstDeclaration node) {
    	
    }

    public void visit(FunctionCallOp node) {
    	int i, n;
    	List param = node.getParmList();
    	
    	n = param.size();
    	for ( i = 0; i < n; i++ ) // accept(param.get(i));
    		;
    	
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
    	
    }

    public void visit(StringOp node) {
    	int i;
    	int n;
    	String s;
    	
    	s = node.getValue();
    	n = s.length();
    	
    	for ( i = 0; i < n; i++ )
    		gerador.pWriter.println("CTE " + s.charAt(i));    		
    }    

    public void visit(TupleOp node) {
    	
    }

    public void visit(VarOp node) {
    	gerador.pWriter.println("LOAD B " + 0 /* Get address */ );
    }

    public void visit(DiadOp node) {
    	int OP;
    	Expression expr;
    	    	
    	OP = node.getKind();
    	
    	switch (OP) {
    	
    	case PL747Consts.INDEX_OP:
    		break;
    		
    	case PL747Consts.SEL_OP:
    		break;
    		
    	case PL747Consts.ADD_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("ADD");
    		break;
    		
    	case PL747Consts.SUB_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("SUB");
    		break;
    		    		
    	case PL747Consts.MULT_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("MUL");
    		break;
    		
    	case PL747Consts.DIV_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("DIV");
    		break;
    		
    	case PL747Consts.MOD_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("MOD");
    		break;
    		
    	case PL747Consts.EQ_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("EQL");
    		break;
    		
    	case PL747Consts.NE_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("NEQ");
    		break;
    		
    	case PL747Consts.GT_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("GT");
    		break;
    		
    	case PL747Consts.LT_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("LT");
    		break;
    		
    	case PL747Consts.GE_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("GTE");
    		break;
    		
    	case PL747Consts.LE_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("LTE");
    		break;
    		
    	case PL747Consts.AND_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("AND");
    		break;
    		
    	case PL747Consts.OR_OP:
    		// accept(node.getFirstOperand());
    		// accept(node.getSecondOperand());
    		gerador.pWriter.println("OR");
    		break;
    		    		
    	case PL747Consts.ASSIGN_OP:
    		// accept(node.getSecondOperand());
    		
    		expr = node.getFirstOperand();
    		
    		if ( expr instanceof VarOp )
    		{
    			/* ((VarOp)expr).getName();
    			 * pegar na tebela de simbolos e dar
    			 * um store
    			 */
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

    }

    public void visit(IfStat node) {
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
    	gerador.pWriter.println(":cond_" + Label);
    	// accept(node.getCondition());
    	gerador.pWriter.println("JMPF :end_" + Label);
    	// accept(node.getStat());
    	gerador.pWriter.println("JMP :cond_" + Label);
    	gerador.pWriter.println(":end_" + Label);
    	Label++;
    }

    public void visit(DoStat node) {
    	gerador.pWriter.println(":cond_" + Label);
    	// accept(node.getStat());
    	// accept(node.getCondition());
    	gerador.pWriter.println("JMPT :cond_" + Label);    	    	
    }

    public void visit(ForStat node) {
    	String var = node.getVariable();
    	
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
    	// accept(node.getValue());
    	gerador.pWriter.println("JMP :" + "" /* Gerar Label */);
    }
}

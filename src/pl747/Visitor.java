package pl747;

import java.io.IOException;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Variable;

import pl747.codigo.gerador;
import pl747.semantico.*;
import pl747.tabelaSimbolos.*;

public class Visitor {
	
    public void visit(VarDeclaration node) {
    	int i, j, size;
    	Type type;
    	List varList;
    	
    	try {
    		type = node.getType();
    		    		
    		if ( type instanceof VectorType )
    		{    			
    			size = Integer.parseInt(((VectorType)type).getSize());
    			gerador.oFile.writeChars("ALLOC " + size + "\n");
    		}
    		else if ( type instanceof StructType )
    		{
    			varList = ((StructType)type).getElementList();    			
    			size = GetTotalSize(varList);    			
    			gerador.oFile.writeChars("ALLOC " + size + "\n");
    		}
    		else
    		{
    			gerador.oFile.writeChars("ALLOC 1\n");
			}
    		
    		
    		gerador.oFile.writeChars("");
    	}
    	catch (IOException ex) {
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
    	
    	body = node.getBody();
    	param = node.getParmList();
    	isPrototype = node.isPrototype();
    	scope = node.getScope();
    	
    	Symbol s;
    	s = scope.getSymbol(node.getName());    	
    	
    	if ( !isPrototype ) {    		
    		try {
    			gerador.oFile.writeChars(":" + node.getName() + "\n");
    			gerador.oFile.writeChars("ENTER\n");
    			
    			/*if ( body instanceof CompoundStat )
    				((CompoundStat)body).getStatList();
    			else*/
    				
    		}
    		catch (IOException ex) {
    			ex.printStackTrace();
    			System.err.print("Erro ao escrever no arquivo\n");
    			System.exit(1);
    		}    		
    	}    	
    }

    public void visit(ConstDeclaration node) {

    }

    public void visit(FunctionCallOp node) {
	
    }

    public void visit(ConstOp node) {

    }

    public void visit(StringOp node) {

    }

    public void visit(TupleOp node) {

    }

    public void visit(VarOp node) {

    }

    public void visit(DiadOp node) {

    }

    public void visit(UnaryOp node) {

    }

    public void visit(CompoundStat node) {

    }

    public void visit(IfStat node) {

    }

    public void visit(WhileStat node) {

    }

    public void visit(DoStat node) {

    }

    public void visit(ForStat node) {

    }

    public void visit(ReturnStat node) {

    }
}

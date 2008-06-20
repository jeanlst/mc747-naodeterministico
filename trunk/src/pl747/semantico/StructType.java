package pl747.semantico;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;
import java.util.*;

/**
 * Representa um tipo estrutura.
 */
public class StructType extends Type {

	private List<VarDeclaration> elementList;
	private Object symbType;	
	
	public StructType(){
		super(null);
		elementList = new ArrayList<VarDeclaration>();		
	}
	
	
	public List<VarDeclaration> getElementList() {
		return elementList;
	}
	
	
	@Override
	public void addChild(TreeNode child) throws Exception {
		
		
		List<Declaration> list = null;
		if (child instanceof VarDeclaration) {
			list = new ArrayList<Declaration>();
			list.add((VarDeclaration)child);
		}
		else {
			list = ((VarDeclList) child).getDeclarations();
		}		
		
		//inclui campos da estrutura
		for (Declaration dec : list) {
			this.elementList.add((VarDeclaration)dec);
		}		
	}


	@Override
	public String getName() {
		return null; //"struct";
	}
	
	@Override
	  public PrimTypeSymb getSymbType() {
	      if(symbType != null){
	         StructTypeSymb sts = new StructTypeSymb();
	         for(VarDeclaration vd: elementList){
	             PrimTypeSymb ts = vd.getSymbType();
	             int level = SymbolTable.getCurLevel();
	             FieldSymb fs = new FieldSymb(vd.name,level,ts);
	             sts.addField(fs);
	         }
	         symbType = sts;
	      }
	      return (PrimTypeSymb)symbType;
	  }
	

}

package pl747.codigo;

import pl747.TreeNode;
import pl747.Visitor;
import pl747.tabelaSimbolos.*;
import pl747.semantico.AbsNode;

import java.io.FileWriter;
import java.io.PrintWriter;

public class gerador {
	
	public static FileWriter oFile;
	public static PrintWriter pWriter;
	public static int addr;
	public static String rLabel;
	public static Visitor v = new Visitor();
	
	public boolean GenerateCode(TreeNode tree, String filename) {
		
		int i;		
		
		this.addr = 0;
		
		try {
			oFile = new FileWriter(filename);
			pWriter = new PrintWriter(oFile);
			
			i = 0;
			
			pWriter.println("INIT");
			pWriter.println("LOADR T");
			pWriter.println("STORER B");
			pWriter.println("LOADR B");
			pWriter.println("STORER BM");
			pWriter.println("CTE 0");
			pWriter.println("LOADR PC");
			pWriter.println("CTE 3");
			pWriter.println("ADD");
			pWriter.println("CALL :MAIN");
			pWriter.println("STOP");
			
			if ( tree != null )
			 ((AbsNode)tree).accept(v);
			
			pWriter.close();
			oFile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.print("Could not open file " + filename + "\n");
			return false;
		}
		
		return true;
	}

}
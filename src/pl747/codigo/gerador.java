package pl747.codigo;

import pl747.TreeNode;
import pl747.Visitor;
import pl747.tabelaSimbolos.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class gerador {
	
	public static FileWriter oFile;
	public static PrintWriter pWriter;
	public static int addr;
	
	public boolean GenerateCode(TreeNode tree, String filename) {
		
		int i;
		Visitor v = new Visitor();
		
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
			
			// tree.accept(v);	
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.print("Could not open file " + filename + "\n");
			return false;
		}
		
		return true;
	}

}
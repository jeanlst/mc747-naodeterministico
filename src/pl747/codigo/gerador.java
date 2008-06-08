package pl747.codigo;

import pl747.TreeNode;
import pl747.Visitor;
import pl747.tabelaSimbolos.*;
import java.io.RandomAccessFile;

public class gerador {
	
	public static RandomAccessFile oFile;
	
	public boolean GenerateCode(TreeNode tree, String filename) {
		
		int i;
		Visitor v = new Visitor();
				
		try {
			oFile = new RandomAccessFile(filename, "w");
			
			i = 0;
			
			oFile.writeChars("INIT\n");
			oFile.writeChars("LOADR T\n");
			oFile.writeChars("STORER B\n");
			oFile.writeChars("LOADR B\n");
			oFile.writeChars("STORER BM\n");
			oFile.writeChars("CTE 0\n");
			oFile.writeChars("LOADR PC\n");
			oFile.writeChars("CTE 3\n");
			oFile.writeChars("ADD");
			oFile.writeChars("CALL :MAIN\n");
			oFile.writeChars("STOP\n");
			
			// tree.accept(v);	
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.print("Could not open file " + filename);
			return false;
		}
		
		return true;
	}

}
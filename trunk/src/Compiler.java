
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;



public class Compiler {

	/**
	 * @param args
	 */
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		CompilerFacade compiler = new CompilerFacade();
		String fileIn = "";
		String fileOut = "";
		
		try {
			fileIn = args[0];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Arquivo de entrada (codigo fonte): ");
			DataInputStream in = new DataInputStream(new BufferedInputStream(System.in));
			try {
				fileIn = in.readLine();
			}
			catch (Exception er) {
				System.out.println("Erro");				
			}

		}
		
		try {
			fileOut = args[1];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Arquivo de saida sem extensão (executavel a ser gerado): ");
			DataInputStream in = new DataInputStream(new BufferedInputStream(System.in));
			try {
				fileOut = in.readLine();
			}
			catch (Exception er) {
				System.out.println("Erro");				
			}

		}

		
		compiler.compile(fileIn, fileOut+".sith");
	}

}

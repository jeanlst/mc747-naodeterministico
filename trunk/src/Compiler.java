
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class Compiler {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		CompilerFacade compiler = new CompilerFacade();
		String file = "";
		
		try {
			file = args[0];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Digite o endereco do arquivo: ");
			DataInputStream in = new DataInputStream(new BufferedInputStream(System.in));
			try {
				file = in.readLine();
			}
			catch (Exception er) {
				System.out.println("Erro");
			}

		}
		
		compiler.compile(file);
	}

}


import java.io.BufferedInputStream;
import java.io.DataInputStream;



public class Compiler {

	/**
	 * @param args
	 */
	
	@SuppressWarnings("deprecation")
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
				file = "tests/"+file;
			}
			catch (Exception er) {
				System.out.println("Erro");				
			}

		}
		
		compiler.compile(file);
	}

}

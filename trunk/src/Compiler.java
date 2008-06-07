
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
		String file = "";
		
		try {
			file = args[0];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Digite o endereco do arquivo: ");
			DataInputStream in = new DataInputStream(new BufferedInputStream(System.in));
			try {
				file = in.readLine();
				file = "tests/test"+file+".pl747";
			}
			catch (Exception er) {
				System.out.println("Erro");				
			}

		}
		
		try {
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			BufferedReader input = null;
			try{
				input =  new BufferedReader(new FileReader(file));
			}
			catch(Exception e)
			{
				System.out.println("Erro: Arquivo não encontrado");
				return;
			}
			try {
				String line = null; //not declared within while loop
				/*
				 * readLine is a bit quirky :
				 * it returns the content of a line MINUS the newline.
				 * it returns null only for the END of the stream.
				 * it returns an empty String if two newlines appear in a row.
				 */
				System.out.println("======================== Inicio do arquivo =============================");
				while (( line = input.readLine()) != null){
					System.out.println(line.toString());
				}
				System.out.println("========================= Fim do arquivo ===============================");
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		
		compiler.compile(file);
	}

}


import pl747.codigo.gerador;
import pl747.semantico.AbsNode;
import pl747.sintatico.*;
import java.util.ArrayList;

public class CompilerFacade {

	public CompilerFacade() {
		
	}
	
	@SuppressWarnings("hiding")
	public void compile(String fileIn, String fileOut) {
		Scanner scanner = null;
		/* Declaração usada para geração de código */
		/* start */
		gerador G = new gerador();
		/* end */
		
		/* Analise lexica */
		try {			
			scanner = new Scanner( new java.io.FileReader(fileIn) );
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("File not found : \""+fileIn+"\"");
			System.exit(1);
		}
		catch (java.io.IOException e) {
			System.out.println("Error opening file \""+fileIn+"\"");
			System.exit(1);
		}
		
		/* Analise sintatica */
		Object program = null;
		try {
			Parser p = new Parser(scanner);
			program = p.parse().value;
		}
		catch (java.io.IOException e) {
			System.out.println("An I/O error occured while parsing : \n"+e);
			System.exit(1);
		}
		catch (Exception e) {
			System.out.println("Error");
			System.exit(1);
		}
		
		/* Analise semantica */
		ArrayList<String> errorList = new ArrayList<String>();
		boolean check = true;
		try {
			check = ((AbsNode)program).check(errorList);
		}
		catch (Exception e) {
			System.out.println("Error");
			System.exit(1);
		}

		if (!check) {
			System.err.println("Semantic Error");
			for (String string : errorList) {
				System.err.println(string);
			}
		}
		else {
			System.out.println("Nenhum erro encontrado");

			/* Geracao de codigo */
			G.GenerateCode(((AbsNode)program), fileOut);
		}	
		return ;
	}
}

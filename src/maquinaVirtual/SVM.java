package maquinaVirtual;
import java.io.*;

public class SVM 
{
    /* Registradores da maquina virtual */
    private final static int _maxStackSize = 65536;
    private static int B;  /* Registrador base            */
    private static int BM; /* Sempre aponta para o main   */
    private static int PC; /* Aponta para a proxima       */ 
    private static int T;  /* Aponta para o topo da pilha */  

    /* Pilha */
    private static int[] stack = new int[_maxStackSize];
    
    /* Strings para entrada e saida */
    private static String gString = null;
    private static int sPointer = -1;
    
    /* Verifica se o programa já terminou */
    private static boolean RUNNING;
    
    /* Labels */
	private static int nLabel;
	private static String[] sLabel;
	private static int[] iLabel;
	
    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args) {
    	
    	int i, j;    /* Auxiliares                     */
    	int nLines;  /* Número de linhas do programa   */
    	String line; /* Auxiliar para leitura de linha */
    	
    	/* Auxiliar para leitura de arquivo */
    	RandomAccessFile oFile;
    	
    	/* Classe que guarda as instruções */
    	InstructionImage[] iImage;
    	
    	if ( args.length != 1 )
    	{
    		System.err.println("Número de argumentos inválido");
    		System.exit(1);
    	}
    	
    	if ( args[0].substring(args[0].length() - 4, args[0].length()).compareTo("sith") != 0 )
    	{
    		System.err.println("\"Fear is the path to the dark side. Fear leads to anger. Anger leads to hate. Hate leads to suffering.\"");
    		System.err.println("Extensão inválida");
    		System.exit(1);
    	}
    	
    	/* Lendo as instruções */
    	try {
    		oFile = new RandomAccessFile(args[0], "r");    	
    	
    		nLabel = 0;
    	
    		nLines = 0;
    		while ( (line = oFile.readLine()) != null ) {
    			if ( line.charAt(0) == ':' )
    				nLabel++;
    			if ( line.charAt(0) != ';' )
    				nLines++;
    		}
    	
    		oFile.seek(0);
    	
    		iImage = new InstructionImage[nLines];
    		sLabel = new String[nLabel];
    		iLabel = new int[nLabel];
    	
    		i = 0;
    		j = 0;
    		while ( (line = oFile.readLine()) != null ) {
    			if ( line.charAt(0) != ';' )
    				iImage[i] = new InstructionImage(line);    			
    			
    			if ( line.charAt(0) != ';' && iImage[i].OP == InstructionImage._LABEL )
    			{
    				sLabel[j] = iImage[i].param[0];
    				iLabel[j] = i;
    				j++;
    			}
    		
    			if ( line.charAt(0) != ';' )
    				i++;
    		}    		
    		
    		/* Executa o programa */
    		SVM.execute(iImage);    	    	    	
    	
    		oFile.close();
    	
    		}
    		catch (IOException e)
    		{
    			e.printStackTrace();
    			System.exit(1);
    		}
    }
    
    private static void execute(InstructionImage[] iImage) {
    	int cPC = PC;    	
    	RUNNING = true;
    	while ( RUNNING )
    	{
    		cPC = PC;
    		
    		/* Verifica qual instrução será executada */
    		switch (iImage[PC++].OP) {
	    		case(InstructionImage._ALLOC):
	    			ALLOC(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._FREE):
	    			FREE(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._CALL):
	    			CALL(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._RET):
	    			RET();
	    			break;
	    		
	    		case(InstructionImage._ENTER):
	    			ENTER();
	    			break;
	    		
	    		case(InstructionImage._ADD):
	    			ADD();
	    			break;
	    		
	    		case(InstructionImage._SUB):
	    			SUB();
	    			break;
	    		
	    		case(InstructionImage._MUL):
	    			MUL();
	    			break;
	    		
	    		case(InstructionImage._DIV):
	    			DIV();
	    			break;
	    		
	    		case(InstructionImage._MOD):
	    			MOD();
	    			break;
	    		
	    		case(InstructionImage._JMP):
	    			JMP(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._JMPF):
	    			JMPF(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._JMPT):
	    			JMPT(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._INIT):
	    			INIT();
	    			break;
	    		
	    		case(InstructionImage._STOP):
	    			STOP();
	    			break;
	    		
	    		case(InstructionImage._CTE):
	    			CTE(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._LT):
	    			LT();
	    			break;
	    		
	    		case(InstructionImage._GT):
	    			GT();
	    			break;
	    		
	    		case(InstructionImage._LTE):
	    			LTE();
	    			break;
	    		
	    		case(InstructionImage._GTE):
	    			GTE();
	    			break;
	    		
	    		case(InstructionImage._EQL):
	    			EQL();
	    			break;
	    				
	    		case(InstructionImage._NEQ):
	    			NEQ();
	    			break;
	    		
	    		case(InstructionImage._LOAD):
	    			LOAD(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._STORE):
	    			STORE(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._LDADDR):
	    			LDADDR(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._LDI):
	    			LDI(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._STI):
	    			STI(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._LDX):
	    			 LDX();
	    			 break;
	    		
	    		case(InstructionImage._STX):
	    			STX();
	    			break;
	    		
	    		case(InstructionImage._AND):
	    			AND();
	    			break;
	    		
	    		case(InstructionImage._OR):
	    			OR();
	    			break;
	    		
	    		case(InstructionImage._XOR):
	    			XOR();
	    			break;
	    				
	    		case(InstructionImage._NOT):
	    			NOT();
	    			break;
	    		
	    		case(InstructionImage._NEG):
	    			NEG();
	    			break;
	    		
	    		case(InstructionImage._INC):
	    			INC(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._DEC):
	    			DEC(iImage[cPC].param[0], iImage[cPC].param[1]);
	    			break;
	    		
	    		case(InstructionImage._LOADR):
	    			LOADR(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._STORER):
	    			STORER(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._READ):
	    			READ(iImage[cPC].param[0]);
	    			break;
	    		
	    		case(InstructionImage._PRINT):
	    			PRINT(iImage[cPC].param[0]);
	    			break;
	    			
	    		case(InstructionImage._LABEL):
	    			break;
    		}    		
    	}
    }
    
    /* A descrição das instruções podem ser encontradas na documentação */
    
    private static void ALLOC(String p1) {
    	int t, i;
    	
    	try {
    		t = T + 1;
    		T = T + Integer.parseInt(p1);
    		for ( i = t; i <= T; i++ )
    			stack[t] = -1;
    	}
    	catch (NumberFormatException e) {
    		e.printStackTrace();
    		System.exit(1);
    	}
    }
    
	private static void FREE(String p1) { 
		try {
			T = T - Integer.parseInt(p1);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void CALL(String p1) { 
		JMP(p1);
	}
	
	private static void RET() { 
		PC = stack[B+1];
		T = stack[T];
		B = stack[B];
	}
	
	private static void ENTER() { 
		
	}
	
	private static void ADD() {
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		stack[T] = a + b;		
	}
	
	private static void SUB() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		stack[T] = b - a;
	}
	
	private static void MUL() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		stack[T] = a * b;
	}
	
	private static void DIV() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		stack[T] = b / a;
	}
	
	private static void MOD() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		stack[T] = b%a;
	}
	
	private static void JMP(String p1) {
		int i;
		int jump_to = -1;
		
		for ( i = 0; i < nLabel && jump_to == -1; i++ )
			if ( sLabel[i].compareTo(p1) == 0 )
				jump_to = iLabel[i];
		
		PC = jump_to;
	}
	
	private static void JMPF(String p1) { 
		int a;
		int i;
		int jump_to = -1;
		
		for ( i = 0; i < nLabel && jump_to == -1; i++ )
			if ( sLabel[i].compareTo(p1) == 0 )
				jump_to = iLabel[i];
		
		a = stack[T];
		if ( a == 0 )
			PC = jump_to;
		T = T - 1;
	}
	
	private static void JMPT(String p1) { 
		int a;
		int i;
		int jump_to = -1;
		
		for ( i = 0; i < nLabel && jump_to == -1; i++ )
			if ( sLabel[i].compareTo(p1) == 0 )
				jump_to = iLabel[i];
		
		a = stack[T];
		if ( a != 0 )
			PC = jump_to;
		T = T - 1;
	}
	
	private static void INIT() { 
		T = -1;
		B = 0;
		BM = 0;
	}
	
	private static void STOP() { 
		RUNNING = false;
	}
	
	private static void CTE(String p1) { 
		int a;
		try { 
			a = Integer.parseInt(p1);
		 
			T = T + 1;
			stack[T] = a;
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);			
		}
	}
	
	private static void LT() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		if ( b < a )
			stack[T] = 1;
		else
			stack[T] = 0;
	} 
	
	private static void GT() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		if ( b > a )
			stack[T] = 1;
		else
			stack[T] = 0;
	} 
	
	private static void LTE() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		if ( b <= a )
			stack[T] = 1;
		else
			stack[T] = 0;
	} 
	
	private static void GTE() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		if ( b >= a )
			stack[T] = 1;
		else
			stack[T] = 0;	
	} 
	
	private static void EQL() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		
		if ( b == a )
			stack[T] = 1;
		else
			stack[T] = 0;
	} 
	
	private static void NEQ() { 
		int a, b;
		a = stack[T];
		b = stack[T-1];
		T = T - 1;
		if ( b != a )
			stack[T] = 1;
		else
			stack[T] = 0;	
	} 
	
	private static void LOAD(String p1, String p2) {
		int b;
		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
			
			T = T + 1;
			stack[T] = stack[b + Integer.parseInt(p2)];
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void STORE(String p1, String p2) {
		int b;
		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
		
			stack[b + Integer.parseInt(p2)] = stack[T];
			T = T - 1;
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void LDADDR(String p1, String p2) {
		int b;
		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
		
			T = T + 1;
			stack[T] = b + Integer.parseInt(p2);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void LDI(String p1, String p2) {
		int b;		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
		
			T = T + 1;
			stack[T] = stack[stack[b + Integer.parseInt(p2)]];
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}  
	
	private static void STI(String p1, String p2) {
		int b;
		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
		
			stack[stack[b + Integer.parseInt(p2)]] = stack[T];
			T = T - 1;
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}  
	
	private static void LDX() { 
		stack[T] = stack[stack[T]];	
	}  
	
	private static void STX() { 
		stack[stack[T - 1]] = stack[T];
		T = T - 2;
	}  
	
	private static void AND() { 
		int a, b;
		a = stack[T];
		b = stack[T - 1];
		T = T - 1;
		stack[T] = a & b;
	}  
	
	private static void OR() { 
		int a, b;
		a = stack[T];
		b = stack[T - 1];
		T = T - 1;
		stack[T] = a | b;
	}   
	
	private static void XOR() { 
		int a, b;
		a = stack[T];
		b = stack[T - 1];
		T = T - 1;
		stack[T] = a ^ b;
	}  
	
	private static void NOT() { 
		int a;
		a = stack[T];
		if ( a != 0 )
			stack[T] = 0;
		else
			stack[T] = 1;
	}  
	
	private static void NEG() { 
		stack[T] = -stack[T];
	}  
	
	private static void INC(String p1, String p2) { 
		int a;
		int b;
		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
		
			a = stack[b + Integer.parseInt(p2)];
			a++;
			stack[b + Integer.parseInt(p2)] = a;	
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}  
	
	private static void DEC(String p1, String p2) { 
		int a;
		int b;
		
		try {
			if ( p1.compareTo("B") == 0 ) b = B;
			else b = Integer.parseInt(p1);
		
			a = stack[b + Integer.parseInt(p2)];
			a--;
			stack[b + Integer.parseInt(p2)] = a;
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void LOADR(String p1) {
		T = T + 1;
		if ( p1.compareToIgnoreCase("B") == 0 )
		{
			stack[T] = B;
		}
		else if ( p1.compareToIgnoreCase("BM") == 0 )
		{
			stack[T] = BM;
		}
		else if ( p1.compareToIgnoreCase("PC") == 0 )
		{
			stack[T] = PC;
		}
		else if ( p1.compareToIgnoreCase("T") == 0 )
		{
			stack[T] = T;
		}
		else
			T = T - 1;		
	}	
	
	private static void STORER(String p1) { 		
		if ( p1.compareToIgnoreCase("B") == 0 )
		{			
			B = stack[T];
		}
		else if ( p1.compareToIgnoreCase("BM") == 0 )
		{
			BM = stack[T];
		}
		else if ( p1.compareToIgnoreCase("PC") == 0 )
		{
			PC = stack[T];
		}
		else if ( p1.compareToIgnoreCase("T") == 0 )
		{
			T = stack[T];
		}
		else
			T = T + 1;
		
		T = T - 1;		
	}
	
	/* Mudança no arquivo da primeira entrega */
	private static void READ(String p1) {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int i;
		int r = 0;
		String rString;
		char c;
		
		if ( sPointer == -1 )
		{
			try {
				gString = br.readLine();
			}
			catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			sPointer = 0;
		}
		
		if ( p1.compareTo("INT") == 0 )
		{
			i = gString.indexOf(" ", sPointer);
			
			if ( i == -1 )
			{
				i = gString.indexOf("\n", sPointer);
				if ( i == -1 )
				{
					System.err.println("Boo-Yaa !");
					System.exit(1);
				}
			}
						
			rString = gString.substring(sPointer, i);
			try { 
				r = Integer.parseInt(rString);
			}
			catch (NumberFormatException e) {				
				e.printStackTrace();
				System.exit(1);
			}
			sPointer = i;
		}
		else if ( p1.compareTo("CHAR") == 0 )
		{
			c = gString.charAt(sPointer++);
			r = (int)c;
		}
		else if ( p1.compareTo("BOOLEAN") == 0 )
		{
			r = 1;
			i = gString.indexOf("true", sPointer);
			
			if ( i != sPointer )
			{
				i = gString.indexOf("false", sPointer);
				r = 0;
			}

			if ( i != sPointer )
			{
				System.err.println("Couldn't read boolean from user.");
				System.exit(1);
			}
		}
			
		if ( sPointer >= gString.length() )
		{
			gString = null;
			sPointer = -1;
		}
		
		T = T + 1;
		stack[T] = r;		
	}  
	
	private static void PRINT(String p1) {
		char c;
		
		if ( p1.compareTo("INT") == 0 )
		{
			System.out.print(stack[T]);
		}
		else if ( p1.compareTo("CHAR") == 0 )
		{
			c = (char)stack[T];
			System.out.print(c);
		}
		else if ( p1.compareTo("BOOLEAN") == 0 )
		{
			if (stack[T] == 1)
				System.out.print("true");
			else
				System.out.print("false");
		}
		
		T--;
	} 
	
}	

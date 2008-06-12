package maquinaVirtual;

public class InstructionImage {
	
	/* "nomes" das instruções */
	public final static int _ALLOC  = 0;
	public final static int _FREE   = 1;
	public final static int _CALL   = 2;
	public final static int _RET    = 3;
	public final static int _ENTER  = 4;
	public final static int _ADD    = 5;
	public final static int _SUB    = 6;
	public final static int _MUL    = 7;
	public final static int _DIV    = 8;
	public final static int _MOD    = 9;
	public final static int _JMP    = 10;
	public final static int _JMPF   = 11;
	public final static int _JMPT   = 12;
	public final static int _INIT   = 13;
	public final static int _STOP   = 14;
	public final static int _CTE    = 15;
	public final static int _LT     = 16;
	public final static int _GT     = 17;
	public final static int _LTE    = 18;
	public final static int _GTE    = 19;
	public final static int _EQL    = 20;
	public final static int _NEQ    = 21;
	public final static int _LOAD   = 22;
	public final static int _STORE  = 23;
	public final static int _LDADDR = 24;
	public final static int _LDI    = 25;
	public final static int _STI    = 26;
	public final static int _LDX    = 27;
	public final static int _STX    = 28;
	public final static int _AND    = 29;
	public final static int _OR     = 30;
	public final static int _XOR    = 31;
	public final static int _NOT    = 32;
	public final static int _NEG    = 33;
	public final static int _INC    = 34;
	public final static int _DEC    = 35;
	public final static int _LOADR  = 36;
	public final static int _STORER = 37;
	public final static int _READ   = 38;
	public final static int _PRINT  = 39;
	public final static int _LABEL  = 40;
	
	public final static String[] _sInst = { "ALLOC", 
											"FREE",
											"CALL",
											"RET",
											"ENTER",
											"ADD",
											"SUB",
											"MUL",
											"DIV",
											"MOD",
											"JMP",
											"JMPF",
											"JMPT",
											"INIT",
											"STOP",
											"CTE",
											"LT",
											"GT",
											"LTE",
											"GTE",
											"EQL",
											"NEQ",
											"LOAD",
											"STORE",
											"LDADDR",
											"LDI",
											"STI",
											"LDX",
											"STX",
											"AND",
											"OR",
											"XOR",
											"NOT",
											"NEG",
											"INC",
											"DEC",
											"LOADR",
											"STORER",
											"READ",
											"PRINT"
	};
	
	public int OP;
	public String[] param;	

	public InstructionImage(String s) {
		int i;
		String OPs;
		
		if ( s.charAt(0) == ':' )
		{
			this.OP = _LABEL;
			this.param = new String[2];
			this.param[0] = s;
			this.param[1] = null;
		}
		
		if ( this.OP != _LABEL )
			if ( s.indexOf(" ") == -1 )
			{
				OPs = s;
				param = null;		
				
				i = 0;
				this.OP = -1;
				while ( this.OP == -1 )
				{
					if ( OPs.compareToIgnoreCase(_sInst[i]) == 0 )
						this.OP = i;
					i++;
				}
			}
			else
			{
				OPs = s.substring(0, s.indexOf(" "));
				
				i = 0;
				this.OP = -1;
				while ( this.OP == -1 )
				{
					if ( OPs.compareToIgnoreCase(_sInst[i]) == 0 )
						this.OP = i;
					i++;
				}
			
				param = new String[2];
									
				i = s.indexOf(" ");
				
				if ( s.indexOf(" ", i+1) == -1 )
				{
					param[0] = s.substring(i + 1);
					param[1] = null;
				}
				else
				{
					param[0] = s.substring(i+1, s.indexOf(" ", i+1));
					param[1] = s.substring(s.indexOf(" ", s.indexOf(" ", i+1)) + 1, s.length());
				}
			}
	}
	
}



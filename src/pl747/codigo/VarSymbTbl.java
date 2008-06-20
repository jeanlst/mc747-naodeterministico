package pl747.codigo;

import pl747.tabelaSimbolos.PrimTypeSymb;
import pl747.tabelaSimbolos.VarSymb;

public class VarSymbTbl extends VarSymb {
	private int addr;
	
	public VarSymbTbl(String name, int level, PrimTypeSymb type) {
		super(name, level, type);
		// TODO Auto-generated constructor stub
	}
	
	public void setAddress(int address) {
		this.addr = address;
	}
	
	public int getAddress() {
		return this.addr;
	}
}

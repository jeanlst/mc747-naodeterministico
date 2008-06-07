package pl747.tabelaSimbolos;
/**
 * Descreve um tipo vetor (an�nimo).
 * @author Fernando
 *
 */

public class VectorTypeSymb extends PrimTypeSymb{
	
	private int size;            // tamanho do vetor
	private Symbol elementType;  // tipo de cada elemento do vetor
	
	/**
	 * Construtor
	 * @param size - tamanho do vetor
	 * @param elementType - tipo dos elementos do vetor
	 */
	public VectorTypeSymb(int size, Symbol elementType){
		super(null);
		this.size = size;
		this.elementType = elementType;
	}
	
	/** getters & setters **/
	public int getSize() {
		//System.out.println("VectorTypeSymb.getSize():"+this.toString());
		return size; 
	}
	public Symbol getElementType() { return elementType; }
	public void setSize(int siz){ size = siz; }
	public void setElementType(PrimTypeSymb type) { elementType = type; }

    @Override
    public String toString(){
    	return "[vector name:"+name+" level:"+level+" type:"+elementType.toString()+" size:"+size+"]";
    }
    
	
}

package pl747.semantico;

import java.util.*;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;


/**
 * Representa uma declaracao de variavel da linguagem.
 */
public class VarDeclaration extends Declaration {
	
	private boolean isRef;
	private Expression value;
	private boolean add; 
	
	public VarDeclaration(boolean isRef, Type type, String nome) {
		super(nome);
		this.isRef = isRef;
		this.type = type;
		this.value = null;
		this.add = false;
	}

	public VarDeclaration(boolean isRef, ConstOp value, Type type, String nome) {
		super(nome);
		this.isRef = isRef;
		this.value = value;
		this.type = type;
		this.add = false;
	}		
		
	/**
	 * Devolve o tipo da variavel representada por este objeto.
	 * @return tipo da variavel
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Devolve o objeto Expression que define o value inicial da variavel (pode ser null).
	 * @return expression que define o valor inicial da variavel
	 */
	public Expression getInitialValue() {
		return value;
	}

	/**
	 * Devolve true se a variavel representada por este objeto eh um parametro passado por referencia.
	 */
	public boolean isRef() {		
		return this.isRef;		
	}

	public void addChild(TreeNode child){
		try {
			value = (Expression) child;
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check( List<String> errorList ) {
	
		boolean result = true;
				
		/* Verificando se a variavel ja foi declarada */
		if ((SymbolTable.localSearch(this.name) != null)||(this.add == true)) {
			errorList.add("Redeclaracao da variavel " + this.name +"");
			result = false;
		}
		
		/* Verificando se a variavel foi inicializada */
		if(this.value != null){	
			//TODO verificar se a inicialização de um struct está correta!
			
			Type tipoValue = null;
			if (this.value.getType() instanceof Type) {
				tipoValue = (Type) this.value.getType();
			}
			
			String esteTipo = tipoValue.getName();
			Type tipoVariavel = null;
			if (this.type instanceof Type) {
				tipoVariavel = (Type) this.type;
			}
			
			String tipoCerto = tipoVariavel.getName();
			
			/* Verificando se a inicializacao eh compativel */
			if (esteTipo != tipoCerto) {
				errorList.add("Variavel " + this.name + " nao pode ser " + esteTipo + ", pois foi declarada como " + tipoCerto + "");
				result = false;
			}
		}
		
		Symbol tSymb = null; 
		
		/* Verificando se a variavel eh um vetor */
		if (this.type instanceof VectorType){
			
			int s = 0;
			
			Symbol size = SymbolTable.search(((VectorType)this.type).getSize());
			if (size == null){
				s = Integer.parseInt(((VectorType)this.type).getSize());			
			}
			else
			{		
				// Buscando simbolos e referencias cruzadas
				while (size != null)
				{
					String name = ((ConstOp)((ConstSymb) size).getValue()).getValue();
					size = SymbolTable.search(name);					
				}				
				
																
				// Convertendo valor encontrado para o tamanho (em inteiro) do vetor
				try{
				s = Integer.parseInt((((ConstOp)((ConstSymb) size).getValue()).getValue()));
				}
				catch(Exception e){
					errorList.add("Tamanho invalido para o vetor");	
					result = false;
				}	
			}
			if (s > 0) {
				// Incluindo vetor na tabela de Simbolos
				Symbol elementType = new PrimTypeSymb(((Type)((VectorType) this.type).getType()).getName());
				tSymb = new VectorTypeSymb(s, elementType);
			}
			else{
				errorList.add("Tamanho invalido para o vetor");	
				result = false;			
			}		
		}
		/* Verificando se a variavel eh um struct */
		else if (this.type instanceof StructType){
			
			StructType estrutura = ((StructType)this.type);
			
			List<VarDeclaration> elementList = estrutura.getElementList();
			
			tSymb = new StructTypeSymb();
			
			//Para cada campo da estrutura, tenta inseri-lo no simbolo da mesma
			for (VarDeclaration no: elementList) {
				
				FieldSymb campo = new FieldSymb(no.getName(), SymbolTable.getCurLevel(), (PrimTypeSymb)SymbolTable.search(no.getType().getName()));
				
				if (! ((StructTypeSymb)tSymb).addField(campo)) {
					errorList.add("Campo "+ campo.getName()+" invalido para a estrutura. Ja existe outro campo com esse nome");
					result = false;
				}
				
			}
		}
		else{		
			tSymb = new PrimTypeSymb(this.type.getName());		
		}
		
		// Adicionando simbolo na tabela de Simbolos 
		VarSymb symb = new VarSymb(this.getName(),SymbolTable.getCurLevel(),(PrimTypeSymb)tSymb);
		SymbolTable.getCurScope().addSymbol(symb);			
		this.add = true;
		
		return result;
	}
}

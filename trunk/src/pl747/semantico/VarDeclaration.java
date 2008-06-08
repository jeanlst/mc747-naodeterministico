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
	 * @throws Exception 
	 */
	public boolean check( List<String> errorList ) throws Exception {
	
		boolean result = true;
				
		/* Verificando se a variavel ja foi declarada */
		if ((SymbolTable.localSearch(this.name) != null)||(this.add == true)) {
			errorList.add("Redeclaracao da variavel " + this.name +"");
			result = false;
		}
		
		/* Verificando se a variavel foi inicializada */
		if(this.value != null){	
			
			
			try 
			{
				result = result && value.check(errorList);
			} 
			catch (Exception e){}
						
			Type tipoValue = null;
			if (this.value.getType() instanceof Type) {
				tipoValue = (Type) this.value.getType();
			}
			
			String esteTipo = tipoValue.getName();
			Type tipoVariavel = null;
			if (this.type instanceof Type) {
				tipoVariavel = (Type) this.type;
			}
			
			if (this.getType() instanceof StructType) {
				if (this.value instanceof TupleOp) {
					StructType struct = (StructType)this.getType();
					
					List<VarDeclaration> campos = struct.getElementList();					
					List<Expression> valores = ((TupleOp)this.value).getElementList();
					
					int tam_c = campos.size();
					int tam_v = valores.size();
					
					if (tam_c == tam_v)
					{
						for(int i = 0; i<tam_c; i++)
						{
							result = result && campos.get(i).check(errorList);
							result = result && valores.get(i).check(errorList);
							if (result)
							{
								if(campos.get(i).getType().getName() != valores.get(i).getType().getName())
								{
									errorList.add("Tipos incompatíveis na inicialização da estrutura");	
									result = false;	
								}
							}
						}
						
					}
					else
					{
						errorList.add("Tupla e estrutura não possuem mesmo número de campos");	
						result = false;		
					}
					
				}
				
				
			}
			
			
			
			String tipoCerto = tipoVariavel.getName();
			
			/* Verificando se a inicializacao eh compativel */
			//if (esteTipo != tipoCerto) {
			if ((esteTipo != tipoCerto) && !((esteTipo == "struct") && (tipoCerto == "vector"))) {
				errorList.add("Variavel " + this.name + " nao pode ser " + esteTipo + ", pois foi declarada como " + tipoCerto + "");
				result = false;
			}
			
		}
		
		Symbol tSymb = null; 
		
		/* Verificando se a variavel eh um vetor */
		if (this.type instanceof VectorType){
			
			int s = 0;
			
			// Buscando simbolos e referencias cruzadas
			String tamanho = ((VectorType)this.getType()).getSize();

			try{
				s = Integer.parseInt(tamanho);
			}
			catch(Exception e){	

				Symbol size = SymbolTable.search(tamanho);

				if (size instanceof ConstSymb) {
					s = Integer.parseInt((((ConstOp)((ConstSymb) size).getValue()).getValue()));						
				}
				else{
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
				if(result != false){
					errorList.add("Tamanho invalido para o vetor");	
					result = false;			
				}
			}		
		}
		/* Verificando se a variavel eh um struct */
		else if (this.type instanceof StructType){
			
			StructType estrutura = ((StructType)this.type);
			
			List<VarDeclaration> elementList = estrutura.getElementList();
			
			tSymb = new StructTypeSymb();
			
			//Para cada campo da estrutura, tenta inseri-lo no simbolo da mesma
			for (VarDeclaration no: elementList) {
				
				String nome_campo = no.getName();
				int level_campo = SymbolTable.getCurLevel();
				String nome_tipo_campo = no.getType().getName();
				PrimTypeSymb tipo_campo = new PrimTypeSymb(nome_tipo_campo);
				
				FieldSymb campo = new FieldSymb(nome_campo, level_campo, tipo_campo);				
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

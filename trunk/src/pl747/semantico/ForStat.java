package pl747.semantico;

import java.util.List;

import pl747.TreeNode;
import pl747.tabelaSimbolos.*;

/**
 * Descreve um comando do tipo 'for'.
 */
public class ForStat extends StatOp {

	private VarOp var;
	private Expression start;
	private Expression finish; 
	private boolean dir;
	private CompoundStat body;
	
	
	/**
	 * Construtor de ForStat
	 * @param var : TreeNode - Variavel de iteracao
	 * @param start : TreeNode - valor inicial
	 * @param finish : TreeNode - valor final
	 * @param dir : boolean - true: incremento / false: decremento
	 */
	public ForStat(TreeNode var, TreeNode start, TreeNode finish, boolean dir) {
		this.var    = (VarOp) var;
		this.start  = (Expression) start;
		this.finish = (Expression) finish;
		this.dir    = dir;
		this.body = new CompoundStat();
	}

	public void addChild(TreeNode child) throws Exception {
		this.body.addChild(child);		
	}
		
	/**
	 * Retorna o nome da variavel usada como contador do comando for.
	 */
	public String getVariable() {
		return this.var.getName();
	}

	/**
	 * Retorna a representacao da expressao que define o valor inicial do contador.
	 */
	public Expression getStartValue() {
		return start;
	}

	/**
	 * Retorna a representacao da expressao que define o valor final do contador.
	 */
	public Expression getFinalValue() {
		return this.finish;
	}

	/**
	 * Devolve true se a contagem for no sentido crescente e false em caso contrario.
	 */
	public boolean getDirection() {
		return this.dir;
	}

	/**
	 * Devolve a representacao do comando que eh repetido.
	 */
	public Expression getStat() {
		return this.body;
	}

	/**
	 * Faz a verificacao semantica da sub-arvore representada por este objeto. Retorna true se nao houver erros e false em caso contrario. A lista errorList acumula os erros encontrados.
	 * @param errorList lista de erros encontrados em toda a verificacao
	 * @return true se nao houver erros e false em caso contrario
	 */
	public boolean check(List<String> errorList) throws Exception {
				
		// Verificando se a variavel foi declarada;
		boolean r1 = this.var.check(errorList);
		boolean r2 = true;	
		
		if (r1 == true)
		{			
		// Verificando se os tipos sao iguais
			
				
		// Encontrando tipo da Expressao start
		String tStart = null; 
		if (start instanceof VarOp) 
		{
			VarOp vStart = (VarOp) start;
			tStart = ((VarSymb) SymbolTable.search(vStart.getName())).getType().getName();	
		}
		else if(start instanceof ConstOp)
		{
			ConstOp cStart = (ConstOp) start;
			tStart = ((Type)cStart.getType()).getName();
		}
		else
		{
			tStart = ((Type)start.getType()).getName();
		}
		
		
		//	Encontrando tipo da Expressao finish
		String tFinish = null; 
		if (finish instanceof VarOp) 
		{
			VarOp vFinish = (VarOp) finish;
			tFinish = ((VarSymb) SymbolTable.search(vFinish.getName())).getType().getName();	
		}
		else if(finish instanceof ConstOp)
		{
			ConstOp cFinish = (ConstOp) finish;
			tFinish = ((Type)cFinish.getType()).getName();
		}
		else
		{
			tFinish = ((Type)finish.getType()).getName();
		}
								
		// Tipo da Variavel
		String tVar = ((VarSymb) SymbolTable.search(this.var.getName())).getType().getName();
		
		// Verificando se os tipos sao compativeis
		if((!tVar.equals(tStart)) || 
		   (!tVar.equals(tFinish))){			
			errorList.add("Tipos incompativeis para iteracao");	
			r1 = false;			
		}
		}
		// Verificando os comandos a serem repetidos
		boolean r3 = this.body.check(errorList);
		
		return r1 && r2 && r3;
	}

}

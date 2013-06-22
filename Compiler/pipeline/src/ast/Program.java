package ast;

import symbol_table.Symbol;

public class Program {
	public Symbol id=null;
	public Com_Statement com_statement=null;
	public Program(Symbol id,Com_Statement stmt)
	{
		this.id=id;
		this.com_statement=stmt;
	}
	
}

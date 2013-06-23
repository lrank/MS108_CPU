package ast;

import symbol_table.Symbol;

public class Declarator {
	public Symbol id=null;
	public Integer n1=0,n2=0;
	public Declarator(Symbol id)
	{
		this.id=id;
	}
	public Declarator(Symbol id,Integer n1,Integer n2)
	{
		this.id=id;
		this.n1=n1;
		this.n2=n2;
	}
	
}

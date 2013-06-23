package ast;

import symbol_table.Symbol;

public class Expression5 extends Expression {
	public Symbol id=null;
	public Integer n1=0,n2=0,num=0;
	public Expression5(Symbol pe,Integer n1,Integer n2,Integer i)
	{
		id=pe;
		this.n1=n1;
		this.n2=n2;
		num=i;
	}
	
}

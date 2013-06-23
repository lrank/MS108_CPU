package ast;

import symbol_table.Symbol;

public class Expression2 extends Expression {
	public Symbol id=null;
	public Integer num=0;
	public Expression2(Symbol id,Integer num)
	{
		this.id=id;
		this.num=num;
	}

}

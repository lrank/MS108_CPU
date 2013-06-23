package ast;

import symbol_table.Symbol;

public class Expression1 extends Expression {
	public Symbol id=null;
	public Integer num=0;
	public Expression1(Symbol id,Integer num)
	{
		this.id=id;
		this.num=num;
	}

}

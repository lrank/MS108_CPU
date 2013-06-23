package ast;

public class Exp_Statement extends Statement {
	public Expression expression=null;
	public Exp_Statement(Expression exp)
	{
		expression=exp;
	}
	
}

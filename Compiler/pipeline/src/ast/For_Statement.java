package ast;

public class For_Statement extends Statement {
	public Expression1 expression1=null;
	public Expression2 expression2=null;
	public Expression3 expression3=null;
	public Com_Statement com_statement=null;
	public For_Statement(Expression1 e1,Expression2 e2,Expression3 e3,Com_Statement cs)
	{
		expression1=e1;
		expression2=e2;
		expression3=e3;
		com_statement=cs;
	}
	
}

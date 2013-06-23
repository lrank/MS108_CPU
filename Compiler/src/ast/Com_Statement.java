package ast;

public class Com_Statement extends Statement {
	public Declaration_Temp3 declaration_temp3=null;
	public Statement_Temp3 statement_temp3=null;
	public Com_Statement(Declaration_Temp3 dt,Statement_Temp3 stmt)
	{
		declaration_temp3=dt;
		statement_temp3=stmt;
	}
	
}

package ast;

public class Declarators {
	public Declarator declarator=null;
	public Declarators_Temp declarators_temp=null;
	public Declarators(Declarator d,Declarators_Temp dt)
	{
		declarator=d;
		declarators_temp=dt;
	}
	
}

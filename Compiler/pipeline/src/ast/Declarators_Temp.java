package ast;

public class Declarators_Temp {
	public Declarator declarator=null;
	public Declarators_Temp declarators_temp=null;
	public Declarators_Temp(Declarator d,Declarators_Temp dt)
	{
		declarator=d;
		declarators_temp=dt;
	}
	
}

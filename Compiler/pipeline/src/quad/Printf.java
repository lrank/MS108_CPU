package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import temp.Temp;
import assem.*;
import analysis.Expression;

public class Printf extends Quad {
	public Temp temp;
	public Printf(Temp t)
	{
		temp=t;
	}
	@Override
	public String toString()
	{
		return "printf " + temp;
	}
	public AssemList gen() throws Exception
	{
		return AssemList.L(new Assem("move $a0,%",temp),
			   AssemList.L(new Assem("li $v0,1"),
			   AssemList.L(new Assem("syscall"))));
	}
	@Override
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(temp);
		return set;
	}
	@Override
	public Set<Expression> genExp()
	{
		return new LinkedHashSet<Expression>();
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return new LinkedHashSet<Expression>();
	}

}

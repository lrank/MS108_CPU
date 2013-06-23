package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import analysis.Expression;
import assem.*;

public class Leave extends Quad {
	@Override
	public String toString()
	{
		return "leave main";
	}
	public AssemList gen() throws Exception
	{
		/*return AssemList.L(new Assem("li $v0,10"),
			   AssemList.L(new Assem("syscall")));*/
		return null;
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

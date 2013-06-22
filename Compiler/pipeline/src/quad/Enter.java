package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import assem.AssemList;
import analysis.Expression;

public class Enter extends Quad {
	@Override
	public String toString()
	{
		return "enter main";
	}
	public AssemList gen() throws Exception
	{
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
		return U;
	}
}

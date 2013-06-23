package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import temp.Label;
import assem.*;
import analysis.Expression;

public class LABEL extends Quad {
	public Label l;
	public LABEL(Label l)
	{
		this.l=l;
	}
	@Override
	public String toString()
	{
		return l+":";
	}
	@Override
	public AssemList gen() {
		return AssemList.L(new Assem("!%:", l));
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

package quad;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import analysis.Expression;
import assem.Assem;
import assem.AssemList;
import temp.Label;

public class Goto extends Quad {
	public Label target;
	public Goto(Label l)
	{
		target=l;
	}
	@Override
	public String toString()
	{
		return "goto " + target;
	}
	@Override
	public AssemList gen()
	{
		return AssemList.L(new Assem("j %",target));
	}
	@Override
	public void replaceLabelOf(Label old,Label l)
	{
		if (target.equals(old)) target=l;
	}
	@Override
	public Label jumpLabel()
	{
		return target;
	}
	@Override
	public boolean isJump()
	{
		return true;
	}
	@Override
	public Quad jumpTargetIn(List<Quad> quads)
	{
		return findTargetIn(quads,target);
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

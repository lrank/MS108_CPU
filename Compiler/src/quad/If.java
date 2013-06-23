package quad;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import temp.*;
import assem.*;
import analysis.Expression;

public class If extends Quad {
	public Temp left;
	public Temp right;
	public Label target;
	public If(Temp rs,Temp rt,Label l)
	{
		this.left=rs;
		this.right=rt;
		target=l;
	}
	@Override
	public String toString()
	{
		return "if "+ left + " >= " + right + " goto " + target;
	}
	@Override
	public boolean isJump()
	{
		return true;
	}
	@Override
	public Label jumpLabel()
	{
		return target;
	}
	@Override
	public void replaceLabelOf(Label old,Label l)
	{
		if (target.equals(old)) target=l;
	}
	@Override
	public Quad jumpTargetIn(List<Quad> quads)
	{
		return findTargetIn(quads,target);
	}
	@Override
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(left);
		return set;
	}
	@Override
	public AssemList gen()
	{
		return AssemList.L(new Assem("bge %,%,%",left,right,target));
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
	@Override
	public void replaceUseOf(Temp old,Temp t)
	{
		if (left.equals(old)) left=t;
	}
	
}

package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import analysis.Expression;
import assem.*;
import temp.Temp;

public class Move extends Quad {
	public Temp rd,rs;
	public Move(Temp rd,Temp rs)
	{
		this.rd=rd;
		this.rs=rs;
	}
	@Override
	public String toString()
	{
		return rd + "=" + rs;
	}
	@Override
	public AssemList gen()
	{
		return AssemList.L(new Assem("move @,%",rd,rs));
		//return AssemList.L(new Assem("lw $t1,%",rs),AssemList.L(new Assem("sw $t1,%",rd)));
	}
	@Override
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(rd);
		return set;
	}
	@Override
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(rs);
		return set;
	}
	@Override
	public Set<Expression> genExp()
	{
		// TODO if a single variable is gen-ed here then copy propagation is done? i dont know
		return new LinkedHashSet<Expression>();
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return killExpBy(U,rd);
	}
	@Override
	public void replaceUseOf(Temp old,Temp t)
	{
		if (rs.equals(old)) rs=t;
	}

}

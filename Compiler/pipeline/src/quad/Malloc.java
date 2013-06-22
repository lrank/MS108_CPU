package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import temp.*;
import assem.*;
import analysis.Expression;

public class Malloc extends Quad {
	public Temp temp;
	public int offset;
	public Malloc(Temp t,int c)
	{
		temp=t;
		offset=c;
	}
	@Override
	public String toString()
	{
		return "malloc offset: (" + offset + ") to " + temp;
	}
	@Override
	public AssemList gen() throws Exception
	{
		return AssemList.L(new Assem("addiu @,$sp,%",temp,offset));
	}
	@Override
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(temp);
		return set;
	}
    @Override
    public Set<Temp> use()
	{
		return new LinkedHashSet<Temp>();
	}
	@Override
	public Set<Expression> genExp()
	{
		return new LinkedHashSet<Expression>();
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return killExpBy(U,temp);
	}
	@Override
	public void replaceUseOf(Temp old,Temp t)
	{
		if (temp.equals(old)) temp=t;
	}
	
}

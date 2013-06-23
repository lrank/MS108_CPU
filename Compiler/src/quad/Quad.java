package quad;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import util.*;
import analysis.Expression;
import assem.*;
import temp.*;

public abstract class Quad implements Constants {
	public abstract AssemList gen() throws Exception;
	public String getRegister(String st) throws Exception
    {
    	for (int i=0;i<regNames.length;++i)
    		if (regNames[i].equals(st)) return ""+i;
    	throw new Exception("fail in find reg "+st);
    }
    public void replaceLabelOf(Label old,Label l) {}
    public void clearAll()
    {
		leader=false;
		successors.clear();
		IN.clear();
		OUT.clear();
		avail.clear();
	}
	private boolean leader=false;
	public void setLeader()
	{
		leader=true;
	}
	public boolean isLeader()
	{
		return leader;
	}
	public boolean isJump()
	{
		return false;
	}
    public Label jumpLabel()
    {
		return null;
	}
	public Quad jumpTargetIn(List<Quad> quads)
	{
		return null;
	}
	protected Quad findTargetIn(List<Quad> quads,Label target)
	{
		for (Quad q : quads)
			if (q instanceof LABEL && ((LABEL) q).l.equals(target)) return q;
		return null;
	}
	public Set<Temp> def()
	{
		return new LinkedHashSet<Temp>();
	}
	public Set<Temp> use()
	{
		return new LinkedHashSet<Temp>();
	}
	public void replaceUseOf(Temp old,Temp t) {}
	
	private List<Quad> successors=new LinkedList<Quad>();

	public void addSuccessor(Quad q)
	{
		successors.add(q);
	}
	public List<Quad> getSuccessors()
	{
		return successors;
	}
	public Set<Temp> IN=new LinkedHashSet<Temp>();
	public Set<Temp> OUT=new LinkedHashSet<Temp>();
	public Set<Expression> avail=new LinkedHashSet<Expression>();
	public abstract Set<Expression> genExp() throws Exception;
	public abstract Set<Expression> killExp(Set<Expression> U);
	public String toExp()
	{
		return "";
	}
	protected Set<Expression> killExpBy(Set<Expression> U,Temp t)
	{
		Set<Expression> x=new LinkedHashSet<Expression>();
		for (Expression e : U)
			if (e.isKilledBy(t)) x.add(e);
		return x;
	}
}

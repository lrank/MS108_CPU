package analysis;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import quad.*;
import temp.Temp;

public class BasicBlock
{	
	public static int count=0;
	public int number=0;
	public BasicBlock()
	{
		number=++count;
	}
	public LinkedList<Quad> quads=new LinkedList<Quad>();
	public void addQuad(Quad q)
	{
		quads.add(q);
	}
	public LinkedList<Quad> getQuads()
	{
		return quads;
	}
	public Quad getLastQuad()
	{
		return quads.descendingIterator().next();
	}
	public Object getFirstQuad()
	{
		return quads.get(0);
	}
	public LinkedList<BasicBlock> successors=new LinkedList<BasicBlock>();
	public void addSuccessor(BasicBlock succ)
	{
		successors.add(succ);
	}
	public LinkedList<BasicBlock> getSuccessors()
	{
		return successors;
	}
	public LinkedList<BasicBlock> predecessors=new LinkedList<BasicBlock>();
	public void addPredecessor(BasicBlock pred)
	{
		predecessors.add(pred);
	}
	public LinkedList<BasicBlock> getPredecessors()
	{
		return predecessors;
	}
	public String toString()
	{
		return "B" + number;
	}
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		Iterator<Quad> iter=quads.descendingIterator();
		while (iter.hasNext())
		{
			Quad q=iter.next();
			set.addAll(q.def());
			set.removeAll(q.use());
		}
		return set;
	}
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		Iterator<Quad> iter=quads.descendingIterator();
		while (iter.hasNext())
		{
			Quad q=iter.next();
			set.removeAll(q.def());
			set.addAll(q.use());
		}
		return set;
	}
	public Set<Temp> IN=new LinkedHashSet<Temp>();
	public Set<Temp> OUT=new LinkedHashSet<Temp>();
	public Set<Expression> avail=new LinkedHashSet<Expression>();
	public Set<Expression> allExp() throws Exception
	{
		Set<Expression> x=new LinkedHashSet<Expression>();
		for (Quad q : quads)
		    Add(x,q.genExp());
		return x;
	}
	public Set<Expression> genExp() throws Exception
	{
		Set<Expression> x=new LinkedHashSet<Expression>();
		for (int i=0;i<quads.size();++i)
		{
			Quad q=quads.get(i);
			Set<Expression> temp=q.genExp();
			x.addAll(temp);
			for (Iterator<Expression> iter=x.iterator();iter.hasNext();)
			{
				Expression e=iter.next();
				if (temp.iterator().hasNext())
				{
					Expression eee=temp.iterator().next();
					//if (e.isKilledBy(eee.dst) || e.exp.equals(eee.exp) && !e.dst.equals(eee.dst))
					//	iter.remove();
				}
				else if (q instanceof Store)
				{
					if (e.isKilledBy(((Store) q).x)) iter.remove();
				}
				else break;
			}
			/*Set<Expression> y=new LinkedHashSet<Expression>();
			for (Expression e : x)
			{
				if (temp.iterator().hasNext())
				{
					Expression eee=temp.iterator().next();
					if (!(e.isKilledBy(eee.dst) || e.exp.equals(eee.exp) && !e.dst.equals(eee.dst)))
						y.add(e);
				}
				else if (q instanceof Store)
				{
					if (!(e.isKilledBy(((Store) q).x))) y.add(e);
				}
				else y.add(e);
			}
			x=y;*/
		}
		return x;
	}
	public Set<Expression> killExp(Set<Expression> U)
	{
		Set<Expression> x=new LinkedHashSet<Expression>();
		for (int i=0;i<quads.size();++i)
		{
			Quad q=quads.get(i);
			Iterator<Temp> i2=q.def().iterator();
			if (i2.hasNext())
			{
				Temp t=i2.next();
				for (Iterator<Expression> iter=x.iterator();iter.hasNext();)
				{
					Expression e=iter.next();
					if (e.exp.equals(q.toExp()) && e.dst.equals(t)) iter.remove();
				}
			}
			/*Set<Expression> y=new LinkedHashSet<Expression>();
			for (Expression e : x)
			{
				if (!(e.exp.equals(q.toExp()) && i2.hasNext() && e.dst.equals(i2.next()))) y.add(e);
			}
			x=y;*/
			x.addAll(q.killExp(U));
		}
		return x;
	}
	public void Add(Set<Expression> x,Set<Expression> u)
	{
		for (Expression e : u)
		{
			boolean flag=false;
			for (Expression e2 : x)
				if (e.toString().equals(e2.toString()))
				{
					flag=true;
					break;
				}
			if (!flag) x.add(e);
		}
	}
	
}

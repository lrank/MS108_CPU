package analysis;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import quad.Quad;

public class AvailableExpressionAnalyzer
{
	public void analyze(LinkedList<BasicBlock> blocks) throws Exception
	{
		Set<Expression> U=new LinkedHashSet<Expression>();
		for (BasicBlock n : blocks)
			U.addAll(n.allExp());
		for (BasicBlock n : blocks)
		{
			n.avail.clear();
			n.avail.addAll(U);
		}
		boolean changed=true;
		while (changed)
		{
			changed=false;
			for (BasicBlock n : blocks)
				if (n.getPredecessors().isEmpty()) n.avail.clear();
				else
				{
					Set<Expression> x=new LinkedHashSet<Expression>(U);
					for (BasicBlock p : n.getPredecessors())
						Retain(x,p.avail);
					Remove(x,n.killExp(U));
					Add(x,n.genExp());
					//x.retainAll(U);
					String st="";
					for (Expression e : n.avail)
						if (!st.contains(e.toString())) st+=e.toString();
					String st2="";
					for (Expression e : x)
						if (!st2.contains(e.toString())) st2+=e.toString();
					if (!st.equals(st2))
					{
						n.avail=x;
						changed=true;
					}
				}
		}
		BasicBlock lastbb=null;
		for (BasicBlock n : blocks)
		{
			Quad last=null;
			for (int i=0;i<n.getQuads().size();++i)
			{
				Quad q=n.getQuads().get(i);
				if (last==null)
				{
					if (n.getPredecessors().isEmpty())
					{
						q.avail.clear();
					}
					else
					{
						Set<Expression> x=new LinkedHashSet<Expression>(U);
						/*for (BasicBlock p : n.getPredecessors())
							x.retainAll(p.avail);*/
						for (BasicBlock p : n.getPredecessors())
							Retain(x,p.avail);
						q.avail=x;
					}
					/*if (lastbb!=null) q.avail=lastbb.avail;
					else q.avail.clear();*/
				}
				else
				{
					q.avail.clear();
					Add(q.avail,last.avail);
					Remove(q.avail,last.killExp(U));
					Add(q.avail,last.genExp());
				}
				last=q;
			}
			lastbb=n;
		}
	}
	public void Retain(Set<Expression> x,Set<Expression> u)
	{
		for (Iterator<Expression> iter=x.iterator();iter.hasNext();)
		{
			boolean flag=false;
			Expression eee=((Expression) iter.next());
			for (Expression e2 : u)
				if (e2.toString().equals(eee.toString()))
				{
					flag=true;
					break;
				}
			if (!flag)
			{
				iter.remove();
				//iter=x.iterator();
			}
		}
	}
	public void Remove(Set<Expression> x,Set<Expression> u)
	{
		for (Iterator<Expression> iter=x.iterator();iter.hasNext();)
		{
			boolean flag=false;
			Expression eee=((Expression) iter.next());
			for (Expression e2 : u)
				if (e2.toString().equals(eee.toString()))
				{
					flag=true;
					break;
				}
			if (flag)
			{
				iter.remove();
				//iter=x.iterator();
			}
		}
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

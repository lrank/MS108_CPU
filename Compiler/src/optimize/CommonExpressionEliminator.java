package optimize;

import java.util.LinkedList;
import java.util.List;

import translate.CompilationUnit;
import quad.*;
import analysis.*;

public class CommonExpressionEliminator {
	public boolean eliminate(CompilationUnit u)
	{
		boolean found=false;
		List<Quad> result=new LinkedList<Quad>();
		for (Quad q : u.getQuads())
		{
			Quad e=eliminated(q);
			if (e==null) result.add(q);
			else
			{
				result.add(e);
				found=true;
				//System.out.println("replace : "+q+"  to  "+e);
			}
		}
		u.setQuads(result);
		return found;
	}
	private Quad eliminated(Quad q)
	{
		if (q instanceof Binary)
		{
			for (Expression e : q.avail)
				if (((Binary) q).toExp().equals(e.exp))
					return new Move(((Binary) q).rd,e.dst);
		}
		else if (q instanceof BinaryConst)
		{
			for (Expression e : q.avail) 
				if (((BinaryConst) q).toExp().equals(e.exp))
					return new Move(((BinaryConst) q).rd,e.dst);
		}
		else if (q instanceof Load)
		{
			for (Expression e : q.avail)
				if (((Load) q).toExp().equals(e.exp))
					return new Move(((Load) q).x,e.dst);
		}
		return null;
	}
	
}

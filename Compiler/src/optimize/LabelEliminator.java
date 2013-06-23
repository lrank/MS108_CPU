package optimize;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import quad.*;
import temp.Label;
import translate.CompilationUnit;

public class LabelEliminator
{
	public boolean eliminate(CompilationUnit u)
	{
		List<Quad> quads=new ArrayList<Quad>(u.getQuads());
		// remove duplicated labels
		for (int i=0;i<quads.size()-1;i++)
		{
			if (quads.get(i) instanceof LABEL && quads.get(i+1) instanceof LABEL)
			{
				LABEL q=(LABEL) quads.get(i+1);
				Label l=((LABEL) quads.get(i)).l;
				List<Quad> result=new LinkedList<Quad>();
				for (Quad quad : u.getQuads())
				{
					if (quad!=q)
					{
						quad.replaceLabelOf(q.l,l);
						result.add(quad);
					}
				}
				u.setQuads(result);
				return true;
			}
		}
		// remove unused labels
		boolean removed=false;
		List<Quad> result=new LinkedList<Quad>();
		for (Quad quad : u.getQuads())
		{
			if (quad instanceof LABEL)
			{
				if (isLabelUsed(quads,((LABEL) quad).l)) result.add(quad);
				else removed=true;
			}
			else result.add(quad);
		}
		if (removed) u.setQuads(result);
		return removed;
	}
	private boolean isLabelUsed(List<Quad> quads,Label label)
	{
		for (Quad q : quads)
			if (label.equals(q.jumpLabel())) return true;
		return false;
	}
	
}
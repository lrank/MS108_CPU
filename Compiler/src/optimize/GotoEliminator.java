package optimize;

import java.util.List;

import translate.CompilationUnit;
import quad.*;
import temp.Label;

public class GotoEliminator {
	public void eliminate(CompilationUnit u)
	{
		List<Quad> quads=u.quads;
		for (Quad q : quads)
		{
			if (q instanceof Goto)
			{
				Label target=((Goto) q).target;
				int i;
				for (i=0;i<quads.size();++i)
				{
					Quad t=quads.get(i);
					if (t instanceof LABEL && ((LABEL) t).l.equals(target)) break;
				}
				if (i<quads.size()-1)
				{
					Quad next=quads.get(i+1);
					if (next instanceof Goto)
					{
						((Goto) q).target=((Goto) next).target;
					}
				}
			}
			else if (q instanceof If)
			{
				Label target=((If) q).target;
				int i;
				for (i=0;i<quads.size();++i)
				{
					Quad t=quads.get(i);
					if (t instanceof LABEL && ((LABEL) t).l.equals(target)) break;
				}
				if (i<quads.size()-1)
				{
					Quad next=quads.get(i+1);
					if (next instanceof Goto)
					{
						((If) q).target=((Goto) next).target;
					}
				}
			}
		}
	}
	
}

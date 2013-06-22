package optimize;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import quad.*;
import translate.*;
import temp.Temp;

public class DeadCodeEliminator {
	public void eliminate(CompilationUnit u)
	{
		List<Quad> result=new LinkedList<Quad>();
		for (Quad q : u.getQuads())
		{
			if (q instanceof Binary || q instanceof BinaryConst || q instanceof Load || q instanceof Move)
			{
				Set<Temp> x=new LinkedHashSet<Temp>(q.def());
				x.retainAll(q.OUT);
				if (x.size()!=0) result.add(q);
				else
				{
					//System.out.println(q+"    :  dead");
				}
			}
			else result.add(q);
		}
		u.setQuads(result);
	}
	
}

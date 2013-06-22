package optimize;

import java.util.LinkedHashMap;
import java.util.Map;

import analysis.BasicBlock;

import temp.*;
import quad.*;

public class LocalCopyPropagation {
	public void propagate(BasicBlock bb)
	{
		Map<Temp,Temp> tmap=new LinkedHashMap<Temp,Temp>();
		Map<Temp,Const> cmap=new LinkedHashMap<Temp,Const>();
		Map<Temp,Temp> back=new LinkedHashMap<Temp,Temp>();
		for (int i=0;i<bb.getQuads().size();++i)
		{
			Quad q=bb.getQuads().get(i);
			for (Temp t : q.use())
			{
				if (tmap.containsKey(t))
					q.replaceUseOf(t,tmap.get(t));
				else if (cmap.containsKey(t))
				{
					/*if (q instanceof Binary) bb.getQuads().set(i,new BinaryConst(((Binary) q).rd,((Binary) q).op,((Binary) q).rs,cmap.get(t)));
					else if (q instanceof Move) bb.getQuads().set(i,new UnaryConst(((Move) q).rd,cmap.get(t)));
					else if (q instanceof Branch)
					{
						if (((Branch) q).right instanceof Temp && ((Branch) q).right.equals(t))
							((Branch) q).right=cmap.get(t);
					}*/
				}
			}
			if (q instanceof Move)
			{
				tmap.put(((Move) q).rd,((Move) q).rs);
				cmap.remove(((Move) q).rd);
				back.put(((Move) q).rs,((Move) q).rd);
			}
			else if (q instanceof UnaryConst)
			{
				cmap.put(((UnaryConst) q).rd,((UnaryConst) q).cst);
				tmap.remove(((UnaryConst) q).rd);
			}
			else
			for (Temp t : q.def())
			{
				tmap.remove(back.get(t));
				cmap.remove(t);
			}
		}
	}
	
}

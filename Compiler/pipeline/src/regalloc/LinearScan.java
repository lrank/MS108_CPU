package regalloc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.TreeSet;

import analysis.*;
import temp.Temp;
import translate.CompilationUnit;
import translate.Translate;

public final class LinearScan implements RegAlloc,Comparator<LiveInterval>
{
	private static final int R=numOfSavedRegisters;
	private TreeSet<Integer> freeRegs=new TreeSet<Integer>();
	private ArrayList<LiveInterval> active=new ArrayList<LiveInterval>();
	public LinearScan(CompilationUnit cu,Analyzer analyzer,Translate trans)
	{
		/*for (int i=0;i<R;i++)
			freeRegs.add(i+baseOfSavedRegisters);
		freeRegs.add(3);   // $v1  level.saveReg  level.loadReg  enter.loadArgument
		*/
		for (int i=1;i<32;++i)
			freeRegs.add(i);
		cu.findLiveIntervals(analyzer,trans);
		for (LiveInterval i : cu.getLiveIntervals())
		{
			expireOldIntervals(i);
			if (active.size()==31) spillAtInterval(i);
			else
			{
				i.register=getFreeRegister();
				active.add(i);
			}
		}
	}
	
	public void expireOldIntervals(LiveInterval i)
	{
		Collections.sort(active,this);
		ListIterator<LiveInterval> iter=active.listIterator();
		while (iter.hasNext())
		{
			LiveInterval j=iter.next();
			if (j.getEndPoint()>=i.getStartPoint()) return;
			putFreeRegister(j.register);
			iter.remove();
		}
	}
	public void spillAtInterval(LiveInterval i)
	{
		LiveInterval spill=active.get(active.size()-1);	// last interval in active
		if (spill.getEndPoint()>i.getEndPoint())
		{
			i.register=spill.register;
			spill.spilled=true;
			spill.register=spillReg;
			active.remove(spill);
			active.add(i);
		}
		else
		{
			i.spilled=true;
			i.register=spillReg;
		}
	}
	private int getFreeRegister()
	{
		return freeRegs.pollFirst();
	}
	private void putFreeRegister(int r)
	{
		freeRegs.add(r);
	}
	@Override
	public String map(Temp temp)
	{
		LiveInterval i=temp.getLiveInterval();
		return "$"+regNames[i.register];
	}
	/**
	 * sort by increasing end point
	 */
	@Override
	public int compare(LiveInterval a,LiveInterval b) {
		return a.getEndPoint()-b.getEndPoint();
	}
}

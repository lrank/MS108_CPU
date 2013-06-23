package translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import analysis.Analyzer;
import analysis.BasicBlock;
import analysis.LiveInterval;
import quad.Quad;
import temp.Label;

public class CompilationUnit
{
	public List<Quad> quads=null;
	public Label label=null;
	public CompilationUnit(List<Quad> quads,Label label)
	{
		this.quads=quads;
		this.label=label;
	}
	public List<Quad> getQuads()
	{
		return quads;
	}
	public void setQuads(List<Quad> quads)
	{
		if (!(quads instanceof LinkedList))
		{
			quads=new LinkedList<Quad>(quads);
		}
		// keep it a list
		this.quads=quads;
	}
	public Label getLabel()
	{
		return label;
	}
	/*public void findBranches(Analyzer analyzer)
	{
		quads=analyzer.findBranches(quads);
	}*/
	public LinkedList<BasicBlock> blocks=null;
	
	public void findBasicBlocks(Analyzer analyzer)
	{
		blocks=analyzer.getBasicBlocks(quads);
	}
	public void findLiveness(Analyzer analyzer)
	{
		analyzer.findLiveness(blocks);
	}
	public LinkedList<BasicBlock> getBasicBlocks()
	{
		return blocks;
	}
	public ArrayList<LiveInterval> liveIntervals;
	
	public void findLiveIntervals(Analyzer analyzer,Translate trans)
	{
		liveIntervals=new ArrayList<LiveInterval>(analyzer.findLiveIntervals(quads,label.toString().equals("_GLOBAL"),trans).values());
		Collections.sort(liveIntervals);	// sort by start point
	}
	public ArrayList<LiveInterval> getLiveIntervals()
	{
		return liveIntervals;
	}
	
}
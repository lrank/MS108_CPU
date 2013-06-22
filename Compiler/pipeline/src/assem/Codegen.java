package assem;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import quad.Quad;
import regalloc.RegAlloc;
import translate.CompilationUnit;
import optimize.*;

public class Codegen {
	private List<Quad> quads;
	public Codegen() {}
	public LinkedList<String> lines=new LinkedList<String>();
	public Codegen(List<Quad> q)
	{
		quads=q;
	}
	private LinkedList<Assem> inits=new LinkedList<Assem>();
	public void gen(Assem assem)
	{
		inits.add(assem);
	}
	public void gen(AssemList assems)
	{
		for (AssemList p=assems;p!=null;p=p.tail)
			gen(p.head);
	}
	public void gen(CompilationUnit cu,RegAlloc allocator) throws Exception
	{
		ArrayList<Quad> quads=new ArrayList<Quad>(cu.getQuads());
		ArrayList<Assem> assems=new ArrayList<Assem>();
		for (Quad q : quads)
			for (AssemList p=q.gen();p!=null;p=p.tail)
				assems.add(p.head);
		//RemoveTopLevelDisplay.process(assems);   ???
		/*for (Assem assem : assems)
			System.out.println(assem);
		System.out.println("*************************");*/
		for (Assem assem : assems)
			lines.add(StrengthReduction.process(assem).toString(allocator));
	}
    /*public void flush() throws Exception
    {
    	for (int i=0;i<quads.size();++i)
    		for (AssemList list=quads.get(i).gen();list!=null;list=list.tail)
    			out.add((""+list.head).replace("main","main_here"));
    }*/
	public void flush(PrintStream out,boolean opt) throws Exception
	{
		ArrayList<String> insertsMain=new ArrayList<String>();
		if (opt)
		{
			LastOptimize.optimize(lines);
		}
		/*lines=new LinkedList<String>();
		lines.add("li $t0,0");
		lines.add("li $t1,0");
		lines.add("li $t2,6");
		lines.add("L1:");
		lines.add("bge $t1,$t2,L2");
		lines.add("addu $t0,$t0,$t1");
		lines.add("addiu $t1,$t1,1");
		lines.add("j L1");
		lines.add("addiu $t1,$t1,1");
		lines.add("L2:");*/
		for (Assem init : inits)
			insertsMain.add(""+init);
		insertsMain.addAll(lines);
		Asm.scanLabel(insertsMain);
		for (Assem init : inits)
			Asm.print(""+init,out);
		for (String line : lines)
		{
			Asm.print(line.replace("main","main_here").replace("add ","addu ")
					      .replace("addi ","addiu ").replace("sub ","subu ")
					      .replace("subi ","subiu ").replace("div ","divu "),out);
			/*if (line.startsWith("main:"))
			{
				for (String insert : insertsMain)
					out.println(insert);
			}*/
		}
		out.println("11100000000000000000000000000000");
		out.println("11100000000000000000000000000000");
		out.println("11100000000000000000000000000000");
		out.println("11100000000000000000000000000000");
		out.println("11100000000000000000000000000000");
		out.println("11111111111111111111111111111111");
	}
    /*public void print()
    {
    	for (int i=0;i<lines.size();++i)
    		System.out.println(lines.get(i));
    }*/
	
}

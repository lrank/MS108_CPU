package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import ast.*;
import syn.parser;
import translate.*;
import analysis.*;
import assem.*;
import optimize.*;
import quad.*;
import regalloc.*;
//import com.google.gson.Gson;

public class Main {
	public static int totalquads=0;
	private static int show(List<CompilationUnit> units,boolean debug_mode)
	{
		if (debug_mode)
		{
			int total=0;
			for (CompilationUnit u : units)
				total+=u.quads.size();
			/*System.out.println("#"+total);
			for (CompilationUnit u : units)
				for (Quad q : u.getQuads())
					System.out.println("#"+q);*/
			return total;
		}
		int total=0;
		for (CompilationUnit u : units)
			total+=u.quads.size();
		totalquads=total;
		return total;
	}
	
	public static String pathOf(String filename) {
		return Main.class.getResource(filename).getPath();
	}

	private static int test(String filename) throws Exception {
		InputStream inp = new FileInputStream(filename);
		parser par = new parser(inp);
		java_cup.runtime.Symbol parseTree = null;
		try {
			parseTree = par.parse();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			inp.close();
		}
		Program prog=(Program) parseTree.value;
		Translate trans=new Translate();
		trans.transProgram(prog);
		List<CompilationUnit> units=trans.getUnits();
		show(units,false);
		
		Analyzer analyzer=new Analyzer();
		LabelEliminator le=new LabelEliminator();
		GotoEliminator ge=new GotoEliminator();
			
		for (CompilationUnit u : units)
		{
			while (le.eliminate(u));
			//show(units.get(0),debug_mode || true);
			//u.findBranches(analyzer);
			//show(units.get(0),debug_mode || true);
			u.findBasicBlocks(analyzer);
			u.findLiveness(analyzer);
		}

		if (true)
		{
		    DeadCodeEliminator dce=new DeadCodeEliminator();
		    AvailableExpressionAnalyzer availexp=new AvailableExpressionAnalyzer();
			CommonExpressionEliminator comexp=new CommonExpressionEliminator();
			LocalCopyPropagation lcp=new LocalCopyPropagation();
		    for (CompilationUnit u : units)
		    {
				List<Quad> oldQuads=new LinkedList<Quad>();
				do
				{
					oldQuads.clear();
					oldQuads.addAll(u.getQuads());

					//   <--  common expression elimination  -->
					do
					{
						u.findBasicBlocks(analyzer);
						availexp.analyze(u.getBasicBlocks());
					}
	  				while (comexp.eliminate(u));
						
					//if (debug_mode) System.out.println("*************common exp**********");
					show(units,false);
						//   <--  local copy propagation  -->
					for (BasicBlock bb : u.getBasicBlocks())
							lcp.propagate(bb);
						
					//if (debug_mode) System.out.println("*************local copy propagation**********");
					show(units,false);
					//   <--  dead code elimination  -->
					u.findLiveness(analyzer);
					dce.eliminate(u);
					u.findBasicBlocks(analyzer);
						
					u.findLiveness(analyzer);
						
					//if (debug_mode) System.out.println("*************dead  code  elimination**********");
					show(units,false);
				}
				while (!u.getQuads().equals(oldQuads));
			}
		}

		//if (debug_mode) System.out.println("<---  Last Intermediate Representation  -->");
		for (CompilationUnit u : units)
		{
			while (le.eliminate(u));
			//show(units.get(0),debug_mode || true);
			//u.findBranches(analyzer);
			//show(units.get(0),debug_mode || true);
			u.findBasicBlocks(analyzer);
			u.findLiveness(analyzer);
		}
		show(units,true);
		    
		//	    <--  code generation  -->
		Codegen code=new Codegen(trans.quads);
		/*code.gen(new Assem(".data"));
		code.gen(new Assem("printf_buf: .space 2"));
		code.gen(new Assem(".align 2"));
		code.gen(new Assem(".text"));
		code.gen(new Assem(".globl main"));*/
		//code.gen(new Assem("main:"));
		//code.gen(new Assem("top_level:"));
		//code.gen(new Assem("li $sp,0x10010000"));
		//code.gen(new Assem("addiu $sp,$sp,-4     # move up a cell"));
		//code.gen(new Assem("add $fp, $sp, $zero   # start using memory here"));
		//code.gen(new Assem("addu $gp,$sp,$zero   # set global pointer"));
		//code.gen(new Assem("addi $sp, $sp, -%",Level.MaxDepth*Level.Maxlocal*Constants.wordSize));
		//fprintf(stdout, "%s", global_buffer));
		for (CompilationUnit u : units)
			code.gen(u,new LinearScan(u,analyzer,trans));
		//code.flush();
		/*code.gen(new Assem(".data"));
		code.gen(new Assem("gc_sp_limit:"));
		code.gen(new Assem(".word 10000000"));
		code.gen(new Assem(".text"));
		code.gen(new Assem("li $gp,0x10010000"));
		code.gen(new Assem("li $sp,0x10010000"));
		code.gen(new Assem("li $fp,0x10010000"));
		code.gen(new Assem("jal main"));*/
		code.flush(System.out,true);

		return 0;
	}

	public static void main(String argv[]) throws Exception {
		//semantic(pathOf("example5.c"));
		try {
			File fl=new File("simple.c");
		    test(fl.getAbsolutePath());
			//semantic(fl.getAbsolutePath());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Error(e.toString());
		}
		if (argv.length>0)
		{
			try {
				System.exit(test(argv[0]));
			}
			catch (Exception e)
			{
				//e.printStackTrace(System.out);
				System.exit(1);
			}
		}
	}
}

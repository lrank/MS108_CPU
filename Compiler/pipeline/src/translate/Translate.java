package translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.*;
import quad.*;
import ast.*;
import temp.*;
import type.*;

public class Translate {
	private Env env=null;
	public Translate()
	{
		this(new Env());
	}
	public Translate(Env e)
	{
		env=e;
	}
	public List<Quad> quads=new LinkedList<Quad>();
	private int offset=0;
	public List<CompilationUnit> getUnits()
	{
		List<CompilationUnit> u=new LinkedList<CompilationUnit>();
		u.add(new CompilationUnit(quads,new Label("main")));
		return u;
	}
	public void emit(Quad q)
	{
		quads.add(q);
	}
	public void printCode()
	{
		for (Quad q:quads)
			System.out.println(q);
	}
	public void transProgram(Program prog) throws Exception
	{
		emit(new Enter());
		Object[] a=new Object[100];
		a[0]=0;
		transCom_Statement(prog.com_statement,a);
		emit(new Leave());
	}
	public void transDeclaration(Declaration d) throws Exception
	{
		transDeclarators(d.declarators);
	}
	public void transDeclarators(Declarators d) throws Exception
	{
		transDeclarator(d.declarator);
		Declarators_Temp dt=d.declarators_temp;
		while (dt!=null)
		{
			transDeclarator(dt.declarator);
			dt=dt.declarators_temp;
		}
	}
	public void transDeclarator(Declarator d) throws Exception
	{
		if (d.n1==0 && d.n2==0)
		{
			env.funcEnv.put(d.id,new VarEntry(Type.INT));
			//emit(new Malloc(((VarEntry) env.funcEnv.get(d.id)).temp,offset));
			//offset+=4;
		}
		else
		{
			env.funcEnv.put(d.id,new VarEntry(new Array(new Array(Type.INT,d.n2),d.n1)));
			//emit(new Malloc(((VarEntry) env.funcEnv.get(d.id)).temp,offset));
			//offset+=d.n1*d.n2*4;
			if (d.id.toString().equals("c"))
			{
				emit(new UnaryConst(((VarEntry) env.funcEnv.get(d.id)).temp,new Const(offset)));
				return ;
			}
			File fl=new File("ram_data.txt");
			BufferedReader inp=new BufferedReader(new FileReader(fl));
			String st=null;
			while ((st=inp.readLine())!=null)
			{
				if (st.length()>0 && st.charAt(0)=='@')
				{
					char ch=st.charAt(st.length()-1);
					if (d.id.toString().equals(ch+""))
					{
						String ad="";
						for (int i=1;i<st.length();++i)
							if (st.charAt(i)!='\t' && st.charAt(i)!=' ')
							{
								if (st.charAt(i)!='_') ad=ad+st.charAt(i);
							}
							else break;
						int num=0;
						for (int i=0;i<ad.length();++i)
							if (ad.charAt(i)>='0' && ad.charAt(i)<='9') num=num*16+(ad.charAt(i)-'0');
							else if (ad.charAt(i)>='a' && ad.charAt(i)<='z') num=num*16+(ad.charAt(i)-'a'+10);
							else num=num*16+(ad.charAt(i)-'A'+10);
						emit(new UnaryConst(((VarEntry) env.funcEnv.get(d.id)).temp,new Const(num)));
						if (ch=='b') offset=num+d.n2*d.n1*4;
					}
				}
			}
		}
	}
	public void transStatement(Statement s,Object[] obj) throws Exception
	{
		if (s instanceof Exp_Statement) transExp_Statement((Exp_Statement) s,obj);
		else if (s instanceof Com_Statement) transCom_Statement((Com_Statement) s,obj);
		else if (s instanceof For_Statement) transFor_Statement((For_Statement) s,obj);
		else transReturn_Statement((Return_Statement) s);
	}
	public void transExp_Statement(Exp_Statement s,Object[] obj) throws Exception
	{
		transExpression(s.expression,obj);
	}
	public void transCom_Statement(Com_Statement s,Object[] obj) throws Exception
	{
		env.beginScope();
		Declaration_Temp3 dt=s.declaration_temp3;
		while (dt!=null)
		{
			transDeclaration(dt.declaration);
			dt=dt.declaration_temp3;
		}
		Statement_Temp3 st=s.statement_temp3;
		while (st!=null)
		{
			transStatement(st.statement,obj);
			st=st.statement_temp3;
		}
		env.endScope();
	}
	public void transFor_Statement(For_Statement s,Object[] obj) throws Exception
	{
		env.funcEnv.put(s.expression1.id,new VarEntry(Type.INT));
		VarEntry var=(VarEntry) env.funcEnv.get(s.expression1.id);
		emit(new UnaryConst(var.temp,new Const(0)));
		Label here=new Label(),next=new Label();
		obj[0]=((Integer) obj[0])+1;
		/*int unroll=(s.expression2.num>2)?2:s.expression2.num;
		if (s.expression2.num%2==0) unroll=2;
		else unroll=1;*/
		int unroll=1;
		for (int i=0;i<s.expression2.num%unroll;++i)
		{
			obj[(Integer) obj[0]]=unroll+i;
			transCom_Statement(s.com_statement,obj);
		}
		Temp tt=new Temp();
		emit(new UnaryConst(tt,new Const(s.expression2.num/unroll)));
		emit(new LABEL(here));
		emit(new If(var.temp,tt,next));
		for (int i=0;i<unroll;++i)
		{
			obj[(Integer) obj[0]]=i;
			transCom_Statement(s.com_statement,obj);
		}
		emit(new BinaryConst(var.temp,var.temp,"+",new Const(unroll)));
		emit(new Goto(here));
		emit(new LABEL(next));
		obj[0]=((Integer) obj[0])-1;
	}
	public void transReturn_Statement(Return_Statement s) {}
	public void transExpression(Expression e,Object[] obj)
	{
		if (e instanceof Expression4) transExpression4((Expression4) e,obj);
		else if (e instanceof Expression5) transExpression5((Expression5) e);
		else transExpression6((Expression6) e,obj);
	}
	public void transExpression4(Expression4 e,Object[] obj)
	{
		Temp t1=transPostfix_Exp(e.p2),t2=transPostfix_Exp(e.p3),t3=new Temp(),t4=new Temp(),t5=new Temp(),t7=new Temp(),t8=new Temp();
		int fixa=((Integer) obj[1])*((Array) ((VarEntry) env.funcEnv.get(e.p2.id1)).type).elementType.getwidth()+((Integer) obj[2])*4;
		int fixb=((Integer) obj[2])*((Array) ((VarEntry) env.funcEnv.get(e.p3.id1)).type).elementType.getwidth()+((Integer) obj[3])*4;
		while (fixa>=262144)
		{
			emit(new BinaryConst(t1,t1,"+",new Const(262143)));
			fixa=fixa-262143;
		}
		emit(new Load(t3,t1,new Const(fixa)));
		while (fixb>=262144)
		{
			emit(new BinaryConst(t2,t2,"+",new Const(262143)));
			fixb=fixb-262143;
		}
		emit(new Load(t4,t2,new Const(fixb)));
		emit(new Binary(t5,t3,"*",t4));
		Temp t6=transPostfix_Exp(e.p1);
		int fixc=((Integer) obj[1])*((Array) ((VarEntry) env.funcEnv.get(e.p1.id1)).type).elementType.getwidth()+((Integer) obj[3])*4;
		while (fixc>=262144)
		{
			emit(new BinaryConst(t6,t6,"+",new Const(262143)));
			fixc=fixc-262143;
		}
		emit(new Load(t7,t6,new Const(fixc)));
		emit(new Binary(t8,t7,"+",t5));
		emit(new Store(t6,new Const(fixc),t8));
	}
	public void transExpression5(Expression5 e)
	{
		VarEntry var=(VarEntry) env.funcEnv.get(e.id);
		Temp t1=var.temp,t2=new Temp();
		emit(new UnaryConst(t2,new Const(e.num)));
		emit(new Store(t1,new Const(e.n1*((Array) var.type).elementType.getwidth()+e.n2*4),t2));
	}
	public void transExpression6(Expression6 e,Object[] obj)
	{
		Temp t1=transPostfix_Exp(e.postfix_exp),t2=new Temp();
		int fixc=((Integer) obj[1])*((Array) ((VarEntry) env.funcEnv.get(e.postfix_exp.id1)).type).elementType.getwidth()+((Integer) obj[2])*4;
		while (fixc>=262144)
		{
			emit(new BinaryConst(t1,t1,"+",new Const(262143)));
			fixc=fixc-262143;
		}
		emit(new Load(t2,t1,new Const(fixc)));
		emit(new Printf(t2));
	}
	public Temp transPostfix_Exp(Postfix_Exp e)
	{
		Temp t1=new Temp(),t2=new Temp(),t3=new Temp(),t4=new Temp();
		Type t=((VarEntry) env.funcEnv.get(e.id1)).type;
		Temp c=((VarEntry) env.funcEnv.get(e.id1)).temp;
		Temp i=((VarEntry) env.funcEnv.get(e.id2)).temp,j=((VarEntry) env.funcEnv.get(e.id3)).temp;
		emit(new BinaryConst(t2,i,"*",new Const(((Array) t).elementType.getwidth())));
		emit(new Binary(t1,c,"+",t2));
		emit(new BinaryConst(t3,j,"*",new Const(4)));
		emit(new Binary(t4,t1,"+",t3));
		return t4;
	}
	
}

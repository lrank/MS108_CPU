package assem;

import java.io.PrintStream;
import java.util.*;

import util.Constants;

public class Asm implements Constants {
	private static int getOperator(String op) throws Exception
	{
		if (op.contains("lw ")) return 0;
		if (op.contains("sw ")) return 1;
		if (op.contains("li ")) return 2;
		if (op.contains("addu ")) return 3;
		if (op.contains("addiu ")) return 4;
		if (op.contains("sll ")) return 5;
		if (op.contains("mul ")) return 9;
		if (op.contains("bge ")) return 7;
		if (op.contains("j ")) return 8;
		if (op.contains("move")) return 10;
		if (op.contains(":")) return -1;
		throw new Exception("unknown operation in: "+op);
	}
	private static void print(int op,int up,PrintStream out)
	{
		for (int j=up-1;j>=0;--j)
			if (((1<<j) & op)!=0) out.print("1");
		    else out.print("0");
		//out.print("("+op+")");
	}
	private static int[] getRegister(String k) throws Exception
	{
		int a[]=new int[4];
		a[0]=0;
		for (int i=0;i<k.length();++i)
			if (k.charAt(i)=='$')
			{
				a[0]++;
				String reg=k.substring(i+1,i+3);
				boolean flag=false;
				for (int j=0;j<regNames.length;++j)
					if (regNames[j].substring(0,2).equals(reg))
					{
						if (a[0]>=4) throw new Exception("get reg error: "+k);
						a[a[0]]=j;
						flag=true;
						break;
					}
				if (!flag) throw new Exception("$"+reg);
			}
		return a;
	}
	private static int toInt(String k)
	{
		char c[]=k.toCharArray();
		int flag=1,i=0;
		if (c[0]=='-')
		{
			flag=-1;
			i=1;
		}
		int d=0;
		for (;i<c.length;++i)
			d=d*10+c[i]-'0';
		return d*flag;
	}
	public static void print(String k,PrintStream out) throws Exception
	{
		int op=getOperator(k);
		if (op==-1) return;
		//out.print(k+"    ");
		int a[]=getRegister(k);
		if (op==9 && a[0]==3) op=6;
		if (op>=0) print(op,4,out);
		switch (op)
		{
		case 0:
		case 1:print(a[1],5,out);print(a[2],5,out);
			   int i,j;
			   for (i=0;i<k.length();++i)
				   if (k.charAt(i)==',') break;
			   for (j=i+1;j<k.length();++j)
				   if (k.charAt(j)=='(') break;
			   print(toInt(k.substring(i+1,j)),18,out);
			   break;
		case 2:print(a[1],5,out);
			   for (i=k.length()-1;i>=0;--i)
			       if (k.charAt(i)==',') break;
			   print(toInt(k.substring(i+1,k.length())),23,out);
			   break;
		case 3:
		case 6:print(a[1],5,out);print(a[2],5,out);print(a[3],5,out);print(0,13,out);break;
		case 4:
		case 9:
		case 5:print(a[1],5,out);print(a[2],5,out);
			   for (i=k.length()-1;i>=0;--i)
				   if (k.charAt(i)==',') break;
			   for (j=i+1;j<k.length();++j)
				   if (k.charAt(j)==' ') break;
			   print(toInt(k.substring(i+1,j)),18,out);
			   break;
		case 10:print(a[1],5,out);print(a[2],5,out);print(0,18,out);break;
		case 7:print(a[1],5,out);print(a[2],5,out);
			   for (i=k.length()-1;i>=0;--i)
				   if (k.charAt(i)==',') break;
			   print(map.get(k.substring(i+1,k.length()).intern()),18,out);
			   break;
		case 8:for (i=k.length()-1;i>=0;--i)
			       if (k.charAt(i)==' ') break;
			   print(map.get(k.substring(i+1,k.length()).intern()),28,out);
			   break;
		default:break;
		}
		out.println("");
	}
	private static Hashtable<String,Integer> map=new Hashtable<String,Integer>();
	public static void scanLabel(ArrayList<String> asm)
	{ /*
		int start=/*0x00400000-1;
		boolean flag=true;
		for (String s:asm)
		{
			//System.out.println(s);
			if (s.contains(":"))
			{
				int i=0;
				for (;i<s.length();++i)
					if (s.charAt(i)==':') break;
				if (!flag) map.put(s.substring(0,i).intern(),start+1);
				else
				{
					map.put(s.substring(0,i).intern(),start+2);
					flag=false;
				}
			}
			else start+=1;
		}
		start=0; */
		int start = 0;
		for (String s:asm) {
			if (s.contains(":"))
				map.put(s.substring(0,s.indexOf(':')).intern(),start);
			else
				++start;
		}
		start = 0;//for debug
	}

}

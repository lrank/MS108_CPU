package assem;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import util.Constants;
import regalloc.DefaultMap;
import regalloc.RegAlloc;
import temp.*;

public class Assem implements Constants {
	public String format;
	public Object[] params;
	public Assem(String format,Object... params)
	{
		this.format=format;
		this.params=params;
	}
	/*public String toString()
	{
		StringBuffer st=new StringBuffer();
		//System.out.println(format);
		for (int i=0,j=0;i<format.length();++i)
		{
			char ch=format.charAt(i);
			if ((ch=='%' || ch=='@') && (!(format.length()>=6) || !format.substring(0,6).equals(".ASCII")))
			{
				if (params[j] instanceof Temp)
				{
					String s=((Temp) params[j++]).reg*wordSize+"($gp)";
					//System.out.println(format+"  "+s+"  before");
					st.append(s);
				}
				else
				{
					String s=""+params[j++];
					//System.out.println(format+"  "+s+"     before");
					st.append(s);
				}
			}
			else st=st.append(ch);
		}
		//System.out.println(st+"  after");
		return new String(st);
	}*/
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		for (int i=0,j=0;i<format.length();i++)
		{
			char c=format.charAt(i);
			if (c=='@' || c=='%')
			{
				if (c=='@' && params[j] instanceof Temp) set.add((Temp) params[j]);
				++j;
			}
		}
		return set;
	}
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		for (int i=0,j=0;i<format.length();i++)
		{
			char c=format.charAt(i);
			if (c=='@' || c=='%') {
				if (c=='%' && params[j] instanceof Temp) set.add((Temp) params[j]);
				++j;
			}
		}
		return set;
	}
	public String special()
	{
		if (params.length==0) return format;
		return "";
	}
	public String toString()
	{
		String st=special();
		if (st!="") return st;
		return toString(DefaultMap.getSingleton());
	}
	public String toString(RegAlloc allocator)
	{
		return isSpill() ? doSpill(allocator) : doNormal(allocator,false);
	}
	private boolean isSpill()
	{
		for (Object param : params)
		{
			//System.out.println(format);
			if (param instanceof Temp && ((Temp) param).getLiveInterval().spilled) return true;
		}
		return false;
	}
	private String doNormal(RegAlloc allocator,boolean hasspill)
	{
		StringBuffer buf=new StringBuffer();
		if (format.charAt(0)=='!') format=format.substring(1);
		else if (format.charAt(0)=='^')
		{
			format=format.substring(1);
			if (!hasspill) return "";
		}
		else buf.append('\t');
		for (int i=0,j=0;i<format.length();i++)
		{
			char c=format.charAt(i);
			if (c=='@' || c=='%')
			{
				if (params[j] instanceof Temp) buf.append(allocator.map((Temp) params[j]));
				else buf.append(params[j]);
				++j;
			}
			else buf.append(c);
		}
		return buf.toString();
	}
	private String doSpill(RegAlloc allocator)
	{
		StringBuffer before=new StringBuffer();
		StringBuffer after=new StringBuffer();
		TreeSet<Integer> freeRegs=new TreeSet<Integer>();
		freeRegs.add(26);	// $k0
		freeRegs.add(27);	// $k1
		for (Temp t : use())
			if (t.getLiveInterval().register==spillReg)
			{
				int r=freeRegs.pollFirst();
				t.getLiveInterval().register=r;
				/*// lw $r,wordSize*t.home.depth($gp)
				// lw $r,wordSize*t.index($r)
				before.append("\t\t\tlw $" + regNames[r] + "," + wordSize * t.home.depth + "($gp)\t# load display for spilling\n");
				before.append("\t\t\tlw $" + regNames[r] + "," + wordSize * t.index + "($" + regNames[r] + ")\t# load for spilling\n");*/
				// lw $r, -(t.reg+1)*wordSize($sp)
				before.append("\t\t\tlw $"+regNames[r]+",-"+t.reg*wordSize+"($sp)\n");
				/*if (format.contains("mul") || format.contains("div") || format.contains("add") || format.contains("sub") || format.contains("b"))
					before.append("\t\t\tlw $"+regNames[r]+",0($"+regNames[r]+")\n");*/
			}
		for (Temp t : def())
			if (t.getLiveInterval().register==spillReg)
			{
				//int r=freeRegs.pollFirst();
				int r=26;
				t.getLiveInterval().register=r;
			}
		// possibly a function call
		String normal=doNormal(allocator,true);
		for (Temp t : def())
			if (t.getLiveInterval().spilled && t.getLiveInterval().register!=spillReg)
			{
				int r=t.getLiveInterval().register;
				t.getLiveInterval().register=spillReg;
				int d=26+27-r;	// another $k?
				/* lw $d,wordSize*t.home.depth($gp)
				// sw $r,wordSize*t.index($d)
				after.append("\n\t\t\tlw $" + regNames[d] + "," + wordSize * t.home.depth + "($gp)\t# load display for spilling");
				after.append("\n\t\t\tsw $" + regNames[r] + "," + wordSize * t.index + "($" + regNames[d] + ")\t# write back for spilling");*/
				// sw $r, -(t.reg+1)*wordSize($sp)
				after.append("\n\t\t\tsw $"+regNames[r]+",-"+t.reg*wordSize+"($sp)\n");
			}
		for (Temp t : use())
			if (t.getLiveInterval().spilled && t.getLiveInterval().register!=spillReg)
				t.getLiveInterval().register=spillReg;
		return before + /*((flag)?normal+"\n":"") +*/ normal + after;
	}
	
}

package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import analysis.Expression;
import assem.Assem;
import assem.AssemList;
import temp.*;

public class BinaryConst extends Quad {
	public Temp rd,rs;
	public Const rt;
	public String op;
	public BinaryConst(Temp rd,Temp rs,String op,Const rt)
	{
		this.rd=rd;
		this.rs=rs;
		this.rt=rt;
		this.op=op;
	}
	@Override
	public String toString()
	{
		return rd + " = " + rs + op + rt;
	}
	@Override
    public AssemList gen() throws Exception
    {
    	String st=op.equals("+")?"addiu":"mul";
    	/*return AssemList.L(new Assem("lw $t1,%",rs),
    		   AssemList.L(new Assem(st+"$t1,$t1,%",rt.value),
    		   AssemList.L(new Assem("sw $t1,%",rd))));*/
    	return AssemList.L(new Assem("% @,%,% #I",st,rd,rs,rt.num));
    }
    @Override
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(rd);
		return set;
	}
	@Override
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(rs);
		return set;
	}
	public String toExp() {
		return "(" + rs + ")" + op + "(" + rt + ")";
	}
	@Override
	public Set<Expression> genExp() throws Exception
	{
		Set<Expression> x=new LinkedHashSet<Expression>();
		/*if (!rs.equals(rd))*/ x.add(new Expression(toExp(),rd));
		//else throw new Exception(toString());
		return x;
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return killExpBy(U,rd);
	}
	@Override
	public void replaceUseOf(Temp old,Temp t)
	{
		if (rs.equals(old)) rs=t;
	}
	
}

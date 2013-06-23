package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import assem.Assem;
import assem.AssemList;
import temp.*;
import analysis.Expression;

public class UnaryConst extends Quad {
	public Temp rd;
	public Const cst;
	public UnaryConst(Temp rd,Const cst)
	{
		this.rd=rd;
		this.cst=cst;
	}
	@Override
	public String toString()
	{
		return rd + "=" + " #" + cst;
	}
	@Override
    public AssemList gen()
    {
    	return AssemList.L(new Assem("li @,%",rd,cst.num));
    	//return AssemList.L(new Assem("li $t1,%",cst.value),AssemList.L(new Assem("sw $t1,%",rd)));
    }
    @Override
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(rd);
		return set;
	}
    public String toExp()
	{
		return "(" + cst + ")";
	}
    @Override
	public Set<Expression> genExp()
	{
    	Set<Expression> x=new LinkedHashSet<Expression>();
		//x.add(new Expression(toExp(),rd));
		return x;
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return killExpBy(U,rd);
	}
	
}

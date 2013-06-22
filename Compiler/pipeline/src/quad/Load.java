package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import temp.*;
import util.*;
import assem.*;
import analysis.Expression;

public class Load extends Quad implements Constants {  /*  x:=y[z]  */
	public Temp x,y;
	public Const z;
	public Load(Temp x,Temp y,Const z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	@Override
	public String toString()
	{
		return x + " = " + y + "[" + z + "]";
	}
	@Override
	public AssemList gen()
	{
		return AssemList.L(new Assem("lw @,%(%)",x,z.num,y));
		/*return AssemList.L(new Assem("lw $t1,%",y),
			   AssemList.L(new Assem("lw $t2,%($t1)",may.value),
			   AssemList.L(new Assem("sw $t2,%",x))));*/
	}
	@Override
	public Set<Temp> def()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(x);
		return set;
	}
	@Override
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(y);
		return set;
	}
	public String toExp()
	{
		return "(" + y + ")[" + z + "]";
	}
	@Override
	public Set<Expression> genExp() throws Exception
	{
		Set<Expression> x=new LinkedHashSet<Expression>();
		if (!y.equals(this.x)) x.add(new Expression(toExp(),this.x));
		else throw new Exception(toString());
		return x;
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return killExpBy(U,x);
	}
	@Override
	public void replaceUseOf(Temp old,Temp t)
	{
		if (y.equals(old)) y=t;
		if (x.equals(old)) x=t;
	}
}

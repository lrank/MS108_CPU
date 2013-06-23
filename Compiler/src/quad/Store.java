package quad;

import java.util.LinkedHashSet;
import java.util.Set;

import temp.Const;
import temp.Temp;
import assem.*;
import analysis.Expression;

public class Store extends Quad {    /*  x[y]:=z  */
	public Temp x,z;
	public Const y;
	public Store(Temp x,Const y,Temp z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	@Override
	public String toString()
	{
		return x + "[" + y + "]= " + z;
	}
	@Override
	public AssemList gen()
	{
		return AssemList.L(new Assem("sw %,%(%)",z,y.num,x));
		/*return AssemList.L(new Assem("lw $t1,%",x),
			   AssemList.L(new Assem("lw $t2,%",z),
			   AssemList.L(new Assem("sw $t2,%($t1)",y.value))));*/
	}
	@Override
	public Set<Temp> use()
	{
		Set<Temp> set=new LinkedHashSet<Temp>();
		set.add(x);
		set.add(z);
		return set;
	}
	@Override
	public Set<Expression> genExp()
	{
		return new LinkedHashSet<Expression>();
	}
	@Override
	public Set<Expression> killExp(Set<Expression> U)
	{
		return killExpBy(U,x);
	}
	@Override
	public void replaceUseOf(Temp old,Temp t)
	{
		if (x.equals(old)) x=t;
		if (z.equals(old)) z=t;
	}

}

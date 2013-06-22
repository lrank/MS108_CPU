package temp;

public class Const extends Address {
	public int num;
	public Const(int n)
	{
		num=n;
	}
	@Override
	public String toString()
	{
		return ""+num;
	}
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Const)) return false;
		return ((Const) o).num==this.num;
	}

}

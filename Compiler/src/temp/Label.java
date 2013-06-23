package temp;

public class Label extends Address {
	public static int count=0;
	public String label;
	public Label()
	{
		label="L"+(count++);
	}
	public Label(String s)
	{
		label=s;
	}
	@Override
	public String toString()
	{
		return label;
	}
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Label)) return false;
		return ((Label) o).label.equals(this.label);
	}
	
}

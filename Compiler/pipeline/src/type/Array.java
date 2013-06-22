package type;

public class Array extends Type {
	public int capacity=0;
	public Type elementType=null;
	public Array(Type e,int c)
	{
		elementType=e;
		capacity=c;
	}
	public int getwidth()
	{
		return elementType.getwidth()*capacity;
	}
	
}

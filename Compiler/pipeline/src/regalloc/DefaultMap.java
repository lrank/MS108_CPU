package regalloc;

import temp.Temp;

public class DefaultMap implements RegAlloc
{
	@Override
	public String map(Temp t)
	{
		return t.toString();
	}	
	private static DefaultMap singleton=null;
	public static DefaultMap getSingleton()
	{
		if (singleton==null) singleton=new DefaultMap();
		return singleton;
	}
	
}

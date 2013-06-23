package temp;

import analysis.LiveInterval;

public class Temp extends Address {
	public static int maxTemp=0;
	public int reg;
	public Temp()
	{
		reg=maxTemp++;
	}
	@Override
	public String toString()
	{
		return "t"+reg;
	}
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Temp)) return false;
		return ((Temp) o).reg==this.reg;
	}
	@Override
	public int hashCode()
    {
		return reg;
	}
	public LiveInterval interval=null;
	public void expandInterval(int qcount)
	{
		if (interval==null) interval=new LiveInterval(this,qcount);
		interval.insert(qcount);
	}
	public LiveInterval getLiveInterval()
	{
		return interval;
	}
	public void clearLiveInterval()
	{
		interval=null;
	}
	
}

package optimize;

import java.util.LinkedList;

public class LastOptimize {
	public static void optimize(LinkedList<String> lines)
    {
    	for (int i=0;i<lines.size()-1;++i)
    		if (lines.get(i).length()>2 && lines.get(i+1).length()>2 && lines.get(i).substring(0,2).equals("sw") && lines.get(i+1).substring(0,2).equals("lw") && lines.get(i).substring(2).equals(lines.get(i+1).substring(2)))
    		{
    			lines.remove(i+1);
    			i--;
    		}
    }
	
}

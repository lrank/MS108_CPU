package regalloc;

import temp.Temp;
import util.Constants;

public interface RegAlloc extends Constants
{
	String map(Temp temp);
	
}

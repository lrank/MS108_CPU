package translate;

import type.*;
import temp.*;

public class VarEntry {
	public Type type=null;
	public Temp temp;
	public VarEntry(Type ty)
	{
		type=ty;
		temp=new Temp();
	}
	
}

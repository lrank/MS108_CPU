package optimize;

import assem.Assem;
import temp.Const;
import util.Constants;

public class StrengthReduction implements Constants
{
	public static Assem process(Assem asm)
	{
		if (asm.format.equals("% @,%,% #I"))   // BinopI
		{
			if (asm.params[0].equals("mul"))
			{
				assert(asm.params[3] instanceof Const);
				int n=(Integer) asm.params[3];
				if (n==1) return new Assem("move @,% #optSR",asm.params[1],asm.params[2]);
				int m=0;
				while (n>1)
				{
					if (n%2==0)
					{
						n/=2;
						++m;
					}
					else break;
				}
				if (n==1)  // n is 2^m
				{
					return new Assem("sll @,%,% #optSR",asm.params[1],asm.params[2],m);
				}
			}
		}
		return asm;
	}

}

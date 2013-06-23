package translate;

import symbol_table.*;
import type.*;

public final class Env {
    public Table typeEnv=null;
    public Table funcEnv=null;
    public Env()
    {
    	inittEnv();
    	initfEnv();
    }
    private static Symbol sym(String s) {
		return Symbol.symbol(s);
	}
	private void inittEnv()
	{
		typeEnv=new Table();
		typeEnv.put(sym("int"), Type.INT);
	}
	private void initfEnv()
	{
		funcEnv=new Table();
	}
	public void beginScope()
	{
		typeEnv.beginScope();
		funcEnv.beginScope();
	}
	public void endScope()
	{
		typeEnv.endScope();
		funcEnv.endScope();
	}
    
}

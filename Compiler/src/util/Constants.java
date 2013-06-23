package util;

public interface Constants {
	static final String[] opStr = {
		"+","-","*","/","=","==","!=","<",">","<=",">=","<<",">>","|","^","&","%","||","&&","sizeof","~","!"
	};

	static final boolean[] isInvertable = {
		true, false, true, false, true, true, true, true, true, true
	};

	static final int[] opInverted = {
		//Additive_Op.OpType.PLUS, -1, OpExp.TIMES, -1, OpExp.EQ, OpExp.NEQ, OpExp.GT, OpExp.GE, OpExp.LT, OpExp.LE
	};

	static final String[] ifFalseCmp = {
		"", "", "", "", "","ne", "eq", "ge", "le", "gt", "lt"
	};

	static final String[] assemStr = {
		"addu", "subu", "mul", "divu", "seq", "sne", "slt", "sle", "sgt", "sge"
	};

	static final String[] assemStrI = {
		"addiu", "subu", "mul", "divu", "seq", "sne", "slt", "sle", "sgt", "sge"
	};
	
	public final static int PLUS = 0, MINUS = 1, TIMES = 2, DIVIDE = 3, EQ = 4,
	NEQ = 5, LT = 6, LE = 7, GT = 8, GE = 9;

	static final int wordSize = 4;

	static int savedRegistersOffset = 2;	// old $ra, old display
	static int baseOfSavedRegisters = 8;	// start from $t0
	static int numOfSavedRegisters = 18;	// number of saved registers

	static final int paramRegBase = 4;	// start from $a0
	static final int paramRegNum  = /*4*/3;	// $a0-$a3

	static final String[] regNames = {
		"zero", "at",
		"v0", "v1",
		"a0", "a1", "a2", "a3",
		"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
		"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
		"t8", "t9",
		"k0", "k1",
		"gp", "sp", "fp", "ra",
		"spill"	// special register
	};

	static final int spillReg = regNames.length - 1;

	static final int rewriteLimit = 100;

	static final String[] libFunctions = {
		"print", "printi", "flush", "getchar", "ord", "chr",
		"size", "substring", "concat", "not", "exit"
	};

	static final int minInt = -32768;
	static final int maxInt = 32767;

	static final String[] peepholeRegNames = {
		"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
		"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
		"t8", "t9",
	};
	
}

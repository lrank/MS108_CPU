package symbol_table;

import symbol_table.Symbol;

class Binder {
	Object value;
	Symbol prevtop;
	Binder tail;
	int level;

	Binder(Object v, Symbol p, Binder t,int l) {
		value = v;
		prevtop = p;
		tail = t;
		level=l;
	}
}

/**
 * The Table class is similar to java.util.Dictionary, except that each key must
 * be a Symbol and there is a scope mechanism.
 */

public class Table {

	private java.util.Dictionary<Symbol, Binder> dict = new java.util.Hashtable<Symbol, Binder>();
	private Symbol top = null;
	private Binder marks = null;
	public int level=0;

	/**
	 * Gets the object associated with the specified symbol in the Table.
	 */
	public Object get(Symbol key) {
		Binder e = dict.get(key);
		if (e == null)
			return null;
		else
			return e.value;
	}
	
	public Object findlast(Symbol key)
	{
		if (dict.get(key)==null) return null;
		Symbol t=top;
		while (t!=null)
		{
			if (t.toString().equals(key.toString())) return dict.get(key).value;
			Binder e=dict.get(t);
			t=e.prevtop;
		}
		return null;
	}
	public void remove(Symbol key)
	{
		Binder e = dict.get(key);
		if (e.tail != null)
			dict.put(key, e.tail);
		else
			dict.remove(key);
	}

	/**
	 * Puts the specified value into the Table, bound to the specified Symbol.
	 */
	public void put(Symbol key, Object value) {
		dict.put(key, new Binder(value, top, dict.get(key),level));
		top = key;
	}
	
	/**
	 * Remembers the current state of the Table.
	 */
	public void beginScope() {
		marks = new Binder(null, top, marks,level);
		level++;
		top = null;
	}

	/**
	 * Restores the table to what it was at the most recent beginScope that has
	 * not already been ended.
	 */
	public void endScope() {
		while (top != null) {
			Binder e = dict.get(top);
			if (e.tail != null)
				dict.put(top, e.tail);
			else
				dict.remove(top);
			top = e.prevtop;
		}
		level--;
		top = marks.prevtop;
		marks = marks.tail;
	}

	/**
	 * Returns an enumeration of the Table's symbols.
	 */
	public java.util.Enumeration<Symbol> keys() {
		return dict.keys();
	}
	
	public Table() {}
}

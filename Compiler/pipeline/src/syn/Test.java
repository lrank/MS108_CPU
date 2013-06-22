package syn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import com.google.gson.Gson;

import ast.*;

public class Test {
	public static String pathOf(String filename) {
		return Test.class.getResource(filename).getPath();
	}

	private static int test(String filename) throws Exception {
		InputStream inp = new FileInputStream(filename);
		parser par = new parser(inp);
		java_cup.runtime.Symbol parseTree = null;
		try {
			parseTree = par.parse();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			inp.close();
		}
		Gson gson = new Gson();
		System.out.println(gson.toJson(parseTree.value));
		return 0;
	}

	public static void main(String argv[]) throws Exception {
		//semantic(pathOf("example5.c"));
		try {
			//File fl=new File("test.c");
		    test("D:\\e\\计算机系统组成\\pipeline\\src\\syn\\example1.c");
			//semantic(fl.getAbsolutePath());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Error(e.toString());
		}
		if (argv.length>0)
		{
			try {
				System.exit(test(argv[0]));
			}
			catch (Exception e)
			{
				//e.printStackTrace(System.out);
				System.exit(1);
			}
		}
	}
	
}

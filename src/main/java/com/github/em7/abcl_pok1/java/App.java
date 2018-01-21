package com.github.em7.abcl_pok1.java;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.armedbear.lisp.Interpreter;
import org.armedbear.lisp.Package;
import org.armedbear.lisp.Packages;
import org.armedbear.lisp.Symbol;

/**
 * Hello world!
 *
 */
public class App
{
	public static class MyAbcl implements Runnable {

		public void run() {
			System.out.println( "Running ABCL, starting SWANK server" );

	        Path depsDir = FileSystems.getDefault().getPath("deps");
	        String swankPathStr = depsDir.resolve("swank-all.jar").toAbsolutePath().toString().replace("\\", "\\\\");

	        Interpreter interpreter = Interpreter.createInstance();
	        interpreter.eval("(setf CL:*LOAD-VERBOSE* t) ;verbose loading for asdf systems");
	        interpreter.eval("(require :abcl-contrib)");

	        //interpreter.eval("(push (pathname \"jar:file:c:/Users/madera/workspace-abcl-pok/java/deps/swank-all.jar!/swank/\") asdf:*central-registry*)");
	        interpreter.eval("(push (pathname \"jar:file:" + swankPathStr + "!/swank/\") asdf:*central-registry*)");

	        System.out.println(swankPathStr);

	        //this doesn't work
	        //interpreter.eval("(require :asdf-jar)");
	        //interpreter.eval("(asdf-jar:add-to-asdf \"" + swankPathStr + "\")");
	        interpreter.eval("(asdf:load-system :swank)");
	        interpreter.eval("(swank:create-server :style :spawn :dont-close t)");

	        // Variant 2 - init in resource script
	        interpreter.eval("(load \"init.lisp\")"); //TODO can't find in src/main/resources
	        Package clUser = Packages.findPackage("CL-USER");
	        Symbol helloFn = clUser.findAccessibleSymbol("hello");
	        helloFn.getSymbolFunction().execute();


	        while (true) {
	        	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}

	}

    public static void main( String[] args )
    {
        Path path = FileSystems.getDefault().getPath(".");
        System.out.println(path.toAbsolutePath().toString());

        Thread abclThread = new Thread(new MyAbcl());
        abclThread.start();
        try {
			abclThread.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

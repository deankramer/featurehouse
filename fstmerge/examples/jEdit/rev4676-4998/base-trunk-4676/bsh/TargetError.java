


package bsh;

import java.lang.reflect.InvocationTargetException;

import java.io.PrintStream;



public class TargetError extends EvalError 
{
	Throwable target;
	boolean inNativeCode;

	public TargetError(
		String msg, Throwable t, SimpleNode node, boolean inNativeCode )
	{
		super(msg, node);
		target = t;
		this.inNativeCode = inNativeCode;
	}

	public TargetError(Throwable t, SimpleNode node )
	{
		this("TargetError", t, node, false);
	}

	
	public TargetError( String s, Throwable t )
	{
		this(s, t, null, false);
	}

	public Throwable getTarget()
	{
		
		if(target instanceof InvocationTargetException)
			return((InvocationTargetException)target).getTargetException();
		else
			return target;
	}

	public String toString() {

		return super.toString() 
			+ "\nTarget exception: " + 
			printTargetError( target );
	}

    public void printStackTrace() { 
		printStackTrace( false, System.err );
	}

    public void printStackTrace( PrintStream out ) { 
		printStackTrace( false, out );
	}

    public void printStackTrace( boolean debug, PrintStream out ) {
		if ( debug ) {
			super.printStackTrace( out );
			System.out.println("--- Target Stack Trace ---");
		}
		target.printStackTrace( out );
	}

	
	public String printTargetError( Throwable t ) 
	{
		String s = target.toString();

		if ( Capabilities.canGenerateInterfaces() )
			s += "\n" + xPrintTargetError( t );

		return s;
	}

	
	public String xPrintTargetError( Throwable t ) 
	{
		String getTarget =
			"import java.lang.reflect.UndeclaredThrowableException;"+
			"result=\"\";"+
			"while ( target instanceof UndeclaredThrowableException ) {"+
			"	target=target.getUndeclaredThrowable(); " +
			"	result+=\"Nested: \"+target.toString();" +
			"}"+
			"return result;";
		Interpreter i = new Interpreter();
		try {
			i.set("target", t);
			return (String)i.eval( getTarget );
		} catch ( EvalError e ) {
			throw new InterpreterError("xprintarget: "+e.toString() );
		}
	}

	
	public boolean inNativeCode() { 
		return inNativeCode; 
	}
}

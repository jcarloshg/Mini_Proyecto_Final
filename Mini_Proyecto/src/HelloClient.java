import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

import org.omg.CORBA.*;
import org.omg.CORBA.ORBPackage.InvalidName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

public class HelloClient 
{
	static Hello helloImpl;
	
  public HelloClient() {
	  try {
		  	final String[] args = new String[4];
			args[0] = "-ORBInitialPort";
			args[1] = "1050";
			args[2] = "-ORBInitialHost";
			args[3] = "localhost";
			final ORB orb = ORB.init(args, null);

			// get the root naming context
			final org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			final NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// resolve the Object Reference in Naming
			final String name = "Hello";
			helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

			System.out.println("Obtained a handle on server object: " + helloImpl);

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String mandarOperacionMat(final String operString) {
		return operString + " = " + helloImpl.operacionMat(operString);
	}

	public String sayHello() {
		return helloImpl.sayHello("Hola Servidor");
		
	}
	
	
	public void mandarMensaje(final String mensaje) {
		helloImpl.mandarMensaje(mensaje);
	}

	public String checharUltimo() {
		final String msj = helloImpl.checarUltimoMsj();
		// Revisar mensaje
		if (msj.charAt(0) != '{') {
			return helloImpl.checarUltimoMsj();
		}
		// parse json.
		StringTokenizer tokens = new StringTokenizer(msj, ",");
		String bracket = tokens.nextToken();
		String base64 = tokens.nextToken();
		String archivo = tokens.nextToken();
		String formato = tokens.nextToken();
		// decode
		String path = "C:\\eclipse-workspace\\Descargas";
		String outputFilePath = path + File.separatorChar + archivo;
		FileOutputStream fos;
		byte[] decoded;

		try {
	    	decoded = Base64.getDecoder().decode(base64);
	    	// System.out.println(new String(decoded));
	    	fos = new FileOutputStream(outputFilePath);
	    	fos.write(decoded);
	    	fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return archivo;
	}

	public void mandarArchivo(final String mensaje) {
		helloImpl.mandarArchivo(mensaje);
	}

	public static void main(final String args[])
  {  }
  
  }

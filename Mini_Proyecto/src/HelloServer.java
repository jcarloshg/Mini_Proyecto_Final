import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

class HelloImpl extends HelloPOA 
{
  private ORB orb;
  String mensajeRecivido = " ";
  StringBuilder archivoRecibido = new StringBuilder();
  boolean isMensaje = true;

  public void setORB(final ORB orb_val) {
    orb = orb_val;
  }
  
  public String sayHello(final String mensaje) {
	    System.out.println("Saludo recivido: " + mensaje);
	    Mensajeria.mostrarText("Saludo recibido: \n" + mensaje);
	    return "\nHello world !!\n";
	  }

  public void shutdown() {
	    orb.shutdown(false);
	  }

  public String operacionMat(final String operacion) {
    System.out.println("servidor realizando operación");
    Mensajeria.mostrarText("servidor realizando operación");
    final StringTokenizer token = new StringTokenizer(operacion);
    String resultado = " ";

    final int operando1 = Integer.parseInt(token.nextToken());
    final String operador = token.nextToken();
    final int operando2 = Integer.parseInt(token.nextToken());

    switch (operador) {
      case "+":
        resultado = String.valueOf(operando1 + operando2);
        break;
      case "-":
        resultado = String.valueOf(operando1 - operando2);
        break;
      case "*":
        resultado = String.valueOf(operando1 * operando2);
        break;
      case "/":
        resultado = String.valueOf(operando1 / operando2);
        break;
      case "^":
        resultado = String.valueOf((int) Math.pow(operando1, operando2));
        break;
      default:
        break;
    }

    System.out.println("resultado: " + resultado);
    Mensajeria.mostrarText(operacion + " = " + resultado);
    return resultado;
  }

  public void mandarMensaje(final String mensaje) {
    // TODO Auto-generated method stub
    isMensaje = true;
    mensajeRecivido = mensaje;
    System.out.println("Mensaje recibido: \n" + mensajeRecivido);
    Mensajeria.mostrarText("Mensaje recibido: \n" + mensajeRecivido);
  }

  public String checarUltimoMsj() {
    // TODO Auto-generated method stub
    if (isMensaje)
      return mensajeRecivido;
    else 
      return archivoRecibido.toString();
  }

  @Override
  public void mandarArchivo(final String archivo) {
    // encode
    final File file = new File(archivo);
    byte[] encoded;
    String resB64 = "";
    try {
      encoded = Base64.getEncoder().encode(Files.readAllBytes(file.toPath()));
			resB64 = new String(encoded, StandardCharsets.US_ASCII);
    } catch(final IOException e) {
      e.printStackTrace();
    }

    // parse to json
    String ext = "";
    final String name = file.getName();

    final int lastIndex = name.lastIndexOf(".");
    if(lastIndex == -1) ext = "";
    else ext = name.substring(lastIndex);
    
    final StringBuilder json = new StringBuilder();
    json.append("{,");
    final String format = String.format("%s,", resB64);
    json.append(format);
    final String format2 = String.format("%s,", name);
    json.append(format2);
    final String format3 = String.format("%s,", ext);
    json.append(format3);
    json.append("}");

    System.out.println("Mensaje recibido: \n" + json);
    isMensaje = false;
    archivoRecibido = json;
    Mensajeria.mostrarText(name);
  }
}

public class HelloServer implements Runnable {
  Thread serve;

  public HelloServer() {
    // TODO Auto-generated constructor stub
    serve = new Thread(this);
    serve.start();
  }

  public static void main(final String args[]) {
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

    try {
      // create and initialize the ORB
      final String[] args = new String[4];
      args[0] = "-ORBInitialPort";
      args[1] = "1050";
      args[2] = "-ORBInitialHost";
      args[3] = "localhost";
      final ORB orb = ORB.init(args, null);

      // get reference to rootpoa & activate the POAManager
      final POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // create servant and register it with the ORB
      final HelloImpl helloImpl = new HelloImpl();
      helloImpl.setORB(orb);

      // get object reference from the servant
      final org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
      final Hello href = HelloHelper.narrow(ref);

      // get the root naming context
      // NameService invokes the name service
      final org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      // Use NamingContextExt which is part of the Interoperable
      // Naming Service (INS) specification.
      final NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // bind the Object Reference in Naming
      final String name = "Hello";
      final NameComponent path[] = ncRef.to_name(name);
      ncRef.rebind(path, href);

      System.out.println("HelloServer ready and waiting ...");

      // wait for invocations from clients
      orb.run();
    } catch (final Exception e) 
	    {
	      System.err.println("ERROR: " + e);
	      e.printStackTrace(System.out);
	    }

	    System.out.println("HelloServer Exiting ...");
}
}

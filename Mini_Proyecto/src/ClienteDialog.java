import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ClienteDialog extends JDialog  implements Runnable{
	private final JTextField textMsj;
	public final JTextPane textPane;
	static String auxArch = "";

	Thread cliente;
	HelloClient client;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		try {
			final ClienteDialog dialog = new ClienteDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ClienteDialog() {

		client = new HelloClient();
		setTitle("Cliente Archivos");
		// client = new HelloClient();

		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(null);
		{
			final JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 414, 283);
			getContentPane().add(scrollPane);

			textPane = new JTextPane();
			scrollPane.setViewportView(textPane);
		}
		textMsj = new JTextField();
		textMsj.setBounds(10, 305, 200, 45);
		getContentPane().add(textMsj);
		textMsj.setColumns(10);
		{
			final JButton btnEnviarMsj = new JButton("Mensaje");
			btnEnviarMsj.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					String saludo = textMsj.getText();
					System.out.println(saludo);
					if (saludo.length() == 7) {
						client.sayHello();
					} else {
						client.mandarMensaje(saludo);
					}
				}
			});
			btnEnviarMsj.setBounds(215, 305, 100, 45);
			getContentPane().add(btnEnviarMsj);

			final JButton btnEnviarArch = new JButton("Archivo");
			btnEnviarArch.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					//Creamos el objeto JFileChooser
					JFileChooser fc=new JFileChooser();
					//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
					int seleccion=fc.showOpenDialog(getContentPane());
					//Si el usuario, pincha en aceptar
					String path = "";
					if(seleccion==JFileChooser.APPROVE_OPTION){
						//Seleccionamos el fichero
						File fichero=fc.getSelectedFile();
						//Ecribe la ruta del fichero
						path = fichero.getAbsolutePath();
						System.out.println(path);
					}

					client.mandarArchivo(path);
				}
			});
			btnEnviarArch.setActionCommand("Cancel");
			btnEnviarArch.setBounds(325, 305, 100, 45);
			getContentPane().add(btnEnviarArch);
		}

		cliente = new Thread(this);
		cliente.start();
	}

	public void mensaje(final String mandarOperacionMat) {
		String aux = textPane.getText();
		aux += "\n" + mandarOperacionMat;
		textPane.setText(" ");
		textPane.setText(aux);
	}

	public void run() {
		while (true) {
			String aux = "";
			final String auxMsj = client.checharUltimo();
			if (aux != auxMsj) {
				aux = auxMsj;
				mensaje(aux);
			}
			try {
				Thread.sleep(3000);
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

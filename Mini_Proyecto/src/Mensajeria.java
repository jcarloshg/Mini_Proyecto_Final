

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class Mensajeria{

	private JFrame frmWhatsappPirata;
	JScrollPane scrollPane;
	static JTextPane textPane;
	
	private HelloServer server;
	
	List<ClienteDialog> listaClientes;
	private JLabel lblNewLabel;
	private JButton crearCli_Ope;
	private JButton crearCli_Arch;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mensajeria window = new Mensajeria();
					window.frmWhatsappPirata.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mensajeria() {
		server = new HelloServer();
		listaClientes= new ArrayList<ClienteDialog>();
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWhatsappPirata = new JFrame();
		frmWhatsappPirata.setTitle("WhatsApp CORBA");
		frmWhatsappPirata.setBounds(100, 100, 400, 418);
		frmWhatsappPirata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWhatsappPirata.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 364, 269);
		frmWhatsappPirata.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		crearCli_Arch = new JButton("Crear Cliente Archivo");
		crearCli_Arch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClienteDialog clienteD = new ClienteDialog();
				clienteD.setVisible(true);
				listaClientes.add(clienteD);
			}
		});
		crearCli_Arch.setBounds(212, 323, 162, 45);
		frmWhatsappPirata.getContentPane().add(crearCli_Arch);
		
		lblNewLabel = new JLabel("Servidor CORBA");
		lblNewLabel.setBounds(10, 11, 138, 14);
		frmWhatsappPirata.getContentPane().add(lblNewLabel);
		
		crearCli_Ope = new JButton("Crear Cliente Operaaci\u00F3n");
		crearCli_Ope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtroDialogo otro = new OtroDialogo();
				otro.setVisible(true);
			}
		});
		crearCli_Ope.setBounds(10, 325, 162, 45);
		frmWhatsappPirata.getContentPane().add(crearCli_Ope);
	}

	public static void mostrarText(String input) {
		String aux = textPane.getText();
		aux += "\n" + input;
		textPane.setText("");
		textPane.setText(aux);
	}
}

// C:\\Users\\Jose Carlos\\Desktop\\beatles.jpg
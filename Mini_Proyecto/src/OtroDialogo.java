import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OtroDialogo extends JDialog {
	private JTextField textMsj;
	private JTextPane textPane;
	
	HelloClient client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OtroDialogo dialog = new OtroDialogo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OtroDialogo() {
		client = new HelloClient();
		
		setTitle("Cliente Operaci\u00F3n Mate");
		setBounds(100, 100, 450, 402);
		getContentPane().setLayout(null);
		
		textMsj = new JTextField();
		textMsj.setColumns(10);
		textMsj.setBounds(10, 304, 304, 45);
		getContentPane().add(textMsj);
		
		JButton btnOperacion = new JButton("Enviar Opera");
		btnOperacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mensaje(client.mandarOperacionMat(textMsj.getText()));
			}
		});
		btnOperacion.setActionCommand("Cancel");
		btnOperacion.setBounds(324, 304, 100, 45);
		getContentPane().add(btnOperacion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 9, 414, 283);
		getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
	}

	protected void mensaje(String mandarOperacionMat) {
		String aux = textPane.getText();
		aux += "\n"+mandarOperacionMat;
		textPane.setText(" ");
		textPane.setText(aux);
		
	}
}

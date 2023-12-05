import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

class Servidor {
	//################################### Atributos de objeto ###################################
	
	private String name = "Servidor";
	private JFrame v = new JFrame(name);
	private JLabel label = new JLabel();
	private JTextField inputBox = new JTextField();
	private JTextArea print = new JTextArea();
	private JButton botonEnviar = new JButton();
	private ServerSocket server;
	private Socket socket;
	
	//################################### Metodos y main ###################################
	
	public static void main(String args[]) {
		Servidor serv = new Servidor();
		/*Thread hiloServidor = new Thread(s);
		hiloServidor.start();
		s.run();*/
		serv.ventana();
		serv.AbrirConn();
		serv.recibe();
	}
	
	public void AbrirConn () {
		try {
			server = new ServerSocket(8181);
			socket = server.accept();
			print.append("Conexion entrante desde: " + socket.getInetAddress() + ":" + socket.getPort() + "\n\n");
		} catch (Exception e) {
			print.append(e.getMessage() + "\n\n");
		}
	}
	
	public void CerrarConn () {
		try {
			socket.close();
			server.close();
		} catch (Exception e) {
			print.append(e.getMessage() + "\n\n");
		}
	}
	
	public void ventana() {

		// Configurar ventana
		v.setTitle(name);
		v.setSize(600, 600);
		v.setLocationRelativeTo(null);
		v.setLayout(null);
		v.setResizable(false);
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Componentes de la ventana
		label.setText(name);
		label.setBounds(250, 5, 100, 20);
		print.setBounds(5, 30, 580, 450);
		print.setEditable(false);
		inputBox.setBounds(5, 490, 580, 30);
		inputBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
					salida.writeUTF(name + ": " + inputBox.getText());
					print.append("Tu: " + inputBox.getText() + "\n\n");
					inputBox.setText("");
				} catch (Exception ex) {
					print.append(ex.getMessage() + "\n\n");
				}
			}
		});
		botonEnviar.setText("Enviar");
		botonEnviar.setBounds(250, 530, 100, 30);
		botonEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
					salida.writeUTF(name + ": " + inputBox.getText());
					print.append("Tu: " + inputBox.getText() + "\n\n");
					inputBox.setText("");
				} catch (Exception ex) {
					print.append(ex.getMessage() + "\n\n");
				}
			}
		});
		v.add(label);
		v.add(print);
		v.add(inputBox);
		v.add(botonEnviar);
		v.setVisible(true);
	}
	
	public void recibe() {
		while(true) {
			try {
			DataInputStream entrada = new DataInputStream(socket.getInputStream());
			print.append(entrada.readUTF() + "\n\n");
			} catch(Exception e) {
				print.append(e.getMessage());
			}
		}
	}
	
	/*
	//+++++++++++++++++++++++++++++++++++ Codigo Antiguo +++++++++++++++++++++++++++++++++++
	 * 
	public void run() {
		String txtIn;
		String txtOut;
		String name = "Servidor";
		int c = 0;
		Scanner in = new Scanner(System.in);

		try {
			do {
				ServerSocket server = new ServerSocket(8181);
				Socket socket = server.accept();
				if (c == 0) {
					System.out
							.println("\nConexion entrante desde: " + socket.getInetAddress() + ":" + socket.getPort());
					c = 1;
				}

				DataInputStream entrada = new DataInputStream(socket.getInputStream());
				txtIn = entrada.readUTF();
				System.out.println("\n" + txtIn + "\n");

				DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
				System.out.println("Tu: ");
				txtOut = in.nextLine();
				salida.writeUTF(name + ": " + txtOut);

				entrada.close();
				salida.close();
				server.close();
				socket.close();
			} while (true);

		} catch (Exception e) {
			System.out.println(e);
		}
		;
	}
	*/
}
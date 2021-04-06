package udp.clientes;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;

public class Recebedor implements Runnable {
	private DatagramSocket socket;

	public Recebedor(DatagramSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while(true) {
			try {
				byte[] input = new byte[1024];
				DatagramPacket packet = new DatagramPacket(input, input.length);

				socket.receive(packet);
	
				String msg = new String(packet.getData());
	
				System.out.println("msgRecebida: " + msg);
			}catch(IOException e) {
				System.err.println("Erro ao receber mensagem");
			}
		}
		
	}
}

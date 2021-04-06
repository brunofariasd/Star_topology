package udp.clientes;

import java.net.DatagramSocket;
import java.net.DatagramPacket; 
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Process {
	
	private final static int portServer = 666;
	private int minhaPorta;
	private String nomeProcesso;
	public Process(String nomeProcesso, int minhaPorta) {
		this.setMinhaPorta(minhaPorta);
		this.setNomeProcesso(nomeProcesso);
	}

	public int getMinhaPorta() {
		return minhaPorta;
	}

	public void setMinhaPorta(int minhaPorta) {
		this.minhaPorta = minhaPorta;
	}
	
	public String getNomeProcesso() {
		return nomeProcesso;
	}

	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}
	
	public void execute() {
		try {
			DatagramSocket socket = new DatagramSocket(this.getMinhaPorta());

			try (Scanner in = new Scanner(System.in)) {
				DatagramPacket packet;

				ExecutorService executorService = Executors.newCachedThreadPool();
				executorService.execute(new Recebedor(socket));

				while(true) {
					byte[] output = new byte[1024];

					System.out.println("MsgEnviada: ");
					String msg = in.nextLine();
					msg = msg+":"+getNomeProcesso();
					output = msg.getBytes();
					packet = new DatagramPacket(output, output.length, InetAddress.getLocalHost(), portServer);				

					socket.send(packet);
				}
			}
		}catch(UnknownHostException e) {
			System.err.println("Não foi possivel indentificar o endereço");
		}catch(SocketException e) {
			System.err.println("Não conectado ao servidor");
		}catch(IOException e) {
			System.err.println("Erro no envio de mensagem");
		}
	}

}

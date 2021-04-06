package udp.clientes;

import java.net.DatagramSocket;
import java.net.DatagramPacket; 
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Servidor {
	
	final int portServer = 666;
	static String [] msgSeparada(String msg) {
		
		String [] arrayBytes = msg.split(":");
		return arrayBytes;
	}
	
	public static void main(String [] args) {
		try {
			
			Scanner in = new Scanner(System.in);
			Hashtable<String, Integer> clientes = new Hashtable<String, Integer>();
			
			System.out.print("Digite o numero de Processos: ");
			int n = in.nextInt();
			for (int i = 0; i < n; i++) {
				in.nextLine();
				System.out.print("Digite o Nome do Processo: ");
				String nome = in.nextLine();
				System.out.print("Digite o Numero do processo: ");
				int porta = in.nextInt();
				clientes.put(nome.toUpperCase(), porta);
			}
			
			try (DatagramSocket socket = new DatagramSocket(666)) {
				System.out.println("Servidor conectado port 666");

				byte[] input = new byte[1024];
				byte[] output = new byte[1024];
				DatagramPacket packetRecebido = new DatagramPacket(input, input.length);
				DatagramPacket packetEnviado;

				in.close();
				while(true) {
					socket.receive(packetRecebido);
					String[] msgSeparada = msgSeparada(new String(packetRecebido.getData()));
					output = msgSeparada[1].getBytes();
					if (msgSeparada[0].toUpperCase().equalsIgnoreCase("all")) {
						Set<String> interatorClientes = clientes.keySet();
						Iterator<String> iterator = interatorClientes.iterator();
						while(iterator.hasNext()) {
							String key = iterator.next();
							packetEnviado = new DatagramPacket(output, output.length, InetAddress.getLocalHost(), clientes.get(key.toUpperCase()));
							socket.send(packetEnviado);
							System.out.println("DE "+msgSeparada[2]+" P/ "+msgSeparada[0]);
							System.out.println(">>: "+msgSeparada[1]);
						}
					}else {
						packetEnviado = new DatagramPacket(output, output.length, InetAddress.getLocalHost(), clientes.get(msgSeparada[0].toUpperCase()));
						socket.send(packetEnviado);
						System.out.println("DE "+msgSeparada[2]+" P/ "+msgSeparada[0]);
						System.out.println(">>: "+msgSeparada[1]);
					}
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

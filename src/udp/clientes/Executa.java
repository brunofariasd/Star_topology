package udp.clientes;

import java.util.Scanner;

public class Executa {
	public static void main(String [] args) {
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Digite o Nome do Processo: ");
		String nome = in.nextLine();
		System.out.print("Digite o Numero do processo: ");
		int porta = in.nextInt();
		
		Process processo = new Process(nome, porta);
		
		processo.execute();
		
		in.close();
	}
}

package com.pberna.psp.sensores.servidor.Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class ClienteUDP {
    public static void main(String[] args) {
		DatagramSocket sock = null;
		int port = 7777;
		int espera = 5000;
		String mensaje;
		int idSensor = Integer.parseInt(args[0]);
		try
		{
			sock = new DatagramSocket();
			InetAddress host = InetAddress.getByName("localhost");
			
			while(true)
			{
				//Lee la entrada y envía el mensaje
                Random random = new Random();
				float temperatura = random.nextFloat(20,35);
                mensaje = idSensor+" "+temperatura+"";	
				byte[] mensajeComoBytes = mensaje.getBytes();
				
				DatagramPacket dp = new DatagramPacket(mensajeComoBytes , 
						mensajeComoBytes.length , host , port);
				sock.send(dp);
                System.out.println("Id de sensor y temperatura enviada: " + mensaje);
				
                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}
		catch(IOException e)
		{
			System.err.println("IOException " + e);
		}
	}
	
	public static void echo(String msg)
	{
		System.out.println(msg);
	}
}

package com.pberna.psp.sensores.servidor.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
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
				
				//recibimos la respuesta
				//se almacena en un buffer de entrada
				byte[] buffer = new byte[65536];
				DatagramPacket reply = new DatagramPacket(buffer,
						buffer.length);
				sock.receive(reply);
				
				byte[] data = reply.getData();
				String respuestaServidor = new String(data, 0, reply.getLength());
				
				//imprimir los detalles del input data - client ip : client
				//port - client message
				echo(reply.getAddress().getHostAddress() + " : " +
						reply.getPort() + " - " + respuestaServidor);
                
                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
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

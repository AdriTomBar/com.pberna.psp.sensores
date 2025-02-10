package com.pberna.psp.sensores.servidor.Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.pberna.psp.sensores.servidor.Sensores.DatosSensores;

public class ServidorUDP extends Thread {

	private DatosSensores datosSensores;
	
	public ServidorUDP(DatosSensores datosSensores) {
		this.datosSensores = datosSensores;
	}
	
	@Override
	public void run() {
		DatagramSocket socketUDP = null;
		try {
			// 1. Crear un server socket, local port number
			socketUDP = new DatagramSocket(7777);
			
			// buffer para incoming data
			byte[] buffer = new byte[65536];			
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			
			// communication loop
			while (true) {
				// 2. Esperar incoming data
				System.out.println("Server socket creado. Esperando datos entrantes...");				
				socketUDP.receive(incoming);
				
				//ha llegado una nueva petición, con los datos en objeto incoming
				byte[] data = incoming.getData();				
				String mensajePeticion = new String(data, 0, incoming.getLength());
				
				ProcesamientoSolicitudUDP procesamientoSolicitudUDP = new ProcesamientoSolicitudUDP(socketUDP, datosSensores);
				procesamientoSolicitudUDP.start(); 
			}
		} catch (IOException e) {
			System.err.println("IOException " + e);
		}
	}

	
}

package com.pberna.psp.sensores.servidor.Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.pberna.psp.sensores.servidor.Sensores.DatosSensores;

public class ServidorTCP extends Thread {

	private DatosSensores datosSensores;
	
	public ServidorTCP(DatosSensores datosSensores) {
		this.datosSensores  = datosSensores;
	}
	
	@Override
	public void run() {
		try (ServerSocket server = new ServerSocket(8888);) {

			System.out.println("Soy el servidor y espero alguna conexión");

			while (true) {
				Socket socket = server.accept();
				System.out.println("Ha llegado una nueva petición TCP");
				
				ProcesamientoSolicitudTCP procesamientoSolicitud = new ProcesamientoSolicitudTCP(socket, datosSensores);
				procesamientoSolicitud.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

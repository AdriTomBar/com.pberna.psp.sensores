package com.pberna.psp.sensores.servidor.Servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


import com.pberna.psp.sensores.servidor.Sensores.DatosSensores;

public class ProcesamientoSolicitudTCP extends Thread {

	private Socket socket;
	private DatosSensores datosSensores;

	public ProcesamientoSolicitudTCP(Socket socket, DatosSensores datosSensores) {
		this.socket = socket;
		this.datosSensores = datosSensores;
	}

	@Override
	public void run() {

		try (InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);) {
			procesarSolicitud(inputStreamReader, outputStreamWriter);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void procesarSolicitud(InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter) {

		try (BufferedReader br = new BufferedReader(inputStreamReader);
				BufferedWriter bw = new BufferedWriter(outputStreamWriter);) {
			String peticion = br.readLine();
			String[] datosPeticion = peticion.split(" ");

			String comando = "";
			int idSensor = 0;
			float umbral = 0;

			if (datosPeticion.length == 1) {
				comando = datosPeticion[0];
			}
			if (datosPeticion.length == 2) {
				comando = datosPeticion[0];
				idSensor = Integer.parseInt(datosPeticion[1]);
			}
			if (datosPeticion.length == 3) {
				comando = datosPeticion[0];
				idSensor = Integer.parseInt(datosPeticion[1]);
				umbral = Float.parseFloat(datosPeticion[2]);
			}

			switch (comando) {
				case "ENCENDER_SENSOR":
					if (datosSensores.encenderSensor(idSensor)){
						bw.write("RESPUESTA: Sensor " + idSensor + " activado");
						bw.newLine();
						bw.flush();
					}else{
						bw.write("RESPUESTA: Sensor " + idSensor + " no encontrado");
						bw.newLine();
						bw.flush();
					}
					break;

				case "APAGAR_SENSOR":
					if(datosSensores.apagarSensor(idSensor)){
						bw.write("RESPUESTA: Sensor " + idSensor + " desactivado");
						bw.newLine();
						bw.flush();
					}else{
						bw.write("RESPUESTA: Sensor " + idSensor + " no encontrado");
						bw.newLine();
						bw.flush();
					}
					
					break;

				case "ESTABLECER_UMBRAL":
					if(datosSensores.establecerUmbralSensor(idSensor, umbral)){
						bw.write("RESPUESTA: Umbral " + umbral + " establecido para el Sensor " + idSensor);
						bw.newLine();
						bw.flush();
					}else{
						bw.write("RESPUESTA: Sensor " + idSensor + " no encontrado");
						bw.newLine();
						bw.flush();
					}
					break;

				case "SOLICITAR_ESTADO_SENSORES":
					String respuesta = datosSensores.obtenerEstadoSensor().toString();
					System.out.println(respuesta);
					bw.write(respuesta);
					bw.newLine();
					bw.flush();
					break;

				default:
					bw.write("### COMANDO NO RECONOCIDO ###");
					bw.newLine();
					bw.flush();
					break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

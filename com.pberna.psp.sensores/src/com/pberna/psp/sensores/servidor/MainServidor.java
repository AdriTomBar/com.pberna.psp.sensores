package com.pberna.psp.sensores.servidor;

import com.pberna.psp.sensores.servidor.Sensores.DatosSensores;
import com.pberna.psp.sensores.servidor.Sensores.Sensor;
import com.pberna.psp.sensores.servidor.Servidor.ServidorTCP;
import com.pberna.psp.sensores.servidor.Servidor.ServidorUDP;

public class MainServidor {

	public static void main(String[] args) {
		DatosSensores datosSensores = new DatosSensores();
		Sensor sensor1 = new Sensor();
		sensor1.setIdSensor(1234);
		datosSensores.aniadirSensor(sensor1);
		ServidorTCP servidorTCP = new ServidorTCP(datosSensores);
		ServidorUDP servidorUDP = new ServidorUDP(datosSensores);
		
		
		
		//lanzamos hilos de los servidores
		servidorTCP.start();
		servidorUDP.start();
	}

}

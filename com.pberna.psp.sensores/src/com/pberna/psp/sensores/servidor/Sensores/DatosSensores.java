package com.pberna.psp.sensores.servidor.Sensores;

import java.util.LinkedList;
import java.util.List;

public class DatosSensores {
	
	private List<Sensor> listaSensores; 
	
	public DatosSensores() {
		listaSensores = new LinkedList<Sensor>();
	}
	
	public synchronized boolean encenderSensor(int sensorId) {
		Sensor s = buscarSensorPorId(sensorId);
		if (s != null) {
			s.setEstado(EstadoSensor.ENCENDIDO);
			return true;
		}
		return false;
	}	
	public synchronized boolean apagarSensor(int sensorId) {        
		Sensor s = buscarSensorPorId(sensorId);
		if (s != null) {
			s.setEstado(EstadoSensor.APAGADO);
			return true;
		}
		return false;
	}
	public synchronized boolean establecerUmbralSensor(int sensorId, float umbral) {		
		Sensor s = buscarSensorPorId(sensorId);
		if (s != null){
			s.setUmbral(umbral);
			return true;
		}
		return false;
		
	}	

	public  String obtenerEstadoSensor() {
		StringBuilder texto = new StringBuilder("RESPUESTA:");
		for (Sensor sensor : listaSensores) {
			texto.append("Sensor ").append(sensor.getIdSensor())
				 .append(": ").append(sensor.getEstado())
				 .append(", Umbral: ").append(sensor.getUmbral()).append("\n");
		}
		return texto.toString();
	}

	public synchronized void registrarMedicion(int sensorId, float medida) {
		Sensor s = buscarSensorPorId(sensorId);
		if (s != null) {
			s.setUltimaMedida(medida);
		} else {
			System.out.println("Error: Sensor no encontrado.");
		}
	}

	public synchronized void aniadirSensor(Sensor s){
		listaSensores.add(s);
		System.out.println("Sensor añadido");
	}

	public Sensor buscarSensorPorId(int sensorId) {
		for (Sensor sensor : listaSensores) {
			if (sensor.getIdSensor() == sensorId) {
				return sensor;
			}
		}
		return null; 
	}
}

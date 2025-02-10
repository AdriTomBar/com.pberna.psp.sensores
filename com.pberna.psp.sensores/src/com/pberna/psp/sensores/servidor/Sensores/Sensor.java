package com.pberna.psp.sensores.servidor.Sensores;

public class Sensor {
	private int idSensor;
	private EstadoSensor estado;
	private float umbral;
	private Float ultimaMedida;	

	public Sensor() {
		idSensor = 0;
		estado = EstadoSensor.APAGADO;
		umbral = 0;
		ultimaMedida = null;
	}
	
	public int getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	public EstadoSensor getEstado() {
		return estado;
	}
	public void setEstado(EstadoSensor estado) {
		this.estado = estado;
	}
	public float getUmbral() {
		return umbral;
	}
	public void setUmbral(float umbral2) {
		this.umbral = umbral2;
	}
	
	public Float getUltimaMedida() {
		return ultimaMedida;
	}

	public void setUltimaMedida(Float ultimaMedida) {
		this.ultimaMedida = ultimaMedida;
	}
}

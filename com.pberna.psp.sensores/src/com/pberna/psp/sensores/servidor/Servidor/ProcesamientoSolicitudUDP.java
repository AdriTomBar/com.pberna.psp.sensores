package com.pberna.psp.sensores.servidor.Servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.pberna.psp.sensores.servidor.Sensores.DatosSensores;
import com.pberna.psp.sensores.servidor.Sensores.EstadoSensor;
import com.pberna.psp.sensores.servidor.Sensores.Sensor;

class ProcesamientoSolicitudUDP extends Thread {
    private DatagramSocket socketUDP;
    private DatagramPacket incoming;
    private DatosSensores datosSensores;

    public ProcesamientoSolicitudUDP(DatagramSocket socketUDP, DatagramPacket incoming, DatosSensores datosSensores) {
        this.socketUDP = socketUDP;
        this.incoming = incoming;
        this.datosSensores = datosSensores;
    }

    @Override
    public void run() {
        try {
            String mensajePeticion = new String(incoming.getData(), 0, incoming.getLength());
            System.out.println("Datos recibidos: " + mensajePeticion);
            
            // Procesar el mensaje y actualizar datosSensores
            // (Se asume que el mensaje tiene formato correcto "SensorID:valor")
            String[] partes = mensajePeticion.split(" ");
            if (partes.length == 2) {
                int sensorID = Integer.parseInt(partes[0].trim());
                float valor = Float.parseFloat(partes[1].trim());
                Sensor sensor = datosSensores.buscarSensorPorId(sensorID);
                float umbral = sensor.getUmbral();
                if (sensor.getEstado() == EstadoSensor.ENCENDIDO){
                    datosSensores.registrarMedicion(sensorID, valor);
                    System.out.println("Sensor " + sensorID + " actualizado con valor " + valor);
                    if (sensor.getUltimaMedida() > umbral){
                        System.out.println("ALERTA: Lectura de temperatura de " + valor + "ºC en el Sensor " + sensorID + " supera el umbral de "+ umbral+"ºC\n");
                    }
                }
                
            }
        } catch (Exception e) {
            System.err.println("Error procesando solicitud UDP: " + e.getMessage());
        }
    }
}
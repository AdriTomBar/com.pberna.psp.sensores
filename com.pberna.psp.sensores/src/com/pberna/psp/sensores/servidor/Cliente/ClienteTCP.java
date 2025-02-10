package com.pberna.psp.sensores.servidor.Cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) throws Exception {
        String comando = "";
		String idSensor = "";
        String umbral = "";
        if(args.length <1) {
			throw new Exception("Uso: ClienteTCP <comando> <SensorId>");
		}

        if (args.length == 1){
            comando = args[0];
       }


        if (args.length == 2){
             comando = args[0];
		     idSensor = args[1];
        }

        if (args.length == 3){
             comando = args[0];
		     idSensor = args[1];
             umbral = args[2];
       }
        

        try (Socket socket = new Socket("localhost", 8888);) {

            System.out.println("Voy a hacer una nueva petición");

            try (InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

                hacerPeticionSumaVerificacion(inputStreamReader, outputStreamWriter, comando,idSensor,umbral);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void hacerPeticionSumaVerificacion(InputStreamReader inputStreamReader,
            OutputStreamWriter outputStreamWriter, String comando , String idSensor, String umbral) {
        try (BufferedWriter bw = new BufferedWriter(outputStreamWriter);
                BufferedReader br = new BufferedReader(inputStreamReader);) {

            // Mandamos los comandos al servidor
            
            System.out.println("Mando comando " + comando + " al sensor " + idSensor);
            bw.write(comando + " " + idSensor + " "+ umbral);
            bw.newLine();
            bw.flush();

            //Recibimos la respuesta

            String respuesta = br.readLine();
            System.out.println("Recibido " + respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
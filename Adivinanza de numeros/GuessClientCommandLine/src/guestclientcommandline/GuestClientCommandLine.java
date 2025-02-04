package guestclientcommandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GuestClientCommandLine {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error, debe indicar el servidor y el puerto.");
            System.exit(1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(host, port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor " + host + " en el puerto " + port + ".");

            // Leer y mostrar el mensaje de bienvenida del servidor
            String welcomeMessage = input.readLine();
            System.out.println("Servidor: " + welcomeMessage);

            boolean stop = false;
            int attempts = 0;

            while (!stop) {
                attempts++;
                System.out.print("Introduzca su número: ");
                int myNumber = Integer.parseInt(stdInput.readLine());
                System.out.println("Intento " + attempts + ", número " + myNumber);

                // Enviar número al servidor
                output.println(myNumber);

                // Leer la respuesta del servidor
                String answer = input.readLine();
                System.out.println("La respuesta del servidor es: " + answer);

                if (answer.equals("Acierto")) {
                    stop = true;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error en la conexión: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Por favor, ingrese un número válido.");
        }
    }
}

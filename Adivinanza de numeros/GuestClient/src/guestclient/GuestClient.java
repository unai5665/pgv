package guestclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GuestClient {
    public static void main(String[] args) {
        String host = "172.16.6.122"; // Cambiar a localhost para pruebas locales
        int port = 12346;

        try (Socket socket = new Socket(host, port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor " + host + " en el puerto " + port + ".");

            // Leer y mostrar el mensaje inicial del servidor
            String serverMessage = input.readLine();
            System.out.println(serverMessage);  // Imprimir mensaje de bienvenida

            boolean stop = false;

            while (!stop) {
                try {
                    // Solicitar número al usuario
                    System.out.print("Introduce tu número: ");
                    String userInput = stdInput.readLine();
                    if (userInput == null) {
                        continue;  // Si no hay entrada del usuario, continuar al siguiente ciclo
                    }

                    // Intentar convertir la entrada del usuario en número
                    int myNumber;
                    try {
                        myNumber = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        // Si no es un número válido, muestra mensaje y sigue pidiendo otro número
                        System.out.println("Por favor, introduzca un número válido.");
                        continue;  // Vuelve al inicio del ciclo
                    }

                    // Enviar número al servidor
                    output.println(myNumber);

                    // Leer la respuesta del servidor
                    String answer = input.readLine();
                    if (answer != null) {
                        System.out.println(answer);  // Mostrar respuesta del servidor
                    }

                    // Si el usuario acertó, termina el ciclo
                    if ("Acierto".equals(answer)) {
                        stop = true;
                    }

                } catch (IOException e) {
                    // Si ocurre un error con la conexión
                    System.err.println("Error de entrada/salida: " + e.getMessage());
                    break;  // Salir del bucle en caso de error
                }
            }
        } catch (IOException ex) {
            System.err.println("Error de conexión: " + ex.getMessage());
        }
    }
}

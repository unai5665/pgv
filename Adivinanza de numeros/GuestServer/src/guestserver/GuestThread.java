package guestserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class GuestThread implements Runnable {
    private Socket socket;

    public GuestThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Cliente conectado: " + socket.getInetAddress() +
                    " atendido por " + Thread.currentThread().getName());

            int secretNumber = 1 + new Random().nextInt(100); // Número entre 1 y 100
            System.out.println("Número secreto generado: " + secretNumber); // Imprimir el número secreto para depuración
            output.println("Bienvenido al juego de adivinanza. ¡Adivina el número entre 1 y 100!");

            boolean stop = false;

            while (!stop) {
                // Leer el intento del cliente
                String clientInput = input.readLine();
                if (clientInput != null) {
                    System.out.println("Recibido del cliente: " + clientInput); // Mostrar la entrada del cliente

                    int guess = Integer.parseInt(clientInput);

                    if (guess < secretNumber) {
                        output.println("Mayor");
                    } else if (guess > secretNumber) {
                        output.println("Menor");
                    } else {
                        output.println("Acierto");
                        stop = true;
                    }
                } else {
                    // Si la entrada del cliente es nula, significa que el cliente cerró la conexión
                    break;
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error en la conexión: " + ex.getMessage());
        }
    }
}
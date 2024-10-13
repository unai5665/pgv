package Modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author unai
 */
public class Proceso {
    private String pid, euser, command;
    
    public Proceso() {
        this.pid = "";
        this.euser = "";
        this.command = "";
    }
    
    public Proceso(String pid, String euser, String command) {
        this.pid = pid;
        this.euser = euser;
        this.command = command;
    }
    
    public String getPid() {
        return this.pid;
    }
    
    public String getEuser() {
        return this.euser;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public List<Proceso> getSystemProcessList() {
        List<Proceso> processList = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("ps", "-eo", "pid,euser,comm");
        
        try {
            Process p = pb.start();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            // Leer y descartar la línea de encabezado
            buffer.readLine(); 
            
            String currentLine;
            while ((currentLine = buffer.readLine()) != null) {
                // Saltar líneas vacías
                if (currentLine.trim().isEmpty()) continue;

                // Dividir la línea usando espacios como delimitador
                String[] parts = currentLine.trim().split("\\s+", 3); // Hasta 3 partes: PID, EUSER, COMMAND
                
                // Verificar que tengamos las tres partes
                if (parts.length == 3) {
                    String pid = parts[0];
                    String euser = parts[1];
                    String command = parts[2];
                    
                    // Crear el objeto Proceso y añadirlo a la lista
                    Proceso proceso = new Proceso(pid, euser, command);
                    processList.add(proceso);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processList;
    }

    public boolean killProcess(String pid) {
        try {
            ProcessBuilder pb = new ProcessBuilder("kill", "-9", pid);
            Process process = pb.start();
            process.waitFor();  
            return process.exitValue() == 0;  
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "PID: " + this.pid + " EUSER: " + this.euser + " COMMAND: \n" + this.command;
    }
}

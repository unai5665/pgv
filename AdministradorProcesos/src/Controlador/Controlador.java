package Controlador;
import Vista.*;
import Modelo.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author unai
 */
public class Controlador {
    
    public void getAllProccesses(Vista vista) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("PID");
        tableModel.addColumn("EUSER");
        tableModel.addColumn("COMMAND");
        List<Proceso> processList = new Proceso().getSystemProcessList();
        for (Proceso each : processList) {
            tableModel.addRow(new Object[] {each.getPid(), each.getEuser(), each.getCommand()});
        }
        System.out.println("Número de procesos " + processList.size());
        vista.jTable1.setModel(tableModel);
        vista.setVisible(true);
    }
    
    public void killSelectedProcess(Vista vista) {
        int selectedRow = vista.jTable1.getSelectedRow();
        if (selectedRow != -1) {  
            String pid = vista.jTable1.getValueAt(selectedRow, 0).toString();  // Obtiene el PID de la fila seleccionada
            Proceso proceso = new Proceso();
            boolean isKilled = proceso.killProcess(pid);
            if (isKilled) {
                JOptionPane.showMessageDialog(vista, "Proceso " + pid + " finalizado correctamente.");
                getAllProccesses(vista);  
            } else {
                JOptionPane.showMessageDialog(vista, "Error al finalizar el proceso " + pid);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un proceso para finalizar.");
        }
    }

    public boolean killProcess(String pid) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

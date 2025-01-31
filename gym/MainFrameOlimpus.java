package gym;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrameOlimpus extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrameOlimpus frame = new MainFrameOlimpus();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrameOlimpus() {
    	setTitle("OLYMPUS GYM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        
        JMenuBar menuBar = new JMenuBar();

        
        JMenu menuMembresias = new JMenu("Membresías");
        JMenu menuAsistencias = new JMenu("Asistencias");
        JMenu menuClientes = new JMenu("Clientes");
        JMenu menuInscripciones = new JMenu("Inscripciones");

        
        JMenuItem itemMembresias = new JMenuItem("Ver Membresías");
        JMenuItem itemAsistencias = new JMenuItem("Ver Asistencias");
        JMenuItem itemClientes = new JMenuItem("Ver Clientes");
        JMenuItem itemInscripciones = new JMenuItem("Ver Inscripciones");

        
        menuMembresias.add(itemMembresias);
        menuAsistencias.add(itemAsistencias);
        menuClientes.add(itemClientes);
        menuInscripciones.add(itemInscripciones);

        
        menuBar.add(menuMembresias);
        menuBar.add(menuAsistencias);
        menuBar.add(menuClientes);
        menuBar.add(menuInscripciones);

        
        setJMenuBar(menuBar);

        
        itemMembresias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMembresiasInterface();
            }
        });

        itemAsistencias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAsistenciasInterface();
            }
        });

        itemClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showClientesInterface();
            }
        });

        itemInscripciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInscripcionesInterface();
            }
        });
    }

    
    private void showMembresiasInterface() {
       
        JFrame membresiasFrame = new MembresiasIF();
        membresiasFrame.setVisible(true);
    }

    private void showAsistenciasInterface() {
       
        JFrame asistenciasFrame = new AsistenciasIF();
        asistenciasFrame.setVisible(true);
    }

    private void showClientesInterface() {
       
        JFrame clientesFrame = new ClienteIF();
        clientesFrame.setVisible(true);
    }

    private void showInscripcionesInterface() {
       
        JFrame inscripcionesFrame = new InscripcionesIF();
        inscripcionesFrame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ConexionServidor implements ActionListener { //Esta interfaz significa que la clase puede manejar eventos de accion, como clics de boton

    private Logger log = Logger.getLogger(String.valueOf(ConexionServidor.class));
    private Socket socket;
    private JTextField tfMensaje; //Para que el usuario ingrese mensajes
    private String usuario;
    private DataOutputStream salidaDatos;

    public ConexionServidor(Socket socket, JTextField tfMensaje, String usuario) {
        this.socket = socket;
        this.tfMensaje = tfMensaje;
        this.usuario = usuario;
        try {
            this.salidaDatos = new DataOutputStream(socket.getOutputStream()); //Inicializa salidaDatos para que este listo para enviar datos al servidor a traves del socket proporcionado.
            //Una vez este configurado asi, puede ser utilizado en otros metodos de la clase para enviar mensajes y datos al servidor
        } catch (IOException ex) { //Esto significa que si ocurre un error durante la creación del DataOutputStream, específicamente al intentar obtener el flujo de salida del socket (socket.getOutputStream()), se capturará la excepción IOException
           // log.error("Error al crear el stream de salida : " + ex.getMessage());
        } catch (NullPointerException ex) { //Una NullPointerException ocurre cuando intentas acceder a un objeto que es null, lo cual no está permitido en Java y genera este tipo de excepción.
           // log.error("El socket no se creo correctamente. ");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //Este metodo es de la interfaz. Este metodo se ejecutara cuando se produzca un evento de accion, como presionar el boton
        try {
            salidaDatos.writeUTF(usuario + ": " + tfMensaje.getText() );
            tfMensaje.setText("");
        } catch (IOException ex) {
            //log.error("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }
}
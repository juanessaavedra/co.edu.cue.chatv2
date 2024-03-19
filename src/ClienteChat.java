import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Clase principal del cliente del chat
 *
 * @author Ivan Salas Corrales <http://programandoointentandolo.com>
 */

public class ClienteChat extends JFrame { //Esta clase representa una ventana de la interfaz de usuario de Swing

    private Logger log = Logger.getLogger(String.valueOf(ClienteChat.class));
    private JTextArea mensajesChat; //Area de texto para mostrar los mensajes del chat
    private Socket socket;

    private int puerto;
    private String host;
    private String usuario;

    public ClienteChat(){ //Inicializa la ventana de chat del cliente
        super("Cliente Chat"); //Titulo de la ventana

        // Elementos de la ventana
        mensajesChat = new JTextArea(); //Componente de la interfaz de usuario de Swing en Java que poporciona un area de texto de varias lineas, donde los usuarios pueden escribir y visualizar texto
        mensajesChat.setEnabled(false); // El area de mensajes del chat no se debe de poder editar
        mensajesChat.setLineWrap(true); // Las lineas se parten al llegar al ancho del textArea
        mensajesChat.setWrapStyleWord(true); // Las lineas se parten entre palabras (por los espacios blancos)
        JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat); //Contenedor que poporciona barras de desplazamiento para ver el contenido de sus componentes hijo cuando el contenido excede el tamaño visible del JScrollPane
        //Es util cuando el contenido dentro de un area de texto de mensajesChat, es demasiado grande para caber completamente dentro de la ventana de la aplicacion
        JTextField tfMensaje = new JTextField(""); //Se utilizara para que los usuarios ingresen mensajes de chat en la aplicacion
        JButton btEnviar = new JButton("Enviar"); //Se utilizara en la interfaz de usuario. Se usara para enviar los mensajes ingresados por el usuario al servidor de chat


        // Colocacion de los componentes en la ventana
        Container c = this.getContentPane(); //this.getContentPane obtiene el panel de contenido de la ventana actual JFrame. El panel de contenido es donde se agregan y organizan los componentes de la interfaz de usuario en una ventana
        c.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(scrollMensajesChat, gbc);
        // Restaura valores por defecto
        gbc.gridwidth = 1;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(tfMensaje, gbc);
        // Restaura valores por defecto
        gbc.weightx = 0;

        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(btEnviar, gbc);

        this.setBounds(400, 100, 400, 500); //Establece la posicion y el tamaño de la ventana. Los cuatro argumentos representan la posicion x, la posicion y, el ancho y el alto
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//El programa finalizara cuando el usuario cierre la ventana principal.

        // Ventana de configuracion inicial
        VentanaConfiguracion vc = new VentanaConfiguracion(this);
        host = vc.getHost();
        puerto = vc.getPuerto();
        usuario = vc.getUsuario();

        log.info("Quieres conectarte a " + host + " en el puerto " + puerto + " con el nombre de ususario: " + usuario + ".");

        // Se crea el socket para conectar con el Sevidor del Chat
        try {
            socket = new Socket(host, puerto);
        } catch (UnknownHostException ex) {
          //  log.error("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } catch (IOException ex) {
           // log.error("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }

        // Accion para el boton enviar
        btEnviar.addActionListener(new ConexionServidor(socket, tfMensaje, usuario));
        //Cuando el boton sea clicado, se ejecutara el codigo dentro del actionListerner

    }

    /**
     * Recibe los mensajes del chat reenviados por el servidor
     */

    public void recibirMensajesServidor(){
        // Obtiene el flujo de entrada del socket
        DataInputStream entradaDatos = null;
        String mensaje;
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
           // log.error("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
           // log.error("El socket no se creo correctamente. ");
        }

        // Bucle infinito que recibe mensajes del servidor
        boolean conectado = true;
        while (conectado) {
            try {
                mensaje = entradaDatos.readUTF();
                mensajesChat.append(mensaje + System.lineSeparator());
            } catch (IOException ex) {
              //  log.error("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
               // log.error("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        // Carga el archivo de configuracion de log4J
        //PropertyConfigurator.configure("log4j.properties");

        ClienteChat c = new ClienteChat(); //Inicializa la ventana del chat del cliente y todos los componentes asociados con esta
        c.recibirMensajesServidor();
    }

}

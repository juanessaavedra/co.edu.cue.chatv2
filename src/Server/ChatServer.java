package Server;

import Chat.ChatMessages;
import Client.ConnectionClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Servidor para el chat.
 *
 * En el patrón observer hay 2 tipos de elementos los observadores y los observados (Chat.MensajesChat es un observado). Si un objeto quiere observar a otro se apunta a su lista de observadores para avisarle
 * de que quiere saber cuando cambia su estado para realizar alguna acción, por ejemplo mostrar el cambio, y el objeto observado lo que hace es informar a todos los objetos que lo están observando para decirles que su estado ha cambiado.

 *
 */

public class ChatServer {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        // Carga el archivo de configuracion de log4J
       // PropertyConfigurator.configure("log4j.properties");
        Logger log = Logger.getLogger(String.valueOf(ChatServer.class));

        int port = 5050;
        int maxConnections = 10; // Maximo de conexiones simultaneas
        ServerSocket servidor = null;
        Socket socket = null;
        ChatMessages mensajes = new ChatMessages(); //se crea un objeto (de la clase Chat.MensajesChat) que será el que se utilizará para permitir que se puedan intercambiar mensajes entre múltiples clientes que es la idea de un chat.

        try {
            // Se crea el serverSocket
            servidor = new ServerSocket(port, maxConnections);

            // Bucle infinito para esperar conexiones
            while (true) {
                log.info("Servidor a la espera de conexiones."); //.info sugiere que el mensaje es de nivel de información. Este nivel se utiliza para mensajes que proporcionan información sobre el estado de funcionamiento del programa
                socket = servidor.accept(); //Metodo del servidor el cual espera y acepta la conexion del cliente
                log.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");

                ConnectionClient cc = new ConnectionClient(socket, mensajes);
                cc.start();

            }
        } catch (IOException ex) {
           // log.error("Error: " + ex.getMessage());
        } finally{
            try {
                socket.close();
                servidor.close();
            } catch (IOException ex) {
               // log.error("Error al cerrar el servidor: " + ex.getMessage());
            }
        }
    }
}
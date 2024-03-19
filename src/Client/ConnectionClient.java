package Client;

import Chat.ChatMessages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Esta clase gestiona el envio de datos entre el servidor y el cliente al que atiende.
 *
 * @author Ivan Salas Corrales <http://programandoointentandolo.com>
 */

public class ConnectionClient extends Thread implements Observer { //Hereda Thread e implementa la interfaz Observer

    private Logger log = Logger.getLogger(String.valueOf(ConnectionClient.class)); //Clase usada para el logging
    //Invoca el .getLogger que es un metodo estatico de Logger en donde se obtiene la istancia del logger basada en la clase Client.ConexionCliente. String.valueOf convierte el nombre de la clase en una cadena
    private Socket socket;
    private ChatMessages messages;
    private DataInputStream dataEntry;
    private DataOutputStream dataOutput;

    public ConnectionClient(Socket socket, ChatMessages mensajes){ //Constructor
        this.socket = socket;
        this.messages = messages;

        try {
            dataEntry = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
          //  log.error("Error al crear los stream de entrada y salida : " + ex.getMessage());
        }
    }

    @Override
    public void run(){
        String mensajeRecibido;
        boolean conectado = true;
        // Se apunta a la lista de observadores de mensajes
        messages.addObserver(this);

        while (conectado) {
            try {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = dataEntry.readUTF();
                // Pone el mensaje recibido en mensajes para que se notifique 
                // a sus observadores que hay un nuevo mensaje.
                messages.setMensaje(mensajeRecibido);
            } catch (IOException ex) {
                log.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
                conectado = false;
                // Si se ha producido un error al recibir datos del cliente se cierra la conexion con el.
                try {
                    dataEntry.close();
                    dataOutput.close();
                } catch (IOException ex2) {
                  //  log.error("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            // Envia el mensaje al cliente
            dataOutput.writeUTF(arg.toString());
        } catch (IOException ex) {
           // log.error("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }
}

/**
 *  finalmente la clase Client.ConexionCliente que solo tiene dos métodos, el método run que hereda de Thread y que es el que se encarga de recibir
 *  los mensajes del cliente y el método update que es necesario implementar por implementar la interfaz Observer y que es el encargado de enviar
 *  el mensaje al cliente.
 *
 * **/
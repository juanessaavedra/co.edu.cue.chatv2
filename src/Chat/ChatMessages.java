package Chat;

import java.util.Observable;

/** // Esta clase es muy sencilla porque únicamente tiene un set y get aunque puesto que hereda de Observable
 *  hay que saber de que va el patrón Observer.**/

public class ChatMessages extends Observable { //Solo tiene un get y un set


    private String mensaje;

    public ChatMessages(){
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
        // Indica que el mensaje ha cambiado
        this.setChanged();  // mediante la función setChanged() se indica que el estado del objeto observable a cambiado y además hay que notificárselo a sus observadores
        // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
        // (Internamente notifyObservers llama al metodo update del observador)
        this.notifyObservers(this.getMensaje()); //con notifyObservers(Object o) que le pasará al objeto observador el objeto o que se quiera enviar, en nuestro caso un mensaje de un cliente.

    }
}

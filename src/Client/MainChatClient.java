package Client;

public class MainChatClient {
    public static void main(String[] args) {
        // Carga el archivo de configuracion de log4J
        //PropertyConfigurator.configure("log4j.properties");

        ChatClient chatClient = new ChatClient(); //Inicializa la ventana del chat del cliente y todos los componentes asociados con esta
        chatClient.receiveServerMessages();
    }
}

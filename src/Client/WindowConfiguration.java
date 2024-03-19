package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Una sencilla ventana para configurar el chat
 *
 * Esta parte representa una aplicacion de chat cliente que se ejecuta en una interfaz grafica de usuario (GUI)
 *
 * @author Ivan Salas Corrales <http://programandoointentandolo.com/>
 */

public class WindowConfiguration extends JDialog{ // Esta clase representa una ventana de la interfaz de usuario de Swing

    private Logger log = Logger.getLogger(String.valueOf(WindowConfiguration.class)); //Registrar mensajes de registro
    private JTextField tfUsuario;
    private JTextField tfHost;
    private JTextField tfPuerto;

    //Para que el usuario ingrese informacion de configuracion, como nombre de usuario, host y el puerto del servidor

    /**
     * Constructor de la ventana de configuracion inicial
     *
     * @param father Ventana father
     */

    public WindowConfiguration(JFrame father) {
        super(father, "Configuracion inicial", true); //Llama al contstructor de JDialog, para inicializar la ventana de dialogo.
        //1 parametro: Indicar que esta ventana de dialogo es modal, 2 parametro: Titulo de la ventana

        JLabel lbUsuario = new JLabel("Usuario:");  //Se crean etiquetas JLbal para describir los campos de entrada y de texto
        JLabel lbHost = new JLabel("Host:");
        JLabel lbPuerto = new JLabel("Puerto:");

        tfUsuario = new JTextField(); //Los JTextField para que el usuario ingrese la informacion de configuracion.
        tfHost = new JTextField("localhost");
        tfPuerto = new JTextField("5000");

        JButton btAceptar = new JButton("Aceptar"); //Se crea el boton de aceptar que cierra la ventana de configuracion.
        btAceptar.addActionListener(new ActionListener() { //Se agrega al boton para controlar el evento de clic y ocultar la ventana de dialogo

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            } //Es de la interfaz ActionListener
        });

        Container c = this.getContentPane();  //Se obtiene el panel de contenido de la ventana de dialogo
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); //Se configura su diseño, con GridBagConstraints que es un administrador de diseño flexible

        gbc.insets = new Insets(20, 20, 0, 20); //Establecer los margenes internos alrededor de cada componente

        gbc.gridx = 0;
        gbc.gridy = 0;
        c.add(lbUsuario, gbc); //En la fila 0

        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(lbHost, gbc); //En la fila 1

        gbc.gridx = 0;
        gbc.gridy = 2; //En la fila 2
        c.add(lbPuerto, gbc);

        //gridx y gridy indican las coordenadas de la celda de la cuadricula en la que se colocara el componente.


        gbc.ipadx = 100; //Ipadx se utiliza para especificar el espacio adicional en la direccion horizontal. En este caso tendran un ancho adicional de 100 pixeles
        gbc.fill = GridBagConstraints.HORIZONTAL; //Esta linea significa que los componentes deben estirarse horizontalmente para ocupar todoo el espacio disponible en su celda

        gbc.gridx = 1; //Columna 1
        gbc.gridy = 0; //Fila 0
        c.add(tfUsuario, gbc);

        gbc.gridx = 1; //Columna 1
        gbc.gridy = 1; //Fila 1
        c.add(tfHost, gbc);

        gbc.gridx = 1; //Columna 1
        gbc.gridy = 2; //Fila 2
        c.add(tfPuerto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; //Para que el boton ocupe dos columnas en lugar de solo una
        gbc.insets = new Insets(20, 20, 20, 20); //Insets se actualiza para establecer los margenes alrededor del boton
        c.add(btAceptar, gbc); //btAceptar se coloca en la fila 3, columna 0.

        this.pack(); // Le da a la ventana el minimo tamaño posible
        this.setLocation(450, 200); // Posicion de la ventana
        this.setResizable(false); // Evita que se pueda estirar la ventana
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Deshabilita el boton de cierre de la ventana 
        this.setVisible(true); //Se hace visible la ventana de configuracion en la pantalla del usuario
    }

    /**En resumen, este fragmento de código configura las restricciones de diseño para colocar los componentes (JLabel, JTextField y JButton)
     * en una cuadrícula utilizando GridBagLayout. Se especifican las coordenadas de la celda, el ancho adicional para los componentes de texto,
     * cómo deben rellenar su celda y los márgenes alrededor de cada componente. **/


    public String getUsuario(){
        return this.tfUsuario.getText();
    }

    public String getHost(){
        return this.tfHost.getText();
    }

    public int getPuerto(){
        return Integer.parseInt(this.tfPuerto.getText());
    }



}
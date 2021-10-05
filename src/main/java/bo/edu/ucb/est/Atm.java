package bo.edu.ucb.est;
import java.util.ArrayList;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 *
 * @author melany.182
 */
/* Estados
-5: Primera vez, saludo
-4: Registro del nombre
-3: Registro del pin
-2: Verificación del pin
-1: Ingreso del pin
0: Menú principal
*/
public class Atm extends TelegramLongPollingBot {
    public ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
    
    @Override
    public String getBotToken() {
        return "2007254728:AAFW03QGLwQ6L1MlbIU8DalqPCEtdIUFOdc";
    }

    @Override
    public void onUpdateReceived(Update update) { // se ejecuta cada vez que llega un mensaje
        for (int i=0; i<usuarios.size(); i++) {
            System.out.println(usuarios.get(i).toString());
    	}
    	int aux=0;
    	Long idusuario=update.getMessage().getFrom().getId();
    	for (int i=0; i<usuarios.size(); i++) { // verificación de existencia
            if ((usuarios.get(i).getUserId()).equals(idusuario)) { // si ya existe
                aux=1;
                break;
            }
    	}
    	if (aux==0) { // si no existe todavía
            Usuario usuario=new Usuario(idusuario,-5);
            usuarios.add(usuario);
    	}
    	System.out.println("Mensaje: "+update.toString()+"\n");
        
        if (update.hasMessage()) {
            for (int i=0; i<usuarios.size(); i++) {
                if ((usuarios.get(i).getUserId()).equals(update.getMessage().getFrom().getId())) {
                    int state=usuarios.get(i).getEstado();
                    switch (state) {
                        case -5: {
                            enviarMensaje(update,"¡Bienvenido/a al Banco de La Fortuna!");
                            enviarMensaje(update,"He notado que aún no eres cliente, así que procedamos a registrarte.");
                            enviarMensaje(update,"¿Cuál es tu nombre completo?");
                            usuarios.get(i).setEstado(-4);
                            break;
                        }
                        case -4: {
                            usuarios.get(i).setNombre(update.getMessage().getText());
                            enviarMensaje(update,"Por favor, elige un PIN de seguridad. Este te será requerido cada vez que ingreses al sistema.");
                            usuarios.get(i).setEstado(-3);
                            break;
                        }
                        case -3: {
                            usuarios.get(i).setPin(update.getMessage().getText());
                            enviarMensaje(update,"¡Te hemos registrado exitosamente!\nEnvía un mensaje cualquiera para continuar."); // *******
                            usuarios.get(i).setEstado(-2);
                            break;
                        }
                        case -2: {
                            enviarMensaje(update,"¡Hola de nuevo, estimado/a "+usuarios.get(i).getNombre()+"!");
                            enviarMensaje(update,"Solo por seguridad ¿cuál es tu PIN?");
                            usuarios.get(i).setEstado(-1);
                            break;
                        }
                        case -1: {
                            if ((update.getMessage().getText()).equals(usuarios.get(i).getPin())) {
                                enviarMensaje(update,"¡Bienvenido/a!");
                                usuarios.get(i).setEstado(0);
                            }
                            else {
                                enviarMensaje(update,"Lo siento, el pin es incorrecto.");
                                usuarios.get(i).setEstado(-2);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
  
    public void enviarMensaje(Update update,String mensaje) {
        SendMessage message=new SendMessage(); // crea el objeto para enviar el mensaje
	message.setChatId(update.getMessage().getChatId().toString()); // define a quién se le enviará el mensaje
	message.setText(mensaje);
	try {
            execute(message); // envía el mensaje
	}
	catch (TelegramApiException e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public String getBotUsername() {
        return "atmKaune_bot";
    }
}

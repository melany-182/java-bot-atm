package bo.edu.ucb.est;
import java.util.ArrayList;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Atm extends TelegramLongPollingBot {
    public ArrayList<Integer> usuarios=new ArrayList<Integer>();
    
    @Override
    public String getBotToken() {
        return "2007254728:AAFW03QGLwQ6L1MlbIU8DalqPCEtdIUFOdc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            
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

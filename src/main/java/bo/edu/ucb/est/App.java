package bo.edu.ucb.est;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/**
 *
 * @author melany.182
 */
public class App {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi=new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Atm());
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
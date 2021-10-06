package bo.edu.ucb.est;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
// import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 *
 * @author melany.182

 * Estados
-6: Primera vez, saludo
-5: Registro del nombre
-4: Registro del pin
-3: Verificación del pin
-2: Ingreso del pin
-1: Menú principal
 0: Ingreso de la opción
 1: Visualización del saldo
 2: 
21: 
 3:
31:
 4:
41:
42:
 5:
*/
public class Atm extends TelegramLongPollingBot {
    private List<Usuario> usuarios=new ArrayList<Usuario>();
    private List<Integer> numerosCuentas=new ArrayList<Integer>();
    
    @Override
    public String getBotToken() {
        return "2007254728:AAFW03QGLwQ6L1MlbIU8DalqPCEtdIUFOdc";
    }

    @Override
    public void onUpdateReceived(Update update) { // se ejecuta cada vez que llega un mensaje (actualización)
        for (int i=0; i<usuarios.size(); i++) {
            System.out.println("\n"+usuarios.get(i).toString());
    	}
    	int aux=0;
    	Long idusuario=update.getMessage().getFrom().getId();
    	for (int i=0; i<usuarios.size(); i++) { // verificación de existencia
            if ((usuarios.get(i).getUserId()).equals(idusuario)) { // si ya existe
                aux=1;
                break;
            }
    	}
        switch (aux) {
            case 0: { // si no existe todavía
                Usuario usuario=new Usuario(idusuario,-6);
                usuarios.add(usuario);
                break;
            }
            case 1: break;
        }
    	/*if (aux==0) { // si no existe todavía
            Usuario usuario=new Usuario(idusuario,-6);
            usuarios.add(usuario);
    	}*/
    	System.out.println("Mensaje: "+update.toString()+"\n");
        
        if (update.hasMessage()) {
            Message message=update.getMessage();
            if (message.hasText()) { // ***************
            for (int i=0; i<usuarios.size(); i++) {
                if ((usuarios.get(i).getUserId()).equals(update.getMessage().getFrom().getId())) {
                    int state=usuarios.get(i).getEstado();
                    switch (state) {
                        case -6: {
                            enviarMensaje(update,"*¡Bienvenido/a al Banco de La Fortuna!* 🏦");
                            enviarMensaje(update,"He notado que aún no es cliente. Procedamos a registrar sus datos.");
                            enviarMensaje(update,"¿Cuál es su nombre completo?");
                            usuarios.get(i).setEstado(-5);
                            break;
                        }
                        case -5: {
                            usuarios.get(i).setNombre(update.getMessage().getText());
                            enviarMensaje(update,"Por favor, elija un PIN de seguridad. Este le será requerido cada vez que ingrese al sistema.");
                            usuarios.get(i).setEstado(-4);
                            break;
                        }
                        case -4: {
                            usuarios.get(i).setPin(update.getMessage().getText());
                            enviarMensaje(update,"¡Se le ha registrado exitosamente!");
                            enviarMensaje(update,"_Envíe un mensaje cualquiera para continuar._ 📩");
                            usuarios.get(i).setEstado(-3);
                            break;
                        }
                        case -3: {
                            enviarMensaje(update,"¡Hola de nuevo, estimado/a "+usuarios.get(i).getNombre()+"!");
                            enviarMensaje(update,"Solo por seguridad ¿cuál es su PIN?");
                            usuarios.get(i).setEstado(-2);
                            break;
                        }
                        case -2: {
                            if ((update.getMessage().getText()).equals(usuarios.get(i).getPin())) {
                                enviarMensaje(update,"*¡Bienvenido/a!*");
                                enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                enviarMensaje(update,"Elija una opción:");
                                usuarios.get(i).setEstado(0);
                            }
                            else {
                                enviarMensaje(update,"*El PIN ingresado es incorrecto.* 🚫");
                                enviarMensaje(update,"¡Hola de nuevo, estimado/a "+usuarios.get(i).getNombre()+"!");
                                enviarMensaje(update,"Solo por seguridad ¿cuál es su PIN?");
                                usuarios.get(i).setEstado(-2);
                            }
                            break;
                        }
                        case -1: {
                            enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                            enviarMensaje(update,"Elija una opción:");
                            usuarios.get(i).setEstado(0);
                            break;
                        }
                        case 0: {
                            try {
                                int opcion=Integer.parseInt(update.getMessage().getText());
                                usuarios.get(i).setOpcion(opcion);
                                //String listaCuentas="";
                                int flag=0;
                                if ((usuarios.get(i).getCuentas().size())>0) {
                                    int j=1;
                                    for (Cuenta cuenta : usuarios.get(i).getCuentas()) {
                                        usuarios.get(i).setListaCuentas("*"+j+".* _"+cuenta.getTipo()+"_ "+cuenta.getNumero()+"\n");// listaCuentas=listaCuentas+"*"+j+".* _"+cuenta.getTipo()+"_ "+cuenta.getNumero()+"\n";
                                        j++;
                                    }
                                    flag=1;
                                }
                                switch (usuarios.get(i).getOpcion()) {
                                    case 1: {
                                        if (flag==0) {         /// VER POSIBILIDAD DE BOTONES
                                            enviarMensaje(update,"Usted no tiene registrada ninguna cuenta. Para consultar esta opción, primero *cree una cuenta*.");
                                            enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                            enviarMensaje(update,"Elija una opción:");
                                            usuarios.get(i).setEstado(0);
                                        }
                                        else {
                                            enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                            usuarios.get(i).setEstado(1);
                                        }
                                        break;
                                    }
                                    case 2: {
                                        if (flag==0) {
                                            enviarMensaje(update,"Usted no tiene registrada ninguna cuenta. Para consultar esta opción, primero *cree una cuenta*.");
                                            enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                            enviarMensaje(update,"Elija una opción:");
                                            usuarios.get(i).setEstado(0);
                                        }
                                        else {
                                            enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                            usuarios.get(i).setEstado(2);
                                        }
                                        break;
                                    }
                                    case 3: {
                                        if (flag==0) {
                                            enviarMensaje(update,"Usted no tiene registrada ninguna cuenta. Para consultar esta opción, primero *cree una cuenta*.");
                                            enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                            enviarMensaje(update,"Elija una opción:");
                                            usuarios.get(i).setEstado(0);
                                        }
                                        else {
                                            enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                            usuarios.get(i).setEstado(3);
                                        }
                                        break;
                                    }
                                    case 4: {
                                        enviarMensaje(update,"Seleccione la moneda de su nueva cuenta:\n*1.* Bolivianos\n*2.* Dólares");
                                        usuarios.get(i).setEstado(4);
                                        break;
                                    }
                                    case 5: {
                                        enviarMensaje(update,"¡Hasta pronto!");
                                        enviarMensaje(update,"_Cuando desee volver a ingresar al sistema, envíe un mensaje cualquiera._ 📩");
                                        usuarios.get(i).setEstado(-3);
                                        break;
                                    }
                                    default: {
                                        enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                        enviarMensaje(update,"Elija una opción:");
                                        usuarios.get(i).setEstado(0);
                                        break;
                                    }
                                }
                            }
                            catch (NumberFormatException e) {
                                e.printStackTrace();
                                enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                enviarMensaje(update,"Elija una opción:");
                                usuarios.get(i).setEstado(0);
                            }
                            break;
                        }
                        case 1: { // consulta de saldo
                            try {
                                int flag=0;
                                int opcCuenta=Integer.parseInt(update.getMessage().getText());
                                usuarios.get(i).setOpcionCuenta(opcCuenta);
                                for (int j=0; j<usuarios.get(i).getCuentas().size(); j++) {
                                    if ((usuarios.get(i).getOpcionCuenta())==(j+1)) {
                                        flag=1;
                                        enviarMensaje(update,usuarios.get(i).getCuentas().get(j).toString());
                                        //
                                        //
                                        // botón maybe
                                        //
                                        //
                                        //
                                        break;
                                    }
                                }
                                if (flag==0) {
                                    enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                    usuarios.get(i).setEstado(1);
                                }
                                else {
                                    enviarMensaje(update,"_Menú Principal_\n\n*1.* Ver saldo.\n*2.* Retirar dinero.\n*3.* Depositar dinero.\n*4.* Crear cuenta.\n*5.* Salir.");
                                    enviarMensaje(update,"Elija una opción:");
                                    usuarios.get(i).setEstado(0);
                                }
                            }
                            catch (Exception e) { // NumberFormatException más específico
                                e.printStackTrace();
                                enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                usuarios.get(i).setEstado(1);
                            }
                            break;
                        }
                        case 2: { // retirar dinero
                            try {
                                int flag=0;
                                int opcCuenta=Integer.parseInt(update.getMessage().getText());
                                usuarios.get(i).setOpcionCuenta(opcCuenta);
                                for (int j=0; j<usuarios.get(i).getCuentas().size(); j++) {
                                    if ((usuarios.get(i).getOpcionCuenta())==(j+1)) {
                                        flag=1;
                                        enviarMensaje(update,"Saldo actual: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                        enviarMensaje(update,"Ingrese la cantidad a retirar (en "+usuarios.get(i).getCuentas().get(j).getMoneda()+"):");
                                        break;
                                    }
                                }
                                if (flag==0) {
                                    enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                    usuarios.get(i).setEstado(2);
                                }
                                else {
                                    usuarios.get(i).setEstado(21);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                usuarios.get(i).setEstado(2);
                            }
                            break;
                        }
                        case 21: {
                            for (int j=0; j<usuarios.get(i).getCuentas().size(); j++) {
                                try {
                                    double monto=Double.parseDouble(update.getMessage().getText()); // se podría mejorar
                                    usuarios.get(i).setMonto(monto);
                                    if ((usuarios.get(i).getOpcionCuenta())==(j+1)) {
                                        if ((usuarios.get(i).getMonto()>0) && (usuarios.get(i).getMonto()<=usuarios.get(i).getCuentas().get(j).getSaldo())) {
                                            usuarios.get(i).getCuentas().get(j).setSaldo((usuarios.get(i).getCuentas().get(j).getSaldo())-(usuarios.get(i).getMonto()));
                                            enviarMensaje(update,"Transacción realizada exitosamente. ✅\nNuevo saldo: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                            usuarios.get(i).setEstado(-1);
                                        }
                                        else { // if (monto==0 || monto<0 || monto>saldo) 
                                            enviarMensaje(update,"El monto ingresado no es válido. Ingrese un monto diferente.");
                                            // OK.
                                            enviarMensaje(update,"Saldo actual: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                            enviarMensaje(update,"Ingrese la cantidad a retirar (en "+usuarios.get(i).getCuentas().get(j).getMoneda()+"):");
                                            usuarios.get(i).setEstado(21);
                                        }
                                        break;
                                    }
                                }
                                catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    enviarMensaje(update,"No puede ingresar un valor que no sea un número real. Ingrese un valor diferente.");
                                    // botón
                                    enviarMensaje(update,"Saldo actual: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                    enviarMensaje(update,"Ingrese la cantidad a retirar (en "+usuarios.get(i).getCuentas().get(j).getMoneda()+"):");
                                    usuarios.get(i).setEstado(21);
                                }
                            }
                            break;
                        }
                        case 3: { // depositar dinero
                            try {
                                int flag=0;
                                int opcCuenta=Integer.parseInt(update.getMessage().getText());
                                usuarios.get(i).setOpcionCuenta(opcCuenta);
                                for (int j=0; j<usuarios.get(i).getCuentas().size(); j++) {
                                    if ((usuarios.get(i).getOpcionCuenta())==(j+1)) {
                                        flag=1;
                                        enviarMensaje(update,"Saldo actual: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                        enviarMensaje(update,"Ingrese la cantidad a depositar (en "+usuarios.get(i).getCuentas().get(j).getMoneda()+"):");
                                        break;
                                    }
                                }
                                if (flag==0) {
                                    enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                    usuarios.get(i).setEstado(3);
                                }
                                else {
                                    usuarios.get(i).setEstado(31);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                enviarMensaje(update,"Seleccione una de sus cuentas:\n"+usuarios.get(i).getListaCuentas());
                                usuarios.get(i).setEstado(3);
                            }
                            break;
                        }
                        case 31: {
                            for (int j=0; j<usuarios.get(i).getCuentas().size(); j++) {
                                try {
                                    double monto=Double.parseDouble(update.getMessage().getText()); // se podría mejorar
                                    usuarios.get(i).setMonto(monto);
                                    if ((usuarios.get(i).getOpcionCuenta())==(j+1)) {
                                        if ((usuarios.get(i).getMonto()>0)) {
                                            usuarios.get(i).getCuentas().get(j).setSaldo((usuarios.get(i).getCuentas().get(j).getSaldo())+(usuarios.get(i).getMonto()));
                                            enviarMensaje(update,"Transacción realizada exitosamente. ✅\nNuevo saldo: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                            usuarios.get(i).setEstado(-1);
                                        }
                                        else { // if (monto==0 || monto<0)
                                            enviarMensaje(update,"El monto ingresado no es válido. Ingrese un monto diferente.");
                                            // OK.
                                            enviarMensaje(update,"Saldo actual: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                            enviarMensaje(update,"Ingrese la cantidad a depositar (en "+usuarios.get(i).getCuentas().get(j).getMoneda()+"):");
                                            usuarios.get(i).setEstado(31);
                                        }
                                        break;
                                    }
                                }
                                catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    enviarMensaje(update,"No puede ingresar un valor que no sea un número real. Ingrese un valor diferente.");
                                    // botón
                                    enviarMensaje(update,"Saldo actual: "+usuarios.get(i).getCuentas().get(j).getSaldo()+" "+usuarios.get(i).getCuentas().get(j).getMoneda());
                                    enviarMensaje(update,"Ingrese la cantidad a depositar (en "+usuarios.get(i).getCuentas().get(j).getMoneda()+"):");
                                    usuarios.get(i).setEstado(31);
                                }
                            }
                            break;
                        }
                        case 4: { // crear cuenta
                            try {
                                int opcMon=Integer.parseInt(update.getMessage().getText());
                                usuarios.get(i).setOpcMoneda(String.valueOf(opcMon));
                                if ((usuarios.get(i).getOpcMoneda()).equals("1")) {
                                    usuarios.get(i).setOpcMoneda("Bolivianos");
                                }
                                else if ((usuarios.get(i).getOpcMoneda()).equals("2")) {
                                    usuarios.get(i).setOpcMoneda("Dólares");
                                }
                                else {
                                    enviarMensaje(update,"Seleccione la moneda de su nueva cuenta:\n*1.* Bolivianos\n*2.* Dólares");
                                    usuarios.get(i).setEstado(4);
                                    break; // importante
                                }
                                enviarMensaje(update,"Seleccione el tipo de su nueva cuenta:\n1. Cuenta Corriente\n2. Caja de Ahorros");
                                usuarios.get(i).setEstado(41);
                            }
                            catch (NumberFormatException e) {
                                e.printStackTrace();
                                enviarMensaje(update,"Seleccione la moneda de su nueva cuenta:\n*1.* Bolivianos\n*2.* Dólares");
                                usuarios.get(i).setEstado(4);
                            }
                            break;
                        }
                        case 41: {
                            try {
                                int opcTip=Integer.parseInt(update.getMessage().getText());
                                usuarios.get(i).setOpcTipo(String.valueOf(opcTip));
                                if ((usuarios.get(i).getOpcTipo()).equals("1")) {
                                    usuarios.get(i).setOpcTipo("Cuenta Corriente");
                                }
                                else if ((usuarios.get(i).getOpcTipo()).equals("2")) {
                                    usuarios.get(i).setOpcTipo("Caja de Ahorros");
                                }
                                else {
                                    enviarMensaje(update,"Seleccione el tipo de su nueva cuenta:\n1. Cuenta Corriente\n2. Caja de Ahorros");
                                    usuarios.get(i).setEstado(41);
                                    break; // importante
                                }
                                String cuentaGen=String.valueOf(generarNumeroAleatorio());
                                usuarios.get(i).setCuentaGenerada(cuentaGen);
                                Cuenta nuevaCuenta=new Cuenta(usuarios.get(i).getCuentaGenerada(),usuarios.get(i).getOpcMoneda(),usuarios.get(i).getOpcTipo(),0);
                                usuarios.get(i).addCuenta(nuevaCuenta); // importante
                                
                                SendMessage sendMessage=new SendMessage();
                                sendMessage.setText("Se le ha creado una cuenta de tipo "+usuarios.get(i).getOpcTipo()+", en "+usuarios.get(i).getOpcMoneda()+" y con un saldo inicial igual a 0.00. *¡Felicidades!*\nEl número de su cuenta es: "+usuarios.get(i).getCuentaGenerada());
                                sendMessage.setParseMode(ParseMode.MARKDOWN);
                                sendMessage.setChatId(message.getChatId().toString());
                                
                                InlineKeyboardMarkup ikm=new InlineKeyboardMarkup();
                                List<List<InlineKeyboardButton>> inlineButtons=new ArrayList<>();
                                List<InlineKeyboardButton> lista=new ArrayList<>();
                                InlineKeyboardButton button1=new InlineKeyboardButton();
                                button1.setText("OK. ¡Gracias!");
                                button1.setCallbackData("menu_principal");
                                lista.add(button1);
                                inlineButtons.add(lista);
                                ikm.setKeyboard(inlineButtons);
                                sendMessage.setReplyMarkup(ikm);
                                try {
                                    execute(sendMessage);
                                }
                                catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                                //enviarMensajeBoton(update,"Se le ha creado una cuenta de tipo "+usuarios.get(i).getOpcTipo()+", en "+usuarios.get(i).getOpcMoneda()+" y con un saldo inicial igual a 0.00. ¡Felicidades!\nEl número de su cuenta es : "+usuarios.get(i).getCuentaGenerada(),ikm);
                                usuarios.get(i).setEstado(-1);
                            }
                            catch (NumberFormatException e) {
                                e.printStackTrace();
                                enviarMensaje(update,"Seleccione el tipo de su nueva cuenta:\n1. Cuenta Corriente\n2. Caja de Ahorros");
                                usuarios.get(i).setEstado(41);
                            }
                            break;
                        }
                        default: exit(0);
                    }
                }
            }
        }
        }
        if (update.hasCallbackQuery()) { // else if (update.hasInlineQuery()) { // 
            Message message=update.getCallbackQuery().getMessage();
            CallbackQuery cbq=update.getCallbackQuery();
            // String x=update.getCallbackQuery().getId();
            //Long id=cbq.getFrom().getId();
            String data=cbq.getData();
            //int estado=0;
            
            /*AnswerCallbackQuery acq=new AnswerCallbackQuery();
            acq.setCallbackQueryId(x);
            acq.setText("a");
            acq.notify();*/
            
            SendMessage sendMessage=new SendMessage();
            sendMessage.setParseMode(ParseMode.MARKDOWN);
            sendMessage.setChatId(message.getChatId().toString()); // why?
            //sendMessage.setChatId(update.getMessage().getChatId().toString());
            if (data.equals("menu_principal")) {
                
                System.out.println("\nFUNCIONAAAAAAAAAAAAAAAAAAAAa\n");
                
                sendMessage.setText("Elija una opción:\n1. Ver saldo.\n2. Retirar dinero.\n3. Depositar dinero.\n4. Crear cuenta.\n5. Salir.");
                //estado=0;
            }
            else if (data.equals("otra_cosa")) {
                //estado=-1;
            }
            //else {}
            
            try {
                execute(sendMessage);
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
            
            /*for (int i=0; i<usuarios.size(); i++) {
                if ((usuarios.get(i).getUserId()).equals(id)) {
                    usuarios.get(i).setEstado(estado);
                    break; 
                }
            }*/
        }
        // else { }
    }
  
    public void enviarMensaje(Update update,String mensaje) {
        SendMessage message=new SendMessage(); // crea el objeto para enviar el mensaje
	message.setChatId(update.getMessage().getChatId().toString()); // define a quién se le enviará el mensaje
	message.setText(mensaje);
        message.setParseMode(ParseMode.MARKDOWN);
	try {
            execute(message); // envía el mensaje
	}
	catch (TelegramApiException e) {
	    e.printStackTrace();
	}
    }
    
    public void enviarMensajeBoton(Update update,String mensaje,InlineKeyboardMarkup ikm) {
        SendMessage message=new SendMessage(); // crea el objeto para enviar el mensaje
	// message.setChatId(message.getChatId()); // por qué no funciona?
        message.setChatId(update.getMessage().getChatId().toString()); // define a quién se le enviará el mensaje
	message.setText(mensaje);
        message.setParseMode(ParseMode.MARKDOWN);
        message.setReplyMarkup(ikm);
	try {
            execute(message); // envía el mensaje
	}
	catch (TelegramApiException e) {
	    e.printStackTrace();
	}
    }
    
    public int generarNumeroAleatorio() { // método para generar un número de cuenta que no se repetirá en toda la ejecución del programa
        int numeroCuenta=(int)Math.floor(Math.random()*(1000000-1+1)+1); // (N-M+1)+M) valor entre [M y N]
        if (validar(numeroCuenta)==true) {
            numerosCuentas.add(numeroCuenta);
            //return numeroCuenta;
        }
        else {
            generarNumeroAleatorio();
        }
        return numeroCuenta;
    }
    
    public boolean validar(int x) {
        for (int i=0; i<numerosCuentas.size(); i++) {
            if (numerosCuentas.get(i)==x) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String getBotUsername() {
        return "atmKaune_bot";
    }
}

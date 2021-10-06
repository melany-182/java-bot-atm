package bo.edu.ucb.est;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author melany.182
 */
public class Usuario {
    private Long userId;
    private int estado;
    private int opcion;
    private int opcionCuenta;
    private String nombre;
    private String pin;
    private double monto;
    private List<Cuenta> cuentas;
    private String listaCuentas;
    private String opcMoneda;
    private String opcTipo;
    private String cuentaGenerada;
    
    public Usuario(Long userId,int estado) {
        this.userId=userId;
        this.estado=estado;
        this.cuentas=new ArrayList();
        this.listaCuentas="";
    }

    public Long getUserId() {
        return userId;
    }

    public int getEstado() {
        return estado;
    }
    
    public int getOpcion() {
        return opcion;
    }
    
    public int getOpcionCuenta() {
        return opcionCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPin() {
        return pin;
    }

    public double getMonto() {
        return monto;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
    
    public String getListaCuentas() {
        return listaCuentas;
    }
    
    public String getOpcMoneda() {
        return opcMoneda;
    }
    
    public String getOpcTipo() {
        return opcTipo;
    }
    
    public String getCuentaGenerada() {
        return cuentaGenerada;
    }

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public void setEstado(int estado) {
        this.estado=estado;
    }
    
    public void setOpcion(int opcion) {
        this.opcion=opcion;
    }
    
    public void setOpcionCuenta(int opcionCuenta) {
        this.opcionCuenta=opcionCuenta;
    }

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setPin(String pin) {
        this.pin=pin;
    }

    public void setMonto(double monto) {
        this.monto=monto;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas=cuentas;
    }
    
    public void setListaCuentas(String listaCuentas) {
        this.listaCuentas=listaCuentas;
    }
    
    public void setOpcMoneda(String opcMoneda) {
        this.opcMoneda=opcMoneda;
    }
    
    public void setOpcTipo(String opcTipo) {
        this.opcTipo=opcTipo;
    }
    
    public void setCuentaGenerada(String cuentaGenerada) {
        this.cuentaGenerada=cuentaGenerada;
    }
    
    public void addCuenta(Cuenta nuevaCuenta) {
        this.cuentas.add(nuevaCuenta); // después de tantos intentos, faltaba el this para que funcione
    }

    @Override
    public String toString() {
        return "Usuario{"+"userId="+userId+", estado="+estado+", opcion="+opcion+", opcionCuenta="+opcionCuenta+", nombre="+nombre+", pin="+pin+", monto="+monto+", cuentas="+cuentas+", opcMoneda="+opcMoneda+", opcTipo="+opcTipo+", cuentaGenerada="+cuentaGenerada+"}";
    }
}

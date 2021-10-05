package bo.edu.ucb.est;
import java.util.ArrayList;
/**
 *
 * @author melany.182
 */
public class Usuario {
    private Long userId;
    private int estado;
    private String nombre;
    private String pin;
    private double monto;
    private ArrayList<Cuenta> cuentas;
    
    public Usuario(Long userId,int estado) {
        this.userId=userId;
        this.estado=estado;
    }

    public Long getUserId() {
        return userId;
    }

    public int getEstado() {
        return estado;
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

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public void setEstado(int estado) {
        this.estado=estado;
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

    public void setCuentas(ArrayList<Cuenta> cuentas) {
        this.cuentas=cuentas;
    }

    @Override
    public String toString() {
        return "Usuario["+"userId="+userId+", estado="+estado+", nombre="+nombre+", pin="+pin+", monto="+monto+", cuentas="+cuentas+"]";
    }
}

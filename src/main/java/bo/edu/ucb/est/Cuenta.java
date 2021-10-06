package bo.edu.ucb.est;
/**
 *
 * @author melany.182
 */
public class Cuenta {
    private String numero;
    private String moneda;
    private String tipo;
    private double saldo;

    public Cuenta(String numero,String moneda,String tipo,double saldo) {
        this.numero=numero;
        this.moneda=moneda;
        this.tipo=tipo;
        this.saldo=saldo;
    }

    public String getNumero() {
        return numero;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getTipo() {
        return tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setNumero(String numero) {
        this.numero=numero;
    }

    public void setMoneda(String moneda) {
        this.moneda=moneda;
    }

    public void setTipo(String tipo) {
        this.tipo=tipo;
    }

    public void setSaldo(double saldo) {
        this.saldo=saldo;
    }
    
    @Override
    public String toString() {
        return "Número de cuenta: "+getNumero()+"\nMoneda: "+getMoneda()+"\nTipo: "+getTipo()+"\nSaldo actual: "+getSaldo()+" "+getMoneda();
    }
}

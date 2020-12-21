package ConcessionariaPop.model;

import java.util.Date;

public class Compra implements Imprimir{
    private Carro veiculo;
    private Cliente cliente;
    private Date data;
    private String formaPagamento;
    private String status;
    private double precoFinal;
 
    public Compra(Carro veiculo, Cliente cliente, Date data, String formaPagamento){
        this.cliente=cliente;
        this.data=data;
        this.formaPagamento=formaPagamento;
        this.veiculo=veiculo;
        this.status = "Em Andamento";
        if (formaPagamento.equals("A Vista"))
            precoFinal=veiculo.getPreco();
        else
            precoFinal=veiculo.getPreco()+veiculo.getPreco()*0.05;
    }
    
    public void atualizarStatus(){
        this.status="Finalizado";
    }
    
    public double cacularParcela(){
        double entrada=this.precoFinal-(this.precoFinal*0.25);
        return (this.precoFinal-entrada)/36;
    }
    
    @Override
    public String imprimir(){
        String info = this.cliente.imprimir() + this.veiculo.imprimir();
        if (formaPagamento.equals("A Vista"))
            info+= "Forma de pagamento: " + formaPagamento + "\nPreço total: R$" + String.format("%.2f",precoFinal) + "\n";
        else{
            info+= "Forma de pagamento: " + formaPagamento + 
                    "\nEntrada: R$" + String.format("%.2f",(precoFinal * 0.25)) + "\nParcela: (36x) R$" + String.format("%.2f", cacularParcela());
        }
            return info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Carro getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Carro veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
 
 
 
 
 
 
}

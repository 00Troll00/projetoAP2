package ConcessionariaPop.model;

public abstract class Carro implements Imprimir{
	protected int ano;
	protected String modelo;
	protected String marca;
	protected double preco;
        protected String cor;

    public Carro(int ano, String modelo, String marca, double preco, String cor){
        this.modelo=modelo;
        this.marca=marca;
        this.preco=preco;
        this.ano=ano;
        this.cor=cor;
    }
        
    @Override
    public String imprimir(){
        return "Modelo do Carro: " + this.modelo +
                "\nMarca do Carro: " + this.marca +
                "\nAno do Carro: " + this.ano + "\n";
    }
    
    
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void atualizarEstoque(){
        
    }
}

package ConcessionariaPop.model;

public class CarroNovo extends Carro implements Imprimir{
    private int qntEstoque;
    

    
    public CarroNovo(int ano, String modelo, String marca, double preco, int qntEstoque, String cor){
        super(ano,modelo, marca, preco, cor);
        this.qntEstoque=qntEstoque;        
    }
    
    @Override
    public String imprimir(){
        return super.imprimir() + "\nStatus do Carro: Novo\n";
    }
    
    @Override
    public void atualizarEstoque(){
        this.qntEstoque--;
    }
    
    public void setQntEstoque(int qntEstoque){
        this.qntEstoque=qntEstoque;
    }
    
    public int getQntEstoque(){
        return qntEstoque;
    }

   
}

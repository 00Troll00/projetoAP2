package ConcessionariaPop.model;

public class Cliente implements Imprimir{
    private String nome;
    private String cpf;
    private String telefone;
    private Endereco endereco;



public Cliente(){}

    public Cliente(String nome, String cpf, String telefone, Endereco endereco){
        this.nome=nome;
        this.cpf=cpf;
        this.telefone=telefone;
        this.endereco=endereco;
    }

    
    public String imprimir(){
        return "Nome do Cliente: " + this.nome + "\nCPF do Cliente: " + this.cpf + "\n";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
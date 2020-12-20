package ConcessionariaPop.controller.utils;

import ConcessionariaPop.model.Cliente;
import ConcessionariaPop.model.Endereco;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class IODatabaseCliente {
    private final static String PATH = System.getProperty("user.dir") + "\\pseudo-db\\clientData.txt";
    
    public static ObservableList<Cliente> ler(){
        ObservableList<Cliente> cList = FXCollections.observableArrayList();
        
        try(BufferedReader br = new BufferedReader(new FileReader(PATH))){
            String line = br.readLine();

            while (line != null){
                String fields [] = line.split(",");
                Cliente c = new Cliente(fields[0], fields[1], fields[2], new Endereco(fields[3], 
                        Integer.parseInt(fields[4]), fields[5], fields[6]));
                
                cList.add(c);
                line = br.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            return cList;
        }
    }
    
    public static void remover(ObservableList<Cliente> cList){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))){
            for(Cliente c : cList){
                String line;
                line = c.getNome() + "," + c.getCpf() + "," + c.getTelefone() + "," +
                        c.getEndereco().getLogradouro() + "," + c.getEndereco().getNumero() + "," +
                        c.getEndereco().getBairro() + "," + c.getEndereco().getCidade();
                
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public static void adicionar(Cliente c){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true))){
            String line;
                line = c.getNome() + "," + c.getCpf() + "," + c.getTelefone() + "," +
                        c.getEndereco().getLogradouro() + "," + c.getEndereco().getNumero() + "," +
                        c.getEndereco().getBairro() + "," + c.getEndereco().getCidade();
                
                bw.write(line);
                bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

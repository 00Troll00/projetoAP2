package ConcessionariaPop.controller.utils;

import ConcessionariaPop.model.Carro;
import ConcessionariaPop.model.CarroNovo;
import ConcessionariaPop.model.CarroSemiNovo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class IODatabaseCarro {
    private final static String PATH_CARRO_NOVO = System.getProperty("user.dir") + "\\pseudo-db\\carroNovoData.txt";
    private final static String PATH_CARRO_SEMINOVO = System.getProperty("user.dir") + "\\pseudo-db\\carroSemi-novoData.txt";
    
    public static ObservableList<Carro> ler(){
        ObservableList<Carro> cList = FXCollections.observableArrayList();
        
        try(BufferedReader br = new BufferedReader(new FileReader(PATH_CARRO_NOVO))){
            String line = br.readLine();

            while (line != null){
                String fields [] = line.split(",");
                Carro c = new CarroNovo(Integer.parseInt(fields[0]), fields[1], fields[2],
                        Double.parseDouble(fields[3]), Integer.parseInt(fields[4]), fields[5]);
                
                cList.add(c);
                line = br.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        try(BufferedReader br = new BufferedReader(new FileReader(PATH_CARRO_SEMINOVO))){
            String line = br.readLine();

            while (line != null){
                String fields [] = line.split(",");
                Carro c = new CarroSemiNovo(Integer.parseInt(fields[0]), fields[1], fields[2],
                        Double.parseDouble(fields[3]), Integer.parseInt(fields[4]), fields[5]);
                
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
    
    
    public static void remover(ObservableList<Carro> cList){
        ObservableList<Carro> cNovo = FXCollections.observableArrayList(
                cList.stream().filter(c -> c.getClass().equals(CarroNovo.class)).collect( Collectors.toList()));
        ObservableList<Carro> cSNovo = FXCollections.observableArrayList(
                cList.stream().filter(c -> c.getClass().equals(CarroSemiNovo.class)).collect(Collectors.toList()));
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_CARRO_NOVO))){
            for(Carro c : cNovo){
                CarroNovo cNew = (CarroNovo )c;
                String line = cNew.getAno() + "," + cNew.getModelo() + "," + cNew.getMarca() + "," +
                        cNew.getPreco() + "," + cNew.getQntEstoque() + "," + cNew.getCor();
                
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_CARRO_NOVO))){
            for(Carro c : cSNovo){
                CarroSemiNovo cSNew = (CarroSemiNovo )c;
                String line = cSNew.getAno() + "," + cSNew.getModelo() + "," + cSNew.getMarca() + "," +
                        cSNew.getPreco() + "," + cSNew.getKmRodado()+ "," + cSNew.getCor();
                
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public static void adicionar(Carro c){
        String PATH = (c.getClass().equals(CarroNovo.class)) ? PATH_CARRO_NOVO : PATH_CARRO_SEMINOVO;
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true))){
            String line;
            
            if (c.getClass().equals(CarroNovo.class)){
                CarroNovo cNew = (CarroNovo )c;
                line = cNew.getAno() + "," + cNew.getModelo() + "," + cNew.getMarca() + "," +
                        cNew.getPreco() + "," + cNew.getQntEstoque() + "," + cNew.getCor();
            } else{
                CarroSemiNovo cSNew = (CarroSemiNovo )c;
                line = cSNew.getAno() + "," + cSNew.getModelo() + "," + cSNew.getMarca() + "," +
                        cSNew.getPreco() + "," + cSNew.getKmRodado()+ "," + cSNew.getCor();
            }
                
            bw.write(line);
            bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ConcessionariaPop.controller;

import ConcessionariaPop.controller.utils.IODatabaseCarro;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ConcessionariaPop.model.Carro;
import ConcessionariaPop.model.CarroNovo;
import ConcessionariaPop.model.CarroSemiNovo;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLCadastroCarroViewController implements Initializable {
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneHome;
    @FXML
    private Label lblEstoque_km;
    @FXML
    private JFXTextField txtModelo;
    @FXML
    private JFXTextField txtKm_Estoque;
    @FXML
    private JFXToggleButton tBtnNovo;
    @FXML
    private JFXTextField txtAno;
    @FXML
    private JFXTextField txtMarca;
    @FXML
    private JFXTextField txtPreco;
    @FXML
    private JFXComboBox<String> comboBoxCor = new JFXComboBox();
    //declarações fora do fxml
    private ObservableList<String> cores;
    @FXML
    private TableView<Carro> tableViewCarrosCad;
    @FXML
    private TableColumn<Carro, String> colunaModelo;
    @FXML
    private TableColumn<Carro, String> colunaMarca;
    @FXML
    private TableColumn<Carro, Integer> colunaAno;
    @FXML
    private TableColumn<Carro, CarroSemiNovo> colunaKm;
    @FXML
    private TableColumn<Carro, CarroNovo> colunaEstoque;
    @FXML
    private TableColumn<Carro, String> colunaCor;
    @FXML
    private TableColumn<Carro, Double> colunaPreco;
    @FXML
    private Label lblErro;
    @FXML
    private JFXTextField txtPesquisar;
    
    private ObservableList<Carro> carro = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colunaMarca.setCellValueFactory(new PropertyValueFactory<Carro, String>("marca"));
        colunaModelo.setCellValueFactory(new PropertyValueFactory<Carro, String>("modelo"));
        colunaEstoque.setCellValueFactory(new PropertyValueFactory<Carro, CarroNovo>("qntEstoque"));
        colunaKm.setCellValueFactory(new PropertyValueFactory<Carro, CarroSemiNovo>("kmRodado"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<Carro, Integer>("ano"));
        colunaCor.setCellValueFactory(new PropertyValueFactory<Carro, String>("cor"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<Carro, Double>("preco"));
        
        carro = IODatabaseCarro.ler();
        
        carregarCores();
        updateTableCarrosCad();
        tableViewCarrosCad.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewCarrosCad.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selCarro(newValue));
    }
    
    public void updateTableCarrosCad(){
        tableViewCarrosCad.setItems(carro);
    }
    //método para colocar as cores no combo box
    public void carregarCores(){
        cores = FXCollections.observableArrayList("Preto","Cinza","Branco","Vermelho");
        comboBoxCor.setItems(cores);
    }
    //método para encerrar o programa
    @FXML
    private void handleCloseWindow(MouseEvent event) {
        System.exit(0);
    }
    //Usa o toggle button para alterar na GUI o nome do texto Kms Rodados/Estoque
    @FXML
    private void mudarKm_Estoque(ActionEvent event) {
        if(lblEstoque_km.getText().equals("KMs Rodados"))
            lblEstoque_km.setText("Estoque");
        else
            lblEstoque_km.setText("KMs Rodados");        
    }
    //método para adicionar um carro ao array list carros após precionar o botão "+"
    @FXML
    private void addCarro(ActionEvent event) {
        try{
            txtModelo.getText();
            int ano, km_estoque;
            double preco;
            //transforma os tipo String nos tipos usado pelo programa
            preco=Double.parseDouble(txtPreco.getText());
            ano=Integer.parseInt(txtAno.getText());
            km_estoque=Integer.parseInt(txtKm_Estoque.getText());
            //adiciona um carro semi novo caso o campo seja igual a "KMs Rodados"
            if(lblEstoque_km.getText().equals("KMs Rodados")){
                CarroSemiNovo carro = new CarroSemiNovo(ano, txtModelo.getText(), txtMarca.getText(), preco, km_estoque, comboBoxCor.getValue());
                this.carro.add(carro);
                IODatabaseCarro.adicionar(carro);
            //adiciona um carro novo caso o campo seja igual a "Estoque"
            }else{
                CarroNovo carro = new CarroNovo(ano, txtModelo.getText(), txtMarca.getText(), preco, km_estoque, comboBoxCor.getValue());
                this.carro.add(carro);
                IODatabaseCarro.adicionar(carro);
            }
            //todas as linhas abaixo servem para resetar os campos
            txtModelo.setText("");
            txtMarca.setText("");
            txtAno.setText("");
            txtPreco.setText("");
            txtKm_Estoque.setText("");
            comboBoxCor.setValue("");
            
            updateTableCarrosCad();
        }
        //exceção para caso os campos ano, preço e kilometros/estoque não possuam somente números
        catch(NumberFormatException e){            
        }
    }

    
    @FXML
    public void delCarro(){
        ObservableList<Carro> selectedRows;
        selectedRows = tableViewCarrosCad.getSelectionModel().getSelectedItems();
        
        for(Carro car: selectedRows){
            carro.remove(car);
        } 
        
        IODatabaseCarro.remover(carro);
    }
    
    public void selCarro(Carro carro){
        if (carro != null){
            txtModelo.setText(carro.getModelo());
            txtMarca.setText(carro.getMarca());
            txtAno.setText(String.valueOf(carro.getAno()));
            txtPreco.setText(String.valueOf(carro.getPreco()));
            comboBoxCor.setValue(carro.getCor());
        } else {
            txtModelo.setText("");
            txtMarca.setText("");
            txtAno.setText("");
            txtPreco.setText("");
            comboBoxCor.setValue("");
        }
    }
    
    public void searchCarro (MouseEvent event) throws Exception{
        lblErro.setText("");
        String modelo = txtPesquisar.getText();
        boolean isFind = false;     // gambiarra ou preguiça?
         
        for (Carro car: carro)
            if (car.getModelo().equals(modelo)){
                isFind = true;
                tableViewCarrosCad.setItems(FXCollections.observableArrayList(car));
            }
        
        if (!isFind){
            updateTableCarrosCad();
            lblErro.setText("Cliente inexistente.");
        }
    }
    
   
}

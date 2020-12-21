package ConcessionariaPop.controller;

import static ConcessionariaPop.controller.utils.Alerts.showInformation;
import ConcessionariaPop.model.Carro;
import ConcessionariaPop.model.CarroNovo;
import ConcessionariaPop.model.CarroSemiNovo;
import ConcessionariaPop.model.Cliente;
import ConcessionariaPop.model.Compra;
import ConcessionariaPop.model.Endereco;
import ConcessionariaPop.model.exceptions.ObjetoInexistenteException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author dog
 */
public class FXMLAgendamentoViewController implements Initializable{
    
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneHome;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private Label lblCampoVazio;
    @FXML
    private Label lblErro;
    @FXML
    private JFXTextField txtCPF;
    @FXML
    private JFXTextField txtCarro;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXComboBox<String> cbFormaPagamento;
    @FXML
    private TableView<Carro> tableViewCarro;
    @FXML
    private TableColumn<Carro, String> colummModelo;
    @FXML
    private TableColumn<Carro, String> colummMarca;
    @FXML
    private TableColumn<Carro, String> colummAno;
    @FXML
    private TableColumn<Carro, String> colummEstoque;
    @FXML
    private TableColumn<Carro, String> colummKMsRodados;
    @FXML
    private TableColumn<Carro, String> colummPreco;
    @FXML
    private TableColumn<Carro, String> colummStatus;
    //Declarações fora do FXML
    private ArrayList<Cliente> clientes = new ArrayList();
    private ArrayList<Carro> carros = new ArrayList();
    private ArrayList<Compra> compras = new ArrayList();
    
    private ObservableList<Carro> obsCarros;
    private ObservableList<String> obsFormasPagamento;
    private Carro carroSelecionado;

 
    @FXML
    private void handleCloseWindow(MouseEvent event) {//fechar o programa
        System.exit(0);
    }

    private void carregarCarroTableView(){
        colummModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colummMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colummAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colummPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colummEstoque.setCellValueFactory(new PropertyValueFactory<>("qntEstoque"));
        colummKMsRodados.setCellValueFactory(new PropertyValueFactory<>("kmRodado"));
        //criando varios carros para compor a table view
        Carro c1 = new CarroNovo(2019, "Modelo X", "Marca X", 50000, 2, "Vermelho");
        Carro c2 = new CarroNovo(2019, "Modelo Z", "Marca X", 50000, 2, "Vermelho");
        Carro c3 = new CarroSemiNovo(2019, "Modelo C", "Marca X", 50000, 2000, "Vermelho");
        Carro c4 = new CarroNovo(2019, "Modelo V", "Marca X", 50000, 2, "Vermelho");
        Carro c5 = new CarroSemiNovo(2019, "Modelo B", "Marca X", 50000, 200, "Vermelho");
        Carro c6 = new CarroSemiNovo(2019, "Modelo N", "Marca X", 50000, 200000, "Vermelho");
        Carro c7 = new CarroNovo(2019, "Modelo M", "Marca X", 50000, 2, "Vermelho");
        
        carros.add(c1);
        carros.add(c2);
        carros.add(c3);
        carros.add(c4);
        carros.add(c5);
        carros.add(c6);
        carros.add(c7);
        
        obsCarros = FXCollections.observableArrayList(carros);
        
        tableViewCarro.setItems(obsCarros);
        
        obsFormasPagamento = FXCollections.observableArrayList("A Vista", "Entrada + Parcelas");
        cbFormaPagamento.setItems(obsFormasPagamento);
        
    }
    
    private Cliente buscaClienteCpf() {//verifica se o cpf digitado existe
        for(Cliente c : clientes){
            if (c.getCpf().equals(txtCPF.getText()))
                return c;
        }
        return null;
    }

    @FXML
    private void add(ActionEvent event) {
        if(buscaClienteCpf()!=null){
            lblErro.setVisible(false);
            Carro car=null;
            for(Carro carro : obsCarros){
                if(carro.equals(carroSelecionado)){
                    if(carro instanceof CarroNovo){
                        carro.atualizarEstoque();
                    }
                    car=carro;   
                }
            }
               
            Cliente cliente = buscaClienteCpf();
            Date data = new Date();
            compras.add(new Compra(car, cliente, data, cbFormaPagamento.getValue()));
            if(car instanceof CarroSemiNovo)
               obsCarros.remove(car);
            if(car instanceof CarroNovo){
                obsCarros.remove(car);
                obsCarros.add(car);
            }
            //else
                //car.atualizarEstoque();
            //carros.add(car);
            txtCPF.setText("");
            txtCarro.setText("");
            cbFormaPagamento.setValue("");          
          
            cbFormaPagamento.setItems(obsFormasPagamento);
            updateTableCarros();
            showInformation(compras.get(compras.size()-1).imprimir());
        }
        else
            lblErro.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarCarroTableView();
        Endereco end = null;
        clientes.add(new Cliente("Alan", "122", "515151", end));
        tableViewCarro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarTableCarro(newValue));
        tableViewCarro.getSortOrder().add(colummModelo);
    }  
    
    public void selecionarTableCarro(Carro carro){
        txtCarro.setText(carro.getModelo() + ", " + carro.getMarca() + ", " + carro.getAno());
        carroSelecionado=carro;
    }
    
    @FXML
    private void searchModelo(MouseEvent event) {
        //lblErro.setText("");
        String modelo = txtPesquisar.getText();
        boolean isFind = false;     // gambiarra ou preguiça?
         
        for (Carro carro: obsCarros)
            if (carro.getModelo().equalsIgnoreCase(modelo)){
                isFind = true;
                tableViewCarro.setItems(FXCollections.observableArrayList(carro));
            }
        
        if (!isFind){
            updateTableCarros();
            //lblErro.setText("Cliente inexistente.");
        }
    }
    
    public void updateTableCarros(){   // Atualizar todos os clientes na tableView
        tableViewCarro.setItems(obsCarros);
    }
    
    //método para abrir a tela de historico
    @FXML
    public void abrirHistorico() throws IOException{        
            Parent root = FXMLLoader.load(getClass().getResource("/ConcessionariaPop/view/HistoricoAgendamentoView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Historico");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();   
    }
}
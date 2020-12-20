package ConcessionariaPop.controller;

import ConcessionariaPop.model.Carro;
import ConcessionariaPop.model.CarroNovo;
import ConcessionariaPop.model.CarroSemiNovo;
import ConcessionariaPop.model.Cliente;
import ConcessionariaPop.model.Compra;
import ConcessionariaPop.model.Endereco;
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
    private JFXTextField txtCPF;
    @FXML
    private JFXTextField txtCarro;
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
    //declarações da tela de historico
    @FXML
    private TableView<Compra> tableViewHistorico;
    @FXML
    private TableColumn<Compra, String> colummCpfHistorico;
    @FXML
    private TableColumn<Compra, String> colummModeloHistorico;
    @FXML
    private TableColumn<Compra, String> colummMarcaHistorico;
    @FXML
    private TableColumn<Compra, String> colummAnoHistorico;
    @FXML
    private TableColumn<Compra, String> colummFormaPagamentoHistorico;
    @FXML
    private TableColumn<Compra, String> colummDataHistorico;
    
    private ObservableList<Compra> obsCompras;
 
    @FXML
    private void handleCloseWindow(MouseEvent event) {
        System.exit(0);
    }

    private void carregarCarroTableView(){
        colummModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colummMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colummAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colummPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colummEstoque.setCellValueFactory(new PropertyValueFactory<>("qntEstoque"));
        colummKMsRodados.setCellValueFactory(new PropertyValueFactory<>("kmRodado"));
        
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
    
    private Cliente buscaClienteCpf() {
        for(Cliente c : clientes){
            if (c.getCpf().equals(txtCPF.getText()))
                return c;
        }
        return null;
    }

    @FXML
    private void add(ActionEvent event) {
        Cliente cliente = buscaClienteCpf();
        Date data = new Date();
        //carroSelecionado
        //System.out.println(carroSelecionado.getModelo());
        
        compras.add(new Compra(carroSelecionado, cliente, data, cbFormaPagamento.getValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarCarroTableView();
        Endereco end = null;
        clientes.add(new Cliente("Alan", "122", "515151", end));
        tableViewCarro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarTableCarro(newValue));
    }  
    
    public void selecionarTableCarro(Carro carro){
        txtCarro.setText(carro.getModelo() + ", " + carro.getMarca() + ", " + carro.getAno());
        carroSelecionado=carro;
    }
    //métodos da tela de historico
    @FXML
    public void abrirHistorico() throws IOException{
        //carregarComprasTableView();
        
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
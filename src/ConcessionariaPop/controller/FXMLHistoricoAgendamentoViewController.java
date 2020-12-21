/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConcessionariaPop.controller;

import static ConcessionariaPop.controller.utils.Alerts.showInformation;
import ConcessionariaPop.model.Carro;
import ConcessionariaPop.model.CarroNovo;
import ConcessionariaPop.model.Cliente;
import ConcessionariaPop.model.Compra;
import ConcessionariaPop.model.Endereco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author alanb
 */
public class FXMLHistoricoAgendamentoViewController implements Initializable {

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
    @FXML
    private TableColumn<Compra, String> colummStatusHistorico;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXTextField txtSelecionado;
    @FXML
    private Label lblErro;
    
    private ArrayList<Compra> compras = new ArrayList();
    
    private ObservableList<Compra> obsCompras;
    private Compra compraSelecionada;
    @FXML
    private JFXButton btnPdf;

    private void carregarComprasTableView(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        colummCpfHistorico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCliente().getCpf()));
        
        colummModeloHistorico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getVeiculo().getModelo()));
        colummMarcaHistorico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getVeiculo().getMarca()));
        colummAnoHistorico.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getVeiculo().getAno())));
        colummDataHistorico.setCellValueFactory((param) -> new SimpleStringProperty(sdf.format(param.getValue().getData())));
        colummFormaPagamentoHistorico.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        colummStatusHistorico.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        obsCompras = FXCollections.observableArrayList(compras);
        tableViewHistorico.setItems(obsCompras);
    }
    
    public void selecionarTableHistorico(Compra compra){
        txtSelecionado.setText(compra.getCliente().getCpf());
        compraSelecionada = compra;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Date data = new Date();
        Endereco end = null;
        Carro c1 = new CarroNovo(2019, "Modelo X", "Marca X", 50000, 2, "Vermelho");
        Cliente cliente = new Cliente("Alan", "122", "515151", end);
        compras.add(new Compra(c1, cliente, data, "A Vista"));
        
        Carro c2 = new CarroNovo(2018, "Modelo Z", "Marca Z", 50000, 2, "Vermelho");
        Cliente cliente2 = new Cliente("Alan", "111", "515151", end);
        compras.add(new Compra(c2, cliente2, data, "Entrada + Parcelas"));
        
        carregarComprasTableView();
        
        tableViewHistorico.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarTableHistorico(newValue));
    }    

    @FXML
    private void searchClient(MouseEvent event) {
        lblErro.setVisible(false);
        String cpf = txtPesquisar.getText();
        boolean isFind = false;     // gambiarra ou preguiça?
         
        for (Compra compra: obsCompras)
            if (compra.getCliente().getCpf().equals(cpf)){
                isFind = true;
                tableViewHistorico.setItems(FXCollections.observableArrayList(compra));
            }
        
        if (!isFind){
            updateTableHistorico();
            lblErro.setVisible(true);
        }
    }
    
    public void updateTableHistorico() throws NullPointerException{   // Atualizar todos os clientes na tableView
        tableViewHistorico.setItems(obsCompras);
    }

    @FXML
    private void confirmarCompra(ActionEvent event) {
        Compra com = null;
        for(Compra compra : obsCompras){
            if(compra.equals(compraSelecionada))
                com=compra;
        }
      
        com.atualizarStatus();
        obsCompras.remove(com);
        obsCompras.add(com);
        //obsCompras = FXCollections.observableArrayList(compras);;
        updateTableHistorico();
        
    }

    @FXML
    private void gerarPdf(ActionEvent event) {
        //System.out.println(compraSelecionada.imprimir());
        showInformation(compraSelecionada.imprimir());
    }
    
}

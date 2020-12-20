/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConcessionariaPop.controller;

import ConcessionariaPop.model.Carro;
import ConcessionariaPop.model.CarroNovo;
import ConcessionariaPop.model.Cliente;
import ConcessionariaPop.model.Compra;
import ConcessionariaPop.model.Endereco;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    private ArrayList<Compra> compras = new ArrayList();
    private ObservableList<Compra> obsCompras;

    private void carregarComprasTableView(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        colummCpfHistorico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCliente().getCpf()));
        
        colummModeloHistorico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getVeiculo().getModelo()));
        colummMarcaHistorico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getVeiculo().getMarca()));
        colummAnoHistorico.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getVeiculo().getAno())));
        colummDataHistorico.setCellValueFactory((param) -> new SimpleStringProperty(sdf.format(param.getValue().getData())));
        colummFormaPagamentoHistorico.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        
        obsCompras = FXCollections.observableArrayList(compras);
        tableViewHistorico.setItems(obsCompras);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Date data = new Date();
        Endereco end = null;
        Carro c1 = new CarroNovo(2019, "Modelo X", "Marca X", 50000, 2, "Vermelho");
        Cliente cliente = new Cliente("Alan", "122", "515151", end);
        compras.add(new Compra(c1, cliente, data, "Teste"));
        carregarComprasTableView();
    }    
    
}

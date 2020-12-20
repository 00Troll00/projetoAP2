package ConcessionariaPop.controller;

import ConcessionariaPop.controller.utils.Alerts;
import ConcessionariaPop.controller.utils.IODatabaseCliente;
import ConcessionariaPop.model.Cliente;
import ConcessionariaPop.model.Endereco;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;


public class FXMLCadastroViewController implements Initializable{    
    // COMPONENTES DA VIEW
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXTextField txtLogradouro;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXTextField txtTelefone;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private Label lblErro;
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneHome;
    @FXML
    private TableColumn<Cliente, String> colunaNome;
    @FXML
    private TableColumn<Cliente, String> colunaCPF;
    @FXML
    private TableColumn<Cliente, String> colunaTelefone;
    @FXML
    private TableColumn<Cliente, String> colunaCidade;
    @FXML
    private TableColumn<Cliente, String> colunaLogradouro;
    @FXML
    private TableColumn<Cliente, String> colunaBairro;
    @FXML
    private TableColumn<Cliente, String> colunaNum;
    @FXML
    private TableView<Cliente> tableViewClientes;
    
    // COMPONENTES DA CLASSE
    private ObservableList<Cliente> clientList = FXCollections.observableArrayList();
    
    
    @FXML
    public void newRegister() throws Exception {   
        try{
            Cliente newCliente = new Cliente(txtNome.getText(), txtCpf.getText(), txtTelefone.getText(),
                 new Endereco (txtLogradouro.getText(), Integer.parseInt(txtNumero.getText()), txtBairro.getText(), txtCidade.getText()));

            if(isCampoVazio(newCliente))
                throw new NullPointerException();
            
            clientList.add(newCliente);
            IODatabaseCliente.adicionar(newCliente);
            updateTableClients();
            
            txtNome.setText("");
            txtCpf.setText("");
            txtTelefone.setText("");
            txtLogradouro.setText("");
            txtNumero.setText("");
            txtBairro.setText("");
            txtCidade.setText("");      
            lblErro.setText("");
        } catch (NullPointerException e){ 
            lblErro.setText("Preencha todos os campos.");
        } catch (NumberFormatException ex) {
            lblErro.setText("Apenas números no campo 'Número'.");
        }
    }
    
    private boolean isCampoVazio (Cliente c){   // Verificar se há algum campo vazio
        if (c.getNome().trim().equals("") || c.getCpf().trim().equals("") || c.getTelefone().trim().equals("") || 
          c.getEndereco().getCidade().trim().equals("") || c.getEndereco().getBairro().trim().equals("") || 
          c.getEndereco().getLogradouro().trim().equals("") )
            return true;
        
        return false;
    }
    
    @FXML
    public void updateTableClients(){   // Atualizar todos os clientes na tableView
        tableViewClientes.setItems(clientList);
    }
    
    // Tornar a tabela editável
    @FXML
    public void editTable(){
        tableViewClientes.setEditable(true);
        colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaCPF.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaTelefone.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaCidade.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaNum.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaBairro.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaLogradouro.setCellFactory(TextFieldTableCell.forTableColumn());
    }
        
    //Salvar alterações na nossa lista
    @FXML
    public void onEditName(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.setNome(clientStringEditEvent.getNewValue());
        clientList = IODatabaseCliente.ler();
    }
        
    @FXML
    public void onEditCpf(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.setCpf(clientStringEditEvent.getNewValue());
        clientList = IODatabaseCliente.ler();
    }
        
    @FXML
    public void onEditTelefone(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.setTelefone(clientStringEditEvent.getNewValue());
        clientList = IODatabaseCliente.ler();
    }
        
    @FXML
    public void onEditCidade(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.getEndereco().setCidade(new SimpleStringProperty(clientStringEditEvent.getNewValue()));
        clientList = IODatabaseCliente.ler();
    }
        
    @FXML
    public void onEditLogradouro(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.getEndereco().setLogradouro(new SimpleStringProperty(clientStringEditEvent.getNewValue()));
        clientList = IODatabaseCliente.ler();
    }
        
    @FXML
    public void onEditBairro(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.getEndereco().setBairro(new SimpleStringProperty(clientStringEditEvent.getNewValue()));
        clientList = IODatabaseCliente.ler();
    }
        
    @FXML
    public void onEditNum(TableColumn.CellEditEvent<Cliente, String> clientStringEditEvent){
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        cliente.getEndereco().setNumero(Integer.parseInt(clientStringEditEvent.getNewValue()));
        clientList = IODatabaseCliente.ler();
    }
    
    // Quando clico no cliente, se ele não for vazio, seus dados irão preencher os campos de textos na view
    @FXML
    public void selCliente(Cliente cliente){
        if (cliente != null){
            txtNome.setText(cliente.getNome());
            txtCpf.setText(cliente.getCpf());
            txtTelefone.setText(cliente.getTelefone());
            txtBairro.setText(cliente.getEndereco().getBairro());
            txtNumero.setText(String.valueOf(cliente.getEndereco().getNumero()));
            txtLogradouro.setText(cliente.getEndereco().getLogradouro());
            txtCidade.setText(cliente.getEndereco().getCidade());
        } else {
            txtNome.setText("");
            txtCpf.setText("");
            txtTelefone.setText("");
            txtBairro.setText("");
            txtNumero.setText("");
            txtLogradouro.setText("");
            txtCidade.setText("");
        }
    }
        
    @FXML // Deletar cliente, mas antes aparece uma janela de confirmação
    public void delClienteSelect(){
        Optional<ButtonType> confirmacao = Alerts.showConfirmation("Deletar Cliente", "Tem certeza que deseja deletar o cliente?");
        ObservableList<Cliente> selectedRows;
        selectedRows = tableViewClientes.getSelectionModel().getSelectedItems();
        
        if (confirmacao.get().equals(ButtonType.OK))
            for(Cliente cliente: selectedRows)
                clientList.remove(cliente);
        
        IODatabaseCliente.remover(clientList);
        updateTableClients();
    }
 
    @FXML // Procurar cliente, caso seja encontrado, exibir na tablea, se não, atualize a tabela e exiba uma mensagem de erro
    public void searchClient (MouseEvent event) throws Exception{
        lblErro.setText("");
        String cpf = txtPesquisar.getText();
        boolean isFind = false;     // gambiarra ou preguiça?
         
        for (Cliente cliente: clientList)
            if (cliente.getCpf().equals(cpf)){
                isFind = true;
                tableViewClientes.setItems(FXCollections.observableArrayList(cliente));
            }
        
        if (!isFind){
            updateTableClients();
            lblErro.setText("Cliente inexistente.");
        }
    }
    
    
    @Override // Tornar a tabela editável, associar as colunas da tabela com os dados do cliente, preencher a tabela com os dados
    public void initialize(URL location, ResourceBundle resources) {
        clientList = IODatabaseCliente.ler();
        
        editTable();
        
        tableViewClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selCliente(newValue));
        
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaCidade.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco().getCidade()));
        colunaNum.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getEndereco().getNumero())));
        colunaBairro.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco().getBairro()));
        colunaLogradouro.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco().getLogradouro()));
        
        updateTableClients();
    }
        
    @FXML // Fechar janela
    public void handleCloseWindow(MouseEvent event) {
        System.exit(0);
    }
}

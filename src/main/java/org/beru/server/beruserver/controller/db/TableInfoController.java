package org.beru.server.beruserver.controller.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.beru.server.beruserver.model.db.info.Type;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.Table;

import java.net.URL;
import java.util.ResourceBundle;

public class TableInfoController implements Initializable {
    @FXML
    public TextField aiText;

    @FXML
    public ComboBox<String> charsetCB;
    @FXML
    public ComboBox<String> collationCB;
    @FXML
    public TextArea descriptionText;
    @FXML
    public ComboBox<String> engineText;
    @FXML
    public TableColumn<Column, String> dataTypeCol;
    @FXML
    public TableColumn<Column, String> defaultCol;
    @FXML
    public TableColumn<Column, String> extraCol;
    @FXML
    public TableColumn<Column, Integer> idCol;
    @FXML
    public TableColumn<Column, String> keyCol;
    @FXML
    public TableColumn<Column, String> nameCol;
    @FXML
    public TableColumn<Column, String> nullCol;
    public TableView<Column> tableCol;

    @FXML
    public Label itemsText;
    @FXML
    public TextField nameText;
    private Table table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setTable(Table table){
        this.table = table;
        nameText.setText(table.getName());
        engineText.setItems(FXCollections.observableArrayList(Type.engines));
        engineText.getSelectionModel().select(table.getEngine());
        aiText.setText(table.getAi()+"");
        charsetCB.setItems(FXCollections.observableArrayList(Type.charsets));
        charsetCB.getSelectionModel().select(table.getCharset());
        collationCB.setItems(FXCollections.observableArrayList(Type.collations));
        collationCB.getSelectionModel().select(table.getCollation());
        descriptionText.setText(table.getDescription());
        setColumns();
    }
    public void setColumns(){
        ObservableList<Column> columns = FXCollections.observableArrayList(table.getColumns());

        idCol.setCellValueFactory(new PropertyValueFactory<Column, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Column, String>("name"));
        dataTypeCol.setCellValueFactory(new PropertyValueFactory<Column, String>("dataType"));
        nullCol.setCellValueFactory(new PropertyValueFactory<Column, String>("nullable"));
        extraCol.setCellValueFactory(new PropertyValueFactory<Column, String>("extra"));
        keyCol.setCellValueFactory(new PropertyValueFactory<Column, String>("key"));
        defaultCol.setCellValueFactory(new PropertyValueFactory<Column, String>("colDefault"));

        tableCol.setItems(columns);
    }
    public void searchInTable(ActionEvent actionEvent) {
    }
}

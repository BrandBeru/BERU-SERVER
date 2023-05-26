package org.beru.server.beruserver.controller.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.beru.server.beruserver.model.db.info.Type;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;

import java.net.URL;
import java.util.ResourceBundle;

public class DBInfoController implements Initializable {
    public TextField schemaNameText;
    public ComboBox<String> charsetCB;
    public ComboBox<String> collationCB;
    public TextField databaseSizeText;
    public TextField sqlPathText;
    public TableView<Table> tablesTable;
    public Label itemsText;
    public TableColumn<Table, Integer> idCol;
    public TableColumn<Table, String> nameCol;
    public TableColumn<Table, String> engineCol;
    public TableColumn<Table, Integer> aiCol;
    public TableColumn<Table, String> dataLengthCol;
    public TableColumn<Table, String> descriptionCol;
    private DB database;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public DB getDatabase() {
        return database;
    }

    public void setDatabase(DB database) {
        this.database = database;
        schemaNameText.setText(database.getName());
        charsetCB.setItems(FXCollections.observableArrayList(Type.charsets));
        charsetCB.getSelectionModel().select(database.getCharset());
        collationCB.setItems(FXCollections.observableArrayList(Type.collations));
        collationCB.getSelectionModel().select(database.getCollation());
        databaseSizeText.setText(database.getSize());
        sqlPathText.setText(database.getSqlPath());
        setTables();
    }
    public void setTables() {
        ObservableList<Table> tables = FXCollections.observableArrayList(database.getTables());

        idCol.setCellValueFactory(new PropertyValueFactory<Table, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));
        engineCol.setCellValueFactory(new PropertyValueFactory<Table, String>("engine"));
        aiCol.setCellValueFactory(new PropertyValueFactory<Table, Integer>("ai"));
        dataLengthCol.setCellValueFactory(new PropertyValueFactory<Table, String>("length"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Table, String>("description"));

        tablesTable.setItems(tables);
    }

    public void searchInTable(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {
    }

    public void revert(ActionEvent actionEvent) {
    }

    public void refresh(ActionEvent actionEvent) {
    }
}

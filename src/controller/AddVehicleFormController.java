package controller;

import db.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Vehicle;
import util.CloseWindow;

import java.io.IOException;
import java.net.URL;

public class AddVehicleFormController implements CloseWindow {
    public AnchorPane AddVehiclePopUp;
    public TextField txtVehicleNo;
    public TextField txtNoPassenger;
    public TextField txtMaxWeight;
    public ComboBox cmbAddVehicleType;
    public void initialize(){

        loadType();
    }

    private void loadType() {

        cmbAddVehicleType.getItems().add("Bus");
        cmbAddVehicleType.getItems().add("Van");
        cmbAddVehicleType.getItems().add("Cargo Lorry");



    }




    public void AddVehicleOnAction(ActionEvent actionEvent) {
        try {
            Database.vehicleTable.add
                    (new Vehicle(String.valueOf(txtVehicleNo.getText()), (String) cmbAddVehicleType.getValue(), Double.parseDouble(txtMaxWeight.getText()),
                            Integer.parseInt(txtNoPassenger.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Added In To Database");
            alert.show();
            clearFields();

        } catch (Throwable e) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Maximum Weight should be Double Type");
            warning.showAndWait();
            txtMaxWeight.clear();

        }
    }

    private void clearFields() {
        txtVehicleNo.clear();
        txtMaxWeight.clear();
        txtNoPassenger.clear();
        cmbAddVehicleType.getSelectionModel().clearSelection();

    }

    public void CancelAddVehicle(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(AddVehiclePopUp);   //CLOSE WINDOW IN CloseWindowUi

        URL resource = getClass().getResource("../view/VehicleDetailsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1= new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();


    }

    @Override
    public void CloseWindowUi(AnchorPane x) throws IOException {
        Stage stage= (Stage) x.getScene().getWindow();
        stage.close();

    }
}

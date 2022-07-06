package controller;

import db.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Driver;
import util.CloseWindow;

import java.io.IOException;
import java.net.URL;

public class AddDriverFormController implements CloseWindow {
    public AnchorPane AddVehiclePopUp;
    public TextField txtDriverName;
    public TextField txtDriverNic;
    public TextField txtLicene;
    public TextField txtAddress;
    public TextField txtContact;

    public void AddDriverOnAction(ActionEvent actionEvent) {
        Database.driverTable.add(new Driver(txtDriverName.getText(), txtDriverNic.getText(),
                txtLicene.getText(), txtAddress.getText(), txtContact.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Added Driver In To Database");
        alert.show();
        ClearFields();


    }

    private void ClearFields() {
        txtDriverName.clear();
        txtDriverNic.clear();
        txtLicene.clear();
        txtAddress.clear();
        txtContact.clear();

    }

    public void CancelAddDriver(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(AddVehiclePopUp);

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

package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Park;
import util.AddToLoadUi;
import util.CloseWindow;
import view.tm.ParkDatabaseTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class VehicleDetailsFormController implements AddToLoadUi, CloseWindow {

    public AnchorPane parkingAnchorpane;
    public ComboBox ManagemntCmbType;
    public TableView<ParkDatabaseTM> tblParking;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colParkingSlot;
    public TableColumn colParkedTime;
    public void initialize() {

        colVehicleNumber.setCellValueFactory(new PropertyValueFactory("VehicleNumber"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory("VehicleType"));
        colParkingSlot.setCellValueFactory(new PropertyValueFactory("Slot"));
        colParkedTime.setCellValueFactory(new PropertyValueFactory("ParkedTime"));

        ManagemntCmbType.getItems().add("In Parking");
        ManagemntCmbType.getItems().add("On Delivery");

        ManagemntCmbType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String text=String.valueOf(newValue);
            if(text.equals("On Delivery")){

                Stage stage1= (Stage) parkingAnchorpane.getScene().getWindow();
                try{
                    stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/VehicleDetails2Form.fxml"))));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        loadAllData();

    }

    private void loadAllData() {
        ObservableList obList = FXCollections.observableArrayList();
        for (Park p: Database.parkInTable) {
            ParkDatabaseTM tm= new ParkDatabaseTM(p.getVehicleNumber(),p.getVehicleType(), p.getSlot(),p.getParkedTime());
            obList.add(tm);
        }
        tblParking.setItems(obList);

    }
    public void btnAddVehicleOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(parkingAnchorpane);

        loadUi("AddVehicleForm");


    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {

            parkingAnchorpane.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/FirstInterfaceForm.fxml"));
            parkingAnchorpane.getChildren().add(parent);

        }

    }

    public void btnAddDriverOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(parkingAnchorpane);

        loadUi("AddDriverForm");



    }

    @Override
    public void loadUi(String location) throws IOException {
        URL resource = getClass().getResource("../view/"+location+".fxml");
        Parent  load =FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1= new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();


    }

    @Override
    public void CloseWindowUi(AnchorPane x) throws IOException {

            Stage stage= (Stage)x.getScene().getWindow();
            stage.close();


        }
}

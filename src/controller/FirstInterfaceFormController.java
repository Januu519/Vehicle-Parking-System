package controller;

import db.Database;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Deliver;
import model.Driver;
import model.Park;
import model.Vehicle;
import view.tm.DriverDatabaseTM;
import view.tm.VehicleDatabaseTM;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

public class FirstInterfaceFormController<colVehicleNumber> {

    public Label setTimeDateToLabel;
    public ComboBox cmbVehicleNo;
    public ComboBox cmbDriverName;
    public Button managemntClose;
    public Button btnPark;
    public Button btnDelivery;
    public Label slotNoToLabel;

    public AnchorPane loginContextAnchor;
    public TextField txtVehicleType;
    //  private SingleSelectionModel VehicleDatabase colVehicleNumber;

    boolean b=false;
    boolean deliver=false;

    public void initialize() {
        try {
            loadHomeDate();
            setTimeAndDate();

            cmbVehicleNo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                VehicleType( newValue);

            });

        }catch (Exception e){

        }


    }

    private void VehicleType(Object newValue) {

        String z= String.valueOf(newValue);
        for (int i=0;i<Database.vehicleTable.size();i++){
            String number=Database.vehicleTable.get(i).getVehicleNo();
            if (z.equals(number)){

               txtVehicleType.setText(Database.vehicleTable.get(i).getVehicleType());

                            }
        }
        parkingSlot();


   }

    private void parkingSlot() {
        switch (txtVehicleType.getText()){
            case "Van" : {
                setslot("Van");
            }break;
            case "Cargo Lorry" : {
                setslot("Cargo Lorry");
            }break;
            case "Bus" : {
                setslot("Bus");
            }break;
        }


    }
    private void setslot(String vehicletype) {
        for (int i=0; i<Database.slotTable.size(); i++){
            for (int j=0; j<Database.slotTable.size(); j++){
                if (vehicletype.equals(Database.slotTable.get(i).getVehicleType()) && Database.slotTable.get(i).getStatus().equals("notUse")) {
                    slotNoToLabel.setText(Database.slotTable.get(i).getSlot());
                    return;
                }
            }
        }
        clearslotlbl();
    }

    private void clearslotlbl() {
        for (int i=0; i<Database.parkInTable.size(); i++){
            if (Database.parkInTable.get(i).getVehicleNumber().equals(cmbVehicleNo.getValue())){
                slotNoToLabel .setText(" ");
            }
        }
    }
    private void setnotuse(String slotnmbr) {
        for (int i=0; i<Database.slotTable.size(); i++){
            if (Database.slotTable.get(i).getSlot().equals(slotnmbr)){
                Database.slotTable.get(i).setStatus("notUse");
            }
        }
    }


    private void loadHomeDate() {
        ObservableList<VehicleDatabaseTM>observableList=FXCollections.observableArrayList();
        for (Vehicle v:Database.vehicleTable){
            VehicleDatabaseTM tm =new VehicleDatabaseTM(v.getVehicleNo());
            observableList.add(tm);
        }

        ObservableList<DriverDatabaseTM>observableList1=FXCollections.observableArrayList();
        for (Driver d: Database.driverTable){
            DriverDatabaseTM dr=new DriverDatabaseTM(d.getName());
            observableList1.add(dr);
        }

        cmbVehicleNo.setItems(observableList);
        cmbDriverName.setItems(observableList1);





    }


    private void setTimeAndDate() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            LocalDate currentDate = LocalDate.now();
            setTimeDateToLabel.setText(currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-" + currentDate.getYear() + "    " + currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void managementLogInOnAction(ActionEvent actionEvent) throws IOException {

        Database.belowUp=loginContextAnchor;


        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1= new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();
    }

    public void parkVehicleOnAction(ActionEvent actionEvent) {
        parkingTest();
        for(int k=0;k<Database.slotTable.size();k++){
            if( slotNoToLabel.getText().equals(Database.slotTable.get(k).getSlot())){
                Database.slotTable.get(k).setStatus("Use");
            }
        }



    }



    public void onDeliveryShiftOnAction(ActionEvent actionEvent) {

        try{
            deliveryTest();
            for(int k=0;k<Database.parkInTable.size();k++){
                if(cmbVehicleNo.getValue().equals(Database.parkInTable.get(k).getVehicleNumber())){
                    setnotuse(Database.parkInTable.get(k).getSlot());
                }
            }

        }catch(Throwable e){
            System.out.println(e);
        }
    }


    private void parkingTest() {
        cmbVehicleNo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            btnPark.setDisable(false);
            String temp=String.valueOf(newValue);
            for (int i=0;i<Database.parkInTable.size();i++){
                b= Database.parkInTable.get(i).getVehicleNumber().contains(temp);
                if(b==true){
                    btnPark.setDisable(true);
                    //  btnPark.setDisable(b);
                }
            }
        });
        if(b==false){
            if(cmbVehicleNo.getValue()!=null){
                String data= (String.valueOf(cmbVehicleNo.getValue())) ;
                Park p=new Park(String.valueOf(cmbVehicleNo.getValue()),txtVehicleType.getText(),slotNoToLabel.getText(),setTimeDateToLabel.getText());
                Database.parkInTable.add(p);

                for(int i=0;i<Database.deliverTable.size();i++){
                    if(Database.deliverTable.get(i).getVehicleNo().contains(data)){
                        Database.deliverTable.remove(i);
                    }
                }
            }
        }
        clearFields();

    }

    private void clearFields() {
        txtVehicleType.clear();


        cmbVehicleNo.getSelectionModel().clearSelection();
        cmbDriverName.getSelectionModel().clearSelection();





    }

    private void deliveryTest() {
        cmbVehicleNo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String temp2=String.valueOf(newValue);
            btnDelivery.setDisable(false);

            for (int i=0;i<Database.deliverTable.size();i++){
                deliver= Database.deliverTable.get(i).getVehicleNo().contains(temp2);

                if(deliver==false){
                    btnDelivery.setDisable(true);
                    // btnPark.setVisible(false);
                }
            }
        });

        if(deliver==false){

            if(cmbVehicleNo.getValue()!=null && cmbDriverName.getValue()!=null){
                String data= (String.valueOf(cmbVehicleNo.getValue())) ;
                for(int i=0;i<Database.parkInTable.size();i++){
                    if(Database.parkInTable.get(i).getVehicleNumber().contains(data)){
                        Database.parkInTable.remove(i);
                    }
                }
                Deliver d=new Deliver(String.valueOf(cmbVehicleNo.getValue()),txtVehicleType.getText(),String.valueOf(cmbDriverName.getValue()),setTimeDateToLabel.getText());
                Database.deliverTable.add(d);
            }
        }
        clearFields();




    }
}

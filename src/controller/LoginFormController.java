package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.AddToLoadUi;
import util.CloseWindow;
import util.CloseWindowInBtn;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static db.Database.belowUp;

public class LoginFormController implements AddToLoadUi, CloseWindowInBtn {

    public TextField txtUserName;
    public Button canselbtn;
    public Button Loginbtn;
    public PasswordField password;
    public AnchorPane loginPopUp;



    public void btncanselOnAction(ActionEvent actionEvent) throws IOException {

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure Cancel?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {
            CloseBtn(canselbtn);
        }

    }
    public void CloseBtn(Button btn) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
int  attemptsLogAdmin=0;
    public void btnLoginManageOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(loginPopUp);

        attemptsLogAdmin++;
        if (attemptsLogAdmin < 5) {  // attempts calculate

            if (txtUserName.getText().equals("janu") &&  password.getText().equals("1234")) {



                loadUitwo("VehicleDetailsForm");

            } else {
                // error warning information
                new Alert(Alert.AlertType.WARNING, "Wrong Password !!! Try Again...").showAndWait();


                loadUi( "LoginForm"); //call to interface loadUi
            }
        } else {
            //  number of wrong input visible false option
            txtUserName.setVisible(false);
            password.setVisible(false);

        }

    }
    private void loadUitwo(String location) throws IOException {
        Stage stage = (Stage)belowUp.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));

    }

    @Override
    public void loadUi(String location) throws IOException {
        URL resources = getClass().getResource("../view/"+location+".fxml");
        Parent load = FXMLLoader.load(resources);
        Scene scene = new Scene(load);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    @Override
    public void CloseWindowUi(AnchorPane x) throws IOException {
        Stage stage = (Stage) x.getScene().getWindow();
        stage.close();

    }
}

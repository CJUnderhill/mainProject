package Controllers;

import UserAccounts.Authentication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Status: incomplete.
 * TODO: clean code, WARNINGS, doxygen
 * TODO: don't allow creation of agents/superagents?
 */
public class createUserController extends UIController{

    @FXML
    private TextField username, password, firstName, middleInitial, lastName, email, phoneNo;
    @FXML
    private ComboBox<String> authentication;
    private ObservableList<String> user_types = FXCollections.observableArrayList("User","Applicant","Agent","SuperAgent");
    @FXML
    public Label errorLabel;

    private Authentication isAuthentic = new Authentication();

    /**
     * Initialize the authentication combo box
     */
    void initializeComboBox() {
        authentication.setItems(user_types);
        authentication.setValue("User");
    }

    @FXML
    public void createUserAction() throws IOException{
        String emailText = email.getText();
        String loginNameText = username.getText();
        String passwordText = password.getText();
        String phoneNum = phoneNo.getText();
        String firstNameText = firstName.getText();
        String middleIn = middleInitial.getText();
        String lastNameText = lastName.getText();
        String combo = authentication.getValue();

        int authLvl = 0;
        switch (combo) {
            case "User": authLvl = 0; break;
            case "Applicant": authLvl = 1; break;
            case "Agent": authLvl = 2; break;
            case "SuperAgent": authLvl = 3; break;
            default: errorLabel.setText("An error occurred with the authentication level"); break;
        }

        isAuthentic.createUser(firstNameText, middleIn, lastNameText, loginNameText, passwordText, emailText, phoneNum, authLvl);
        try {
            setDisplayToMainPage(); //TODO: maybe replace with returnToMainPage?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package client.view.controller;

import client.view.ViewController;
import client.viewmodel.StartViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;


public class StartViewController extends ViewController {
    private StartViewModel viewModel;

    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;


    private String password;


    public StartViewController(){

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getStartViewModel();
    }

    @Override
    public void reset() {

        viewModel.reset();
    }
     public void login(ActionEvent actionEvent) {

        password = passwordTextField.getText();

         viewModel.login(password);

     }
}

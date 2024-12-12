package maquette.controller;

import maquette.model.Lieu;
import maquette.utils.AlertUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LieuxPopupController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private Lieu lieu;
    private boolean isConfirmed = false;

    @FXML
    private void initialize() {
        addButton.setOnAction(e -> handleAdd());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void handleAdd() {
        String nom = nameField.getText().trim();
        String capaciteText = capacityField.getText().trim();
        String adresse = addressField.getText().trim();
        String ville = cityField.getText().trim();

        if (nom.isEmpty() || capaciteText.isEmpty() || adresse.isEmpty() || ville.isEmpty()) {
            AlertUtility.showWarning("Champs Manquants", "Veuillez remplir tous les champs.");
            return;
        }

        int capacite;
        try {
            capacite = Integer.parseInt(capaciteText);
            if (capacite <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            AlertUtility.showWarning("Capacité Invalide", "Veuillez entrer une capacité valide.");
            return;
        }

        lieu = new Lieu(nom, capacite, adresse, ville);
        isConfirmed = true;
        closeWindow();
    }

    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    public Lieu getLieu() {
        return lieu;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}

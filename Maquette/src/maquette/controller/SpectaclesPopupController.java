package maquette.controller;

import maquette.model.Spectacle;
import maquette.utils.AlertUtility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SpectaclesPopupController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timePicker;

    @FXML
    private TextField durationField;

    @FXML
    private TextField spectatorsField;

    @FXML
    private ComboBox<String> venueField;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private Spectacle spectacle;
    private boolean isConfirmed = false;

    @FXML
    private void initialize() {
        addButton.setOnAction(e -> handleAdd());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void handleAdd() {
        String titre = titleField.getText().trim();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";
        String heure = timePicker.getValue();
        String duree = durationField.getText().trim();
        String nombreSpectateursText = spectatorsField.getText().trim();
        String lieu = venueField.getValue();

        if (titre.isEmpty() || date.isEmpty() || heure == null || duree.isEmpty() ||
                nombreSpectateursText.isEmpty() || lieu == null) {
            AlertUtility.showWarning("Champs Manquants", "Veuillez remplir tous les champs.");
            return;
        }

        int nombreSpectateurs;
        try {
            nombreSpectateurs = Integer.parseInt(nombreSpectateursText);
            if (nombreSpectateurs <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            AlertUtility.showWarning("Nombre Invalide", "Veuillez entrer un nombre de spectateurs valide.");
            return;
        }

        spectacle = new Spectacle(titre, date, heure, duree, nombreSpectateurs, lieu);
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

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}

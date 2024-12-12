package maquette.controller;

import maquette.model.Spectacle;
import maquette.utils.AlertUtility;
import maquette.utils.DialogUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SpectaclesController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Spectacle> spectaclesTable;

    @FXML
    private TableColumn<Spectacle, String> titleColumn;

    @FXML
    private TableColumn<Spectacle, String> dateColumn;

    @FXML
    private TableColumn<Spectacle, String> timeColumn;

    @FXML
    private TableColumn<Spectacle, String> durationColumn;

    @FXML
    private TableColumn<Spectacle, Integer> spectatorsColumn;

    @FXML
    private TableColumn<Spectacle, String> venueColumn;

    @FXML
    private Button searchButton;

    private ObservableList<Spectacle> spectaclesData;

    @FXML
    private void initialize() {
        // Initialize table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        spectatorsColumn.setCellValueFactory(new PropertyValueFactory<>("nombreSpectateurs"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        // Load sample data
        loadSampleData();

        // Set search button action
        searchButton.setOnAction(e -> handleSearch());
    }

    private void loadSampleData() {
        spectaclesData = FXCollections.observableArrayList(
                new Spectacle("Concert Rock", "2024-12-15", "20:00", "2h", 500, "Théâtre National"),
                new Spectacle("Pièce de Théâtre", "2024-12-20", "19:00", "1h30", 300, "Salle Polyvalente"),
                new Spectacle("Ballet Classique", "2024-12-25", "18:30", "2h30", 600, "Centre Culturel"),
                new Spectacle("Spectacle de Magie", "2024-12-28", "21:00", "2h", 400, "Auditorium Central")
        );

        spectaclesTable.setItems(spectaclesData);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            spectaclesTable.setItems(spectaclesData);
        } else {
            ObservableList<Spectacle> filteredData = FXCollections.observableArrayList();
            for (Spectacle spectacle : spectaclesData) {
                if (spectacle.getTitre().toLowerCase().contains(searchText) ||
                        spectacle.getLieu().toLowerCase().contains(searchText)) {
                    filteredData.add(spectacle);
                }
            }
            spectaclesTable.setItems(filteredData);
        }
    }

    @FXML
    private void handleAddSpectacle() {
        try {
            SpectaclesPopupController controller = (SpectaclesPopupController) DialogUtility.showModal(
                    "/maquette/view/SpectaclesPopup.fxml",
                    "Ajouter un Spectacle"
            );

            if (controller.isConfirmed()) {
                Spectacle newSpectacle = controller.getSpectacle();
                spectaclesData.add(newSpectacle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtility.showError("Erreur", "Erreur lors de l'ouverture du dialogue");
        }
    }

    @FXML
    private void handleEditSpectacle() {
        Spectacle selectedSpectacle = spectaclesTable.getSelectionModel().getSelectedItem();
        if (selectedSpectacle != null) {
            System.out.println("Modification du spectacle : " + selectedSpectacle.getTitre());
        } else {
            AlertUtility.showWarning("Avertissement", "Veuillez sélectionner un spectacle à modifier.");
        }
    }

    @FXML
    private void handleDeleteSpectacle() {
        Spectacle selectedSpectacle = spectaclesTable.getSelectionModel().getSelectedItem();
        if (selectedSpectacle != null) {
            spectaclesData.remove(selectedSpectacle);
        } else {
            AlertUtility.showWarning("Avertissement", "Veuillez sélectionner un spectacle à supprimer.");
        }
    }
}

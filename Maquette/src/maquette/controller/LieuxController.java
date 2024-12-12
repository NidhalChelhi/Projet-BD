package maquette.controller;

import maquette.model.Lieu;
import maquette.utils.AlertUtility;
import maquette.utils.DialogUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

public class LieuxController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Lieu> lieuxTable;

    @FXML
    private TableColumn<Lieu, String> nameColumn;

    @FXML
    private TableColumn<Lieu, Integer> capacityColumn;

    @FXML
    private TableColumn<Lieu, String> addressColumn;

    @FXML
    private TableColumn<Lieu, String> cityColumn;

    @FXML
    private Button searchButton;

    private ObservableList<Lieu> lieuxData;

    @FXML
    private void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));

        // Load sample data
        loadSampleData();

        // Set search button action
        searchButton.setOnAction(e -> handleSearch());
    }

    private void loadSampleData() {
        lieuxData = FXCollections.observableArrayList(
                new Lieu("Théâtre National", 1200, "5 Rue des Arts", "Paris"),
                new Lieu("Salle Polyvalente", 800, "12 Avenue de la République", "Lyon"),
                new Lieu("Centre Culturel", 500, "3 Boulevard St-Michel", "Marseille"),
                new Lieu("Auditorium Central", 1500, "8 Rue Centrale", "Toulouse"),
                new Lieu("Théâtre Municipal", 1000, "Avenue Habib Bourguiba", "Tunis"),
                new Lieu("Palais des Congrès", 1200, "Rue de la Liberté", "Sousse"),
                new Lieu("Amphithéâtre de Carthage", 2000, "Carthage", "Tunis"),
                new Lieu("Maison de la Culture", 800, "Place des Arts", "Monastir"),
                new Lieu("Centre Culturel", 600, "Rue des Poètes", "Sfax"),
                new Lieu("Salle des Fêtes", 700, "Boulevard de la Paix", "Gabès")
        );

        lieuxTable.setItems(lieuxData);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            lieuxTable.setItems(lieuxData);
        } else {
            ObservableList<Lieu> filteredData = FXCollections.observableArrayList();
            for (Lieu lieu : lieuxData) {
                if (lieu.getNom().toLowerCase().contains(searchText) ||
                        lieu.getAdresse().toLowerCase().contains(searchText) ||
                        lieu.getVille().toLowerCase().contains(searchText)) {
                    filteredData.add(lieu);
                }
            }
            lieuxTable.setItems(filteredData);
        }
    }

    @FXML

    private void handleAddLieu() {
        try {
            LieuxPopupController controller = (LieuxPopupController) DialogUtility.showModal(
                    "/maquette/view/LieuxPopup.fxml",
                    "Ajouter un Lieu"
            );

            if (controller.isConfirmed()) {
                Lieu newLieu = controller.getLieu();
                lieuxData.add(newLieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtility.showError("Erreur", "Erreur lors de l'ouverture du dialogue");
        }
    }


    @FXML
    private void handleEditLieu() {
        Lieu selectedLieu = lieuxTable.getSelectionModel().getSelectedItem();
        if (selectedLieu != null) {
            System.out.println("Modification du lieu : " + selectedLieu.getNom());
        } else {
            AlertUtility.showWarning("Avertissement", "Veuillez sélectionner un lieu à modifier.");
        }
    }

    @FXML
    private void handleDeleteLieu() {
        Lieu selectedLieu = lieuxTable.getSelectionModel().getSelectedItem();
        if (selectedLieu != null) {
            lieuxData.remove(selectedLieu);
        } else {
            AlertUtility.showWarning("Avertissement", "Veuillez sélectionner un lieu à supprimer.");
        }
    }
}

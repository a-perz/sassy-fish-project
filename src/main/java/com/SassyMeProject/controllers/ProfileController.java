package com.SassyMeProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.SassyMeProject.usermodel.User;

public class ProfileController {
    @FXML private TextField usernameField;
    @FXML private TextArea bioField;
    @FXML private ComboBox<String> locationComboBox;
    @FXML private Label errorLabel;
    //@FXML private ImageView profileImageView; (TO SOLVE: Image loading)

    @FXML private Button editButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    //@FXML private Button uploadPictureButton;

    //private ManageProfileUseCase manageProfileUseCase;
    private User currentUser;
    private boolean isEditing = false;

    @FXML
    public void initialize() {
        //manageProfileUseCase = new ManageProfileUseCase(); (TO SOLVE: Use case integration)
        loadUserProfile();
        setEditMode(false);
    }

    private void loadUserProfile() {
        try {
            // Simulación de carga de usuario actual (TO SOLVE: Integrar con base de datos)
            currentUser = new User("sassy_user", "Sassy User");
            currentUser.setBio("This is my bio!");
            currentUser.setLocation("New York");
            //currentUser.setProfilePicturePath("path/to/profile/picture.jpg");
            usernameField.setText(currentUser.getUsername());
            bioField.setText(currentUser.getBio());
            locationComboBox.getItems().addAll("New York", "Los Angeles", "Chicago", "Houston", "Miami");
            locationComboBox.setValue(currentUser.getLocation());
            //if (currentUser.getProfilePicturePath() != null) {
            //    profileImageView.setImage(new Image(currentUser.getProfilePicturePath()));
            //} 
        } catch (Exception e) {
            errorLabel.setText("Error loading profile: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditProfile() {
        setEditMode(true);
    } //TO SOLVE: Put it in the EditButton action in the FXML file

    @FXML
    private void handleSaveProfile() { //Again TO SOLVE with database integration
        errorLabel.setText("");
        if (usernameField.getText().isEmpty()) {
            errorLabel.setText("Username cannot be empty.");
            return;
        }
        if (bioField.getText().length() > 150) {
            errorLabel.setText("Bio cannot exceed 150 characters.");
            return;
        }
        if (locationComboBox.getValue() == null) {
            errorLabel.setText("Please select a location.");
            return;
        }

        try {
            currentUser.setUsername(usernameField.getText());
            currentUser.setBio(bioField.getText());
            currentUser.setLocation(locationComboBox.getValue());
            //manageProfileUseCase.updateProfile(currentUser); (TO SOLVE: Integrar con el caso de uso)
            setEditMode(false);
        } catch (Exception e) {
            errorLabel.setText("Error saving profile: " + e.getMessage());
        }
    }

    private void setEditMode(boolean editing) {
        isEditing = editing;

        //Textos a editar
        usernameField.setEditable(editing);
        bioField.setEditable(editing);
        locationComboBox.setDisable(!editing);

        //Vista de botones
        editButton.setDisable(editing);
        saveButton.setDisable(!editing);
        cancelButton.setDisable(!editing);
    }
}

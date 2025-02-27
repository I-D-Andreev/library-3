import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;


/**
 * Controller class that models the edit tabs for the controller.
 *
 * @author Ivan Andreev
 */
public class EditAccountController extends Controller {

    /**
     * The back button for the table.
     */
    @FXML
    private Button backButton;

    /**
     * Reference to the whole tab pane.
     */
    @FXML
    private TabPane tabPane;

    /**
     * Reference to the tab that needs to be hidden/shown.
     */
    @FXML
    private Tab notificationsTab;

    /**
     * The field for the username of the table.
     */
    @FXML
    private TextField usernameTextField;

    /**
     * The field for the first name of the table.
     */
    @FXML
    private TextField firstNameTextField;

    /**
     * The field for the last name of the table.
     */
    @FXML
    private TextField lastNameTextField;

    /**
     * The field for the phone number of the table.
     */
    @FXML
    private TextField phoneNumberTextField;

    /**
     * The field for the first address line of the table.
     */
    @FXML
    private TextField addressLine1TextField;

    /**
     * The field for the second address line of the table.
     */
    @FXML
    private TextField addressLine2TextField;

    /**
     * The field for the city of the table.
     */
    @FXML
    private TextField cityTextField;

    /**
     * The field for the country of the table.
     */
    @FXML
    private TextField countryTextField;

    /**
     * The field for the postcode of the table.
     */
    @FXML
    private TextField postcodeTextField;

    /**
     * The checkbox for returning a resource notifications.
     */
    @FXML
    private CheckBox returnResourceCheckbox;

    /**
     * The checkbox for new resources notifications.
     */
    @FXML
    private CheckBox newResourceAddedCheckbox;

    /**
     * The checkbox for new reserved resource notifications.
     */
    @FXML
    private CheckBox newReservedResourceCheckbox;

    /**
     * The checkbox for new event notifications.
     */
    @FXML
    private CheckBox newEventCheckbox;

    /**
     * The field for the email address.
     */
    @FXML
    private TextField emailTextField;


    /**
     * The button for choosing a profile image.
     */
    @FXML
    private Button chooseProfileImageButton;

    /**
     * Label that displays the image path.
     */
    @FXML
    private TextField imagePathTextField;

    /**
     * Button to draw a profile image.
     */
    @FXML
    private Button drawProfileImageButton;

    /**
     * Button to edit account information.
     */
    @FXML
    private Button editAccountButton;

    /**
     * Button to delete an account.
     */
    @FXML
    private Button deleteAccountButton;

    /**
     * Textfield for old password.
     */
    @FXML
    private PasswordField oldPasswordTextfield;

    /**
     * Textfield for new password.
     */
    @FXML
    private PasswordField newPasswordTextfield;

    /**
     * Textfield for repeating the new password.
     */
    @FXML
    private PasswordField repeatNewPasswordTextfield;

    /**
     * Button to change the password.
     */
    @FXML
    private Button changePasswordButton;


    /**
     * Textfield for new email when changing email address.
     */
    @FXML
    private TextField newEmailTextfield;

    /**
     * Textfield to repeat new email when changing email address.
     */
    @FXML
    private TextField repeatNewEmailTextfield;

    /**
     * Password field when changing email address.
     */
    @FXML
    private PasswordField passwordTextfield;

    /**
     * Button to change email address.
     */
    @FXML
    private Button changeEmailButton;

    /**
     * Button to save notification preferences.
     */
    @FXML
    private Button savePreferencesButton;


    /**
     * Calls method to fill fields with data.
     */
    @Override
    public void onStart() {
        this.fillInData();
    }

    /**
     * Fills the fields with data.
     */
    private void fillInData() {
        User user = getLibrary().getCurrentUserLoggedIn();
        usernameTextField.setText(user.getUsername());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        phoneNumberTextField.setText(user.getPhoneNumber());
        addressLine1TextField.setText(user.getAddress().getAddressLine1());
        addressLine2TextField.setText(user.getAddress().getAddressLine2());
        cityTextField.setText(user.getAddress().getCity());
        countryTextField.setText(user.getAddress().getCountry());
        postcodeTextField.setText(user.getAddress().getPostcode());
        imagePathTextField.setText(user.getProfileImagePath());
        emailTextField.setText(user.getEmail());


        // librarian doesn't have notifications
        if (user instanceof Librarian) {
            tabPane.getTabs().remove(notificationsTab);
        }

        // if it is a normal user and the tab pane isn't there
        if (user instanceof NormalUser && tabPane.getTabs().indexOf(notificationsTab) == -1) {
            tabPane.getTabs().add(notificationsTab);
        }

        if (user instanceof NormalUser) {
            NormalUser normalUser = (NormalUser) user;
            returnResourceCheckbox.setSelected(
                    normalUser.getNotificationPreferences().getReceiveReturnResourceNotification());

            newResourceAddedCheckbox.setSelected(
                    normalUser.getNotificationPreferences().getReceiveNewAdditionsNotification());

            newEventCheckbox.setSelected(
                    normalUser.getNotificationPreferences().getReceiveEventNotification());

            newReservedResourceCheckbox.setSelected(
                    normalUser.getNotificationPreferences().getReceiveNewReservedResourceNotification());
        }

    }

    /**
     * Sets the text of the label that contains the image path.
     *
     * @param filePath The path to the image.
     */
    public void setImagePathLabelText(String filePath) {
        this.imagePathTextField.setText(filePath);
    }

    /**
     * Bring the user back to the main dashboard after the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void backButtonClicked(ActionEvent event) {
        //if librarian then go back to librarian dashboard else go back to user dashboard
        if (getLibrary().getCurrentUserLoggedIn().hasAdminAccess()) {
            new NewWindow("resources/LibrarianDashboard.fxml", event,
                    "Browse Resources - TaweLib", getLibrary());
        } else {
            new NewWindow("resources/UserDashboard.fxml", event,
                    "Dashboard - TaweLib", getLibrary());
        }
    }

    /**
     * Sends the user to select a profile image when the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void chooseProfileImageButtonClicked(ActionEvent event) {

        new NewWindow("resources/ProfileImage.fxml", event, "Choose Image - TaweLib", getLibrary());
    }

    /**
     * Sends the user to draw a profile image after the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void drawProfileImageButtonClicked(ActionEvent event) {
        DrawAvatar drawAvatar = new DrawAvatar(this.imagePathTextField);
        Stage newerStage = new Stage();
        drawAvatar.start(newerStage);
    }

    /**
     * Sends the user to edit his account after the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void editAccountButtonClicked(ActionEvent event) {
        // mandatory - first name, last name, phone number, address line 1
        // city, country, postcode, profile image
        // optional - addressLine2

        // gather data
        String username = usernameTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String addressLine1 = addressLine1TextField.getText();
        String addressLine2 = addressLine2TextField.getText();
        String city = cityTextField.getText();
        String country = countryTextField.getText();
        String postcode = postcodeTextField.getText();
        String imagePath = imagePathTextField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()
                || addressLine1.isEmpty() || city.isEmpty() || country.isEmpty()
                || postcode.isEmpty() || username.isEmpty() || imagePath.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all the required fields.",
                    ButtonType.OK);
            alert.show();
        } else {
            // create the Address
            Address address = new Address(addressLine1, addressLine2, city, country, postcode);

            // find user
            User user = getLibrary().getUserManager().getUserByUsername(username);

            // change details
            getLibrary().getUserManager().editUser(user, firstName, lastName, phoneNumber, imagePath, address);

            // notify user
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Details changed successfully.",
                    ButtonType.OK);
            alert.show();

        }
    }

    /**
     * Sends the user to delete his account after the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void deleteAccountButtonClicked(ActionEvent event) {
        // create an alert to make sure the user
        // didn't click this button accidentally

        Alert confirmChoiceDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmChoiceDialog.setTitle("Confirm choice");
        confirmChoiceDialog.setHeaderText("Delete account");
        confirmChoiceDialog.setContentText("Are you sure you want to delete your account?");
        confirmChoiceDialog.getButtonTypes().clear();
        confirmChoiceDialog.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = confirmChoiceDialog.showAndWait();

        if (result.get() == ButtonType.YES) {
            User user = getLibrary().getUserManager().getUserByUsername(usernameTextField.getText());
            if ((user instanceof NormalUser) && ((NormalUser) user).getBalance() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Can not delete account because there are outstanding fines",
                        ButtonType.OK);
                alert.show();
            } else {
                getLibrary().getUserManager().removeUser(user);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Account deleted successfully.",
                        ButtonType.OK);
                alert.showAndWait();

                getLibrary().save();
                new NewWindow("resources/Login.fxml", event,
                        "TaweLib - Login", getLibrary());
            }
        }
    }


    /**
     * Changes the password.
     *
     * @param event Clicking the change password button.
     */
    @FXML
    public void changePasswordButtonClicked(ActionEvent event) {
        // newPasswordTextfield, repeatNewPasswordTextfield, oldPasswordTextfield
        // all mandatory !!!!!!!!!!!!!!!!!!!!!
        String salt = getLibrary().getCurrentUserLoggedIn().getSecuritySalting();

        if (!Security.checkPassword(oldPasswordTextfield.getText(), salt,
                getLibrary().getCurrentUserLoggedIn().getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong password!",
                    ButtonType.OK);
            alert.show();
        } else if (!Security.checkPasswordStrength(newPasswordTextfield.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A password must contain at least 6 characters, " +
                    "a digit, a letter and a symbol!",
                    ButtonType.OK);
            alert.show();
        } else if (newPasswordTextfield.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Password can't be empty!",
                    ButtonType.OK);
            alert.show();
        } else if (!newPasswordTextfield.getText().equals(repeatNewPasswordTextfield.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "New password must match!",
                    ButtonType.OK);
            alert.show();
        } else {
            // successful change
            getLibrary().getCurrentUserLoggedIn().setPassword(
                    Security.generatePassword(newPasswordTextfield.getText(), salt));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New password set successfully!",
                    ButtonType.OK);
            alert.showAndWait();

            // clear fields
            oldPasswordTextfield.clear();
            newPasswordTextfield.clear();
            repeatNewPasswordTextfield.clear();

            newPasswordTextfield.styleProperty().setValue("-fx-background-color:white");
            repeatNewPasswordTextfield.styleProperty().setValue("-fx-background-color:white");

        }



    }


    /**
     * Changes the email.
     *
     * @param event Clicking on the change email button.
     */
    @FXML
    public void changeEmailButtonClicked(ActionEvent event) {
        // passwordTextfield, newEmailTextfield, repeatNewEmailTextfield
        // all mandatory!!!!!!!!!!!!!!!!!!!!!!!
        if (passwordTextfield.getText().isEmpty() || newEmailTextfield.getText().isEmpty()
                || repeatNewEmailTextfield.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled in!",
                    ButtonType.OK);
            alert.show();
        } else if (!MailSender.checkCorrectEmail(newEmailTextfield.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect email!",
                    ButtonType.OK);
            alert.show();
        } else if (!newEmailTextfield.getText().equals(repeatNewEmailTextfield.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Emails must match!",
                    ButtonType.OK);
            alert.show();
        } else {
            // success
            getLibrary().getCurrentUserLoggedIn().setEmail(newEmailTextfield.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "New email set successfully!",
                    ButtonType.OK);
            alert.showAndWait();

            // clear fields
            passwordTextfield.clear();
            newEmailTextfield.clear();
            repeatNewEmailTextfield.clear();

            newEmailTextfield.styleProperty().setValue("-fx-background-color:white");
            repeatNewEmailTextfield.styleProperty().setValue("-fx-background-color:white");

        }
    }


    /**
     * Pressing a key when writing in new email.
     *
     * @param event Pressing a key.
     */
    @FXML
    public void newEmailKeyPressed(KeyEvent event) {
        if (newEmailTextfield.getText().equals(repeatNewEmailTextfield.getText())) {
            repeatNewEmailTextfield.styleProperty().setValue("-fx-background-color : #11dd18");
        } else {
            repeatNewEmailTextfield.styleProperty().setValue("-fx-background-color : #ea593c");
        }
    }

    /**
     * Pressing a key when writing a new password.
     *
     * @param event Pressing a key.
     */
    @FXML
    public void newPasswordKeyPressed(KeyEvent event) {
        if (newPasswordTextfield.getText().equals(repeatNewPasswordTextfield.getText())) {
            repeatNewPasswordTextfield.styleProperty().setValue("-fx-background-color : #11dd18");
        } else {
            repeatNewPasswordTextfield.styleProperty().setValue("-fx-background-color : #ea593c");
        }
    }


    /**
     * Handles when fillin in the text field to choose a new email.
     *
     * @param event Pressing a key.
     */
    @FXML
    public void emailKeyPressed(KeyEvent event) {
        if (MailSender.checkCorrectEmail(newEmailTextfield.getText())) {
            // green color
            newEmailTextfield.styleProperty().setValue("-fx-background-color : #11dd18");
        } else {
            // red color
            newEmailTextfield.styleProperty().setValue("-fx-background-color : #ea593c");
        }
    }

    /**
     * Handles when filling in the text field to choose a new password.
     *
     * @param event Pressing a key.
     */
    @FXML
    public void passwordKeyPressed(KeyEvent event) {
        if (Security.checkPasswordStrength(newPasswordTextfield.getText())) {
            // green color
            newPasswordTextfield.styleProperty().setValue("-fx-background-color : #11dd18");
        } else {
            // red color
            newPasswordTextfield.styleProperty().setValue("-fx-background-color : #ea593c");
        }
    }

    /**
     * Saves the user's new notification preferences.
     *
     * @param event Clicking the save preferences button.
     */
    @FXML
    public void savePreferencesButtonClicked(ActionEvent event) {
        // only normal users should have notifications
        if (getLibrary().getCurrentUserLoggedIn() instanceof NormalUser) {
            NotificationPreferences notificationPreferences =
                    ((NormalUser) getLibrary().getCurrentUserLoggedIn()).getNotificationPreferences();

            // change the preferences
            notificationPreferences.setReceiveReturnResourceNotification(returnResourceCheckbox.isSelected());
            notificationPreferences.setReceiveNewAdditionsNotification(newResourceAddedCheckbox.isSelected());
            notificationPreferences.setReceiveEventNotification(newEventCheckbox.isSelected());
            notificationPreferences.setReceiveNewReservedResourceNotification(newReservedResourceCheckbox.isSelected());

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preferences saved.",
                    ButtonType.OK);
            alert.show();
        }
    }
}


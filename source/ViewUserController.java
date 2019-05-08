import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller class for the View User Window.  Handles user actions when the UI is interacted with.
 *
 * @author Sian Pike, Ivan Andreev
 */

public class ViewUserController extends Controller {

    /**
     * The tab to search users.
     */
    @FXML
    private Tab searchUserTab;

    /**
     * Field to type the user to search in.
     */
    @FXML
    private TextField searchUserTextField;

    /**
     * Button to press when we want to search for a user.
     */
    @FXML
    private Button searchButton;

    /**
     * The tab where we can create a user.
     */
    @FXML
    private Tab createUserTab;

    /**
     * The field showing username.
     */
    @FXML
    private TextField usernameTextField;

    /**
     * The password field.
     */
    @FXML
    private PasswordField passwordTextField;

    /**
     * The repeat password field.
     */
    @FXML
    private PasswordField repeatPasswordTextField;

    /**
     * The email field.
     */
    @FXML
    private TextField emailTextField;

    /**
     * The repeat email field.
     */
    @FXML
    private TextField repeatEmailTextField;

    /**
     * The secret question field.
     */
    @FXML
    private TextField secretQuestionTextField;

    /**
     * The secret answer field.
     */
    @FXML
    private TextField secretAnswerTextField;


    /**
     * The field showing first name.
     */
    @FXML
    private TextField firstNameTextField;

    /**
     * The field showing surname.
     */
    @FXML
    private TextField surnameTextField;

    /**
     * The field showing the phone number.
     */
    @FXML
    private TextField phoneNumberTextField;

    /**
     * The field showing the first address line.
     */
    @FXML
    private TextField addressTextField1;

    /**
     * The field showing the second address line.
     */
    @FXML
    private TextField addressTextField2;

    /**
     * The field showing the country of the address.
     */
    @FXML
    private TextField addressCountryTextField;

    /**
     * The field showing the address post code.
     */
    @FXML
    private TextField addressPostcodeTextField;

    /**
     * Button to choose profile image.
     */
    @FXML
    private Button chooseProfileImageButton;

    /**
     * Button to draw your own profile image.
     */
    @FXML
    private Button drawProfileImageButton;

    /**
     * Label showing the profile image.
     */
    @FXML
    private Label profileImageFileLabel;

    /**
     * Button to add a new user.
     */
    @FXML
    private Button addUserButton;

    /**
     * Button to go back to the dashboard.
     */
    @FXML
    private Button backButton;

    /**
     * Button to filter librarians.
     */
    @FXML
    private RadioButton librarianRadioButton;

    /**
     * Button to filter normal users.
     */
    @FXML
    private RadioButton userRadioButton;

    /**
     * Field showing the city the address is from.
     */
    @FXML
    private TextField addressCityTextField;

    /**
     * Text Field showing the thumbnail image path.
     */
    @FXML
    private TextField imagePathTextField;


    /**
     * Goes back to the librarian dashboard when clicked.
     *
     * @param event The current event.
     */
    @FXML
    public void backButtonClicked(ActionEvent event) {

        new NewWindow("resources/LibrarianDashboard.fxml", event, "Dashboard - TaweLib", getLibrary());
    }

    /**
     * Shows a tab with the user information if the user is found, when the button is pressed.
     *
     * @param event The button is pressed.
     */
    @FXML
    public void searchButtonClicked(ActionEvent event) {
        User user = getLibrary().getUserManager().getUserByUsername(searchUserTextField.getText());
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A user with username " + searchUserTextField.getText()
                    + " does not exist.",
                    ButtonType.OK);
            alert.show();
        } else {
            String content = "Username: " + user.getUsername() +
                    "\nFirst name: " + user.getFirstName() +
                    "\nLast name: " + user.getLastName() +
                    "\nPhone number: " + user.getPhoneNumber() +
                    "\nAddress:\n" + user.getAddress().toString();
            // display some contact info about the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
            alert.setTitle("User Information");
            alert.setHeaderText("Contact information");
            alert.show();
        }

    }

    /**
     * Adds a user when the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void addUserButtonClicked(ActionEvent event) {
        // mandatory info - username, first name,last name, phone number, address line 1, city,
        // country, postcode, librarian / user radio button/ image path one of the two
        // + password, repeat password, email, repeat email, secret question, secret answer
        // optional info - address line 2

        // generate salting for the account
        String accountSalt = Security.generateSalt();

        // gather info
        String username = usernameTextField.getText();
        // passwords are stored encoded so we don't have them in their plain text format at any time
        String encodedPassword = Security.generatePassword(passwordTextField.getText(),accountSalt);
        String encodedRepeatPassword = Security.generatePassword(passwordTextField.getText(),accountSalt);
        String emailAddress = emailTextField.getText();
        String repeatEmailAddress = repeatEmailTextField.getText();
        String secretQuestion = secretQuestionTextField.getText();
        String encodedSecretAnswer = Security.generatePassword(secretAnswerTextField.getText(), accountSalt);

        String firstName = firstNameTextField.getText();
        String lastName = surnameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String addressLine1 = addressTextField1.getText();
        String addressLine2 = addressTextField2.getText();
        String city = addressCityTextField.getText();
        String country = addressCountryTextField.getText();
        String addressPostcode = addressPostcodeTextField.getText();
        boolean isLibrarian = librarianRadioButton.isSelected();
        boolean isUser = userRadioButton.isSelected();
        String imagePath = imagePathTextField.getText();

        // check if info is filled in
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
                || phoneNumber.isEmpty() || addressLine1.isEmpty() || city.isEmpty()
                || country.isEmpty() || addressPostcode.isEmpty() || imagePath.isEmpty()
                || (!isLibrarian && !isUser    // has not selected any of the two radio buttons
                || encodedPassword.isEmpty() || encodedRepeatPassword.isEmpty() || emailAddress.isEmpty()
                || repeatEmailAddress.isEmpty() || secretQuestion.isEmpty()
                || encodedSecretAnswer.isEmpty())
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all the required fields.",
                    ButtonType.OK);
            alert.show();
        } else if (getLibrary().getUserManager().getUserByUsername(username) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username already taken.",
                    ButtonType.OK);
            alert.show();
        } else if (!encodedPassword.equals(encodedRepeatPassword)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords must match.",
                    ButtonType.OK);
            alert.show();
        } else if(!emailAddress.equals(repeatEmailAddress)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Emails must match.",
                    ButtonType.OK);
            alert.show();
        } else {
            // create the address
            Address address = new Address(addressLine1, addressLine2, city, country, addressPostcode);

            String typeOfAccount = "";
            if (isLibrarian) {
                getLibrary().getUserManager().addUser(new Librarian(username, encodedPassword, accountSalt,
                        emailAddress, secretQuestion, encodedSecretAnswer, firstName, lastName, phoneNumber,
                        imagePath, address));
                typeOfAccount = "Librarian";
            } else if (isUser) {
                getLibrary().getUserManager().addUser(new NormalUser(username, encodedPassword, accountSalt,
                        emailAddress, secretQuestion, encodedSecretAnswer, firstName, lastName, phoneNumber,
                        imagePath, address));

                typeOfAccount = "User";
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION, typeOfAccount + " account has been created successfully.",
                    ButtonType.OK);
            alert.show();

            this.clearAllCreateAccountFields();
        }
    }

    /**
     * Clears all the fields for creating an account, to be used after we make an account.
     */
    private void clearAllCreateAccountFields() {
        usernameTextField.clear();
        firstNameTextField.clear();
        passwordTextField.clear();
        repeatPasswordTextField.clear();
        emailTextField.clear();
        repeatEmailTextField.clear();
        secretQuestionTextField.clear();
        secretAnswerTextField.clear();
        surnameTextField.clear();
        phoneNumberTextField.clear();
        addressTextField1.clear();
        addressTextField2.clear();
        addressCityTextField.clear();
        addressCountryTextField.clear();
        addressPostcodeTextField.clear();
        librarianRadioButton.setSelected(false);
        userRadioButton.setSelected(false);
        imagePathTextField.setText("No File Chosen.");
    }

    /**
     * Clicking the button makes the user view only normal user.
     *
     * @param event Clicking the button.
     */
    @FXML
    public void userRadioButtonSelected(ActionEvent event) {
        librarianRadioButton.setSelected(false);
    }

    /**
     * Clicking the button makes the user view only librarians.
     *
     * @param event Clicking the button.
     */
    @FXML
    public void librarianRadioButtonSelected(ActionEvent event) {
        userRadioButton.setSelected(false);
    }

    /**
     * Takes the user to choose a profile image tab after the button is clicked.
     *
     * @param event The button is clicked.
     */
    @FXML
    public void chooseProfileImageButtonClicked(ActionEvent event) {
        selectFile(imagePathTextField);
    }


    /**
     * Opens file explorer and adds the file path to the specified text field when an image is chosen.
     *
     * @param imageTextField The specified text field that contains the file path.
     */
    private void selectFile(TextField imageTextField) {

        Stage currentStage = (Stage) imageTextField.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"));

        try {
            File selectedFile = fileChooser.showOpenDialog(currentStage);
            imageTextField.setText(selectedFile.toString());
        } catch (Exception e) {
            imageTextField.setText("");
            e.printStackTrace();
        }
    }

    /**
     * Takes the user to the draw an image tab after the button is clicked.
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
     * When a key is pressed in the repeat email field.
     * @param event Pressing a key.
     */
    @FXML
    public void repeatEmailKeyPressed(KeyEvent event) {
        if(repeatEmailTextField.getText().equals(emailTextField.getText())){
            repeatEmailTextField.styleProperty().setValue("-fx-background-color : #11dd18");
        } else {
            repeatEmailTextField.styleProperty().setValue("-fx-background-color : #ea593c");
        }
    }

    /**
     * When a key is pressed in the repeat password field.
     * @param event Pressing a key.
     */
    @FXML
    public void repeatPasswordKeyPressed(KeyEvent event) {
        if(repeatPasswordTextField.getText().equals(passwordTextField.getText())){
            repeatPasswordTextField.styleProperty().setValue("-fx-background-color : #11dd18");
        } else {
            repeatPasswordTextField.styleProperty().setValue("-fx-background-color : #ea593c");
        }
    }
}


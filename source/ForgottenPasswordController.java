import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Class that controlls ForgottenPassword.fxml
 *
 * @author Ivan Andreev
 */
public class ForgottenPasswordController extends Controller {

    /**
     * The back button.
     */
    @FXML
    private Button backButton;

    /**
     * The textfield for secret answer.
     */
    @FXML
    private PasswordField secretAnswerTextfield;

    /**
     * The textfield for username.
     */
    @FXML
    private TextField usernameTextfield;

    /**
     * The textfield for secret question.
     */
    @FXML
    private TextField secretQuestionTextfield;

    /**
     * The secret question button.
     */
    @FXML
    private Button getSecretQuestionButton;

    /**
     * The button to email a new password.
     */
    @FXML
    private Button emailPasswordButton;

    /**
     * When back button is clicked, goes back to login screen.
     *
     * @param event Back button being clicked.
     */
    @FXML
    public void backButtonClicked(ActionEvent event) {
        new NewWindow("resources/Login.fxml", event,
                "TaweLib - Login", getLibrary());

    }

    @FXML
    public void emailPasswordButtonClicked(ActionEvent event) {
        // check if the secret answer matches (secret answer is saved as a password)
        User user =  getLibrary().getUserManager().getUserByUsername(usernameTextfield.getText());
        if(!Security.checkPassword(secretAnswerTextfield.getText(),
                user.getSecuritySalting(), user.getSecretAnswer())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong security answer!",
                    ButtonType.OK);
            alert.show();
        } else {
            // successfully answers the secret question

            // generate a new password
            String newPassword = Security.generateRandomUnencodedPassword();

            // set the password (encoded) to the user
            user.setPassword(Security.generatePassword(newPassword, user.getSecuritySalting()));

            String newLine = "<br>";
            // send the email to the user with the new password
            String messageText = "Hello." + newLine + "Your new password is: " + newPassword
                    + newLine +  "Best regards, " + newLine + "TaweLib team";

            MailSender.sendEmail(user.getEmail(), "Tawe-Lib new password", messageText);


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "New password sent to email " +
                    user.getEmail() + " !",
                    ButtonType.OK);
            alert.show();

            //  clear the fields
            usernameTextfield.clear();
            secretQuestionTextfield.clear();
            secretAnswerTextfield.clear();
        }
    }

    /**
     * Finds the secret question of the user based on username given.
     *
     * @param event The secret question button being clicked.
     */
    @FXML
    public void getSecretQuestionButtonClicked(ActionEvent event) {
        String username = usernameTextfield.getText();
        User user = getLibrary().getUserManager().getUserByUsername(username);

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username " + username + " doesn't exist.",
                    ButtonType.OK);
            alert.show();
        } else {
            // user is found
            secretQuestionTextfield.setText(user.getSecretQuestion());
            usernameTextfield.setDisable(true);
        }
    }

}

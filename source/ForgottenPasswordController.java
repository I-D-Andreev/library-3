import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ForgottenPasswordController extends Controller {

    @FXML
    private Button backButton;

    @FXML
    private PasswordField secretAnswerTextfield;

    @FXML
    private TextField secretQuestionTextfield;

    @FXML
    private Button getSecretQuestionButton;

    @FXML
    private Button emailPasswordButton;

    @FXML
    public void backButtonClicked(ActionEvent event) {
        new NewWindow("resources/Login.fxml", event,
                "TaweLib - Login", getLibrary());

    }

    @FXML
    public void emailPasswordButtonClicked(ActionEvent event) {

    }

    @FXML
    public void getSecretQuestionButtonClicked(ActionEvent event) {

    }

}

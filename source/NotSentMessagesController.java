import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

/**
 * Controller class for the Not sent messages screen.
 */
public class NotSentMessagesController extends Controller {

    /**
     * Table for the messages that are not sent.
     */
    @FXML
    private TableView<MimeMessageTableRepresentation> notSentMessagesTable;

    /**
     * The column with the user's email.
     */
    @FXML
    private TableColumn<MimeMessageTableRepresentation, String> emailColumn;

    /**
     * The column with the title of the message.
     */
    @FXML
    private TableColumn<MimeMessageTableRepresentation, String> messageTitleColumn;

    /**
     * The back button.
     */
    @FXML
    private Button backButton;

    /**
     * The data inside the table.
     */
    @FXML
    private ObservableList<MimeMessageTableRepresentation> data;

    /**
     * Handles clicking the back button. Goes back to the librarian dashboard.
     *
     * @param event Clicking the back button.
     */
    @FXML
    public void backButtonClicked(ActionEvent event) {
        new NewWindow("resources/LibrarianDashboard.fxml", event, "Dashboard - TaweLib", getLibrary());
    }

    /**
     * Initializes the table and fills it in with data.
     */
    @Override
    public void onStart() {
        data = FXCollections.observableArrayList();
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<MimeMessageTableRepresentation, String>("email"));

        messageTitleColumn.setCellValueFactory(
                new PropertyValueFactory<MimeMessageTableRepresentation, String>("subject"));

        this.fillInData();
        notSentMessagesTable.getItems().addAll(data);
    }

    /**
     * Fills the data in the table.
     */
    private void fillInData() {
        for (MimeMessage m : MailSender.NOT_SENT_MESSAGES) {

            try {
                String email = m.getRecipients(Message.RecipientType.TO)[0].toString();
                String subject = m.getSubject();
                data.add(new MimeMessageTableRepresentation(email, subject));

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Problem filling up the table.",
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }



}

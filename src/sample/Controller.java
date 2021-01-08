package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Controller {

    @FXML
    private TableView<Message> messageTable;

    @FXML
    private TableColumn<Message, String> machineIDColumn;

    @FXML
    private TableColumn<Message, String> descriptionColumn;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button submitButton;

    @FXML
    private TextField machineIDText;

    @FXML
    private TextField descriptionText;

    @FXML
    private Button insertButton;

    @FXML
    private TextField searchTextField;

    private boolean filterDescription = false;

    private ObservableList<Message> messages = FXCollections.observableArrayList
    (
            new Message("DataBase Inconsistencies", "Issue with schema not being updated consistently throughout all platforms. Must  fix uniformity."),
            new Message("Disk Drive Error", "Randomly pops out while watching YouTube"),
            new Message("Audio Jack Broken", "Not a big deal, but is annoying."),
            new Message("Freezing Computers"),
            new Message("Weird Computer Toots", "Odd. Only happens on Windows"),
            new Message("Overheating", "CPU cooler must be faulty"),
            new Message("New Issue"),
            new Message("Monitor Flashing On and Off"),
            new Message("Caution On Older Hardware"),
            new Message("Incompatible on MacOS", "Windows just works so much better for our systems.")
    );
    private SortedList<Message> sortedList;


    public Controller() {

    }

    @FXML
    private void initialize() {
        machineIDColumn.setCellValueFactory(cellData -> cellData.getValue().messageIDProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        messageTable.getItems().setAll(messages);

        FilteredList<Message> filteredMessages = new FilteredList<Message>(messages, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           filteredMessages.setPredicate(message -> {
               if (newValue == null || newValue.isEmpty()) {
                   return true;
               }

               String lowerCaseFilter = newValue.toLowerCase();

               if (!filterDescription) {
                   return message.getMessageID().toLowerCase().contains(lowerCaseFilter);
               } else {
                   return message.getDescription().toLowerCase().contains(lowerCaseFilter);
               }
           });
        });

        sortedList = new SortedList<>(filteredMessages);
        sortedList.comparatorProperty().bind(messageTable.comparatorProperty());
        messageTable.setItems(sortedList);

    }

    public void insertMessage() {
        if (!(machineIDText.getText().equals("") || descriptionText.getText().equals("")) && !duplicateMessageID(messages, machineIDText.getText())) {
            messages.add(new Message(machineIDText.getText(), descriptionText.getText()));
        }
    }

    public void fillDescriptionBox(MouseEvent event) {
        if (event.getClickCount() == 1 && messageTable.getSelectionModel().getSelectedItem() != null) {
            descriptionTextArea.clear();
            descriptionTextArea.setText(messageTable.getSelectionModel().getSelectedItem().getDescription());
        }
    }

    public void updateDescription() {
        messageTable.getSelectionModel().getSelectedItem().setDescription(descriptionTextArea.getText());
        JOptionPane.showMessageDialog(null, "Description Updated.");
    }

    public boolean duplicateMessageID(List<Message> list, String messageID) {
        for (Message message : list) {
            if (message.getMessageID().equals(messageID)) {
                return true;
            }
        }

        return false;
    }

    public void removeMessage() {
        if (messageTable.getSelectionModel().getSelectedItem() != null) {
            messages.remove(messageTable.getSelectionModel().getSelectedItem());
        }
    }

    public void filterDescription() {
        String promptText;
        filterDescription = !filterDescription;
        if (filterDescription) promptText = "Filter (Description)"; else promptText = "Filter (Machine ID)";
        searchTextField.setPromptText(promptText);
    }

    public void removeFiltered() {
        messages.removeAll(sortedList);
    }

    public void countFiltered() {
        JOptionPane.showMessageDialog(null, "Amount From Filter: " + sortedList.size());
    }

}

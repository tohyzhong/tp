package cpp.ui;

import cpp.model.assignment.ContactAssignmentWithContact;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of contact-assignment entries for an assignment.
 */
public class ContactAssignmentListPanel extends UiPart<Region> {
    private static final String FXML = "ContactAssignmentListPanel.fxml";

    @FXML
    private ListView<ContactAssignmentWithContact> contactAssignmentListView;

    /**
     * Creates a {@code ContactAssignmentListPanel} with the given
     * {@code ObservableList} of {@code ContactAssignmentWithContact}.
     */
    public ContactAssignmentListPanel(ObservableList<ContactAssignmentWithContact> cas) {
        super(ContactAssignmentListPanel.FXML);
        this.contactAssignmentListView.setItems(cas);
        this.contactAssignmentListView.setCellFactory(listView -> new ContactAssignmentListViewCell());
    }

    class ContactAssignmentListViewCell extends ListCell<ContactAssignmentWithContact> {
        @Override
        protected void updateItem(ContactAssignmentWithContact caWithContact, boolean empty) {
            super.updateItem(caWithContact, empty);
            if (empty || caWithContact == null) {
                this.setGraphic(null);
                this.setText(null);
            } else {
                this.setGraphic(new ContactAssignmentCard(caWithContact, this.getIndex() + 1).getRoot());
            }
        }
    }
}

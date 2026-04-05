package eus.ehu.controllers;

import java.time.LocalDate;
import java.util.List;

import eus.ehu.businesslogic.BusinessLogic;
import eus.ehu.usermodel.Comment;
import eus.ehu.usermodel.Post;
import eus.ehu.usermodel.User;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CommentOnPostController {

    @FXML
    private TextField wordCount;

    @FXML
    private TextArea commentArea;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;


    // TABLE OF COMMENTS
    @FXML
    private TableView<Comment> commentsTable;

    @FXML
    private TableColumn<Comment, String> usernameColumn;

    @FXML
    private TableColumn<Comment, String> commentTextColumn;
    
    @FXML
    private ObservableList<Comment> comments;
    
    // variables to store the context of the comment
    private Post currentPost;
    private User currentUser;
    private BusinessLogic businessLogic; 


    // initialize method to set up the character limit and word count display
    @FXML
    void initialize() {
        int maxCharacters = 150;
        
        // create a textformatter to prevent exceeding the character limit
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= maxCharacters) {
                return change;
            }
            // reject the change if it exceeds the limit
            return null; 
        });
        
        commentArea.setTextFormatter(textFormatter);

        // show the character count dynamically binding the text property
        wordCount.textProperty().bind(
            Bindings.createStringBinding(
                () -> commentArea.getText().length() + "/" + maxCharacters,
                commentArea.textProperty()
            )
        );

        // TABLE OF COMMENTS
        // set up the column cell value factories for the tables
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("author")); //calls getAuthor() from Comment class
        commentTextColumn.setCellValueFactory(new PropertyValueFactory<>("text")); //calls getText() from Comment class
    }

    // method to receive data from the window that opens this controller
    public void initData(Post post, BusinessLogic bl) {
        this.currentPost = post;
        this.businessLogic = bl;

        // load the comments of the post into the table
        loadComments();
    }

    // handle the save button click event
    @FXML
    void handleSave() {
        String commentText = commentArea.getText(); // get the comment text from the text area
        
        // basic validaiton

        if (commentText.trim().isEmpty()) {
            System.out.println("comment is empty!");
            errorLabel.setVisible(true);
            
            // after 3 secs. -> hide the error label again
            PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(3));
                    
            pause.setOnFinished(e -> { 
                
                // remove error label
                errorLabel.setVisible(false);
            });
            pause.play();
            return;
        }

        // get user from bl (it should be there cause you can't get to the comment screen without being logged in)
        currentUser = businessLogic.getCurrentUser(); // get the current logged-in user from the business logic

        // shouldn't happen but just in case
        if(currentPost == null || currentUser == null) {
            System.out.println("post or user context is missing!");
            return;
        }

        // print the comment details to the console
        System.out.println("comment saved: " + commentText);
        
        // create the comment object
        Comment newComment = new Comment(currentUser.getUsername(), commentText, LocalDate.now(), currentPost);
        
        // send it to the database via business logic
        businessLogic.addCommentToPost(currentPost, newComment);
        
        // clear the comment area after saving
        commentArea.clear();
       
        // refresh the comments table to show the new comment
        loadComments();
    }

    //handle the cancel button click event
    @FXML
    private void cancelComment() {
        openFeedAndCloseComment();
    }

    private void openFeedAndCloseComment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eus/ehu/FeedPage.fxml"));
            Parent root = loader.load();

            // get controller for the feed page
            FeedController feedController = loader.getController();

            // inject the business logic to the feed controller so it can load the posts from the db
            feedController.initData(this.businessLogic); // pass the bl so the feed can load the posts from the db
            
            Stage feedStage = new Stage();
            feedStage.setScene(new Scene(root));
            feedStage.show();

            Stage commentStage = (Stage) saveButton.getScene().getWindow();
            commentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComments() {

        // get comments from current post
        List<Comment> commentsPost = currentPost.getComments();
    
        // add them to the observable list
        comments = FXCollections.observableArrayList(commentsPost);

        // show them in the table
        commentsTable.setItems(comments);
    }
}
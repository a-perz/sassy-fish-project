package eus.ehu.businesslogic;

import java.util.List;

import eus.ehu.usermodel.Comment;
import eus.ehu.usermodel.Post;

public interface BlInterface {
    
    // adds a comment to a specific post and saves it to the database
    void addCommentToPost(Post post, Comment comment);
    
    // persists a newly created post into the database
    void savePost(Post post);

    // increments/decrements the like count of a post and updates it in the database
    void updateLikePost(Post post);

    // retrieves a list of all posts currently stored in the databas
    List<Post> getAllPosts();

    // retrieves a list of all comments currently stored in the database
    List<Comment> getAllComments();
}
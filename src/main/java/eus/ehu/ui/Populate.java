package eus.ehu.ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eus.ehu.businesslogic.BusinessLogic;
import eus.ehu.usermodel.Comment;
import eus.ehu.usermodel.Post;
import eus.ehu.usermodel.Tag;
import eus.ehu.usermodel.User;

/**
 * Small utility to populate the application's database with example data.
 * Run this class to create a few users, posts, comments and favourites.
 */
public class Populate {

	public static void main(String[] args) {
		new Populate().populate();
	}

	public void populate() {
		BusinessLogic bl = BusinessLogic.getInstance();

		Map<String, User> users = new HashMap<>();

		System.out.println("Starting DB population...");

		// Create users via the BusinessLogic login flow so they are saved consistently
		String[][] sampleUsers = { {"alice", "alicepass"}, {"bob", "bobpass"}, {"carol", "carolpass"} };
		for (String[] u : sampleUsers) {
			String username = u[0];
			String pass = u[1];
			boolean ok = bl.login(username, pass);
			if (!ok) {
				System.out.println("Warning: could not create/login user: " + username);
			}
			users.put(username, bl.getCurrentUser());
		}

		// Create posts for users
		Post p1 = new Post();
		p1.setTitle("Delicious homemade paella");
		p1.setDescription("Tried a new recipe today, turned out amazing!");
		p1.setUser(users.get("alice"));
		p1.addTag(Tag.FOOD);
		var res1 = getClass().getResource("/eus/ehu/profilePic/fish.jpg");
		if (res1 != null) p1.setImagePath(res1.toExternalForm());
		p1.setDate(LocalDate.now().minusDays(2));
		p1.setLikeCount(5);
		bl.savePost(p1);

		Post p2 = new Post();
		p2.setTitle("Movie night recommendations");
		p2.setDescription("Looking for feel-good movies. Any suggestions?");
		p2.setUser(users.get("bob"));
		p2.addTag(Tag.MOVIE);
		var res2 = getClass().getResource("/eus/ehu/profilePic/bob.jpg");
		if (res2 != null) p2.setImagePath(res2.toExternalForm());
		p2.setDate(LocalDate.now().minusDays(1));
		p2.setLikeCount(3);
		bl.savePost(p2);

		Post p3 = new Post();
		p3.setTitle("Best workout playlist");
		p3.setDescription("Share your favourite pump-up tracks.");
		p3.setUser(users.get("carol"));
		p3.addTag(Tag.MUSIC);
		var res3 = getClass().getResource("/eus/ehu/profilePic/menta.jpg");
		if (res3 != null) p3.setImagePath(res3.toExternalForm());
		p3.setDate(LocalDate.now());
		p3.setLikeCount(2);
		bl.savePost(p3);

		// Add comments
		Comment c1 = new Comment();
		c1.setAuthor("bob");
		c1.setText("Looks delicious, recipe please!");
		c1.setDate(LocalDate.now().minusDays(1));
		bl.addCommentToPost(p1, c1);

		Comment c2 = new Comment();
		c2.setAuthor("alice");
		c2.setText("I recommend The Intouchables and Amélie.");
		c2.setDate(LocalDate.now());
		bl.addCommentToPost(p2, c2);

		// Mark favourites
		bl.updateFavouriteForUser("alice", p2, true); // alice favourites bob's post
		bl.updateFavouriteForUser("bob", p1, true);   // bob favourites alice's post

		// Summary
		List<Post> allPosts = bl.getAllPosts();
		List<Comment> allComments = bl.getAllComments();

		System.out.println("DB population finished.");
		System.out.println("Posts in DB: " + allPosts.size());
		System.out.println("Comments in DB: " + allComments.size());
		System.out.println("Sample users created: " + users.keySet());
	}

}

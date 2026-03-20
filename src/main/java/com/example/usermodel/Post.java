package com.example.usermodel;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Post {

    private String title;
    private String description;
    private boolean isFavourite;
    private int starRating; // 1-5
    private List<Tag> tags = new ArrayList<>(); // ENUM of tags

    
    // IMAGE
    private Image image;
    private String imagePath;

    public Post(String title, String description, String imagePath) {
        this.title = title;
        this.description = description;
        this.isFavourite = false;
        this.image = new Image(imagePath);
    }

    public Post() {
    }

    // GETTERS & SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public List<Tag> getTags() {
        return tags;
    }


    public void addTag(Tag tag) { // tag w/ ENUM sintax (value)
        tags.add(tag);
    }

    public void clearTags() {
        tags.clear();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    
}

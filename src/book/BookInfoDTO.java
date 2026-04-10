package book;

import author.Author;
import category.Category;

import java.util.ArrayList;

public class BookInfoDTO {

    private int id;
    private String title;
    private int yearPublished;
    private int availableCopies;
    private String summary;
    private ArrayList<Author> authors;
    private ArrayList<Category> categories;

    public BookInfoDTO(int id, String title, int yearPublished, int availableCopies, String summary,
                       ArrayList<Author> authors, ArrayList<Category> categories) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.availableCopies = availableCopies;
        this.summary = summary;
        this.authors = authors;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Book-id:" + id +
                ", Title: '" + title + '\'' +
                ", Year Published: " + yearPublished +
                ", Available Copies: " + availableCopies +
                ", Summary: '" + summary + '\'' +
                ", Author/s: " + authors +
                ", Categories: " + categories;
    }
}

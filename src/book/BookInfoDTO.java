package book;

import author.Author;
import category.Category;

import java.util.ArrayList;
import java.util.List;

public class BookInfoDTO {

    private int id;
    private String title;
    private int yearPublished;
    private int availableCopies;
    private String summary;
    private List<Author> authors;
    private List<Category> categories;

    public BookInfoDTO(int id, String title, int yearPublished, int availableCopies, String summary,
                       List<Author> authors, List<Category> categories) {
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Book #" + id +
                ", Title: '" + title + "'" +
                ", " + yearPublished +
                ", Available Copies: " + availableCopies +
                ", Summary: '" + summary + "'" +
                ", Author/s: " + authors +
                ", Categories: " + categories;
    }
}

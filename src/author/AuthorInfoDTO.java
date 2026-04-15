package author;

public class AuthorInfoDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String nationality;
    private String biography;

    public AuthorInfoDTO(int id, String firstName, String lastName,
                         String nationality, String biography) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.biography = biography;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Author-id:" + id +
                ", Name: '" + firstName + " " + lastName +
                ", Nationality: '" + nationality +
                ", Biography: '" + biography;
    }
}

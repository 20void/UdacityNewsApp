package android.example.newsapp;

public class Article {
    private String name;
    private String section;
    private String author;
    private String date;

    public Article(String name, String section, String author,  String date){
        this.name = name;
        this.section = section;
        this.author = author;
        this.date = date;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}

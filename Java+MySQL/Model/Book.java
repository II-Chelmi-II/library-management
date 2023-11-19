package Model;

public class Book {
    private int bookId;
    private String bookName;
    private int pageNumbers;
    private Topic topic;
    private java.util.Date releaseDate;
    private int authorId;

    public enum Topic {
        COMEDY, ROMANCE, OTHER
    }

    public Book() {
    }

    public Book(int bookId, String bookName, int pageNumbers,Topic topic, java.util.Date releaseDate, int authorId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.pageNumbers = pageNumbers;
        this.topic = topic;
        this.releaseDate = releaseDate;
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(int pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public java.util.Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(java.util.Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}

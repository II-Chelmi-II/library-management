package Model;

public class Author {
    private int authorId;
    private String name;
    private String sex;

    public Author(int authorId, String name, String sex) {
        this.authorId = authorId;
        this.name = name;
        this.sex = sex;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

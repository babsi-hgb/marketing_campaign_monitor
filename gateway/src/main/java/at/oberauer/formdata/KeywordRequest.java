package at.oberauer.formdata;

/**
 * Created by michael on 05.01.18.
 */
public class KeywordRequest {
    private String keyword;
    private int width;
    private int height;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeywordRequest that = (KeywordRequest) o;

        if (width != that.width) return false;
        if (height != that.height) return false;
        return keyword != null ? keyword.equals(that.keyword) : that.keyword == null;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "KeywordRequest{" +
                "keyword='" + keyword + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

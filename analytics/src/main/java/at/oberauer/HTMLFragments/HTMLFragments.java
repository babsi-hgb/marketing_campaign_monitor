package at.oberauer.HTMLFragments;

/**
 * Created by michael on 06.12.17.
 */
public class HTMLFragments {
    public static final String CONTENT_START = "<html><head><title>Title</title></head><body>";
    public static final String CONTENT_END = "</body></html>";
    public static final String TABLE_START = "<table>";
    public static final String TABLE_END = "</table>";
    public static String generateTableRow(String... content){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        for(String s : content){
            sb.append("<td>" + content + "</td>");
        }
        sb.append("</tr>");
        return sb.toString();
    }
}

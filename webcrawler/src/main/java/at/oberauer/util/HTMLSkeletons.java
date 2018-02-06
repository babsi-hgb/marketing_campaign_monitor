package at.oberauer.util;


/**
 * Created by michael on 11.09.17.
 */
public class HTMLSkeletons {
    public static String openBodyTitle(String title){
        return "<!DOCTYPE html><html><head><title>" + title + "</title></head><body>";
    }
    public static String closeBody(){
        return "</body></html>";
    }
    public static String tableRow(String... cols){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        for(String s : cols){
            sb.append("<td>" + s + "</td>");
        }
        sb.append("</tr>");
        return sb.toString();
    }
}

package app.lyricsapp.controller;
import java.util.ResourceBundle;
public class RunCLIeng {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public static String getString(String key) {
        return bundle.getString(key);
    }
}

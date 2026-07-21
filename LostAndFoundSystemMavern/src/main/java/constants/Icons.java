//230939023
package constants;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Icons {

    public static ImageIcon Home;
    public static ImageIcon Search;
    public static ImageIcon Filter;
    public static ImageIcon LostItem;
    public static ImageIcon FoundItem;
    public static ImageIcon Claims;
    public static ImageIcon ViewAllPosts;
    public static ImageIcon Bell;

    static {
        Home = loadIcon("/icons/home.png");
        Search = loadIcon("/icons/search.png");
        Filter = loadIcon("/icons/search.png"); // Replace when you have filter.png
        LostItem = loadIcon("/icons/lost-item.png");
        FoundItem = loadIcon("/icons/found-item.png");
        Claims = loadIcon("/icons/help.png");
        ViewAllPosts = loadIcon("/icons/view-all-posts.png");
        Bell = loadIcon("/icons/bell.png");
    }

    private static ImageIcon loadIcon(String path) {

        URL url = Icons.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("Icon not found: " + path);
        }

        Image image = new ImageIcon(url).getImage();
        Image scaled = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }
}
// 230939023
package constants;
import java.awt.Font;
import java.io.InputStream;
public class Fonts {

    public static Font Bold;
    public static Font BoldItalic;
    public static Font ExtraBold;
    public static Font ExtraBoldItalic;
    public static Font ExtraLight;
    public static Font ExtraLightItalic;
    public static Font Italic;
    public static Font Light;
    public static Font LightItalic;
    public static Font Medium;
    public static Font MediumItalic;
    public static Font Regular;
    public static Font SemiBold;
    public static Font SemiBoldItalic;

    static {
        try {
            Bold = loadFont("/fonts/PlusJakartaSans-Bold.ttf");
            BoldItalic = loadFont("/fonts/PlusJakartaSans-BoldItalic.ttf");
            ExtraBold = loadFont("/fonts/PlusJakartaSans-ExtraBold.ttf");
            ExtraBoldItalic = loadFont("/fonts/PlusJakartaSans-ExtraBoldItalic.ttf");
            ExtraLight = loadFont("/fonts/PlusJakartaSans-ExtraLight.ttf");
            ExtraLightItalic = loadFont("/fonts/PlusJakartaSans-ExtraLightItalic.ttf");
            Italic = loadFont("/fonts/PlusJakartaSans-Italic.ttf");
            Light = loadFont("/fonts/PlusJakartaSans-Light.ttf");
            LightItalic = loadFont("/fonts/PlusJakartaSans-LightItalic.ttf");
            Medium = loadFont("/fonts/PlusJakartaSans-Medium.ttf");
            MediumItalic = loadFont("/fonts/PlusJakartaSans-MediumItalic.ttf");
            Regular = loadFont("/fonts/PlusJakartaSans-Regular.ttf");
            SemiBold = loadFont("/fonts/PlusJakartaSans-SemiBold.ttf");
            SemiBoldItalic = loadFont("/fonts/PlusJakartaSans-SemiBoldItalic.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Font loadFont(String path) throws Exception {
        //Allows the use of the custom_fonts outside the class
        try (InputStream is = Fonts.class.getResourceAsStream(path)) {

            if (is == null) {
                throw new RuntimeException("Font not found: " + path);
            }

            return Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 12);
        }
    }

    //Prevents creating new constructors
    private Fonts() {
    }
}

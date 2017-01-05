import java.util.List;

public class HumbleEntry {
    private String name;
    private String price;
    private List<String> words;
    private List<String> colors;
    private String targetUrl;

    private String imagePath;

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HumbleRegistry {

    private List<HumbleEntry> entries;
    private File dir;
    private Gson gson;

    public HumbleRegistry(File dir){
        this();
        this.setDirectory(dir);
    }

    public HumbleRegistry(){
        this.gson = new Gson();
    }

    public void setDirectory(File dir){
        this.dir = dir;
    }

    private List<HumbleEntry> produceRegistry(File rootDir) {
        ArrayList<HumbleEntry> entries = new ArrayList<HumbleEntry>();
        for(String folderName : rootDir.list()) {
            File folder = new File(rootDir + File.separator + folderName);
            if (folder.isDirectory()) {
                HumbleEntry entry = new HumbleEntry();
                for (String fileName : folder.list()) {
                    File file = new File(rootDir + File.separator + folderName + File.separator + fileName);;
                    if (file.getName().contains(".json")) {
                        try {
                            JsonReader reader = new JsonReader(new FileReader(file));
                            Type type = new TypeToken<HumbleEntry>() {
                            }.getType();
                            HumbleEntry newEntry = gson.fromJson(reader, type);
                            newEntry.setImagePath(entry.getImagePath());
                            entry = newEntry;

                        } catch (FileNotFoundException e) {
                            //Silent
                        }
                    } else if (isImage(file)) {
                        entry.setImagePath(file.getPath());
                    }
                }
                if(entry.getImagePath() != null){
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    private boolean isImage(File file){
        String mimetype = new MimetypesFileTypeMap().getContentType(file);
        return mimetype.split("/")[0].equals("image");
    }

    public String toJson(){
        if(this.dir == null){
            return "";
        }else{
            return gson.toJson(produceRegistry(this.dir));
        }
    }

    public void toFile(String targetFile){
        try(PrintWriter out = new PrintWriter( targetFile )  ){
            out.println(toJson());
        }catch(FileNotFoundException e){
            //Silent
        }
    }
}

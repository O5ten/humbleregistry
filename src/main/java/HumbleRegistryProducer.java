import org.apache.commons.cli.*;

import java.io.File;

public class HumbleRegistryProducer {

    private File rootFolder;
    private String targetFile;

    public HumbleRegistryProducer(CommandLine cmd){
        if(cmd.hasOption("f")){
            rootFolder = new File(cmd.getOptionValue("f"));
        }else{
            rootFolder = new File(System.getProperty("user.dir"));
            System.out.println("No root folder specified, assuming working directory to be root-folder");
        }
        if(cmd.hasOption("t")){
            this.targetFile = cmd.getOptionValue("t");
        }else{
            System.out.println("No target file specified, writing registry to default registry.json");
            this.targetFile = "registry.json";
        }
    }

    public void produceRegistry(){
        HumbleRegistry registry = new HumbleRegistry(rootFolder);
        registry.toFile(targetFile);
    }

    public static void main(String[] args){
        Options options = new Options();
        options.addOption("f", "folder", true, "absolute or relative path to folder to construct registry from");
        options.addOption("t", "target", true, "absolute or relative path to file to write the registry to");
        CommandLineParser parser = new BasicParser();
        try{
            CommandLine cmd = parser.parse(options, args);
            HumbleRegistryProducer humbleRegistryProducer = new HumbleRegistryProducer(cmd);
            System.out.println("Building registry");
            humbleRegistryProducer.produceRegistry();
        }catch(ParseException e){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HumbleRegistry", options);
        }
    }
}

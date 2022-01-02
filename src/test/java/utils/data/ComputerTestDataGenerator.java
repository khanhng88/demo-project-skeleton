package utils.data;

import com.google.gson.Gson;
import testdata.purchasing.computer.ComputerDataObject;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ComputerTestDataGenerator {

    public static ComputerDataObject[] getTestDataFrom(String jsonDataFileLocation) {
        ComputerDataObject[] cheapComputers = new ComputerDataObject[]{};
        String currentProjectLocation = System.getProperty("user.dir");
        try (  Reader reader = Files.newBufferedReader(Paths.get(currentProjectLocation +"/"+ jsonDataFileLocation)))
        {
            // create Gson instance
            Gson gson = new Gson();

            // Convert to array of Computer instances
            cheapComputers = gson.fromJson(reader, ComputerDataObject[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cheapComputers;
    }

}

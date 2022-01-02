package utils.data;

import com.google.gson.Gson;
import testdata.user.UserData;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonData {

    //return User data object
    public static UserData buildUserData(String dataFileLocation) {
        return buildCommonDataObject(dataFileLocation, UserData.class);
    }

    //build common data object from Json
    private static <T> T buildCommonDataObject(String dataFileLocation, Class <T> dataType) {
        String currentProjectLocation = System.getProperty("user.dir");
        try (  Reader reader = Files.newBufferedReader(Paths.get(currentProjectLocation +"/"+ dataFileLocation)))
        {
            // create Gson instance
            Gson gson = new Gson();

            // Convert to array of Computer instances
           return gson.fromJson(reader, dataType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

package files_input_output.part18_manage_files_2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class TransferToExamples {

    /* transferTo()
     *
     * Let's use functionality in the java.net package to make a request to a website, to get a JSON response
     * Will create a local variable with url to request data from US Census Bureau which will return
     *
     * Then create a Uniform Resource Identifier or URI which is a class in java.net and create an instance, passing the create() the urlString
     *
     * URI provides handy ()s to get an input stream based on the U.R.I in a try-with-resources {}
     *
     * Running this prints JSON response printed to the console
     *
     * Do something similar, request this data and dump it to a file
     * Set up a variable jsonPath , a file named USPopulation.txt
     *
     * Running this outputs "USPopulation.txt"
     *
     * Suppose we want this to be a csv file and not JSON
     */

    public static void main(String[] args) {

      String urlString = "https://api.census.gov/data/2019/pep/charagegroups?get=NAME,POP&for=state:*";
        URI uri = URI.create(urlString);
        try( var urlInputStream = uri.toURL().openStream();
        ){
            urlInputStream.transferTo(System.out);
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        Path jsonPath = Path.of("USPopulation.txt");
        try(var reader = new InputStreamReader(uri.toURL().openStream());
            var writer = Files.newBufferedWriter(jsonPath);
            ){
            reader.transferTo(writer);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}


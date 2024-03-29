package files_input_output.part18_manage_files_2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;

public class TransferToExampleCSV {

    /* transferTo() - output to a CSV File
     * We can use transferTo() with a customized writer that will do a transformation first
     * Read from the URl via InputStreamReader
     * Declare a PrintWriter instance and pass the file name "USPopulation.csv"
     * Call transferTo() on reader instance and pass an instance of an anonymous Writer class
     * Implement 3 ()s since Writer is an abstract class
     *  - write() - do some transformation here
     *      - Create a local String instance and pass the args passed to this () to the String constructor
     *          - i.e. character buffer, offset and length
     *      - Replace the left square bracket char with a space and trim at the end
     *      - use replaceAll to replace the closing square bracket - since it's a metacharacter - escape it with \\
     *      - The 2 statements above remove "[" and "]" from the JSON response
     *      - Finally delegate to local variable write()
     *  - flush() - delegate to local variable flush()
     *  - close() - delegate to local variable close()
     *
     *
     * Running this:
     *  - Prints state population data in the console
     *  - Creates USPopulationByState.csv file on the project pane
     *
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


        try(var reader = new InputStreamReader(uri.toURL().openStream());
            PrintWriter writer = new PrintWriter("USPopulationByState.csv");
        ){
            reader.transferTo(new Writer() {
                @Override
                public void write(char[] cbuf, int off, int len) throws IOException {
                    String jsonString = new String(cbuf,off,len);
                    jsonString = jsonString.replace("[","").trim();
                    jsonString = jsonString.replaceAll("\\]","");
                    writer.write(jsonString);
                }

                @Override
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override
                public void close() throws IOException {
                    writer.close();
                }
            });

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}

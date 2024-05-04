package networking.part2_http_url_uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIBasics {

    /* Java's High Level Networking APIs , URI and URL
     * ................................................
     *
     * - In the last video, we left off taking a URI and making it a URL by calling toURL() on the URI instance
     * - This () works fine when the URI is absolute, meaning the URI contains all the info needed that a URL would
     *  require to locate a resource on a network
     * - Let's take a look at relative URIs next
     *   - Update tim site , remove scheme and domain
     *   - Update and print uri in the try-catch
     *
     * - Running this:
     *  - We don't have any problems & we can still get data listed for these URI in the first segment of output,
     *     though with a lot of null values
     *
     * - Let's change timSite URI to URL and print it
     * - Running this:
     *      - Throws an Illegal arg exception with a msg that URI is not absolute
     *      - IF a URI is not absolute, then it's relative which is what we have here - a relative URI
     *
     * What's a relative URI ?
     * ......................
     * A relative URI is a reference to a resource such as a web page, file, or image that's relative to the current
     *  context
     * Relative URIs are commonly used in web development, to specify the location of resources, in relation to the
     *  location of the current document or web page
     * The syntax is very similar to that of specifying a relative path, in a file directory system
     * Hopefully, you recall, a path , if you prefix it with a root drive, or a slash, the root is implied and its an
     *  absolute path
     * However, if you omit the initial slash, the path is relative to the cwd
     * Relative URI's are a similar concept
     *  - The syntax is similar including the use of a
     *      - single dot (.) to indicate the current location
     *      - two dot (.) to specify the parent location
     * Officially though, a relative URI, is not a URL and we can't use toURL() on this
     * You can't get an absolute URL from a relative URI because there isn't enough info in the URI to determine the
     *  absolute location of the resource
     * When you want to access a resource, you'll use a URL
     * At that point, the location of the resource has to be absolute, otherwise the java runtime won't have enough info
     *  to access it
     * There'll be times when you want to use relative URIs, but usually, you'll use them a long with a base URI
     * So, the base URI will specify the root of the relative path, which can be quite handy
     * If you're accessing lots of pages in a website, instead of working with absolute URI's, its probably better to
     *  have a base URI that contains the host info and relative URIs that don't
     * Therefore in that scenario, if the host location changes , you only have to update the base URI
     *  - Let's say for example , the host is located in http://example.com but later it changes to http://example.org
     *  - If you've used absolute URIs throughout your code, you'll have to go through your code and change each and every
     *    URI instance
     * But if you've used relative URIs with a base URI, then you really only have to change 1 instance of the base URI
     * So let's add the base URI for our relative URI
     *
     * Add it as the first line of code in the main()
     *  - Call it baseSite and call create() on URI class, passing it the base URI for Tim's website
     *  - Whenever we need to use my relative URI as a URL, we can resolve it to an absolute URI using the base
     *      - use this directly where we call timSite.toURL()
     *
     * Running this:
     *  - The code executes without any errors
     *  - We get the fully qualified path printed and the URL fields after that
     *
     * Now that we've got the URL, we can use it to communicate with the resource at that location
     * In many cases, that will be a web page
     *
     * Create a new class - WebContent with a main()
     *
     *
     *
     */

    public static void main(String[] args) {
        URI baseSite = URI.create("https://learnprogramming.academy/");
        URI timSite = URI.create("courses/complete-java-masterclass");
        print(timSite);

        try {
            URI uri = new URI("http://user:pw@store.com:5000/products/phones?os=android#samsung");
            print(uri);

            URL url = uri.toURL();
            print(url);

            //URL timSiteurl = timSite.toURL();
            URI masterClassUrl = baseSite.resolve(timSite);
            URL timSiteurl = masterClassUrl.toURL();
            print(timSiteurl);
            System.out.println(timSiteurl);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void print(URI uri){
        System.out.printf("""
                ----------------------------------------
                [scheme:]scheme-specific-part[#fragment]
                ----------------------------------------
                Scheme : %s
                Scheme-specific-part : %s
                    Authority: %s
                        User info : %s
                        Host : %s
                        Port : %s
                        Path : %s
                        Query : %s
                Fragment: %s
                """,
                uri.getScheme(),
                uri.getSchemeSpecificPart(),
                uri.getAuthority(),
                uri.getUserInfo(),
                uri.getHost(),
                uri.getPort(),
                uri.getPath(),
                uri.getQuery(),
                uri.getFragment()
                );
    }

    private static void print(URL url){
        System.out.printf("""
                ----------------------------------------            
                    Authority: %s
                        User info : %s
                        Host : %s
                        Port : %s
                        Path : %s
                        Query : %s
                """,
                url.getAuthority(),
                url.getUserInfo(),
                url.getHost(),
                url.getPort(),
                url.getPath(),
                url.getQuery()
        );
    }
}

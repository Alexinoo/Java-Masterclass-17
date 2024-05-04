package networking.part2_http_url_uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIBasics {

    /* Java's High Level Networking APIs , URI and URL
     * ................................................
     * We looked at java low level networking APIs which involves ports and sockets
     * However, most modern apps are only interested in accessing the internet
     *  - This is easily done using the high-level APIs Java provides
     * These types abstract networking concepts even further starting with URI
     *
     * URI, URL , URN
     * ...............
     * URI
     *  - stands for Uniform Resource Identifier
     *  - Is the broadest category of identifier & it encompasses any resource identifier
     *
     * URL
     *  - stands for Uniform Resource Locator
     *  - specifically locates resources on a network
     *  - includes both the name of the resource, and the protocol and address (like domain name and path) needed to
     *     access it
     *
     * URN
     *  - stands for Uniform Resource Name
     *  - often used to describe physical items , like a book's isbn number.
     *
     * The URL and URN are both subsets of a URI
     * You can think of a URL as a more specific type of a URI
     * URI strings may contain relative paths , but URLS won't
     * Java has classes for URI, URL and URLConnection
     *
     * URL Class
     * ..........
     *  - represents a URL, which Java describes as a pointer to a resource, in the World Wide Web
     *      - a resource can be something simple like a file or directory
     *      - or can be a reference to a more complicated obj, such as a query to a db, or search engine
     *
     * URI Class
     * .........
     *  - is made up of a hierarchical parts
     *      - At the to level, the format is shown as follows, consisting of a scheme, scheme-specific-part, and a fragment
     *          [scheme:]scheme-specific-parts[#fragment]
     *      - The scheme-specific-part consists of an authority, a path, and query as shown
     *          [//authority][path][?query]
     *      - The authority may consist of user-info, and a host and port in the following format
     *          [user-info@][host][:port]
     *  - In total, there are 9 components in the hierarchy, some of which are optional as listed below
     *      - refer from the slide
     *
     * URL to URI
     * ..........
     *  - Although a URI is a super set of URL, this isn't true in Java's class hierarchy
     *      - URL is not a subclass of URI
     *  - If you want to use a URI as a URL, you can use toURL on a URI instance
     *  - Similarly, if you want to use a URL as a URI, you use toURI on a URL instance
     *  - However, not all URIs are URLs, and this transformation may result in an exception
     *
     * Implementations
     * ...............
     *
     * Add a print(URI uri)
     * Print the component parts in a hierarchy
     * use printf and pass a text block as a formatted string
     *  - proper indentation to understand how all the components fit into the URI string
     * print the highest level's components as a header
     *  - Scheme is one of the outermost components - a stand alone component
     *  - Scheme-specific-part - is a composite part
     *      - First component is authority which in turn is composite
     *          - consists of user info, host and the port
     *      - Second component is the path
     *      - Third component is query
     *  - Fragment is an optional component that starts with a # sign
     * All the components can be retrieved by their getter ()s
     *
     * main()
     * Create a URI variable
     * There are 2 ways to do this
     *  - Call create() on URL class and pass a string literal
     *      - doesn't throw an exception
     *
     * call print() and pass the uri
     *  - "https://learnprogramming.academy/"
     *
     * Running this:
     *  - Prints the data for all 9 components
     *      - Scheme as : https
     *      - Scheme-specific-part as : //learnprogramming.academy/
     *      - Authority : learnprogramming.academy
     *      - User info : null
     *      - Host : learnprogramming.academy
     *      - Port : -1 (returns an int in this case -1 if not defined)
     *      - Path : /
     *      - Query: null
     *      - Fragment: null
     *
     * Update URI by adding some additional text to the string we supplied
     *  - "https://learnprogramming.academy/courses/complete-java-masterclass"
     *
     *  - Prints the data for all 9 components
     *      - Scheme as : https
     *      - Scheme-specific-part as : //learnprogramming.academy/courses/complete-java-masterclass
     *      - Authority : learnprogramming.academy
     *      - User info : null
     *      - Host : learnprogramming.academy
     *      - Port : -1 (returns an int in this case -1 if not defined)
     *      - Path : /complete-java-masterclass
     *      - Query: null
     *      - Fragment: null
     *
     * Let's try another kind of URI
     *  - Instead of using URI.create() , use a constructor, new URI()
     *  - throws a checked exception,  URISyntaxException
     *      - surround with a try catch
     *  - call print() on this URI
     *      - "http://user:pw@store.com:5000/products/phones?os=android#samsung"
     *
     * Running this:
     *
     * - Prints the data for all 9 components
     *  - All components are now defined
     *      - Scheme as : https
     *      - Scheme-specific-part as : //user:pw@store.com:5000/products/phones?os=android
     *      - Authority : user:pw@store.com:5000
     *      - User info : user:pw
     *      - Host : store.com
     *      - Port : 5000
     *      - Path : /products/phones
     *      - Query: os=android
     *      - Fragment: samsung
     *
     * We used a fake url , like it's not a real url and doesn't exist
     * You can create a URL instance with an invalid identifier, similar to creating a Path with an invalid file
     *  directory, as long as it is syntactically correct
     * So those are the basics of a URI
     *
     * Next,
     * Turn URI into URL
     *  - call toURL() on one of the my URI instance
     * Add this inside the try block
     *  - create a URL variable called url and assign that the result we get from uri.toURL()
     *      - toURL() throws a checked exception - MalformedURLException - add it to catch block
     *  - print the result out
     *
     *  - Running this again:
     *      - prints the URL's string which is exactly the same as the URI's string
     *
     * Next,
     *  - Copy the entire print() and paste a copy directly below
     *      - change the parameter from URI to URL
     *      - Notice that 3 getters that were on the URI class aren't valid for the URL class
     *          - e.g. getScheme() , getSchemeSpecificPart() and getFragment()
     *          - is because a url is a type that's specifically geared towards locating resources on the internet
     *      - Remove these getter ()s that are not applicable to URL as well as the headers
     *
     *  - main()
     *      - use timSite instead, to print learnprogramming.academy website
     *      - call print() on this
     *
     *  - Running this:
     *      - prints Authority and its subcomponents as well as the path
     *      - query is null in this case
     *
     */

    public static void main(String[] args) {
//        URI timSite = URI.create("https://learnprogramming.academy/");
        URI timSite = URI.create("https://learnprogramming.academy/courses/complete-java-masterclass");
        print(timSite);

        try {
            URI uri = new URI("http://user:pw@store.com:5000/products/phones?os=android#samsung");
            print(uri);

            URL url = uri.toURL();
            System.out.println(url);

            URL timSiteUrl = timSite.toURL();
            print(timSiteUrl);
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

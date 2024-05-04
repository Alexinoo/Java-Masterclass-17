package networking.part2_http_url_uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIAdvanced {

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

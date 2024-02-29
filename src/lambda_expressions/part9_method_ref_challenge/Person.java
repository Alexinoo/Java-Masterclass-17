package lambda_expressions.part9_method_ref_challenge;

public record Person(String first) {

    public String last(String someString){
        return first + " "+ someString.substring(0,someString.indexOf(" "));
    }
}

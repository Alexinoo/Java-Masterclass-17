package lambda_expressions.part1_lambdaIntro;

public record Person(String firstName,String lastName) {
    @Override
    public String toString() {
        return firstName + " "+ lastName;
    }
}

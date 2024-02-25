package lambda_expressions.part3_lambda_exprss_contnd;

@FunctionalInterface
public interface Operation<T> {
    T operate(T value1,T value2);
}

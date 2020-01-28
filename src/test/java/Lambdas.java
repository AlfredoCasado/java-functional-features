import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

@FunctionalInterface
interface Sum {
    Integer sum(Integer op1, Integer op2);
}

@FunctionalInterface
interface ProcessListener {
    void status(String status);
}

class Process {
    public void execute(ProcessListener listener) {
        listener.status("begin");
        listener.status("processing...");
        listener.status("end");
    }
}

class SumStatic {
    public static Integer sum(Integer a, Integer b) { return a+b;}
}

class SumInstance {
    public Integer sum(Integer a, Integer b) { return a+b;}
}

interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}

class Sumador {
    final int result;

    public Sumador(Integer a, Integer b) {
        this.result = a+b;
    }
}

public class Lambdas {

    @Test
    public void lambda_as_replacement_to_anonymous_classes() {
        Process process = new Process();

        // Usando clases anonimas como toda la vida
        process.execute(new ProcessListener() {
            public void status(String status) {
                assertThat(status).isIn("begin", "processing...", "end");
            }
        });

        // Se puede usar una lambda si la interfaz tiene un solo método, es recomendable
        // anotar estas interfaces con @FuncionalInterface
        process.execute((status) ->  {
            assertThat(status).isIn("begin", "processing...", "end");
        });

        // si el cuerpo de la lambda tiene una sola linea se puede hacer menos verboso
        // también se pueden eleminar los parentesis si sólo recibe un parametro
        process.execute(status -> assertThat(status).isIn("begin", "processing...", "end"));
    }

    @Test
    public void variable_assigned_to_a_lambda() {
        Sum lambda = (a, b) -> {
            return a + b;
        };

        // Si la expresión solo tiene una linea se puede simplificar
        Sum lambdaOneLine = (a, b) -> a + b;

        assertThat(lambda.sum(1,2)).isEqualTo(3);
        assertThat(lambdaOneLine.sum(1,2)).isEqualTo(3);
    }

    @Test
    public void variable_assigned_to_a_lambda_using_predefined_interfaces() {
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        assertThat(sum.apply(1,2)).isEqualTo(3);
    }

    @Test
    public void method_references() {
        // referencias a métodos estaticos "Class::method"
        BiFunction<Integer, Integer, Integer> sum = SumStatic::sum;
        assertThat(sum.apply(1,2)).isEqualTo(3);

        // referencias a métodos de una instancia existente
        SumInstance instance = new SumInstance();
        sum = instance::sum;
        assertThat(sum.apply(1,2)).isEqualTo(3);

        // referencias a métodos de instancia
        TriFunction<SumInstance, Integer, Integer, Integer> anotherSum = SumInstance::sum;
        assertThat(anotherSum.apply(new SumInstance(), 1, 2)).isEqualTo(3);

        // referencias a constructor
        BiFunction<Integer,Integer, Sumador> sumador = Sumador::new;
        assertThat(sumador.apply(2,3).result).isEqualTo(5);
    }
}

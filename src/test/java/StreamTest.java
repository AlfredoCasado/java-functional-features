import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class Apprentice {
    private String name;
    private float averageGrade;

    public Apprentice(String name, float averageGrade) {
        this.name = name;
        this.averageGrade = averageGrade;
    }

    public String getName() {
        return name;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apprentice apprentice = (Apprentice) o;
        return Float.compare(apprentice.averageGrade, averageGrade) == 0 &&
                Objects.equals(name, apprentice.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, averageGrade);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", averageGrade=" + averageGrade +
                '}';
    }
}

public class StreamTest {
    List<String> spainCities = List.of("MADRID", "BARCELONA");

    @Test
    public void stream_creation_empty() {
        Stream s = Stream.empty();
        assertThat(s.findFirst().isPresent()).isFalse();
    }

    @Test
    public void stream_creation_of() {
        Stream s = Stream.of(1,2,3);
        assertThat(s.toArray()).contains(1,2,3);
    }

    @Test
    public void stream_creation_iterate() {
        Stream<Integer> s = Stream.iterate(0, n -> n + 1).limit(10);
        assertThat(s.toArray()).contains(1,2,3,4,5,6,7,8,9);
    }

    @Test
    public void stream_from_a_collection() {
        List<String> cities = List.of("madrid", "barcelona", "londres");
        Stream<String> citiesStream = cities.stream();

        assertThat(citiesStream.toArray()).contains("madrid", "barcelona", "londres");
    }

    @Test
    public void stream_from_a_string() {
        String city = "madrid";
        IntStream chars = city.chars();

        assertThat(chars.toArray()).contains('m', 'a', 'd', 'r', 'i','d');
    }

    @Test
    public void stream_pipeline() {
        Optional<Integer> first = Stream.of(1, 2, 3)
                .skip(2)
                .findFirst();

        assertThat(first).contains(3);
    }

    @Test
    public void stream_pipeline_several_transformations() {
       List<String> cities = List.of("madrid", "barcelona", "londres").stream()

                .map(String::toUpperCase) // "MADRID", "BARCELONA", "LONDRES")
                .filter(spainCities::contains) // "MADRID", "BARCELONA"
                .skip(1) // "BARCELONA"

                .collect(Collectors.toList());

        assertThat(cities).contains("BARCELONA");
    }

    @Test
    public void stream_collect() {
        List<Apprentice> apprentices = List.of(
                new Apprentice("alvaro", 7),
                new Apprentice("sonia", 8),
                new Apprentice("abascal", 1)
        );

        Double averageAll = apprentices.stream()
                .collect(Collectors.averagingDouble(Apprentice::getAverageGrade));

        Double averagePassed = apprentices.stream()
                .filter(s -> s.getAverageGrade() >= 5)
                .collect(Collectors.averagingDouble(Apprentice::getAverageGrade));

        Double averageSuspended = apprentices.stream()
                .filter(s -> s.getAverageGrade() < 5)
                .collect(Collectors.averagingDouble(Apprentice::getAverageGrade));

        assertThat(averageAll).isEqualTo(5.33, within(0.01));
        assertThat(averagePassed).isEqualTo(7.5, within(0.01));
        assertThat(averageSuspended).isEqualTo(1);
    }

    @Test
    public void stream_collect_partition() {
        List<Apprentice> apprentices = List.of(
                new Apprentice("alvaro", 7),
                new Apprentice("sonia", 8),
                new Apprentice("abascal", 1)
        );

        Map<Boolean, List<Apprentice>> groupedStudents = apprentices.stream()
                .collect(Collectors.partitioningBy(apprentice -> apprentice.getAverageGrade() >= 5));

        List<Apprentice> pass = groupedStudents.get(true);
        List<Apprentice> not_pass = groupedStudents.get(false);

        assertThat(pass).hasSize(2);
        assertThat(not_pass).hasSize(1);
    }

    @Test
    public void stream_collect_grouping() {
        List<Apprentice> apprentices = List.of(
                new Apprentice("alvaro", 10),
                new Apprentice("sonia", 10),
                new Apprentice("abascal", 1)
        );

        Map<Float, List<Apprentice>> groupedStudents = apprentices.stream()
                .collect(Collectors.groupingBy(apprentice -> apprentice.getAverageGrade()));

        List<Apprentice> good_apprentices = groupedStudents.get(10f);
        List<Apprentice> bad_studewnts = groupedStudents.get(1f);

        assertThat(good_apprentices).hasSize(2);
        assertThat(bad_studewnts).hasSize(1);
    }

    @Test
    public void stream_reduce() {
        Integer sum = Stream.of(1, 2, 3)
                            .reduce(0, (acc, value) -> acc + value);

        assertThat(sum).isEqualTo(6);

        List<Apprentice> apprentices = List.of(
                new Apprentice("alvaro", 10),
                new Apprentice("sonia", 8),
                new Apprentice("abascal", 1)
        );

        Apprentice masListo = apprentices.stream()
                .reduce(new Apprentice("", 0), (acc, apprentice) -> apprentice.getAverageGrade() > acc.getAverageGrade() ? apprentice : acc);

        assertThat(masListo).isEqualTo(new Apprentice("alvaro", 10));

    }

}

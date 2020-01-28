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

class Student {
    private String name;
    private float averageGrade;

    public Student(String name, float averageGrade) {
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
        Student student = (Student) o;
        return Float.compare(student.averageGrade, averageGrade) == 0 &&
                Objects.equals(name, student.name);
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
        List<Student> students = List.of(
                new Student("alvaro", 7),
                new Student("sonia", 8),
                new Student("abascal", 1)
        );

        Double averageAll = students.stream()
                .collect(Collectors.averagingDouble(Student::getAverageGrade));

        Double averagePassed = students.stream()
                .filter(s -> s.getAverageGrade() >= 5)
                .collect(Collectors.averagingDouble(Student::getAverageGrade));

        Double averageSuspended = students.stream()
                .filter(s -> s.getAverageGrade() < 5)
                .collect(Collectors.averagingDouble(Student::getAverageGrade));

        assertThat(averageAll).isEqualTo(5.33, within(0.01));
        assertThat(averagePassed).isEqualTo(7.5, within(0.01));
        assertThat(averageSuspended).isEqualTo(1);
    }

    @Test
    public void stream_collect_partition() {
        List<Student> students = List.of(
                new Student("alvaro", 7),
                new Student("sonia", 8),
                new Student("abascal", 1)
        );

        Map<Boolean, List<Student>> groupedStudents = students.stream()
                .collect(Collectors.partitioningBy(student -> student.getAverageGrade() >= 5));

        List<Student> pass = groupedStudents.get(true);
        List<Student> not_pass = groupedStudents.get(false);

        assertThat(pass).hasSize(2);
        assertThat(not_pass).hasSize(1);
    }

    @Test
    public void stream_collect_grouping() {
        List<Student> students = List.of(
                new Student("alvaro", 10),
                new Student("sonia", 10),
                new Student("abascal", 1)
        );

        Map<Float, List<Student>> groupedStudents = students.stream()
                .collect(Collectors.groupingBy(student -> student.getAverageGrade()));

        List<Student> good_students = groupedStudents.get(10f);
        List<Student> bad_studewnts = groupedStudents.get(1f);

        assertThat(good_students).hasSize(2);
        assertThat(bad_studewnts).hasSize(1);
    }

    @Test
    public void stream_reduce() {
        Integer sum = Stream.of(1, 2, 3)
                            .reduce(0, (acc, value) -> acc + value);

        assertThat(sum).isEqualTo(6);

        List<Student> students = List.of(
                new Student("alvaro", 10),
                new Student("sonia", 8),
                new Student("abascal", 1)
        );

        Student masListo = students.stream()
                .reduce(new Student("", 0), (acc, student) -> student.getAverageGrade() > acc.getAverageGrade() ? student : acc);

        assertThat(masListo).isEqualTo(new Student("alvaro", 10));

    }
}

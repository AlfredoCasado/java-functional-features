import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RedisCache {
    private Integer value = 1;

    public Integer getNumberOfResultsforListings() {
        return value;
    }

    public Optional<Integer> getNumberOfResultsforListingsOptional() {
        return Optional.of(value);
    }
}

public class OptionalTest {
    List<String> SpainCities = List.of("MADRID", "BARCELONA");
    RedisCache redis = new RedisCache();

    @Test
    public void creating_and_optional_with_value() {
        Optional<String> canBeAString = Optional.of("hello world");
        assertThat(canBeAString.isPresent()).isTrue();
        assertThat(canBeAString.get()).isEqualTo("hello wod");
    }

    @Test
    public void creating_and_optional_without_value() {
        Optional<String> canBeAString = Optional.empty();
        assertThat(canBeAString.isPresent()).isFalse();

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    canBeAString.get();
                });
    }

    @Test
    public void get_value_from_optional_vs_typical_approach_with_null() {
        Integer result = redis.getNumberOfResultsforListings();
        if (result != null) {
            // do something
        }

        // con optional
        Optional<Integer> r = redis.getNumberOfResultsforListingsOptional();
        if (r.isPresent()) {
            Integer value = r.get();
            // do something
            assertThat(result).isEqualTo(value);
        }
    }

    @Test
    public void using_optional_if_present() {
        Optional.of(1).ifPresent((value) -> {
             // do something
             assertThat(value).isEqualTo(1);
        });

        Optional.empty().ifPresent((value) -> {
            fail("no debería entrar aqui!");
        });
    }

    @Test
    public void using_optional_if_present_or_else() {
        Optional.of(1).ifPresentOrElse((value) -> {
            assertThat(value).isEqualTo(1);
        }, () -> {
            fail("no debería entrar aqui!");
        });

        Optional.empty().ifPresentOrElse((value) -> {
            fail("no debería entrar aqui!");
        }, () -> {
            // do something else
            assertTrue(true);
        });
    }

    @Test
    public void using_optional_or_else() {
        String v = Optional.of("contenido del optional")
                           .orElse("valor por defecto");
        assertThat(v).isEqualTo("contenido del optional");

        Optional<String> optionalEmpty = Optional.empty();
        v = optionalEmpty.orElse("valor por defecto");
        assertThat(v).isEqualTo("valor por defecto");
    }

    @Test
    public void using_optional_filter() {
        String v = Optional.of("BARCELONA")
                .filter(c -> SpainCities.contains(c))
                .orElse(SpainCities.get(0));

        assertThat(v).isEqualTo("BARCELONA");

        v = Optional.of("LONDRES")
                .filter(SpainCities::contains)
                .orElse(SpainCities.get(0));

        assertThat(v).isEqualTo(SpainCities.get(0));
    }

    @Test
    public void using_optional_map() {
        String spainCity = Optional.of("barcelona")
                .map(String::toUpperCase)
                .orElse(SpainCities.get(0));

        assertThat(spainCity).isEqualTo("BARCELONA");
    }

    @Test
    public void using_optional_map_and_filter() {
        String spainCity = Optional.of("barcelona")
                .map(String::toUpperCase)
                .filter(SpainCities::contains)
                .orElse(SpainCities.get(0));

        assertThat(spainCity).isEqualTo("BARCELONA");

        spainCity = Optional.of("londres")
                .map(String::toUpperCase)
                .filter(SpainCities::contains)
                .orElse(SpainCities.get(0));

        assertThat(spainCity).isEqualTo(SpainCities.get(0));
    }

}

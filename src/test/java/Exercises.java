import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

enum WING { LEFT, RIGHT }

public class Exercises {

    @Test public void
    try_to_do_the_nexts_operations_with_the_political_parties() {
        // obtener el partido de derechas más votado
        // PoliticParty.parties.stream() ...

        // obtener el partido de izquierdas menos votado

        // pintar los partidos ordenados por número de votos

        // pintar los partidos ordenados por el coste en votos de conseguir cada escaño

        // contar los votos a partidos de izquierda y derecha

        // costo del escaño para la izquierda/derecha
    }

    @Test public void
    sample_solutions() {
        section("PRIMER PARTIDO DE DERECHAS MÁS VOTADO", () -> {
            PoliticParty.parties.stream()
                    .filter((p) -> p.getWing().equals(WING.RIGHT))
                    .max(Comparator.comparing(PoliticParty::getVotes))
                    .ifPresent(System.out::println);
        });
        section("PRIMER PARTIDO DE IZQUIERDAS MENOS VOTADO", () -> {
            PoliticParty.parties.stream()
                    .filter((p) -> p.getWing().equals(WING.LEFT))
                    .max(Comparator.comparing(PoliticParty::getVotes).reversed())
                    .ifPresent(System.out::println);
        });
        section("NUMERO DE VOTOS", () -> {
           PoliticParty.parties.stream()
                   .sorted(Comparator.comparingInt(PoliticParty::getVotes).reversed())
                   .forEach(System.out::println);
        });
        section("PARTIDOS ORDENADOS POR COSTE DE ESCAÑO", () -> {
            PoliticParty.parties.stream()
                    .sorted(Comparator.comparingDouble(PoliticParty::seatCostInVotes))
                    .forEach(System.out::println);
        });
        section("VOTOS IZQUIERDA/DERECHA", () -> {
            var leftVotes = PoliticParty.parties.stream()
                    .filter((p) -> p.getWing().equals(WING.LEFT))
                    .collect(Collectors.summingInt(PoliticParty::getVotes));

            var rightVotes = (Integer) PoliticParty.parties.stream()
                    .filter((p) -> p.getWing().equals(WING.RIGHT))
                    .mapToInt(PoliticParty::getVotes)
                    .sum();

            System.out.println(String.format("Votos a la izquierda %s, votos a la derecha %s", leftVotes, rightVotes));

        });
        section("VOTOS IZQUIERDA/DERECHA USANDO GROUP BY", () -> {
            var groups = PoliticParty.parties.stream()
                    .collect(groupingBy(PoliticParty::getWing, summingInt(PoliticParty::getVotes)));

            System.out.println(String.format("Votos a la izquierda %s, votos a la derecha %s", groups.get(WING.LEFT),  groups.get(WING.RIGHT)));
        });
        section("COSTO DEL ESCAÑO IZQUIERDA/DERECHA", () -> {
            PoliticParty.parties.stream()
                    .collect(groupingBy(PoliticParty::getWing, averagingDouble(PoliticParty::seatCostInVotes)))
                    .forEach((k, v)-> System.out.println(String.format("Costo en votos del escaño %s: %s", k, v)));
        });
    }

    private void section(String sectionTitle, Runnable method) {
        System.out.println(sectionTitle);
        System.out.println("=========================================");
        method.run();
        System.out.println("=========================================\n");
    }
}

class PoliticParty {

    public static List<PoliticParty> parties = Arrays.asList(
            new PoliticParty("PSOE", 120, 6752983, WING.LEFT),
            new PoliticParty("PP", 88, 5019869, WING.RIGHT),
            new PoliticParty("VOX", 52, 3640063, WING.RIGHT),
            new PoliticParty("UP", 35, 3097185, WING.LEFT),
            new PoliticParty("ERC", 13, 869934, WING.LEFT),
            new PoliticParty("Ciudadanos", 10, 1637540, WING.RIGHT),
            new PoliticParty("JxCAT", 8, 527375, WING.RIGHT),
            new PoliticParty("PNV", 7, 377423, WING.RIGHT),
            new PoliticParty("EHBildu", 5, 276519, WING.LEFT),
            new PoliticParty("MasPais", 3, 554056, WING.LEFT),
            new PoliticParty("CUP", 2, 244754, WING.LEFT),
            new PoliticParty("CCaPNCNC", 2, 123981, WING.RIGHT),
            new PoliticParty("NA", 2, 98448, WING.RIGHT),
            new PoliticParty("BNG", 1, 119597, WING.RIGHT),
            new PoliticParty("PRC", 1, 68580, WING.LEFT),
            new PoliticParty("PRC", 1, 19696, WING.LEFT)
    );

    private final String name;
    private final int seats;
    private final int votes;
    private final WING wing;

    public PoliticParty(String name, int seats, int votes, WING wing) {
        this.name = name;
        this.seats = seats;
        this.votes = votes;
        this.wing = wing;
    }

    public int getVotes() { return votes; }

    public WING getWing() { return wing; }

    public double seatCostInVotes() { return votes/seats; }

    @Override
    public String toString() {
        return name +
                " seats=" + seats +
                ", votes=" + votes +
                ", seatCostInVotes=" + seatCostInVotes();
    }
}



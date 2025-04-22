package conditions;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;


class TheaterTicketsTest {

    private static final double DELTA = 0.01; // Погрешность для сравнения double

    @Test
    void testCalculateTicketPrice_ChildUnder6() {
        assertThat(TheaterTickets.calculateTicketPrice(5, false, false)).isCloseTo(0.0, offset(DELTA));
        assertThat(TheaterTickets.calculateTicketPrice(3, true, true)).isCloseTo(0.0, offset(DELTA));
    }

    @Test
    void testCalculateTicketPrice_Child6to18() {
        assertThat(TheaterTickets.calculateTicketPrice(10, false, false)).isCloseTo(500.0, offset(DELTA)); // 50%
        assertThat(TheaterTickets.calculateTicketPrice(18, false, true)).isCloseTo(500.0, offset(DELTA)); // 50%
        assertThat(TheaterTickets.calculateTicketPrice(16, true, true)).isCloseTo(500.0, offset(DELTA)); // 50% > 25% выходной
        assertThat(TheaterTickets.calculateTicketPrice(17, true, false)).isCloseTo(500.0, offset(DELTA)); // 50% > 25% будний
    }

    @Test
    void testCalculateTicketPrice_AdultStudentWeekday() {
        assertThat(TheaterTickets.calculateTicketPrice(20, true, false)).isCloseTo(750.0, offset(DELTA)); // 25%
    }

    @Test
    void testCalculateTicketPrice_AdultStudentWeekend() {
        assertThat(TheaterTickets.calculateTicketPrice(22, true, true)).isCloseTo(1000.0, offset(DELTA)); // 0%
    }

    @Test
    void testCalculateTicketPrice_AdultNonStudent() {
        assertThat(TheaterTickets.calculateTicketPrice(30, false, false)).isCloseTo(1000.0, offset(DELTA)); // 0%
        assertThat(TheaterTickets.calculateTicketPrice(45, false, true)).isCloseTo(1000.0, offset(DELTA)); // 0%
    }


    @Test
    void testCalculateTicketPrice_Senior() {
        assertThat(TheaterTickets.calculateTicketPrice(70, false, false)).isCloseTo(700.0, offset(DELTA)); // 30%
        assertThat(TheaterTickets.calculateTicketPrice(66, false, true)).isCloseTo(700.0, offset(DELTA)); // 30%
        assertThat(TheaterTickets.calculateTicketPrice(80, true, false)).isCloseTo(700.0, offset(DELTA)); // 30% > 25% будний
        assertThat(TheaterTickets.calculateTicketPrice(80, true, true)).isCloseTo(700.0, offset(DELTA)); // 30% > 0% выходной
    }


    @Test
    void testCalculateTicketPrice_EdgeCases() {

        assertThat(TheaterTickets.calculateTicketPrice(6, false, false)).isCloseTo(500.0, offset(DELTA)); // Ровно 6 лет -> 50%

        assertThat(TheaterTickets.calculateTicketPrice(19, true, false)).isCloseTo(750.0, offset(DELTA)); // 19, студ, будни -> 25%
        assertThat(TheaterTickets.calculateTicketPrice(19, false, false)).isCloseTo(1000.0, offset(DELTA)); // 19, не студ -> 0%
        assertThat(TheaterTickets.calculateTicketPrice(19, true, true)).isCloseTo(1000.0, offset(DELTA)); // 19, студ, выходной -> 0%

        assertThat(TheaterTickets.calculateTicketPrice(65, false, false)).isCloseTo(1000.0, offset(DELTA)); // Ровно 65, не пенс -> 0%
        assertThat(TheaterTickets.calculateTicketPrice(65, true, false)).isCloseTo(750.0, offset(DELTA)); // Ровно 65, студ, будни -> 25%

        assertThat(TheaterTickets.calculateTicketPrice(66, false, false)).isCloseTo(700.0, offset(DELTA)); // 66 лет -> 30%
    }
}
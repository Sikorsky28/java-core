package conditions;
import org.junit.jupiter.api.Test;
// Импортируем статический метод assertThat из AssertJ
import static org.assertj.core.api.Assertions.assertThat;


class TrafficLightTest {

    @Test
    void testGetRecommendation_RedSignal() {
        assertThat(TrafficLight.getRecommendation("Красный")).isEqualTo("Стой на месте!");
        assertThat(TrafficLight.getRecommendation("красный")).isEqualTo("Стой на месте!"); // Проверка регистра
    }

    @Test
    void testGetRecommendation_YellowSignal() {
        assertThat(TrafficLight.getRecommendation("Желтый")).isEqualTo("Приготовься, но подожди!");
        assertThat(TrafficLight.getRecommendation("ЖЕЛТЫЙ")).isEqualTo("Приготовься, но подожди!");
    }

    @Test
    void testGetRecommendation_GreenSignal() {
        assertThat(TrafficLight.getRecommendation("Зеленый")).isEqualTo("Можно переходить дорогу!");
        assertThat(TrafficLight.getRecommendation("зеленый")).isEqualTo("Можно переходить дорогу!");
    }


    @Test
    void testGetRecommendation_InvalidSignal() {

        assertThat(TrafficLight.getRecommendation("Синий")).isEqualTo("Некорректный сигнал!");
        assertThat(TrafficLight.getRecommendation("")).isEqualTo("Некорректный сигнал!");
        assertThat(TrafficLight.getRecommendation(null)).isEqualTo("Некорректный сигнал!");
    }
}
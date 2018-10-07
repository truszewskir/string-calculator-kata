package pl.rafaltruszewski.stringcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringCalculatorTest {

    private StringCalculator calculaor;

    @BeforeEach
    public void setUp(){
        calculaor = new StringCalculator();
    }

    @Test
    public void should_get_0_for_empty_argument(){
        //given
        String value = "";

        //when
        int sum = calculaor.add(value);

        //then
        assertThat(sum).isEqualTo(0);
    }

    @Test
    public void should_get_sum_for_multi_arguments(){
        //given
        String value = "1,2,3";

        //when
        int sum = calculaor.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_accept_new_line_as_delimiter(){
        //given
        String value = "1\n2,3";

        //when
        int sum = calculaor.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_change_to_different_delimiter(){
        //given
        String value = "//;\n1;2;3";

        //when
        int sum = calculaor.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_fail_with_negative_numbers(){
        //given
        String value = "1,-2,-3";

        //when
        Throwable throwable = catchThrowable(() -> calculaor.add(value));

        //then
        assertThat(throwable).isInstanceOf(NegativesNotAllowed.class).hasMessageContaining("-2,-3");
    }
}

package pl.rafaltruszewski.stringcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    public void setUp(){
        calculator = new StringCalculator();
    }

    @Test
    public void should_get_0_for_empty_argument(){
        //given
        String value = "";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(0);
    }

    @Test
    public void should_get_sum_for_multi_arguments(){
        //given
        String value = "1,2,3";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_accept_new_line_as_delimiter(){
        //given
        String value = "1\n2,3";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_change_to_different_delimiter(){
        //given
        String value = "//;\n1;2;3";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_allow_optional_new_line(){
        //given
        String value = "//;1;2;3";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_fail_with_negative_numbers(){
        //given
        String value = "1,-2,-3";

        //when
        Throwable throwable = catchThrowable(() -> calculator.add(value));

        //then
        assertThat(throwable).isInstanceOf(NegativesNotAllowed.class).hasMessageContaining("-2,-3");
    }

    @Test
    public void should_get_sum_only_for_less_than_1000(){
        //given
        String value = "1001,2002,3003,1,2,3";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void should_allow_delimiter_with_many_characters(){
        //given
        String value = "//[ppp]\n1ppp2ppp3";

        //when
        int sum = calculator.add(value);

        //then
        assertThat(sum).isEqualTo(6);
    }
}

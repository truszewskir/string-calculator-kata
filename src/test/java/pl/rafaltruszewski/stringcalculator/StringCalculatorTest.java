package pl.rafaltruszewski.stringcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}

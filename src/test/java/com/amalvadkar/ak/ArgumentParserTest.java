package com.amalvadkar.ak;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentParserTest {

    @Test
    void given_dash_l_as_argument_list_then_return_argument_object_with_logging_true() {
        Argument argument = ArgumentParser.parse(List.of("-l"));

        assertThat(argument.logging()).isTrue();
    }

    @Test
    void given_dash_l_false_as_argument_list_then_return_argument_object_with_logging_false() {
        Argument argument = ArgumentParser.parse(List.of("-l","false"));

        assertThat(argument.logging()).isFalse();
    }

    @Test
    void given_dash_l_dash_v_as_argument_list_then_return_argument_object_with_logging_true_verbose_true() {
        Argument argument = ArgumentParser.parse(List.of("-l","-v"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
    }

    @Test
    void given_dash_v_dash_l_as_argument_list_then_return_argument_object_with_verbose_true_logging_true() {
        Argument argument = ArgumentParser.parse(List.of("-v","-l"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
    }

    @Test
    void given_dash_v_dash_l_dash_p_8080_as_argument_list_then_return_argument_object_with_verbose_true_logging_true_port_8080() {
        Argument argument = ArgumentParser.parse(List.of("-v","-l","-p", "8080"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(8080);
    }

    @Test
    void given_dash_v_dash_l_dash_p_as_argument_list_then_return_argument_object_with_verbose_true_logging_true_port_0() {
        Argument argument = ArgumentParser.parse(List.of("-v","-l","-p"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(0);
    }
}

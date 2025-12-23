package com.amalvadkar.ak;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArgumentParserTest {

    @Test
    void given_dash_l_as_argument_list_then_return_argument_object_with_logging_true() {
        Argument argument = Argument.parse(List.of("-l"));

        assertThat(argument.logging()).isTrue();
    }

    @Test
    void given_dash_l_false_as_argument_list_then_return_argument_object_with_logging_false() {
        Argument argument = Argument.parse(List.of("-l","false"));

        assertThat(argument.logging()).isFalse();
    }

    @Test
    void given_dash_l_dash_v_as_argument_list_then_return_argument_object_with_logging_true_verbose_true() {
        Argument argument = Argument.parse(List.of("-l","-v"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
    }

    @Test
    void given_dash_v_dash_l_as_argument_list_then_return_argument_object_with_verbose_true_logging_true() {
        Argument argument = Argument.parse(List.of("-v","-l"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
    }

    @Test
    void given_dash_v_dash_l_dash_p_8080_as_argument_list_then_return_argument_object_with_verbose_true_logging_true_port_8080() {
        Argument argument = Argument.parse(List.of("-v","-l","-p", "8080"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(8080);
    }

    @Test
    void given_dash_v_dash_l_dash_p_as_argument_list_then_return_argument_object_with_verbose_true_logging_true_port_0() {
        Argument argument = Argument.parse(List.of("-v","-l","-p"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(0);
    }

    @Test
    void given_dash_v_dash_l_dash_p_dash_d_slash_usr_slash_logs_as_argument_list_then_return_argument_object_with_verbose_true_logging_true_port_0_directory_slash_usr_slash_logs() {
        Argument argument = Argument.parse(List.of("-v","-l","-p", "-d", "/usr/logs"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(0);
        assertThat(argument.logDir()).isEqualTo("/usr/logs");

    }

    @Test
    void given_dash_v_dash_l_dash_p_dash_d_as_argument_list_then_return_argument_object_with_verbose_true_logging_true_port_0_directory_empty() {
        Argument argument = Argument.parse(List.of("-v","-l","-p", "-d"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(0);
        assertThat(argument.logDir()).isEmpty();

    }

    @Test
    void given_dash_l_only_argument_list_then_return_argument_object_with_logging_true_others_with_default_values() {
        Argument argument = Argument.parse(List.of("-l"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isFalse();
        assertThat(argument.port()).isEqualTo(0);
        assertThat(argument.logDir()).isEmpty();

    }

    @Test
    void given_dash_v_dash__true_l_dash_true_p_dash_9090_d_var_slash_logs_dash_c_as_argument_list_then_throw_exception_with_message_dash_c_colon_invalid_flag_with_valid_flags_info() {
        assertThatThrownBy(() -> Argument.parse(List.of("-v","true","-l", "true","-p","9090", "-d", "/var/logs", "-c")))
                .isInstanceOf(InvalidFlagException.class)
                .hasMessage("""
                        Invalid flag : -c
                        
                        Valid flags:
                        
                        -l : For logging
                        -v : For verbose
                        -p : For port
                        -d : For log directory
                        """);
    }
}

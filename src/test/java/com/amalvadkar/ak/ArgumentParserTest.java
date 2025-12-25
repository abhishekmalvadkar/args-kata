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
    void given_dash_v_dash__true_l_dash_true_p_dash_9090_d_var_slash_logs_dash_c_as_argument_list_then_throw_exception_with_message_dash_c_colon_invalid_flag_with_valid_flags_info_in_asc_order_of_flag_name() {
        assertThatThrownBy(() -> Argument.parse(List.of("-v","true","-l", "true","-p","9090", "-d", "/var/logs", "-c")))
                .isInstanceOf(InvalidFlagException.class)
                .hasMessage("""
                        Invalid flag : -c
                        
                        Valid flags:
                        
                        -d : For log directory
                        -l : For logging
                        -p : For port
                        -pr : For profiles
                        -v : For verbose""");
    }

    @Test
    void given_dash_l_with_invalid_value_argument_list_then_throw_exception_with_error_message_that_invalid_value_for_dash_l_flag() {
        assertThatThrownBy(() -> Argument.parse(List.of("-l", "5")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        5 is invalid value for -l flag
                        
                        Only boolean value allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_with_invalid_value_argument_list_then_throw_exception_with_error_message_that_invalid_value_for_dash_v_flag() {
        assertThatThrownBy(() -> Argument.parse(List.of("-l", "true", "-v", "abc")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        abc is invalid value for -v flag
                        
                        Only boolean value allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_with_invalid_value_argument_list_then_throw_exception_with_error_message_that_invalid_value_for_dash_p_flag() {
        assertThatThrownBy(() -> Argument.parse(List.of("-l", "true", "-v", "false", "-p", "cde")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        cde is invalid value for -p flag
                        
                        Only number value allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_with_invalid_value_argument_list_then_throw_exception_with_error_message_that_invalid_value_for_dash_d_flag() {
        assertThatThrownBy(() -> Argument.parse(List.of("-l", "true", "-v", "false", "-p", "8080", "-d", "/var//logs")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        /var//logs is invalid value for -d flag
                        
                        Only linux style directory value allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_slash_var_slash_logs_dash_pr_dev_hash_tag_qa_argument_list_then_throw_exception_with_invalid_profile_message() {
        assertThatThrownBy(() -> Argument.parse(List.of("-v","true","-l", "true","-p", "9090", "-d", "/usr/logs","-pr", "dev#qa")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        dev#qa is invalid value for -pr flag
                        
                        Only comma separated alphabet values are allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_slash_var_slash_logs_dash_pr_dev_comma_qa_argument_list_then_return_all_values_and_profiles_as_list_with_dev_and_qa() {
        Argument argument = Argument.parse(List.of("-v","true","-l", "true","-p", "9090", "-d", "/usr/logs","-pr", "dev,qa"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(9090);
        assertThat(argument.logDir()).isEqualTo("/usr/logs");
        assertThat(argument.profiles()).containsExactly("dev", "qa");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_slash_var_slash_logs_dash_pr_1_comma_qa_argument_list_then_throw_exception_with_invalid_profile_message() {
        assertThatThrownBy(() -> Argument.parse(List.of("-v","true","-l", "true","-p", "9090", "-d", "/usr/logs","-pr", "1,qa")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        1,qa is invalid value for -pr flag
                        
                        Only comma separated alphabet values are allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_slash_var_slash_logs_dash_pr_dev_comma_qa_comma_2_argument_list_then_throw_exception_with_invalid_profile_message() {
        assertThatThrownBy(() -> Argument.parse(List.of("-v","true","-l", "true","-p", "9090", "-d", "/usr/logs","-pr", "dev,qa,2")))
                .isInstanceOf(InvalidFlagValueException.class)
                .hasMessage("""
                        dev,qa,2 is invalid value for -pr flag
                        
                        Only comma separated alphabet values are allowed""");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_slash_var_slash_logs_dash_pr_dev_comma_qa_local_argument_list_then_return_all_values_and_profiles_as_list_with_dev_and_qa_local() {
        Argument argument = Argument.parse(List.of("-v","true","-l", "true","-p", "9090", "-d", "/usr/logs","-pr", "dev,qa,local"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(9090);
        assertThat(argument.logDir()).isEqualTo("/usr/logs");
        assertThat(argument.profiles()).containsExactly("dev", "qa","local");
    }

    @Test
    void given_dash_l_true_dash_v_true_dash_p_8080_dash_d_slash_var_slash_logs_dash_pr_argument_list_then_return_all_values_and_profiles_with_empty_list_as_default_value() {
        Argument argument = Argument.parse(List.of("-v","true","-l", "true","-p", "9090", "-d", "/usr/logs","-pr"));

        assertThat(argument.logging()).isTrue();
        assertThat(argument.verbose()).isTrue();
        assertThat(argument.port()).isEqualTo(9090);
        assertThat(argument.logDir()).isEqualTo("/usr/logs");
        assertThat(argument.profiles()).isEmpty();
    }
}

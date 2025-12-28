import com.amalvadkar.ak.Argument;
import com.amalvadkar.ak.InvalidFlagException;
import com.amalvadkar.ak.InvalidFlagValueException;

void main(String[] args) {
    try {
        Argument argument = Argument.parse(List.of(args));
        IO.println(argument);
    } catch (InvalidFlagException | InvalidFlagValueException e) {
        IO.print(e.getMessage());
    }
}

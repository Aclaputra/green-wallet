package fullstuck.green.wallet.Config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

@Component
public class Helper {
    public static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public static Validator validator = factory.getValidator();
}

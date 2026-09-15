package vslugin.de;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

    private final Map<String, String> errors = new HashMap<>();

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void add(String name, String errorMessage) {
        errors.put(name, errorMessage);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

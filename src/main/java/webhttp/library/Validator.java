package webhttp.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
    private Map<String, List<String>> errors = new HashMap<>();

    public Validator rules(String field, String... rules) {
        // Add validation rules
        return this;
    }

    public boolean validate(Map<String, Object> data) {
        // Perform validation
        return errors.isEmpty();
    }
}
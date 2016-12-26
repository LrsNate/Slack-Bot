package fr.nate.slackbot.environment;

import java.util.HashMap;
import java.util.Map;

public class InMemoryEnvironment implements Environment {

    private final Map<String, String> environment;

    public InMemoryEnvironment() {
        environment = new HashMap<>();
    }

    @Override
    public void set(String key, String value) {
        environment.put(key, value);
    }

    @Override
    public String get(String key) {
        return environment.get(key);
    }
}

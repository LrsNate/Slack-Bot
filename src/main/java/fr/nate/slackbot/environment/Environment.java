package fr.nate.slackbot.environment;

public interface Environment {
    void set(String key, String value);

    String get(String key);
}

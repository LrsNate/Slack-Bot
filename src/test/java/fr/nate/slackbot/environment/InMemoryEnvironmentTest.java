package fr.nate.slackbot.environment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InMemoryEnvironmentTest {

    private Environment environment;

    @Test
    public void testGet() {
        assertEquals(null, environment.get("foo"));
    }

    @Test
    public void testSet() {
        environment.set("foo", "bar");
        assertEquals("bar", environment.get("foo"));
    }

    @Test
    public void testSet_replace() {
        environment.set("foo", "bar");
        environment.set("foo", "baz");
        assertEquals("baz", environment.get("foo"));
    }


    @Before
    public void setUp() {
        environment = new InMemoryEnvironment();
    }

}
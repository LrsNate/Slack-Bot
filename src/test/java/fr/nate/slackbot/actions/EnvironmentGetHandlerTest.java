package fr.nate.slackbot.actions;

import fr.nate.slackbot.environment.Environment;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class EnvironmentGetHandlerTest {

    @Tested
    private EnvironmentGetHandler handler;

    @Injectable
    private Environment environment;

    @Test
    public void testAccept_valid() {
        assertTrue(handler.accept("get foo"));
    }

    @Test
    public void testAccept_nonValid() {
        assertFalse(handler.accept("foo"));
    }

    @Test
    public void testRespond_empty() {
        new Expectations() {{
            environment.get("foo");
            result = null;
        }};
        assertEquals("foo: (null)", handler.respond("get foo"));
    }

    @Test
    public void testRespond_nonEmpty() {
        new Expectations() {{
            environment.get("bar");
            result = "baz";
        }};
        assertEquals("bar: baz", handler.respond("get bar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRespond_invalidInput() {
        handler.respond("foo");
    }
}
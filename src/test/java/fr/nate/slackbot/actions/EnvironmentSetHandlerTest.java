package fr.nate.slackbot.actions;

import fr.nate.slackbot.environment.Environment;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JMockit.class)
public class EnvironmentSetHandlerTest {
    @Tested
    private EnvironmentSetHandler handler;

    @Injectable
    private Environment environment;

    @Test
    public void testAccept_valid() {
        assertTrue(handler.accept("set foo bar"));
    }

    @Test
    public void testAccept_nonValid() {
        assertFalse(handler.accept("foo"));
    }

    @Test
    public void testRespond() {
        assertEquals("Ok.", handler.respond("set foo bar"));
        new Verifications() {{
            environment.set("foo", "bar");
            times = 1;
        }};
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRespond_invalidInput() {
        handler.respond("foo");
    }
}
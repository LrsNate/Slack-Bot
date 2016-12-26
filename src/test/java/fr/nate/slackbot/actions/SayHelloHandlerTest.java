package fr.nate.slackbot.actions;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SayHelloHandlerTest {
    private SayHelloHandler handler;

    @Test
    public void testAccept_valid() {
        assertTrue(handler.accept("hello"));
    }

    @Test
    public void testAccept_nonValid() {
        assertFalse(handler.accept("foo"));
    }

    @Test
    public void testRespond() {
        assertEquals(":wave:", handler.respond("hello"));
    }

    @Before
    public void setUp() {
        handler = new SayHelloHandler();
    }
}
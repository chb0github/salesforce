package org.bongiorno.interviews.salesforce;

import org.bongiorno.interviews.salesforce.dependency.Module;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import static org.bongiorno.interviews.salesforce.dependency.Module.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: christian
 */
public class CommandTest {

    @Before
    public void reset() {
        // Should not be using static. This was a mistake. No time to reverse
        Module.getAll().clear();
    }

    @Test
    public void testInstall() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "INSTALL NETCARD\n" +
                "INSTALL TELNET\n" +
                "INSTALL foo\n" +
                "END";

        Jumpstart app = new Jumpstart(new ByteArrayInputStream(input.getBytes()), new PrintStream(new ByteArrayOutputStream()));
        app.process();


        Set<Module> expected = new HashSet<>(asList(getInstance("NETCARD"), getInstance("TCPIP"), getInstance("TELNET"), getInstance("foo")));
        assertEquals(expected, Module.getInstalled());

    }

    @Test
    public void testListCommand() throws Exception {


        // essentially tested in remove
    }

    @Test
    public void testDependCommand() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "END";
        Jumpstart app = new Jumpstart(new ByteArrayInputStream(input.getBytes()), new PrintStream(new ByteArrayOutputStream()));
        app.process();


        Module module = getInstance("TELNET");

        assertEquals(2, module.getDependencies().size());
        assertTrue(module.getDependencies().containsAll(asList(getInstance("TCPIP"), getInstance("NETCARD"))));

        module = getInstance("TCPIP");
        assertEquals(1, module.getDependencies().size());
        assertTrue(module.getDependencies().iterator().next().equals(getInstance("NETCARD")));

        module = getInstance("NETCARD");
        assertEquals(0, module.getDependencies().size());


        module = getInstance("DNS");
        assertEquals(2, module.getDependencies().size());
        assertTrue(module.getDependencies().containsAll(asList(getInstance("TCPIP"), getInstance("NETCARD"))));

        module = getInstance("BROWSER");
        assertEquals(2, module.getDependencies().size());
        assertTrue(module.getDependencies().containsAll(asList(getInstance("TCPIP"), getInstance("HTML"))));
    }

    @Test
    public void testRemoveCommand() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "INSTALL NETCARD\n" +
                "INSTALL TELNET\n" +
                "INSTALL foo\n" +
                "REMOVE NETCARD\n" +
                "INSTALL BROWSER\n" +
                "INSTALL DNS\n" +
                "END";

        Jumpstart app = new Jumpstart(new ByteArrayInputStream(input.getBytes()), new PrintStream(new ByteArrayOutputStream()));
        app.process();


        List<Module> result = Module.getAll().stream().filter(Module::isInstalled).collect(toList());
        assertEquals(7, result.size());
        assertTrue(result.containsAll(asList(getInstance("NETCARD"), getInstance("TCPIP"), getInstance("TELNET"),
                getInstance("foo"), getInstance("BROWSER"), getInstance("HTML"), getInstance("DNS"))));
    }

    @Test
    public void testBigRemoveCommand() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "INSTALL NETCARD\n" +
                "INSTALL TELNET\n" +
                "INSTALL foo\n" +
                "REMOVE NETCARD\n" +
                "INSTALL BROWSER\n" +
                "INSTALL DNS\n" +
                "LIST\n" +
                "REMOVE TELNET\n" +
                "REMOVE NETCARD\n" +
                "REMOVE DNS\n" +
                "REMOVE NETCARD\n" +
                "INSTALL NETCARD\n" +
                "REMOVE TCPIP\n" +
                "REMOVE BROWSER\n" +
                "REMOVE TCPIP\n" +
                "END";
        Jumpstart app = new Jumpstart(new ByteArrayInputStream(input.getBytes()), new PrintStream(new ByteArrayOutputStream()));
        app.process();
        assertEquals(new HashSet<>(asList(getInstance("NETCARD"), getInstance("foo"))), Module.getInstalled());
    }

    @Test
    public void testStringOutput() throws Exception {
        String input = "DEPEND TELNET TCPIP NETCARD\n" +
                "DEPEND TCPIP NETCARD\n" +
                "DEPEND DNS TCPIP NETCARD\n" +
                "DEPEND BROWSER TCPIP HTML\n" +
                "INSTALL NETCARD\n" +
                "INSTALL TELNET\n" +
                "INSTALL foo\n" +
                "REMOVE NETCARD\n" +
                "INSTALL BROWSER\n" +
                "INSTALL DNS\n" +
                "LIST\n" +
                "REMOVE TELNET\n" +
                "REMOVE NETCARD\n" +
                "REMOVE DNS\n" +
                "REMOVE NETCARD\n" +
                "INSTALL NETCARD\n" +
                "REMOVE TCPIP\n" +
                "REMOVE BROWSER\n" +
                "REMOVE TCPIP\n" +
                "LIST\n" +
                "END";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Jumpstart app = new Jumpstart(new ByteArrayInputStream(input.getBytes()), new PrintStream(baos));
        app.process();
        String result = baos.toString();
        String[] lines = result.split("\n");
        Arrays.stream(lines).forEach(System.out::println);
        assertEquals(50, lines.length);

    }
}


package technion.tdk.spannerlog.rgx;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicRegexTest {


    @Test
    public void compileRange(){
        Rgx rgx = new Rgx("[A-Z]");
        assertEquals("[A-Z]", rgx.getRegexCompiled());
    }

    @Test
    public void compileString(){
        Rgx rgx = new Rgx("married");
        assertEquals("married", rgx.getRegexCompiled());
    }
}

package pp.block4;

import org.junit.Test;
import pp.iloc.Assembler;
import pp.iloc.Simulator;
import pp.iloc.model.Program;
import pp.iloc.parse.FormatException;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MaxTest {

    @Test
    public void doubleAssemblyTest() {
        Simulator.DEBUG = true;
        Program max = parse("src/main/java/pp/block4/cc/iloc/max");
        Simulator s = new Simulator(max);
        s.getVM().init("a", 1, 2, 3, 4, 10, 6, 7, 8, 9);
        s.getVM().setNum("alength", 9);
        s.run();
        assertEquals(s.getVM().getReg("r_max"), 10);
    }

    @Test
    public void fibTest() {
        Program fib = parse("src/main/java/pp/block4/cc/iloc/fib");
        Simulator s = new Simulator(fib);
        s.getVM().setNum("n", 10);
        s.run();
    }

    @Test
    public void fibMTest() {
        Program fib = parse("src/main/java/pp/block4/cc/iloc/fibm");
        Simulator.DEBUG = true;
        Simulator s = new Simulator(fib);
        s.getVM().setNum("n", 10);
        s.getVM().init("x", 1);
        s.getVM().init("y", 1);
        s.getVM().init("z", 1);
        s.getVM().init("one");
        s.run();
    }

    Program parse(String filename) {
        File file = new File(filename + ".iloc");
        try {
            Program result = Assembler.instance().assemble(file);
            String print = result.prettyPrint();
            Program other = Assembler.instance().assemble(print);
            assertEquals(result, other);
            return result;
        } catch (FormatException | IOException e) {
            fail(e.getMessage());
            return null;
        }
    }
}

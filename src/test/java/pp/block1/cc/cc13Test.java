package pp.block1.cc;

import org.junit.Test;
import pp.block1.cc.antlr.cc13;

public class cc13Test {
	private static LexerTester tester = new LexerTester(cc13.class);

	@Test
	public void succeedingTest() {
		tester.correct("\"\"");
		tester.correct("\"The quick brown fox jumps over the lazy dog.\"");
		tester.wrong("The quick brown fox jumps over the lazy dog"); // Must have &quot; around.
        tester.correct("\"The \"\"quick\"\" brown fox jumps over the lazy dog.\"");
        tester.wrong("\"The \"quick\" brown fox jumps over the lazy dog."); // Must have double &quot; in middle of the string.
	}
}

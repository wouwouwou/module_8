package pp.block3;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import pp.block3.cc.symbol.DeclUseLexer;
import pp.block3.cc.symbol.DeclUseList;
import pp.block3.cc.symbol.DeclUseParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeclUseListTest {

    DeclUseList declUseList = new DeclUseList();
    ParseTreeWalker walker = new ParseTreeWalker();

	@Test
	public void fullTest() {
        List<String> errorlist = new ArrayList<>();
        test("(D:aap (U:aap D:noot D:aap (U:noot) (D:noot U:noot )) U:aap)", errorlist);

        errorlist.add("Used identifier at 1::11 is not declared in scope!");
        errorlist.add("The identifier at 1::30 is already declared in scope!");
        errorlist.add("Used identifier at 1::69 is not declared in scope!");
        test("(D:noot (U:aap D:noot D:aap D:noot (U:aap U:noot) (D:noot U:noot)) U:aap)", errorlist);
	}

    private void test(String s, List<String> errorlist) {
        ANTLRInputStream chars = new ANTLRInputStream(s);
        Lexer lexer = new DeclUseLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        DeclUseParser parser = new DeclUseParser(tokens);
        walker.walk(declUseList, parser.program());
        assertEquals(errorlist, declUseList.getErrorList());
    }


}
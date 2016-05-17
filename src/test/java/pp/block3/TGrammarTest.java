package pp.block3;

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import pp.block3.cc.antlr.*;
import pp.block3.cc.antlr.TGrammarAttrParser.TContext;

public class TGrammarTest {

    private final ParseTreeWalker walker = new ParseTreeWalker();
    private final T t = new T();

    @Test
    public void testHat() {
        test(Type.NUM, "2^3");
        test(Type.STR, "\"ab\"^3");
        test(Type.ERR, "3^\"ab\"");
    }

    @Test
    public void testPlus() {
        test(Type.NUM, "1+2");
        test(Type.BOOL, "true+false");
        test(Type.STR, "\"ab\"+\"cd\"");
    }

    @Test
    public void testEquals() {
        test(Type.BOOL, "3=4");
        test(Type.BOOL, "true=false");
        test(Type.BOOL, "\"ab\"=\"zx\"");
    }

    private void test(Type expected, String expr) {
        assertEquals(expected, parseTAttr(expr).type);
        ParseTree tree = parseT(expr);
        this.t.init();
        this.walker.walk(this.t, tree);
        assertEquals(expected, this.t.val(tree));
    }

    private ParseTree parseT(String text) {
        CharStream chars = new ANTLRInputStream(text);
        Lexer lexer = new TGrammarLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        TGrammarParser parser = new TGrammarParser(tokens);
        return parser.t();
    }

    private TContext parseTAttr(String text) {
        CharStream chars = new ANTLRInputStream(text);
        Lexer lexer = new TGrammarAttrLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        TGrammarAttrParser parser = new TGrammarAttrParser(tokens);
        return parser.t();
    }
}

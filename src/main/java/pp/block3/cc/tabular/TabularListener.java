package pp.block3.cc.tabular;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import pp.block3.cc.tabular.LatexParser.*;

public class TabularListener extends LatexBaseListener {

    private static final ParseTreeWalker walker = new ParseTreeWalker();
    private static final TabularListener html = new TabularListener();
    private static final MyErrorListener el = new MyErrorListener();

    private ParseTreeProperty<String> nodes;

    /** Initialises the calculator before using it to walk a tree. */
    public void init() {
        this.nodes = new ParseTreeProperty<>();
    }

    /** Sets the val attribute of a given node. */
    private void set(ParseTree node, String val) {
        this.nodes.put(node, val);
    }

    /** Retrieves the val attribute of a given node. */
    public String val(ParseTree node) {
        return this.nodes.get(node);
    }

    @Override
    public void exitTable(TableContext ctx) {
        String rows = ctx.row().stream().map(this::val).collect(Collectors.joining());
        set(ctx, String.format("<table border=\"1\">\n%s</table>", rows));
    }

    @Override
    public void exitRow(RowContext ctx) {
        String entrys = ctx.entry().stream().map(this::val).collect(Collectors.joining());
        set(ctx, String.format("<tr>\n%s</tr>\n", entrys));
    }

    @Override
    public void exitEntry(EntryContext ctx) {
        String entry = ctx.ENTRY() == null ? "" : ctx.ENTRY().getText();
        set(ctx, String.format("<td>%s</td>\n", entry));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        String file = args[0];
        String contents = readFile(file);

        ParseTree tree = parse(contents);
        if (el.getErrorlist().size() > 0) throw new RuntimeException("Something went wrong with parsing the latex file.");
        html.init();
        walker.walk(html, tree);
        String doc = String.format("<html><body>\n%s\n</body></html>", html.val(tree));
        HTMLWriter.write(doc, file + ".html");
    }

    private static ParseTree parse(String text) {
        CharStream chars = new ANTLRInputStream(text);
        Lexer lexer = new LatexLexer(chars);

        lexer.removeErrorListeners();
        lexer.addErrorListener(el);
        TokenStream tokens = new CommonTokenStream(lexer);

        LatexParser parser = new LatexParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(el);

        return parser.table();
    }

    private static String readFile(String file) throws IOException, URISyntaxException {
        URI path = TabularListener.class.getResource(file).toURI();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}

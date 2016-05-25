package pp.block4.cc.cfg;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import pp.block4.cc.ErrorListener;
import pp.block4.cc.cfg.FragmentParser.*;

/** Template top-down CFG builder. */
public class TopDownCFGBuilder extends FragmentBaseListener {
	/** The CFG being built. */
	private Graph graph;

    private ParseTreeProperty<Node> entrances = new ParseTreeProperty<>();
    private ParseTreeProperty<Node> exits = new ParseTreeProperty<>();

	/** Builds the CFG for a program contained in a given file. */
	public Graph build(File file) {
		Graph result = null;
		ErrorListener listener = new ErrorListener();
		try {
			CharStream chars = new ANTLRInputStream(new FileReader(file));
			Lexer lexer = new FragmentLexer(chars);
			lexer.removeErrorListeners();
			lexer.addErrorListener(listener);
			TokenStream tokens = new CommonTokenStream(lexer);
			FragmentParser parser = new FragmentParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(listener);
			ProgramContext tree = parser.program();
			if (listener.hasErrors()) {
				System.out.printf("Parse errors in %s:%n", file.getPath());
				for (String error : listener.getErrors()) {
					System.err.println(error);
				}
			} else {
				result = build(tree);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/** Builds the CFG for a program given as an ANTLR parse tree. */
	public Graph build(ProgramContext tree) {
		this.graph = new Graph();
		new ParseTreeWalker().walk(this, tree);
		return this.graph;
	}

    @Override
    public void enterDecl(DeclContext ctx) {
        entrances.get(ctx).addEdge(exits.get(ctx));
    }

    @Override
    public void enterPrintStat(PrintStatContext ctx) {
        entrances.get(ctx).addEdge(exits.get(ctx));
    }

    @Override
    public void enterProgram(ProgramContext ctx) {
        Node node = new Node(0, "Program");
        for (StatContext s : ctx.stat()) {
            Node childentry = addNode(s, s.getText() + "<entry>");
            Node childexit = addNode(s, s.getText() + "<exit>");
            entrances.put(s, childentry);
            exits.put(s, childexit);
            node.addEdge(childentry);
            node = childexit;
        }
    }

    @Override
    public void enterWhileStat(WhileStatContext ctx) {
        Node entrance = entrances.get(ctx);
        Node exit = exits.get(ctx);
        Node whilestat = addNode(ctx.stat(), ctx.stat().getText() + "<entry>");
        Node whileexit = addNode(ctx.stat(), ctx.stat().getText() + "<exit>");
        entrances.put(ctx.stat(), whilestat);
        exits.put(ctx.stat(), whileexit);
        entrance.addEdge(whilestat);
        entrance.addEdge(exit);
        whileexit.addEdge(entrance);
    }

    @Override
    public void enterIfStat(IfStatContext ctx) {
        Node entrance = entrances.get(ctx);
        Node exit = exits.get(ctx);
        Node ifentry = addNode(ctx.stat(0), ctx.stat(0).getText() + "<entry>");
        Node ifexit = addNode(ctx.stat(0), ctx.stat(0).getText() + "<exit>");
        entrance.addEdge(exit);
        entrances.put(ctx.stat(0), ifentry);
        exits.put(ctx.stat(0), ifexit);
        if (ctx.stat(1) == null) {
            entrance.addEdge(ifentry);
            ifexit.addEdge(exit);
        } else {
            Node elseEntry = addNode(ctx.stat(1), ctx.stat(1).getText() + "<entry>");
            Node elseExit = addNode(ctx.stat(1), ctx.stat(1).getText() + "<exit>");
            entrances.put(ctx.stat(1), elseEntry);
            exits.put(ctx.stat(1), elseExit);
            entrance.addEdge(ifentry);
            ifexit.addEdge(exit);
            elseExit.addEdge(exit);
        }
    }

    @Override
    public void enterBlockStat(BlockStatContext ctx) {
        Node node = entrances.get(ctx);
        Node exit = exits.get(ctx);
        for (StatContext s : ctx.stat()) {
            Node blockEntry = addNode(s, s.getText() + "<entry>");
            Node blockExit = addNode(s, s.getText() + "<exit>");
            entrances.put(s, blockEntry);
            exits.put(s, blockExit);
            node.addEdge(blockEntry);
            node = blockExit;
        }
        node.addEdge(exit);
    }

    @Override
    public void enterAssignStat(AssignStatContext ctx) {
        entrances.get(ctx).addEdge(exits.get(ctx));
    }

    /** Adds a node to he CGF, based on a given parse tree node.
	 * Gives the CFG node a meaningful ID, consisting of line number and 
	 * a further indicator.
	 */
	private Node addNode(ParserRuleContext node, String text) {
		return this.graph.addNode(node.getStart().getLine() + ": " + text);
	}

	/** Main method to build and print the CFG of a simple Java program. */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [filename]+");
			return;
		}
		TopDownCFGBuilder builder = new TopDownCFGBuilder();
		for (String filename : args) {
            URI path = null;
            try {
                path = TopDownCFGBuilder.class.getResource(filename).toURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (path == null) {
                throw new NullPointerException();
            }
			File file = new File(path);
			System.out.println(filename);
			System.out.println(builder.build(file));
		}
	}
}

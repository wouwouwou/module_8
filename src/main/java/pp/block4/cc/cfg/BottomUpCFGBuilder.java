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

/** Template bottom-up CFG builder. */
public class BottomUpCFGBuilder extends FragmentBaseListener {
	/** The CFG being built. */
	private Graph graph;

    private ParseTreeProperty<Node[]> nodes = new ParseTreeProperty<>();

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
			ParseTree tree = parser.program();
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
	public Graph build(ParseTree tree) {
		this.graph = new Graph();
        new ParseTreeWalker().walk(this, tree);
		return this.graph;
	}

    @Override
    public void exitProgram(ProgramContext ctx) {
        for (int i = 0; i < ctx.stat().size() - 1; i++) {
            Node[] stat1 = nodes.get(ctx.stat(i));
            Node[] stat2 = nodes.get(ctx.stat(i + 1));
            stat1[1].addEdge(stat2[0]);
        }
    }

    @Override
    public void exitDecl(DeclContext ctx) {
        Node n = addNode(ctx, "decl");
        nodes.put(ctx, new Node[] {n,n});
    }

    @Override
    public void exitAssignStat(AssignStatContext ctx) {
        Node n = addNode(ctx, "assign");
        nodes.put(ctx, new Node[] {n,n});
    }

    @Override
    public void exitIfStat(IfStatContext ctx) {
        Node expr = addNode(ctx.expr(), "if");
        Node ifexit = addNode(ctx, "ifexit");
        for (StatContext s : ctx.stat()) {
            Node[] thenelse = nodes.get(s);
            expr.addEdge(thenelse[0]);
            thenelse[1].addEdge(ifexit);
        }
        if (ctx.stat().size() < 2) {
            expr.addEdge(ifexit);
        }
        nodes.put(ctx, new Node[]{expr, ifexit});
    }

    @Override
    public void exitWhileStat(WhileStatContext ctx) {
        Node expr = addNode(ctx.expr(), "whilestat");
        Node[] whilenode = nodes.get(ctx.stat());
        expr.addEdge(whilenode[0]);
        whilenode[1].addEdge(expr);
        Node exitnode = addNode(ctx.expr(), "whileExit");
        expr.addEdge(exitnode);
        nodes.put(ctx, new Node[] {expr, exitnode});
    }

    @Override
    public void exitBlockStat(BlockStatContext ctx) {
        for (int i = 0; i < ctx.stat().size() - 1; i++) {
            Node[] stat1 = nodes.get(ctx.stat(i));
            Node[] stat2 = nodes.get(ctx.stat(i + 1));
            stat1[1].addEdge(stat2[0]);
        }
        nodes.put(ctx, new Node[] {nodes.get(ctx.stat(0))[0], nodes.get(ctx.stat(ctx.stat().size() - 1))[1]});
    }

    @Override
    public void exitPrintStat(PrintStatContext ctx) {
        Node n = addNode(ctx, "print");
        nodes.put(ctx, new Node[]{n, n});
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
		BottomUpCFGBuilder builder = new BottomUpCFGBuilder();
		for (String filename : args) {
            URI path = null;
            try {
                path = BottomUpCFGBuilder.class.getResource(filename).toURI();
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

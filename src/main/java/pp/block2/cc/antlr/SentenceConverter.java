package pp.block2.cc.antlr;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import org.antlr.v4.runtime.tree.*;
import pp.block2.cc.*;
import pp.block2.cc.ll.*;
import pp.block2.cc.antlr.SentenceParser.*;

public class SentenceConverter //
		extends SentenceBaseListener implements Parser {

    /**
     * Factory needed to create terminals of the {@link Sentence}
     * grammar. See {@link pp.block2.cc.ll.SentenceParser} for
     * example usage.
     */
    private final SymbolFactory fact;

    /**
     * Map from Antlr tree nodes to ASTs.
     */
    private ParseTreeProperty<AST> asts;

    /**
     * Used NonTerminals
     */
    private static final NonTerm SENT = new NonTerm("Sentence");
    private static final NonTerm SUBJ = new NonTerm("Subject");
    private static final NonTerm OBJ = new NonTerm("Object");
    private static final NonTerm MOD = new NonTerm("Modifier");

    /**
     * parseerror boolean
     */
    private boolean parseerror;

    public SentenceConverter() {
        this.fact = new SymbolFactory(Sentence.class);
    }

    @Override
    public AST parse(Lexer lexer) throws ParseException {
        this.asts = new ParseTreeProperty<>();
        this.parseerror = false;
        SentenceParser parser = new SentenceParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.sentence();
        new ParseTreeWalker().walk(this, tree);
        if (parseerror) {
            throw new ParseException();
        }
        return asts.get(tree);
    }

    @Override
    public void exitSentence(SentenceContext ctx) {
        AST ast = new AST(SENT);

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ast.addChild(asts.get(ctx.getChild(i)));
        }
        asts.put(ctx, ast);
    }

    @Override
    public void exitSubject(SubjectContext ctx) {
        AST ast = new AST(SUBJ);

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ast.addChild(asts.get(ctx.getChild(i)));
        }
        asts.put(ctx, ast);
    }

    @Override
    public void exitModifier(ModifierContext ctx) {
        AST ast = new AST(MOD);

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ast.addChild(asts.get(ctx.getChild(i)));
        }
        asts.put(ctx, ast);
    }

    @Override
    public void exitObject(ObjectContext ctx) {
        AST ast = new AST(OBJ);

        for (int i = 0; i < ctx.getChildCount(); i++) {
            ast.addChild(asts.get(ctx.getChild(i)));
        }
        asts.put(ctx, ast);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        AST ast = new AST(fact.getTerminal(node.getSymbol().getType()), node.getSymbol());
        asts.put(node, ast);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        this.parseerror = true;
    }
}
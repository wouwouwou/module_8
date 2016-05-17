package pp.block3.cc.symbol;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

public class DeclUseList extends DeclUseBaseListener {
	/** Map storing the val attribute for all parse tree nodes. */
	private ParseTreeProperty<String> errors;

    private MySymbolTable symbolTable;

	/** Initialises the calculator before using it to walk a tree. */
	public void init() {
		this.errors = new ParseTreeProperty<>();
        this.symbolTable = new MySymbolTable();
	}

    @Override
    public void exitProgram(DeclUseParser.ProgramContext ctx) {
        super.exitProgram(ctx);
    }

    @Override
    public void exitSeries(DeclUseParser.SeriesContext ctx) {
        super.exitSeries(ctx);
    }

    @Override
    public void exitUnit(DeclUseParser.UnitContext ctx) {
        super.exitUnit(ctx);
    }

    @Override
    public void exitDecl(DeclUseParser.DeclContext ctx) {
        super.exitDecl(ctx);
    }

    @Override
    public void exitUse(DeclUseParser.UseContext ctx) {
        super.exitUse(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}

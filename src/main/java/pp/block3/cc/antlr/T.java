package pp.block3.cc.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

public class T extends TGrammarBaseListener {

    private ParseTreeProperty<Type> vals;

    public void init() {
        this.vals = new ParseTreeProperty<>();
    }

    @Override
    public void exitPar(TGrammarParser.ParContext ctx) {
        set(ctx, val(ctx.t()));
    }

    @Override
    public void exitStr(TGrammarParser.StrContext ctx) {
        set(ctx, Type.STR);
    }

    @Override
    public void exitBool(TGrammarParser.BoolContext ctx) {
        set(ctx, Type.BOOL);
    }

    @Override
    public void exitNum(TGrammarParser.NumContext ctx) {
        set(ctx, Type.NUM);
    }

    @Override
    public void exitEquals(TGrammarParser.EqualsContext ctx) {
        set(ctx, val(ctx.t(0)) == val(ctx.t(1)) ? Type.BOOL : Type.ERR);
    }

    @Override
    public void exitHat(TGrammarParser.HatContext ctx) {
        set(ctx, (val(ctx.t(0)) == Type.NUM && val(ctx.t(1)) == Type.NUM) ?
                Type.NUM :
                (val(ctx.t(0)) == Type.STR && val(ctx.t(1)) == Type.NUM) ?
                        Type.STR :
                        Type.ERR);
    }

    @Override
    public void exitPlus(TGrammarParser.PlusContext ctx) {
        set(ctx, val(ctx.t(0)) == val(ctx.t(1)) ? val(ctx.t(0)) : Type.ERR);
    }

    private void set(ParseTree node, Type val) {
        this.vals.put(node, val);
    }

    public Type val(ParseTree node) {
        return this.vals.get(node);
    }
}

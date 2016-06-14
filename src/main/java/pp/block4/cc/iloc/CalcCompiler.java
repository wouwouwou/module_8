package pp.block4.cc.iloc;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import pp.block4.cc.ErrorListener;
import pp.block4.cc.iloc.CalcParser.CompleteContext;
import pp.iloc.Simulator;
import pp.iloc.model.*;

import java.util.HashMap;
import java.util.Map;

/** Compiler from Calc.g4 to ILOC. */
public class CalcCompiler extends CalcBaseListener {
	/** Program under construction. */
	private Program prog;
	// Attribute maps and other fields
    private ParseTreeProperty<Reg> regs;
    private int regnum;

	/** Compiles a given expression string into an ILOC program. */
	public Program compile(String text) {
		Program result = null;
		ErrorListener listener = new ErrorListener();
		CharStream chars = new ANTLRInputStream(text);
		Lexer lexer = new CalcLexer(chars);
		lexer.removeErrorListeners();
		lexer.addErrorListener(listener);
		TokenStream tokens = new CommonTokenStream(lexer);
		CalcParser parser = new CalcParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(listener);
		ParseTree tree = parser.complete();
		if (listener.hasErrors()) {
			System.out.printf("Parse errors in %s:%n", text);
            listener.getErrors().forEach(System.err::println);
		} else {
			result = compile(tree);
		}
		return result;
	}

	/** Compiles a given Calc-parse tree into an ILOC program. */
	public Program compile(ParseTree tree) {
        prog = new Program();
        regs = new ParseTreeProperty<>();
        regnum = 0;

        addRegister(tree);
        new ParseTreeWalker().walk(this, tree);
        System.out.println(prog);
		emit(OpCode.out, new Str(""), new Reg("r_" + (regnum - 1)));
        return prog;
	}

    private void addRegister(ParseTree tree) {
        regs.put(tree, new Reg("r_" + regnum));
        regnum++;
    }

    @Override
    public void exitPar(CalcParser.ParContext ctx) {
        addRegister(ctx);
        emit(OpCode.i2i, regs.get(ctx.expr()), regs.get(ctx));
    }

    @Override
    public void exitMinus(CalcParser.MinusContext ctx) {
        addRegister(ctx);

        Reg expr = regs.get(ctx.expr());
        emit(OpCode.rsubI, expr, new Num(0), regs.get(ctx));
    }

    @Override
    public void exitNumber(CalcParser.NumberContext ctx) {
        addRegister(ctx);

        emit(OpCode.loadI, new Num(Integer.parseInt(ctx.getText())), regs.get(ctx));
    }

    @Override
    public void exitTimes(CalcParser.TimesContext ctx) {
        addRegister(ctx);

        Reg expr1 = regs.get(ctx.expr(0));
        Reg expr2 = regs.get(ctx.expr(1));
        emit(OpCode.mult, expr1, expr2, regs.get(ctx));
    }

    @Override
    public void exitPlus(CalcParser.PlusContext ctx) {
        addRegister(ctx);

        Reg expr1 = regs.get(ctx.expr(0));
        Reg expr2 = regs.get(ctx.expr(1));
        emit(OpCode.add, expr1, expr2, regs.get(ctx));
    }

    /** Constructs an operation from the parameters
	 * and adds it to the program under construction. */
	private void emit(OpCode opCode, Operand... args) {
		this.prog.addInstr(new Op(opCode, args));
	}

	/** Calls the compiler, and simulates and prints the compiled program. */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [expr]+");
			return;
		}
		CalcCompiler compiler = new CalcCompiler();
		for (String expr : args) {
			System.out.println("Processing " + expr);
			Program prog = compiler.compile(expr);
			new Simulator(prog).run();
			System.out.println(prog.prettyPrint());
		}
	}
}

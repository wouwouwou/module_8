package pp.block2.cc;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;
import pp.block2.cc.antlr.Calculator;
import pp.block2.cc.antlr.ExprLexer;
import pp.block2.cc.antlr.ExprParser;
import pp.block2.cc.antlr.SentenceConverter;
import pp.block2.cc.ll.Sentence;
import pp.block2.cc.ll.SentenceParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CalculatorTest {
    private Calculator calculator;

	@Test
	public void testCalc() {
        this.calculator = new Calculator();
		// The following will compile as soon as you do the exercise
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("1+1"))), new BigInteger("2"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("2+3"))), new BigInteger("5"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("12+67"))), new BigInteger("79"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("-41+99"))), new BigInteger("58"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("----22+--2"))), new BigInteger("24"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("1*1"))), new BigInteger("1"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("2*5"))), new BigInteger("10"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("-7*7"))), new BigInteger("-49"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("2^2"))), new BigInteger("4"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("2^2+1"))), new BigInteger("5"));
        assertEquals(calculator.calculate(new ExprLexer(new ANTLRInputStream("3^3*2"))), new BigInteger("54"));
	}
}

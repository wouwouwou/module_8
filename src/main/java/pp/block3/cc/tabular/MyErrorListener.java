package pp.block3.cc.tabular;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wouter on 19-5-2016.
 */
public class MyErrorListener extends BaseErrorListener {

    private List<String> errorlist;

    public MyErrorListener() {
        this.errorlist = new ArrayList<>();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        String s = "line " + line + ":" + charPositionInLine + " syntax error at input '" + offendingSymbol.toString() + "'";
        errorlist.add(s);
    }
}

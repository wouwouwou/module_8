package pp.block3.cc.tabular;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

class MyErrorListener extends BaseErrorListener {

    private List<String> errorlist;

    MyErrorListener() {
        this.errorlist = new ArrayList<>();
    }

    List<String> getErrorlist() {
        return this.errorlist;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errorlist.add(msg);
    }
}

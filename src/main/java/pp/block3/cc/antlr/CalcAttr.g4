grammar CalcAttr;

import CalcVocab;

@members {
    private int getValue(String text) {
        return Integer.parseInt(text);
    }
}

expr returns [ int val ]
     : e0=expr TIMES e1=expr
       { $val = $e0.val * $e1.val; }
     | e0=expr PLUS e1=expr
       { $val = $e0.val + $e1.val; }
     | { System.out.println("Evaluating PAR"); }
       LPAR e=expr RPAR
       { $val = $e.val; }
     | { System.out.println("Evaluating NEGATIVE"); }
       MINUS NUMBER
       { $val = -getValue($NUMBER.text); }
     | { System.out.println("Evaluating NUMBER"); }
       NUMBER
       { $val = getValue($NUMBER.text); }
     ;

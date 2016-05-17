grammar TGrammarAttr;

t returns [ Type type ]
  : t0=t HAT t1=t
    { $type = ($t0.type == Type.NUM && $t1.type == Type.NUM) ?
        Type.NUM :
        ($t0.type == Type.STR && $t1.type == Type.NUM) ?
            Type.STR :
            Type.ERR; }
  | t0=t PLUS t1=t
    { $type = ($t0.type == $t1.type) ? $t0.type : Type.ERR; }
  | t0=t EQUALS t1=t
    { $type = ($t0.type == $t1.type) ? Type.BOOL : Type.ERR; }
  | LPAR t RPAR
    { $type = $t.type; }
  | NUM
    { $type = Type.NUM; }
  | BOOL
    { $type = Type.BOOL; }
  | STR
    { $type = Type.STR; }
  ;

HAT : '^';
PLUS : '+';
EQUALS : '=';
LPAR : '(';
RPAR : ')';

NUM : [0-9]+;
BOOL : 'true' | 'false';
STR : '"' [A-Za-z0-9]+ '"';
grammar TGrammar;

t
  : t HAT t     # hat
  | t PLUS t    # plus
  | t EQUALS t  # equals
  | LPAR t RPAR # par
  | NUM         # num
  | BOOL        # bool
  | STR         # str
  ;

HAT : '^';
PLUS : '+';
EQUALS : '=';
LPAR : '(';
RPAR : ')';

NUM : [0-9]+;
BOOL : 'true' | 'false';
STR : '"' [A-Za-z0-9]+ '"';
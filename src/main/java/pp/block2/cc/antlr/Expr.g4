grammar Expr;

expr	: term expr2;
expr2	: PLUS term expr2
		| MIN term expr2;
term	: factor term2;
term2	: MULT factor term2
		| <assoc=right>EXP factor term2;
factor	: PARENTHOPEN expr PARENTHCLOSE
		| (MIN)* DIG;


DIG : ('0'..'9')+;
PLUS : '+';
MIN : '-';
EXP : '^';
MULT : '*';
PARENTHOPEN : '(';
PARENTHCLOSE : ')';

// ignore whitespace
WS : [ \t\n\r] -> skip;

// everything else is a typo
TYPO : [a-zA-Z]+;

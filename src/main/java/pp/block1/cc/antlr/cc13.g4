lexer grammar cc13;

SENTENCE: '\"' (ALFA|NUM|MARK|QUOT)* '\"';
fragment ALFA: 'A'..'Z' | 'a'..'z';
fragment NUM: '0'..'9';
fragment MARK: '.'|','|' ';
fragment QUOT: '\"\"';
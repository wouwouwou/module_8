grammar Latex;

table
  : TABLESTART row* TABLEEND
  ;

row
  : ENTRY (SEP ENTRY)* ENDROW
  ;


TABLESTART : WS '\\begin{tabular}{' [lcr]+ '}' WS;
ENDROW : WS '\\\\' WS;
SEP : WS '&' WS;
ENTRY
  : [A-Za-z0-9]
  | [A-Za-z0-9] [A-Za-z0-9 ]* [A-Za-z0-9]
  ;
TABLEEND : WS '\\end{tabular}' WS;

COMMENT : '%' .*? EOL -> skip;

fragment EOL : '\n';
fragment WS : [ \t\n\r]*;
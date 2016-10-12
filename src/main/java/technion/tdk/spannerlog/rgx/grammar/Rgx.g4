grammar Rgx;

regex
    : regex '|' regexSimple
    | regexSimple
    |
    ;

regexSimple
    : regexSimple regexBasic
    | regexBasic
    ;

regexBasic
    : regexElementry ('*' | '+' | '?')?
    ;

regexElementry
    : group
    | anyChar
    | captureClause
    | chars
    ;

group
    : '(' regex ')'
    ;

anyChar
    : '.'
    ;

captureClause
    : identifier '{' regex '}'
    ;

identifier
    : BeginsWithLetter
    ;

chars
    : BeginsWithNonLetter
    | BeginsWithLetter
    ;

BeginsWithLetter
    : Letter (Letter | Digit | CharEscapeSeq)*
    ;

BeginsWithNonLetter
    : (Digit | CharEscapeSeq) (Letter | Digit | CharEscapeSeq)*
    ;

fragment
Letter
    : [a-zA-Z] | '"' | '|' | '-'
    ;

fragment
Digit
    : [0-9]
    ;

fragment
CharEscapeSeq
    : '\\' ('b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\')
    ;

WS
    : [ \t\r\n]+ -> skip
    ; // skip spaces, tabs, newlines
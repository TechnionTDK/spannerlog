grammar Rgx;

regex
    : regex '|' regexSimple
    | regexSimple
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
    | set
    ;

set
    : positiveSet
    | negativeSet
    ;

positiveSet
    : LBRACK setItems RBRACK
    ;

negativeSet
    : LBRACK Neg setItems RBRACK
    ;

setItems
    : setItem+
    ;

setItem
    : chars
    | range
    ;

range
    : UppercaseLetter RangeChar UppercaseLetter
    | LowercaseLetter RangeChar LowercaseLetter
    | Digit RangeChar Digit
    ;

group
    : LPAREN regex RPAREN
    ;

anyChar
    : Dot
    ;

captureClause
    : identifier '{' regex '}'
    ;

identifier
    : (UppercaseLetter | LowercaseLetter) (UppercaseLetter | LowercaseLetter | Digit | CharEscapeSeq)*
    ;

chars
    : (Digit | CharEscapeSeq | Dot) (UppercaseLetter | LowercaseLetter | Digit | CharEscapeSeq | Dot)*
    | (UppercaseLetter | LowercaseLetter) (UppercaseLetter | LowercaseLetter | Digit | CharEscapeSeq)*
    ;

UppercaseLetter
    : [A-Z]
    ;

LowercaseLetter
    : [a-z]
    ;

Digit
    : [0-9]
    ;

CharEscapeSeq
    : '\\' ('b' | 't' | 'n' | 'f' | 'r' | 's' | '"' | '\'' | '\\' | Dot)
    ;

Dot
    : '.'
    ;

RangeChar
    : '-'
    ;

Neg
    : '^'
    ;

LBRACK
    : '['
    ;

RBRACK
    : ']'
    ;

LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;

WS
    : [ \t\r\n]+ -> skip
    ; // skip spaces, tabs, newlines
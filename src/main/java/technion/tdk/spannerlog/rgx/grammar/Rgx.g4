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
    : '[' setItems ']'
    ;

negativeSet
    : '[^' setItems ']'
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
    : '(' regex ')'
    ;

anyChar
    : '.'
    ;

captureClause
    : identifier '{' regex '}'
    ;

identifier
    : (UppercaseLetter | LowercaseLetter) (UppercaseLetter | LowercaseLetter | Digit | CharEscapeSeq)*
    ;

chars
    : (Digit | CharEscapeSeq) (UppercaseLetter | LowercaseLetter | Digit | CharEscapeSeq)*
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

RangeChar
    : '-'
    ;

CharEscapeSeq
    : '\\' ('b' | 't' | 'n' | 'f' | 'r' | 's' | '"' | '\'' | '\\')
    ;

WS
    : [ \t\r\n]+ -> skip
    ; // skip spaces, tabs, newlines
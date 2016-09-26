grammar Spannerlog;

program
    : statement*
    ;

statement
    : conjunctiveQuery
    ;

conjunctiveQuery
    : rigidConjunctiveQuery
    | softConjunctiveQuery
    ;

rigidConjunctiveQuery
    : conjunctiveQueryHead RigidSeparator conjunctiveQueryBody '.'
    ;

softConjunctiveQuery
    : annotation? conjunctiveQueryHead SoftSeparator conjunctiveQueryBody '.'
    ;

annotation
    : AnnotationSymbol annotationName annotationArguments?
    ;

annotationName
    : 'weight'  # Weight
    ;

annotationArguments
    : '(' annotationArgument (',' annotationArgument)* ')'
    ;

annotationArgument
    :   variable
    |   floatingPointLiteral
    ;

conjunctiveQueryHead
    : dbAtom
    ;

conjunctiveQueryBody
    : atom (',' atom)* (',' condition)*
    ;

condition
    : binaryCondition
    ;

binaryCondition
    : expr CompareOperator expr
    ;

atom
    : dbAtom
    | ieAtom
    ;

dbAtom
    : relationSchemaName termClause
    ;

ieAtom
    : relationSchemaName '<' varExpr '>' termClause    # IEFunction
    | ('RGX')? '<' varExpr '>' Regex                   # Rgx
    ;

termClause
    : '(' (term (',' term)*)? ')'
    ;

term
    : placeHolder
    | expr
    ;

expr
    : integerLiteral
    | floatingPointLiteral
    | booleanLiteral
    | spanLiteral
    | stringExpr
    | varExpr
    ;

stringExpr
    : stringLiteral span*
    ;

varExpr
    : variable span*
    ;

span
    : spanVarClause
    | spanLiteral
    ;

spanVarClause
    : '[' variable ']'
    ;

relationSchemaName
    : Identifier
    ;

variable
    : Identifier
    ;

placeHolder
    : '_'
    ;

spanLiteral
    : '[' IntegerLiteral+ ',' IntegerLiteral+ ']'
    ;

integerLiteral
    : '-'? IntegerLiteral
    ;

floatingPointLiteral
    : '-'? FloatingPointLiteral
    ;

booleanLiteral
    : BooleanLiteral
    ;

stringLiteral
    : StringLiteral
    ;

BooleanLiteral
    : 'True'
    | 'False'
    ;

Identifier
    : Letter LetterOrDigit*
    ;

StringLiteral
    : '"' StringElement* '"'
    ;

IntegerLiteral
   : DecimalNumeral
   ;

FloatingPointLiteral
   : Digit + '.' Digit+
   ;

RigidSeparator
    : ':-'
    ;

SoftSeparator
    : ':~'
    ;

AnnotationSymbol
    : '@'
    ;

CompareOperator
    : '='
    | '!='
    ;

Regex
    :  '\\[' RegexElememt* ']\\'
    ;

WS
    : [ \t\r\n]+ -> skip
    ; // skip spaces, tabs, newlines

Comment
    : '#' ~[\r\n]* -> skip
    ;

fragment
Letter
    : [a-zA-Z]
    ;

fragment
LetterOrDigit
    : [a-zA-Z0-9_$]
    ;

fragment
RegexElememt
    : [a-zA-Z0-9] | [ \t\r\n] | '{' | '}' | '+' | '*'
    ;

fragment
StringElement
    : '\u0020' | '\u0021' | '\u0023' .. '\u007F' | CharEscapeSeq
    ;

fragment
CharEscapeSeq
    : '\\' ('b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\')
    ;

fragment
DecimalNumeral
    : '0' | NonZeroDigit Digit*
    ;

fragment
Digit
    : '0' | NonZeroDigit
    ;

fragment
NonZeroDigit
    : '1' .. '9'
    ;

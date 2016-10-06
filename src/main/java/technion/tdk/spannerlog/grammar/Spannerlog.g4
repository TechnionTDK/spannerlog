grammar Spannerlog;

program
    : statement*
    ;

statement
    : ruleWithConjunctiveQuery
    | predictionVariableDeclaration
    ;

predictionVariableDeclaration
    : '?' relationSchemaName
    ;

ruleWithConjunctiveQuery
    : extractionRule
    | supervisionRule
    | inferenceRule
    ;

extractionRule
    : dbAtom RigidSeparator conjunctiveQueryBody '.'
    ;

supervisionRule
    : dbAtom '=' supervisionExpr RigidSeparator conjunctiveQueryBody '.'
    ;

inferenceRule
    : inferenceRuleHead SoftSeparator conjunctiveQueryBody '.'
    ;

inferenceRuleHead
    : dbAtom                               # IsTrue
    | dbAtom (',' dbAtom)* '=>' dbAtom     # Imply
    | dbAtom ('v' dbAtom)+                 # Or
    | dbAtom ('^' dbAtom)+                 # And
    ;

conjunctiveQueryBody
    : bodyElement (',' bodyElement)*
    ;

bodyElement
    : atom
    | condition
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

supervisionExpr
    : nullExpr
    | ifThenElseExpr
    ;

nullExpr
    : NullLiteral
    ;

ifThenElseExpr
    : 'if' condition 'then' expr ('else' expr)? 'end'
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

NullLiteral
    : 'NULL'
    ;

PositiveLabel
    : 'POS'
    ;

NegativeLabel
    : 'NEG'
    ;

UnknownLabel
    : 'UKN'
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
    : '<-'
    ;

SoftSeparator
    : '<~'
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

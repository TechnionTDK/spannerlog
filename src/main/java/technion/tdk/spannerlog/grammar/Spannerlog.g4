grammar Spannerlog;

program
    : statement*
    ;

statement
    : ruleWithConjunctiveQuery
    | predictionVariableDeclaration
    ;

predictionVariableDeclaration
    : relationSchemaName '?.'
    ;

ruleWithConjunctiveQuery
    : extractionRule
    | supervisionRule
    | inferenceRule
    ;

extractionRule
    : dbAtom Separator conjunctiveQueryBody '.'
    ;

supervisionRule
    : dbAtom Equal supervisionExpr Separator conjunctiveQueryBody '.'
    ;

inferenceRule
    : weight '*' LBRACK inferenceRuleHead RBRACK Separator conjunctiveQueryBody '.'
    ;

inferenceRuleHead
    : dbAtom                               # IsTrue
    | dbAtom (',' dbAtom)* '=>' dbAtom     # Imply
    | dbAtom ('v' dbAtom)+                 # Or
    | dbAtom ('^' dbAtom)+                 # And
    ;

weight
    : placeHolder                      # UnknownWeight
    | placeHolder '(' variable ')'     # UnknownWeightWithFeature
    | integerLiteral                   # IntegerWeight
    | floatingPointLiteral             # FloatWeight
    ;

conjunctiveQueryBody
    : bodyElement (',' bodyElement)*
    ;

bodyElement
    : atom
    | condition
    ;

condition
    : negationCondition
    | exprCondition
    ;

negationCondition
    : '!' exprCondition
    ;

exprCondition
    : compareExpr
    | dotFuncExpr
    ;

atom
    : dbAtom
    | ieAtom
    ;

dbAtom
    : relationSchemaName termClause
    ;

ieAtom
    : materialized? relationSchemaName LANGLE varExpr RANGLE termClause    # IEFunction
    | materialized? ('RGX')? LANGLE varExpr RANGLE Regex                   # Rgx
    ;

materialized
    : AT 'materialized'
    ;

termClause
    : '(' (term (',' term)*)? ')'
    ;

term
    : placeHolder
    | expr
    ;

expr
    : binaryOpExpr
    | exprAux
    ;

binaryOpExpr
    : exprAux operator expr
    ;

compareExpr
    : expr compareOperator expr
    ;

exprAux
    : integerLiteral
    | floatingPointLiteral
    | booleanLiteral
    | spanLiteral
    | stringExpr
    | varExpr
    | funcExpr
    | dotFuncExpr
    | nullExpr
    ;

funcExpr
    : functionName '(' expr (',' expr)? ')'
    ;

dotFuncExpr
    : varExpr '.' functionName '(' expr (',' expr)? ')'
    ;

functionName
    : Identifier
    ;

supervisionExpr
    : '{' PosLabel ':' condition ';' NegLabel ':' condition '}'
    ;

nullExpr
    : NullLiteral
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
    : LBRACK variable RBRACK
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
    : LBRACK IntegerLiteral+ ',' IntegerLiteral+ RBRACK
    ;

integerLiteral
    : Minus? IntegerLiteral
    ;

floatingPointLiteral
    : Minus? FloatingPointLiteral
    ;

booleanLiteral
    : BooleanLiteral
    ;

stringLiteral
    : StringLiteral
    ;

operator
    : Plus | Minus
    ;

compareOperator
    : Equal | NotEqual | LANGLE | RANGLE
    ;

BooleanLiteral
    : 'True'
    | 'False'
    ;

PosLabel
    : 'POS'
    ;

NegLabel
    : 'NEG'
    ;

NullLiteral
    : 'NULL'
    ;

Separator
    : '<-'
    ;

Equal
    : '='
    ;

NotEqual
    : '!='
    ;

Minus
    : '-'
    ;

Plus
    : '+'
    ;

LRGX
    : '\\['
    ;

RRGX
    : ']\\'
    ;

LBRACK
    : '['
    ;

RBRACK
    : ']'
    ;

LANGLE
    : '<'
    ;

RANGLE
    : '>'
    ;

AT
    : '@'
    ;

Regex
    :  LRGX RegexElememt* RRGX
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
    : [a-zA-Z0-9] | [ \t\r\n] | '{' | '}' | '+' | '*' | '.' | '"' | '|' | '(' | ')' | '-' | '\\' | '[' | ']'
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

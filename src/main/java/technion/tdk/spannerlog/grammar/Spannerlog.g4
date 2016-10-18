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
    : dbAtom RigidSeparator conjunctiveQueryBody '.'
    ;

supervisionRule
    : dbAtom Equal supervisionExpr RigidSeparator conjunctiveQueryBody '.'
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
    : relationSchemaName LANGLE varExpr RANGLE termClause    # IEFunction
    | ('RGX')? LANGLE varExpr RANGLE Regex                   # Rgx
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
    : nullExpr
    | ifThenElseExpr
    ;

nullExpr
    : NullLiteral
    ;

ifThenElseExpr
    : 'if' condition 'then' expr elseIfExpr* ('else' expr)? 'end'
    ;

elseIfExpr
    : 'else if' condition 'then' expr
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

NullLiteral
    : 'NULL'
    ;

RigidSeparator
    : '<-'
    ;

SoftSeparator
    : '<~'
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
    : [a-zA-Z0-9] | [ \t\r\n] | '{' | '}' | '+' | '*' | '.' | '"' | '|' | '(' | ')' | '-' | '\\'
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

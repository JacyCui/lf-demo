grammar Circuit;

circ : item* EOF;

item
    : WIRE var=ID            # WireDecl
    | REG  var=ID '<-' exp   # RegDecl
    | var=ID '<=' exp        # Connection
    ;

exp
    : num=NUM                           # Literal
    | var=ID                            # Variable
    | uop '(' exp ')'                   # UnaryExp
    | bop '(' exp ',' exp ')'           # BinaryExp
    | MUX '(' exp ',' exp ',' exp ')'   # MuxExp
    ;

uop : NEG | NOT;

bop
    : ADD | SUB | MUL | DIV | REM
    | LT | LEQ | GT | GEQ | EQ | NEQ
    | SHL | SHR | AND | OR | XOR
    ;

// Reserved Keywords
WIRE        :   'wire'  ;
REG         :   'reg'   ;
LP          :   '('     ;
RP          :   ')'     ;
COMMA       :   ','     ;

// Unary Operators
NEG         :   'neg'   ;
NOT         :   'not'   ;

// Binary Operators
ADD         :   'add'   ;
SUB         :   'sub'   ;
MUL         :   'mul'   ;
DIV         :   'div'   ;
REM         :   'rem'   ;
LT          :   'lt'    ;
LEQ         :   'leq'   ;
GT          :   'gt'    ;
GEQ         :   'geq'   ;
EQ          :   'eq'    ;
NEQ         :   'neq'   ;
SHL         :   'shl'   ;
SHR         :   'shr'   ;
AND         :   'and'   ;
OR          :   'or'    ;
XOR         :   'xor'   ;

// Tunary Operators
MUX         :   'mux'   ;

// Identifier
ID          :   [a-zA-Z_][A-Za-z0-9_]*      ;

// Literal
NUM         :   '0' | '-'?[1-9][0-9]*       ;

// Ignored Tokens
COMMENT     :   '//' ~('\n')* '\n' -> skip  ;
WS          :   [ \t\r\n]+ -> skip          ; // skip spaces, tabs, newlines

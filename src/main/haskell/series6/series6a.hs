module S6E1 where

import FPPrac.Trees
import GHC.Generics
import FP_TypesEtc
import FP_ParserGen
import Tokenizer

grammar :: Grammar
grammar nt = case nt of
  Nmbr       ->      [[nmbr]]
  Vrbl       ->      [[vrbl]]
  Op         ->      [[op]]
  Expr       ->      [[lpar, Expr, Op, Expr, rpar]
                     , [Nmbr]
                     , [Vrbl]]
  Stmt       ->      [[assgn, Vrbl, Expr]
                     , [repet, Expr, Block]
                     , [iff, Expr, thenn, Block, Opt [elsse, Block]]
                     , [Expr]]
  Block      ->      [[lbrc, Rep0 [Stmt], rbrc]]
  Program    ->      [[Block]]

nmbr  = SyntCat Nmbr
vrbl  = SyntCat Vrbl
op    = SyntCat Op

lpar    = Terminal "("
rpar    = Terminal ")"
lbrc   = Terminal "{"
rbrc   = Terminal "}"

assgn   = Terminal "assign"
repet   = Terminal "repeat"
iff     = Terminal "if"
elsse   = Terminal "else"
thenn   = Terminal "then"

tokens = tokenize "{assign a (++4)}"

parseTree = parse grammar Program tokens

testTxt = prpr parseTree
testGr = showTree $ toRoseTree parseTree
testGr2 = showTree $ astToRose $ ptreeToAst $ parseTree

data AST = ASTRoot [AST]
         | ASTStat String AST [AST] [AST]
         | ASTExpr String [AST]
         | ASTBlock [AST]
         | ASTLeaf
            deriving Show

ptreeToAst :: ParseTree -> AST
ptreeToAst (PNode Program l)
    = ASTRoot (map ptreeToAst l)
ptreeToAst (PNode Block l)
    = ASTBlock (map ptreeToAst l)
ptreeToAst (PNode Stmt (st@(PLeaf (Repet,_,_)):e:bl:_))
    = ASTStat "repeat" (ptreeToAst e) [(ptreeToAst bl)] []
ptreeToAst (PNode Stmt (st@(PLeaf (Assgn, _, _)) : (PNode Vrbl [PLeaf (_, v, _)]): e : _))
    = ASTStat (v ++ " assign ") (ptreeToAst e) [] []
ptreeToAst (PNode Stmt (st@(PLeaf (Iff,_,_)):e:(PLeaf (Thenn,_,_)):th:(PLeaf (Elsse,_,_)):el:_))
    = ASTStat "if" (ptreeToAst e) [(ptreeToAst th)] [(ptreeToAst el)]
ptreeToAst (PNode Stmt (st@(PLeaf (Iff,_,_)):e:(PLeaf (Thenn,_,_)):th:_))
    = ASTStat "if" (ptreeToAst e) [(ptreeToAst th)] []
ptreeToAst (PNode Stmt [l])
    = ASTStat "Expr" (ptreeToAst l) [] []
ptreeToAst (PNode Expr ((PLeaf (Bracket,_,_)):f:(PNode Op [PLeaf (_,o,_)]):s:_))
    = ASTExpr o [ptreeToAst f, ptreeToAst s]
ptreeToAst (PNode Expr [(PNode Nmbr [PLeaf (_,n,_)])])
    = ASTExpr n []
ptreeToAst (PNode Expr [(PNode Vrbl [PLeaf (_,n,_)])])
    = ASTExpr n []
ptreeToAst e
    = ASTLeaf

astToRose :: AST -> RoseTree
astToRose (ASTRoot e) = RoseNode "program" (map astToRose e)
astToRose (ASTBlock e) = RoseNode "block" (map astToRose $ init $ tail e)
astToRose (ASTStat str expr stat1 stat2) = RoseNode str buildlist
    where
        buildlist   | length stat1 > 0 && (length stat2 > 0)    = [astToRose expr, RoseNode "" (map astToRose stat1), RoseNode "" (map astToRose stat2)]
                    | length stat1 > 0                          = [astToRose expr, RoseNode "" (map astToRose stat1)]
                    | length stat2 > 0                          = [astToRose expr, RoseNode "" (map astToRose stat2)]
                    | otherwise                                 = [astToRose expr]
astToRose (ASTExpr str []) = RoseNode str []
astToRose (ASTExpr str exprs) = RoseNode str (map astToRose exprs)
astToRose (ASTLeaf) = RoseNode "" []
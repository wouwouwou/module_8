module S6E1 where

import FPPrac.Trees
import FP_TypesEtc
import FP_ParserGen

grammar :: Grammar
grammar nt = case nt of
  Nmbr -> [[nmbr]]
  Vrbl -> [[vrbl]]
  Op   -> [[op]]
  Expr -> [[lpar, Expr, Op, Expr, rpar],
           [Nmbr],
           [Vrbl]]
  Stmt -> [[Vrbl, assgn, Expr]]


nmbr = SyntCat Nmbr
vrbl = SyntCat Vrbl
op = SyntCat Op

lpar = Symbol "("
rpar = Symbol ")"
assgn = Symbol "="

tokens = [(lpar, "(", 0),
          (Nmbr, "2", 1),
          (Op, "+", 2),
          (Vrbl, "abc", 3),
          (rpar, ")", 4)]

parseTree = parse grammar Expr tokens

testTxt = prpr parseTree
testGr = showTree $ toRoseTree parseTree
testGr2 = showTree $ cleanTree parseTree

cleanTree :: ParseTree -> RoseTree
cleanTree (PLeaf (_,s,_)) = RoseNode s []
cleanTree (PNode nt ns) = RoseNode (show nt) (map cleanTree ns)

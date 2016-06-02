module S6E1 where

import FPPrac.Trees
import GHC.Generics
import FP_TypesEtc
import FP_ParserGen
import Tokenizer

grammar :: Grammar
grammar nt = case nt of
  Nmbr -> [[nmbr]]
  Vrbl -> [[vrbl]]
  Op   -> [[op]]
  Expr -> [[lpar, Expr, Op, Expr, rpar],
           [Nmbr],
           [Vrbl]]
  Stmt -> [[assgn, Vrbl, Expr],
           [repet, Expr, Rep1 [Stmt]]]
  Space ->[[space]]


nmbr = SyntCat Nmbr
vrbl = SyntCat Vrbl
op = SyntCat Op
space = SyntCat Space

lpar = Symbol "("
rpar = Symbol ")"
assgn = Terminal "assign"
repet = Terminal "repeat"

tokens = tokenize "assign asdf (100/50)"

parseTree = parse grammar Stmt tokens

testTxt = prpr parseTree
testGr = showTree $ toRoseTree parseTree
testGr2 = showTree $ cleanTree parseTree

cleanTree :: ParseTree -> RoseTree
cleanTree (PNode Expr [PNode Expr e1, PNode Op [PLeaf (Op,o,_)], PNode Expr e2]) = RoseNode (show o) []

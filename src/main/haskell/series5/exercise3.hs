module S5E2 where
import Core
import FPPrac.Trees

pp :: Expr -> RoseTree
pp (Const n) = RoseNode (show n) []
pp (BinExpr o n1 n2) = RoseNode (show o) [pp n1, pp n2]

module S5E6 where
import Core2
import FPPrac.Trees

class CodeGen a where
  codeGen :: a -> [Instr]
  pp      :: a -> RoseTree

instance CodeGen Stmnt where
  codeGen (Assign v e)      = codeGen e ++ [Store v]
  codeGen (Repeat e ss)     = codeGen e ++ [PushPC] ++ concatMap codeGen ss ++ [EndRep]
  pp      (Assign v e)      = RoseNode "Assign" [RoseNode (show v) [], pp e]
  pp      (Repeat e ss)     = RoseNode "Repeat" (pp e : map pp ss)

instance CodeGen Expr where
  codeGen (Const a)       = [PushConst a]
  codeGen (Varbl a)       = [PushAddr a]
  codeGen (BinExpr o a b) = codeGen a ++ codeGen b ++ [Calc o]
  pp      (Const a)       = RoseNode (show a) []
  pp      (Varbl a)       = RoseNode (show a) []
  pp      (BinExpr o a b) = RoseNode (show o) [pp a, pp b]

tree = Repeat (BinExpr Add (Const 4) (Const 8)) [Assign 0 (Const 5), Assign 0 (BinExpr Mul (Varbl 0) (Const 8))]
testPP = showTree $ pp tree
testCodeGen = codeGen tree

module S5E2 where
import Core

codeGen :: Expr -> [Instr]
codeGen (Const a)       = [PushConst a]
codeGen (BinExpr o a b) = codeGen a ++ codeGen b ++ [Calc o]

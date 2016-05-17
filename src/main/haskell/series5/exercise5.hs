module S5E5 where
import Core2

codeGen :: Expr -> [Instr]
codeGen (Const a)       = [PushConst a]
codeGen (BinExpr o a b) = codeGen a ++ codeGen b ++ [Calc o]

codeGen' :: Stmnt -> [Instr]
codeGen' (Assign v e) = codeGen e ++ [Store v]

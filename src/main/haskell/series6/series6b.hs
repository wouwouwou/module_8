module S6B where

import Data.Map

data Expr = Const Int
          | Boolean Bool
          | Var String
          | Tuple Expr Expr
          | Triple Expr Expr Expr
          | Iff Expr Expr Expr
          | BinOp String Expr Expr
          | App Expr Expr
          | Lambda Type String Expr
          deriving Show

data Type = IntType
          | BoolType
          | TwoType Type Type
          | TriType Type Type Type
          | FunType Type Type
          deriving (Show, Eq)

type Env = [(String, Type)]

myEnv :: Env
myEnv = [("+", FunType IntType (FunType IntType IntType)),
         ("-", FunType IntType (FunType IntType IntType)),
         ("*", FunType IntType (FunType IntType IntType)),
         ("&&", FunType BoolType (FunType BoolType BoolType)),
         ("||", FunType BoolType (FunType BoolType BoolType)),
         ("==", FunType IntType (FunType IntType BoolType)),

         ("++", FunType (TwoType IntType IntType) (FunType (TwoType IntType IntType) (TwoType IntType IntType))),
         ("--", FunType (TwoType IntType IntType) (FunType (TwoType IntType IntType) (TwoType IntType IntType))),
         ("**", FunType (TwoType IntType IntType) (FunType (TwoType IntType IntType) (TwoType IntType IntType))),
         ("&&&", FunType (TwoType BoolType BoolType) (FunType (TwoType BoolType BoolType) (TwoType BoolType BoolType))),
         ("|||", FunType (TwoType BoolType BoolType) (FunType (TwoType BoolType BoolType) (TwoType BoolType BoolType))),

         ("+++", FunType (TriType IntType IntType IntType) (FunType (TriType IntType IntType IntType) (TriType IntType IntType IntType))),
         ("---", FunType (TriType IntType IntType IntType) (FunType (TriType IntType IntType IntType) (TriType IntType IntType IntType))),
         ("***", FunType (TriType IntType IntType IntType) (FunType (TriType IntType IntType IntType) (TriType IntType IntType IntType))),
         ("&&&&", FunType (TriType BoolType BoolType BoolType) (FunType (TriType BoolType BoolType BoolType) (TriType BoolType BoolType BoolType))),
         ("||||", FunType (TriType BoolType BoolType BoolType) (FunType (TriType BoolType BoolType BoolType) (TriType BoolType BoolType BoolType))),

         ("x", IntType),
         ("y", IntType),
         ("z", IntType),

         ("a", BoolType),
         ("b", BoolType),
         ("c", BoolType)]

typeOf :: Env -> Expr -> Type
typeOf _env (Const   _)              = IntType
typeOf _env (Boolean _)              = BoolType
typeOf env  (Tuple   e1   e2)        = TwoType (typeOf env e1) (typeOf env e2)
typeOf env  (Triple  e1   e2   e3)   = TriType (typeOf env e1) (typeOf env e2) (typeOf env e3)
typeOf env  (Var     str)            = (fromList env)!str
typeOf env  (BinOp   op   e1   e2)   = case t_op of
                                       FunType t0 (FunType t1 t2)
                                           | t0 == typ_e1 && t1 == typ_e2       -> t2
                                           | otherwise                          -> error "Types don't match"
                                       where
                                           t_op      = (fromList env)!op
                                           typ_e1    = typeOf env e1
                                           typ_e2    = typeOf env e2
typeOf env (App e1 e2)               | typeOf env e2 == t1   = t2
                                     | otherwise             = error "Types don't match"
                                         where (FunType t1 t2) = typeOf env e1
typeOf env (Lambda t1 v e2)  = FunType t1 (typeOf x e2)
                             where x = (v, t1):env

test = typeOf myEnv (BinOp "+++" (Triple (Const 5) (Const 3) (Const 12)) (Triple (Const 22) (Var "x") (Var "y")))
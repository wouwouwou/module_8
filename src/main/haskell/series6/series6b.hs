module S6B where
import Data.Maybe

data Expr = Const Int
          | Var String
          | BinOp String Expr Expr
          | App Expr Expr

data Type = IntType
          | BoolType
          | TwoType (Type, Type)
          | TriType (Type, Type, Type)
          | FunType Type Type
          deriving Eq

type Env = [(String, Type)]

myEnv :: Env
myEnv = [("+", IntType),
         ("-", IntType),
         ("*", IntType),
         ("/", IntType),
         ("&&", BoolType),
         ("||", BoolType),
         ("==", BoolType),
         ("/=", BoolType)]

typeOf :: Env -> Expr -> Type
typeOf _env (Const _)        = IntType
typeOf _env (Var _)          = IntType
typeOf env  (BinOp op e1 e2) = case top of
                             Just t
                               | t1 == t2  -> t
                               | otherwise -> error "incompatible types"
                             Nothing -> error "undefined method"
                             where
                               top = lookup op env
                               t1  = typeOf env e1
                               t2  = typeOf env e2
typeOf env (App e1 e2)     = FunType (typeOf env e1) (typeOf env e2)

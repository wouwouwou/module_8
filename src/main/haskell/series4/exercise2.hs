module S4E2 where
import FPPrac.Trees
import Data.Char

data BinTree a b = BinLeaf b
                 | BinNode a (BinTree a b) (BinTree a b)
                 deriving (Show)

pp :: (Show a, Show b) => BinTree a b -> RoseTree
pp (BinLeaf n) = RoseNode (show n) []
pp (BinNode n t1 t2) = RoseNode (show n) [pp t1, pp t2]

--  CFG
--
--  E -> N
--  E -> '(' E O E ')'
--  N -> [0..9]
--  V -> [A..Za..z]
--  O -> [+-*/^]

type Tree = BinTree Operator Int

data Operator = Pls | Min | Mlt | Div | Pow
              deriving (Show)

data NT = E
        | N
        | V
        deriving Show

toOperator :: Char -> Operator
toOperator '+' = Pls
toOperator '-' = Min
toOperator '*' = Mlt
toOperator '/' = Div
toOperator '^' = Pow
toOperator _ = error ""

parse :: NT -> String -> (Tree, String)
parse E (x:xs) | x == '('  = (BinNode n t1 t2, tail r3) -- verwacht: '(' E O E ')'
               | isDigit x = parse N [x]
               | otherwise = error ""
               where
                 (t1, r1) = parse E xs -- E
                 (n,  r2) = (toOperator (head r1), tail r1) -- O
                 (t2, r3) = parse E r2 -- E

parse N (x:xs) = (BinLeaf (read [x]), xs) -- verwacht: [0..9]

type TreeExt = BinTree Operator (Either Int Char)

parse' :: NT -> String -> (TreeExt, String)
parse' E (x:xs) | x == '('   = (BinNode n t1 t2, tail r3) -- verwacht: '(' E O E ')'
                | isDigit x  = parse' N [x]
                | isLetter x = parse' V [x]
                where
                  (t1, r1) = parse' E xs -- E
                  (n,  r2) = (toOperator (head r1), tail r1) -- O
                  (t2, r3) = parse' E r2 -- E

parse' N (x:xs) = (BinLeaf (Left (read [x])), xs) -- verwacht: [0..9]

parse' V (x:xs) = (BinLeaf (Right x), xs) -- verwacht: [A..Za..z]

module S4E3 where
import Data.Char
import Data.Maybe
import FPPrac.Trees

data Token = TokNum Int
           | TokId String
           | TokOp Operator
           | TokLB
           | TokRB
           deriving Show

data Operator = Pls | Min | Mlt | Div | Pow
           deriving (Show)

isOperator :: Char -> Bool
isOperator x = x `elem` "+-*/^<>="

isBracket :: Char -> Bool
isBracket x = x `elem` "()"

toOperator :: Char -> Operator
toOperator '+' = Pls
toOperator '-' = Min
toOperator '*' = Mlt
toOperator '/' = Div
toOperator '^' = Pow
toOperator _ = error ""

data FsaState = Q
              | R
              deriving (Show, Eq)

fsaNmbr :: FsaState -> Char -> FsaState
fsaNmbr s x = case s of
  Q | isDigit x -> R
    | x == '.'  -> R
    | otherwise -> Q
  R | isDigit x -> R
    | x == '.'  -> R
    | otherwise -> Q

testFsaNmbr = do
  print $ foldl fsaNmbr Q "123"
  print $ foldl fsaNmbr Q "12.34"

fsaIdnt :: FsaState -> Char -> FsaState
fsaIdnt s x = case s of
  Q | isLetter x -> R
    | otherwise  -> Q
  R | isLetter x -> R
    | isDigit  x -> R
    | otherwise  -> Q

testFsaIdnt = do
  print $ foldl fsaIdnt Q "abc"
  print $ foldl fsaIdnt Q "abc123"
  print $ foldl fsaIdnt Q "a1b2c3"

fsaOprt :: FsaState -> Char -> FsaState
fsaOprt s x = case s of
  Q | isOperator x -> R
    | otherwise    -> Q
  R | isOperator x -> R
    | otherwise    -> Q

testFsaOprt = do
  print $ foldl fsaOprt Q "+"
  print $ foldl fsaOprt Q "--"
  print $ foldl fsaOprt Q ">="

fsaBrck :: FsaState -> Char -> FsaState
fsaBrck s x = case s of
  Q | isBracket x -> R
    | otherwise   -> Q
  R -> Q

testFsaBrck = do
  print $ foldl fsaBrck Q "()"

fsaWhiteSpace :: FsaState -> Char -> FsaState
fsaWhiteSpace s x = case s of
  Q | x == ' '  -> R
    | otherwise -> Q
  R | x == ' '  -> R
    | otherwise -> Q

tokenize :: String -> [Token]
tokenize []     = []
tokenize (c:cs) | isNothing fsa = error "parse error"
                | otherwise = token : tokenize rest
                where
                  fsa = findFSA c
                  (token, rest) = findToken "" (fromJust fsa) Q (c:cs)


findFSA :: Char -> Maybe (FsaState -> Char -> FsaState)
findFSA c | isOperator c = Just fsaOprt
          | isDigit c    = Just fsaNmbr
          | isAlpha c    = Just fsaIdnt
          | isBracket c  = Just fsaBrck
          | otherwise    = Nothing

findToken :: String -> (FsaState -> Char -> FsaState) -> FsaState -> String -> (Token, String)
findToken res _fsa _s []     = (tok, "")
                             where tok = makeToken res
findToken res fsa  s  (c:cs) | r == R = findToken (res++[c]) fsa r cs
                             | otherwise = (tok, c:cs)
                             where
                               r = fsa s c
                               tok = makeToken res

makeToken :: String -> Token
makeToken (c:cs) | isOperator c = TokOp (toOperator c)
                 | isDigit c    = TokNum (read (c:cs))
                 | isAlpha c    = TokId (c:cs)
                 | c == '('     = TokLB
                 | c == ')'     = TokRB
                 | otherwise    = error "empty token"

-- Exercise 4
data BinTree a b = BinLeaf b
                 | BinNode a (BinTree a b) (BinTree a b)
                 deriving (Show)

pp :: (Show a, Show b) => BinTree a b -> RoseTree
pp (BinLeaf n) = RoseNode (show n) []
pp (BinNode n t1 t2) = RoseNode (show n) [pp t1, pp t2]

type Tree = BinTree Operator (Either Int String)

parse :: [Token] -> (Tree, [Token])
parse []            = error "empty tree"
parse (TokLB:ts)    = (BinNode d t1 t2, tail r3)
                      where
                        (t1, r1) = parse ts
                        (TokOp d:r2) = r1
                        (t2, r3) = parse r2
parse (TokNum i:ts) = (BinLeaf (Left i), ts)
parse (TokId  s:ts) = (BinLeaf (Right s), ts)

-- Exercise 5
eval :: String -> [(String, Int)] -> Int
eval s l = evaluate l $ fst $ parse $ tokenize s

evaluate :: [(String, Int)] -> Tree -> Int
evaluate _ (BinLeaf (Left i))  = i
evaluate l (BinLeaf (Right s)) = find s l
evaluate l (BinNode o t1 t2)   = case o of
  Pls -> evaluate l t1 + evaluate l t2
  Min -> evaluate l t1 - evaluate l t2
  Mlt -> evaluate l t1 * evaluate l t2
  Div -> evaluate l t1 `div` evaluate l t2
  Pow -> evaluate l t1 ^ evaluate l t2

find :: String -> [(String, Int)] -> Int
find q []         = error (q++" not found")
find q ((k,v):ks) | k == q = v
                  | otherwise = find q ks

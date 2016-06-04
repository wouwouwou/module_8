module Tokenizer where

import Data.Char
import Data.Maybe
import FPPrac.Trees
import FP_TypesEtc

isOperator :: Char -> Bool
isOperator x = x `elem` "+-*/^<>="

isBracket :: Char -> Bool
isBracket x = x `elem` "()"

isBrace :: Char -> Bool
isBrace x = x `elem` "{}"

data FsaState = Q
              | R
              | S
              deriving (Show, Eq)

fsaNmbr :: FsaState -> Char -> FsaState
fsaNmbr s x = case s of
  Q | isDigit x -> R
    | x == '~'  -> R
    | otherwise -> Q
  R | isDigit x -> R
    | x == '.'  -> S
    | otherwise -> Q
  S | isDigit x -> S
    | otherwise -> Q

testFsaNmbr = do
  print $ foldl fsaNmbr Q "123"
  print $ foldl fsaNmbr Q "12.34"
  print $ foldl fsaNmbr Q "12.34.56"

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

fsaBrace :: FsaState -> Char -> FsaState
fsaBrace s x = case s of
  Q | isBrace x   -> R
    | otherwise   -> Q
  R -> Q

testFsaBrace = do
  print $ foldl fsaBrace Q "{}"

fsaWhiteSpace :: FsaState -> Char -> FsaState
fsaWhiteSpace s x = case s of
  Q | x == ' '  -> R
    | otherwise -> Q
  R | x == ' '  -> R
    | otherwise -> Q

tokenize :: String -> [Token]
tokenize st = sortTok (filterTok(resWords(tokenize' st))) 0

sortTok :: [Token] -> Int -> [Token]
sortTok [] _ = []
sortTok ((a, b, c):ts) n = (a, b, n) : sortTok ts (n + 1)

filterTok :: [Token] -> [Token]
filterTok [] = []
filterTok ((a,s,n):ts) | a == Rswrd = (filterTok' (a, s, n)):filterTok ts
                       | a == WS = filterTok ts
                       | otherwise = (a,s,n):filterTok ts

filterTok' :: Token -> Token
filterTok' (a, s, n)   |  s == "assign"   = (Assgn, s, n)
                       |  s == "repeat"   = (Repet, s, n)
                       |  s == "if"       = (Iff,   s, n)
                       |  s == "then"     = (Thenn, s, n)
                       |  s == "else"     = (Elsse, s, n)
                       |  otherwise       = (a, s, n)

resWords :: [Token] -> [Token]
resWords = map resWords'

resWords' (Vrbl, b, c)    | elem b res = (Rswrd, b, c)
                          | otherwise  = (Vrbl,  b, c)
                          where res = ["if", "then", "repeat", "else", "assign"]
resWords' x = x

tokenize' :: String -> [Token]
tokenize' []     = []
tokenize' (c:cs) | isNothing fsa = error "parse error"
                 | otherwise = token : tokenize' rest
                 where
                  fsa = findFSA c
                  (token, rest) = findToken "" (fromJust fsa) Q (c:cs)


findFSA :: Char -> Maybe (FsaState -> Char -> FsaState)
findFSA c | isOperator c = Just fsaOprt
          | isDigit c    = Just fsaNmbr
          | c == '~'     = Just fsaNmbr
          | isAlpha c    = Just fsaIdnt
          | isBracket c  = Just fsaBrck
          | isBrace c    = Just fsaBrace
          | c == ' '     = Just fsaWhiteSpace
          | otherwise    = Nothing

findToken :: String -> (FsaState -> Char -> FsaState) -> FsaState -> String -> (Token, String)
findToken res _fsa _s []     = (tok, "")
                             where tok = makeToken res
findToken res fsa  s  (c:cs) | r /= Q = findToken (res++[c]) fsa r cs
                             | otherwise = (tok, c:cs)
                             where
                               r = fsa s c
                               tok = makeToken res

makeToken :: String -> Token
makeToken (c:cs) | isOperator c = (Op, c:cs, 0)
                 | isDigit c    = (Nmbr, c:cs, 0)
                 | c == '~'     = (Nmbr, c:cs, 0)
                 | isAlpha c    = (Vrbl, c:cs, 0)
                 | c == '('     = (Bracket, "(", 0)
                 | c == ')'     = (Bracket, ")", 0)
                 | c == '{'     = (Brace, "{", 0)
                 | c == '}'     = (Brace, "}", 0)
                 | c == ' '     = (WS, c:cs, 0)
                 | otherwise    = error "empty token"

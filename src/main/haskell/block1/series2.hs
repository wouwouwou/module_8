module Main where
import Data.Char
import Data.List

-- Exercise 1
myfilter :: (a -> Bool) -> [a] -> [a]
myfilter f xs = [ x | x <- xs, f x]

isEven :: Int -> Bool
isEven n = mod n 2 == 0

myfoldl :: (a -> b -> a) -> a -> [b] -> a
myfoldl _ z []     = z
myfoldl f z (x:xs) = let z' = z `f` x
                     in myfoldl f z' xs

myfoldr :: (a -> b -> b) -> b -> [a] -> b
myfoldr _ z [] = z
myfoldr f z (x:xs) = x `f` myfoldr f z xs

myzipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
myzipWith _ [] _ = []
myzipWith _ _ [] = []
myzipWith f (x:xs) (y:ys) = f x y : myzipWith f xs ys

-- Exercise 2
db :: [(String, Integer, Char, String)]
db = [("Erik", 20, 'M', "Enschede"),("Klaas", 47, 'M', "Enschede"),("Anne", 31, 'F', "Enschede")]

getName :: (String, Integer, Char, String) -> String
getName (a,_,_,_) = a

getAge :: (String, Integer, Char, String) -> Integer
getAge (_,b,_,_) = b

getGender :: (String, Integer, Char, String) -> Char
getGender (_,_,c,_) = c

getPlace :: (String, Integer, Char, String) -> String
getPlace (_,_,_,d) = d

increaseAge :: Integer -> [(String, Integer, Char, String)] -> [(String, Integer, Char, String)]
increaseAge _ [] = []
increaseAge n ((a,b,c,d):xs) = (a, b + n, c, d) : increaseAge n xs

increaseAge' :: Integer -> [(String, Integer, Char, String)] -> [(String, Integer, Char, String)]
increaseAge' n xs = [(a, b + n, c, d) | (a, b, c, d) <- xs]

increaseAge'' :: Integer -> [(String, Integer, Char, String)] -> [(String, Integer, Char, String)]
increaseAge'' n xs = [(getName x, getAge x + n, getGender x, getPlace x) | x <- xs]

yieldNames :: [(String, Integer, Char, String)] -> [String]
yieldNames [] = []
yieldNames ((a,b,c,_):xs) | b >= 20 && b <= 30 && c == 'F' = a : yieldNames xs
                          | otherwise = yieldNames xs

yieldNames' :: [(String, Integer, Char, String)] -> [String]
yieldNames' xs = [a | (a, b, c, _) <- xs, b >= 20, b <= 30, c == 'F']

yieldNames'' :: [(String, Integer, Char, String)] -> [String]
yieldNames'' xs = [getName x | x <- xs, getAge x >= 20, getAge x <= 30, getGender x == 'F']

yieldAge :: String -> [(String, Integer, Char, String)] -> Integer
yieldAge _ [] = 0
yieldAge a ((b,c,_,_):xs) | map toLower a == map toLower b = c
                          | otherwise = yieldAge a xs

-- TODO 2f
-- sortByAge :: [(String, Integer, Char, String)] -> [(String, Integer, Char, String)]
-- sortByAge xs = sort

-- Exercise 3
-- TODO 3a
sieve :: [Integer] -> [Integer]
sieve [] = []
sieve [x] = [x]
sieve (x:xs) = x : sieve (xs \\ [x, x+x..])

primesTo :: (Enum a, Eq a, Num a) => [a]
primesTo = sieve [2..]
        where
        sieve (x:xs) = x: sieve (xs Data.List.\\ [x, x+x..])
        sieve [] = []

primes = [2,3,5,7,9,11,13,17,19,23,29]

isprime :: Int -> Bool
isprime n = True

nprimes :: Int -> [Int]
nprimes n = take n primes

-- TODO 3a
-- primesto :: Int -> [Int]
-- primesto n |

dividers :: Int -> [Int]
dividers n = [a | a <- [2..n], n `mod` a == 0]

isprime' :: Int -> Bool
isprime' n = length (dividers n) == 1

-- Exercise 4
pyth :: Integer -> [(Integer, Integer, Integer)]
pyth n = [(a, b, c) | a <- [2..n], b <- [2..n], c <- [2..n], a^2 + b^2 == c^2]

-- TODO 4b
-- pyth' :: Integer -> [(Integer, Integer, Integer)]
-- pyth' n = pyth

-- Exercise 5
increasing ::  (Ord a, Num a) => [a] -> Bool
increasing [] = True
increasing [_] = True
increasing (x:y:xs) | x <= y = increasing (y:xs)
                    | otherwise = False

-- TODO 5b
-- weakIncr :: (Ord a, Num a) => [a] -> Bool
-- weakIncr [] = True
-- weakIncr [_] = True
-- weakIncr (x:y:xs) | y - x >

-- Exercise 6
sublist :: (Eq a) => [a] -> [a] -> Bool
sublist xs ys = any (sublist' xs) (tails ys)

sublist' :: (Eq a) => [a] -> [a] -> Bool
sublist' [] _ = True
sublist' _ [] = False
sublist' (x:xs) (y:ys) = x == y && sublist' xs ys

partial :: (Eq a) => [a] -> [a] -> Bool
partial [] _ = True
partial _ [] = False
partial (x:xs) (y:ys) | x == y = partial xs ys
                      | otherwise = partial (x:xs) ys

-- Exercise 7
bsort :: (Ord a) => [a] -> [a]
bsort [] = []
bsort xs = case bubble xs of
           t | t == xs -> t
             | otherwise -> bsort t

bubble :: (Ord a) => [a] -> [a]
bubble [x] = [x]
bubble (x:y:xs) | x > y = y : bubble (x:xs)
                | otherwise = x : bubble (y:xs)

mmsort :: (Ord a) => [a] -> [a]
mmsort [] = []
mmsort [x] = [x]
mmsort xs = [a] ++ mmsort (xs \\ [a,b]) ++ [b]
          where
            a = minimum xs
            b = maximum xs

-- TODO 7c
-- isort :: (Eq a) => [a] -> [a]
-- isort [] = []
--
-- ins :: a -> [a] -> [a]
-- ins a b

merge :: (Ord a) => [a] -> [a] -> [a]
merge [] ys = ys
merge xs [] = xs
merge (x:xs) (y:ys) | x < y = x : y : merge xs ys
                    | otherwise = y : x : merge xs ys

msort :: (Ord a) => [a] -> [a]
msort [] = []
msort [x] = [x]
msort xs = merge (msort (fst halves)) (msort (snd halves))
   where
    halves = splitAt (length xs `div` 2) xs

qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) = qsort [a | a <- xs, a <= x] ++ [x] ++ qsort [a | a <- xs, a > x]

main :: IO ()
main = return()

module S2E7 where
import Data.List

bsort :: (Ord a) => [a] -> [a]
bsort [] = []
bsort xs = case bubble xs of
           t | t == xs -> t
             | otherwise -> bsort t

bubble :: (Ord a) => [a] -> [a]
bubble [] = []
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

isort :: (Ord a) => [a] -> [a]
isort xs = foldr ins [] xs

ins :: (Ord a) => a -> [a] -> [a]
ins i [] = [i]
ins i (x:xs) | i > x = x : ins i xs
             | otherwise = i:x:xs

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

module S2E6 where
import Data.List

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

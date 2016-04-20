module Main where

-- Exercise 1
myfilter :: (a -> Bool) -> [a] -> [a]
myfilter f xs = [ x | x <- xs, f x]

isEven :: Int -> Bool
isEven n = mod n 2 == 0

-- myfoldl f [] = []
-- myfoldl f (x:xs) = f x

main :: IO ()
main = return()

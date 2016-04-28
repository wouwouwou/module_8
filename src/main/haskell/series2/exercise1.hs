module S2E1 where

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

module Main where

-- Exercise 1
myfilter :: (a -> Bool) -> [a] -> [a]
myfilter f xs = [ x | x <- xs, f x]

isEven :: Int -> Bool
isEven n = mod n 2 == 0

-- myfoldl :: Foldable t => (b -> a -> b) -> b -> t a -> b
-- myfoldl f z [] = z
-- myfoldl f z (x:xs) = myfoldl f z' xs
--                    where z' = z `f` x

-- myfoldr :: Foldable t => (a -> b -> b) -> b -> t a -> b
-- myfoldr f z [] = z
-- myfoldr f z (x:xs) = x `f` myfoldr f z xs

--myzipWith

-- Exercise 2
db :: [(String, Integer, Char, String)]
db = [("Erik", 20, 'M', "Enschede"),("Klaas", 47, 'M', "Enschede"),("Lindsay", 19, 'F', "Enschede")]

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

-- TODO
-- increaseAge'' :: Integer -> [(String, Integer, Char, String)] -> [(String, Integer, Char, String)]
-- increaseAge'' n xs = []

yieldNames :: [(String, Integer, Char, String)] -> [String]
yieldNames [] = []
yieldNames ((a,b,c,_):xs) | b >= 20 && b <= 30 && c == 'F' = a : yieldNames xs
                          | otherwise = yieldNames xs

yieldNames' :: [(String, Integer, Char, String)] -> [String]
yieldNames' xs = [a | (a, b, c, _) <- xs, b >= 20, b <= 30, c == 'F']

-- TODO
-- yieldNames'' :: [(String, Integer, Char, String)] -> [String]
-- yieldNames'' xs = []

main :: IO ()
main = return()

module Main where
import Data.Char

-- Exercise 1
f x = 2 * x ^ 2 + 3 * x - 5

-- Exercise 2
code :: Int -> Char -> Char
code n x | isLower x = chr (97 + (ord x + n) `mod` 123)
         | isUpper x = chr (65 + (ord x + n) `mod` 91)
         | otherwise = x
-- map (code 3) "Tomorrow evening, 8 o'clock in Amsterdam"

-- Exercise 3
money :: (Fractional a) => a -> a -> Int -> a
money a _ 0 = a
money a r n = money (a * (1 + r / 100)) r (n - 1)
-- money 1000 0.1 2

-- Exercise 4
root1 :: (Floating a, Ord a) => a -> a -> a -> a
root1 a b c = (-b + sqrt d) / (2 * a)
            where
              d = discr a b c
root2 :: (Floating a, Ord a) => a -> a -> a -> a
root2 a b c = (-b - sqrt d) / (2 * a)
            where
              d = discr a b c
discr :: (Floating a, Ord a) => a -> a -> a -> a
discr a b c | d < 0 = error "negative descriminant"
            | otherwise = d
            where
              d = b ^ 2 - 4 * a * c

-- Exercise 5
extrX :: (Floating a) => a -> a -> a -> a
extrX a b _ = (-b) / (2 * a)

extrY :: (Floating a) => a -> a -> a -> a
extrY a b c = a * x ^ 2 + b * x + c
            where
              x = extrX a b c

-- Exercise 6
mylength :: [a] -> Int
mylength [] = 0
mylength (_:xs) = 1 + mylength xs

mysum :: [Int] -> Int
mysum [] = 0
mysum (x:xs) = x + mysum xs

myreverse :: [a] -> [a]
myreverse [] = []
myreverse (x:xs) = myreverse xs ++ [x]

mytake :: [a] -> Int -> [a]
mytake _ 0 = []
mytake [] _ = []
mytake (x:xs) n = x : mytake xs (n-1)

myelem :: (Eq a) => [a] -> a -> Bool
myelem [] _ = False
myelem (x:xs) a | x == a = True
                | otherwise = myelem xs a

myconcat :: [[a]] -> [a]
myconcat [] = []
myconcat (xs:xss) = xs ++ myconcat xss

mymaximum :: (Ord a) => [a] -> a
mymaximum [] = error "empty list"
mymaximum [x] = x
mymaximum (x:xs) = max x (mymaximum xs)

myzip :: [a] -> [b] -> [(a, b)]
myzip [] _ = []
myzip _ [] = []
myzip (x:xs) (y:ys) = (x, y) : myzip xs ys

-- Exercise 7
r :: Num a => a -> a -> [a]
r a d = a : r (a + d) d

r1 :: Num a => a -> a -> Int -> a
r1 a d n = r a d !! n

total :: (Num a, Eq a) => a -> a -> Int -> Int -> a
total a d i j | i == j = x
              | abs(i - j) == 1 = x + y
              | otherwise = x + total a d (i + 1) j
              where
                x = r1 a d i
                y = r1 a d j

-- Exercise 8
allEqual :: (Eq a) => [a] -> Bool
allEqual xs = and ( map (== head xs) (tail xs) )

isAS :: (Num a, Eq a) => [a] -> Bool
isAS [] = True
isAS [_] = True
isAS [_, _] = True
isAS (x:y:z:xs) | x - y == y - z = isAS (y:z:xs)
                | otherwise = False

-- Exercise 9
lengthEqual :: [[Int]] -> Bool
lengthEqual xss = allEqual (map length xss)

totalRows :: [[Int]] -> [Int]
totalRows = map sum

transpose :: [[Int]] -> [[Int]]
transpose ([]:_) = []
transpose xss = map head xss : transpose (map tail xss)

totalColumns :: [[Int]] -> [Int]
totalColumns ([]:_) = []
totalColumns xs = sum (map head xs) : totalColumns (map tail xs)

main :: IO()
main = return()

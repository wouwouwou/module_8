module S1E6 where

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

module S1E8 where

allEqual :: (Eq a) => [a] -> Bool
allEqual xs = and ( map (== head xs) (tail xs) )

isAS :: (Num a, Eq a) => [a] -> Bool
isAS [] = True
isAS [_] = True
isAS [_, _] = True
isAS (x:y:z:xs) | x - y == y - z = isAS (y:z:xs)
                | otherwise = False

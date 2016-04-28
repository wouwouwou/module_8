module S1E9 where

allEqual :: (Eq a) => [a] -> Bool
allEqual xs = and ( map (== head xs) (tail xs) )

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

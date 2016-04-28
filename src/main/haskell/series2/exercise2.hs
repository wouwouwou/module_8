module S2E2 where
import Data.Char
import Data.List

db :: [(String, Integer, Char, String)]
db = [("Erik", 20, 'M', "Enschede"),("Klaas", 47, 'M', "Enschede"),("Anne", 28, 'F', "Enschede")]

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
increaseAge'' n = map (\(a,b,c,d) -> (a,b+n,c,d))

yieldNames :: [(String, Integer, Char, String)] -> [String]
yieldNames [] = []
yieldNames ((a,b,c,_):xs) | b >= 20 && b <= 30 && c == 'F' = a : yieldNames xs
                          | otherwise = yieldNames xs

yieldNames' :: [(String, Integer, Char, String)] -> [String]
yieldNames' xs = [a | (a, b, c, _) <- xs, b >= 20, b <= 30, c == 'F']

yieldNames'' :: [(String, Integer, Char, String)] -> [String]
yieldNames'' xs = map getName (filter (\ (_, b, c, _) -> (b >= 20 && b <= 30 && c == 'F')) xs)

yieldAge :: String -> [(String, Integer, Char, String)] -> Integer
yieldAge _ [] = 0
yieldAge a ((b,c,_,_):xs) | map toLower a == map toLower b = c
                          | otherwise = yieldAge a xs

sortByAge :: [(String, Integer, Char, String)] -> [(String, Integer, Char, String)]
sortByAge xs = [(a, b, c, d) | (b, a, c, d) <- sort [(b, a, c, d) | (a, b, c, d) <- xs]]

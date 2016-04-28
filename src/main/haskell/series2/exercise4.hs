module S2E4 where

pyth :: Integer -> [(Integer, Integer, Integer)]
pyth n = [(a, b, c) | a <- [2..n], b <- [2..n], c <- [2..n], a^2 + b^2 == c^2]

pyth' :: Integer -> [(Integer, Integer, Integer)]
pyth' n = [(a, b, c) | c <- [2..n], b <- [2..c], a <- [2..b], a^2 + b^2 == c^2, gcd a b == 1]

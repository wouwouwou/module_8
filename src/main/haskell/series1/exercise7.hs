module S1E7 where

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

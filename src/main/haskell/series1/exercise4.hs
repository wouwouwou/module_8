module S1E4 where

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

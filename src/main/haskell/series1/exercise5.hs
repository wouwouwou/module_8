module S1E5 where

extrX :: (Floating a) => a -> a -> a -> a
extrX a b _ = (-b) / (2 * a)

extrY :: (Floating a) => a -> a -> a -> a
extrY a b c = a * x ^ 2 + b * x + c
            where
              x = extrX a b c

module S2E5 where

increasing ::  (Ord a, Num a) => [a] -> Bool
increasing [] = True
increasing [_] = True
increasing (x:y:xs) | x <= y = increasing (y:xs)
                    | otherwise = False

weakIncr :: (Ord a, Fractional a) => [a] -> Bool
weakIncr = weakIncr' . reverse

weakIncr' :: (Ord a, Fractional a) => [a] -> Bool
weakIncr' [] = True
weakIncr' [_] = True
weakIncr' (x:xs) = x > sum xs / fromIntegral (length xs) && weakIncr' xs

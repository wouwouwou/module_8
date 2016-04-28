module S1E3 where

money :: (Fractional a) => a -> a -> Int -> a
money a _ 0 = a
money a r n = money (a * (1 + r / 100)) r (n - 1)
-- money 1000 0.1 2

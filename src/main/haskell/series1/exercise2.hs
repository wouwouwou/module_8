module S1E2 where
import Data.Char

code :: Int -> Char -> Char
code n x | isLower x = chr ((ord x + n) `mod` 123)
         | isUpper x = chr ((ord x + n) `mod` 91)
         | otherwise = x
-- map (code 3) "Tomorrow evening, 8 o'clock in Amsterdam"

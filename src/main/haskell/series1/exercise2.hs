module S1E2 where
import Data.Char

code :: Int -> Char -> Char
code n x | isLower x = chr (97 + (ord x + n) `mod` 123)
         | isUpper x = chr (65 + (ord x + n) `mod` 91)
         | otherwise = x
-- map (code 3) "Tomorrow evening, 8 o'clock in Amsterdam"

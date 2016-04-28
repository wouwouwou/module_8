module S2E3 where

sieve :: [Int] -> [Int]
sieve [] = []
sieve (x:xs) = x : sieve [y | y <- xs, y `mod` x /= 0]

primes :: [Int]
primes = sieve [2..]

isprime :: Int -> Bool
isprime n = n `mod` 2 /= 0 && n == last (takeWhile (<=n) primes)

nprimes :: Int -> [Int]
nprimes n = take n primes

primesto :: Int -> [Int]
primesto n = takeWhile (<n) primes

dividers :: Int -> [Int]
dividers n = [a | a <- [2..n], n `mod` a == 0]

isprime' :: Int -> Bool
isprime' n = length (dividers n) == 1

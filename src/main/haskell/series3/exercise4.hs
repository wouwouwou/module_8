module S3E4 where
import FPPrac.Trees

data Tree4a = Leaf4a
            | Node4a Int Tree4a Tree4a

pp4a :: Tree4a -> RoseTree
pp4a Leaf4a = RoseNode "" []
pp4a (Node4a n t1 t2) = RoseNode (show n) [pp4a t1, pp4a t2]

-- Exercise 4a
insertTree :: Int -> Tree4a -> Tree4a
insertTree i Leaf4a = Node4a i Leaf4a Leaf4a
insertTree i (Node4a n t1 t2) | i < n = Node4a n (insertTree i t1) t2
                              | otherwise = Node4a n t1 (insertTree i t2)

ex4a :: IO()
ex4a = showTree (pp4a (insertTree 4 (Node4a 5 Leaf4a Leaf4a)))

-- Exercise 4b
makeTree :: [Int] -> Tree4a
makeTree [] = Leaf4a
makeTree (x:xs) = insertTree x (makeTree xs)

makeTree' :: [Int] -> Tree4a
makeTree' = foldr insertTree Leaf4a

ex4b :: IO()
ex4b = showTree (pp4a (makeTree' [5,3,8,6,2,7,4]))

-- Exercise 4c
makeList :: Tree4a -> [Int]
makeList Leaf4a = []
makeList (Node4a n t1 t2) = makeList t1 ++ [n] ++ makeList t2

-- Exercise 4d
sortList :: [Int] -> [Int]
sortList xs = makeList (makeTree xs)

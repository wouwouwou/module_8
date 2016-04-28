module S3E3 where
import FPPrac.Trees

data Tree1a = Leaf1a Int
            | Node1a Int Tree1a Tree1a

data Tree1d = Leaf1d (Int, Int)
            | Node1d [Tree1d]

-- Exercise 3a
binMirror :: Tree1a -> Tree1a
binMirror (Leaf1a l) = Leaf1a l
binMirror (Node1a n t1 t2) = Node1a n t2 t1

-- Exercise 3b
binMirror' :: Tree1d -> Tree1d
binMirror' (Leaf1d (i1, i2)) = Leaf1d (i2, i1)
binMirror' (Node1d ts) = Node1d ts

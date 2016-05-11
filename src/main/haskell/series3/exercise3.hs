module S3E3 where
import FPPrac.Trees

data Tree1a = Leaf1a Int
            | Node1a Int Tree1a Tree1a

data Tree1d = Leaf1d (Int, Int)
            | Node1d [Tree1d]


pp1d :: Tree1d -> RoseTree
pp1d (Leaf1d l) = RoseNode (show l) []
pp1d (Node1d ts) = RoseNode "" (map pp1d ts)

-- Exercise 3a
binMirror :: Tree1a -> Tree1a
binMirror (Leaf1a l) = Leaf1a l
binMirror (Node1a n t1 t2) = Node1a n t2 t1

-- Exercise 3b
binMirror' :: Tree1d -> Tree1d
binMirror' (Leaf1d (i1, i2)) = Leaf1d (i2, i1)
binMirror' (Node1d ts) = Node1d (map binMirror' (reverse ts))

tree1 = Node1d [Leaf1d (1,2), (Node1d [Leaf1d (1,2), Leaf1d (5,6)]), Leaf1d (3,4)]

ex = showTreeList [pp1d tree1, pp1d $ binMirror' tree1, pp1d $ binMirror' $ binMirror' tree1]

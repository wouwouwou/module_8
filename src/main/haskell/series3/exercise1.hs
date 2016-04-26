module S3E1 where
import FPPrac.Trees

-- Exercise 1a
data Tree1a = Leaf1a Int
            | Node1a Int Tree1a Tree1a

pp1a :: Tree1a -> RoseTree
pp1a (Leaf1a l) = RoseNode (show l) []
pp1a (Node1a n t1 t2) = RoseNode (show n) [pp1a t1, pp1a t2]

tree1a :: IO()
tree1a = showTree (pp1a (Node1a 1 (Leaf1a 2) (Node1a 3 (Leaf1a 4) (Leaf1a 5))))

-- Exercise 1b
data Tree1b = Leaf1b (Int, Int)
            | Node1b (Int, Int) Tree1b Tree1b

pp1b :: Tree1b -> RoseTree
pp1b (Leaf1b l) = RoseNode (show l) []
pp1b (Node1b n t1 t2) = RoseNode (show n) [pp1b t1, pp1b t2]

tree1b :: IO()
tree1b = showTree (pp1b (Node1b (1,2) (Leaf1b (3,4)) (Node1b (5,6) (Leaf1b (7,8)) (Leaf1b (9,10)))))

-- Exercise 1c
data Tree1c = Leaf1c Int
            | Node1c Tree1c Tree1c

pp1c :: Tree1c -> RoseTree
pp1c (Leaf1c l) = RoseNode (show l) []
pp1c (Node1c t1 t2) = RoseNode "" [pp1c t1, pp1c t2]

tree1c :: IO()
tree1c = showTree (pp1c (Node1c (Leaf1c 2) (Node1c (Leaf1c 4) (Leaf1c 5))))

-- Exercise 1d
data Tree1d = Leaf1d (Int, Int)
            | Node1d [Tree1d]

pp1d :: Tree1d -> RoseTree
pp1d (Leaf1d l) = RoseNode (show l) []
pp1d (Node1d ts) = RoseNode "" (map pp1d ts)

tree1d :: IO()
tree1d = showTree (pp1d (Node1d [Leaf1d (1,2), (Node1d [Leaf1d (1,2), Leaf1d (5,6)]), Leaf1d (3,4)]))

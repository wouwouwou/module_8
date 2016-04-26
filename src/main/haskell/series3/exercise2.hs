module S3E2 where
import FPPrac.Trees

data Tree1a = Leaf1a Int
            | Node1a Int Tree1a Tree1a

pp1a :: Tree1a -> RoseTree
pp1a (Leaf1a l) = RoseNode (show l) []
pp1a (Node1a n t1 t2) = RoseNode (show n) [pp1a t1, pp1a t2]

data Tree1b = Leaf1b (Int, Int)
            | Node1b (Int, Int) Tree1b Tree1b

pp1b :: Tree1b -> RoseTree
pp1b (Leaf1b l) = RoseNode (show l) []
pp1b (Node1b n t1 t2) = RoseNode (show n) [pp1b t1, pp1b t2]

-- Exercise 2a
treeAdd :: Int -> Tree1a -> Tree1a
treeAdd n (Leaf1a i) = Leaf1a (i + n)
treeAdd n (Node1a i t1 t2) = Node1a (i + n) (treeAdd n t1) (treeAdd n t2)

tree2a :: IO()
tree2a = showTree (pp1a (treeAdd 1 (Node1a 1 (Leaf1a 2) (Node1a 3 (Leaf1a 4) (Leaf1a 5)))))

-- Exercise 2b
treeSquare :: Tree1a -> Tree1a
treeSquare (Leaf1a i) = Leaf1a (i ^ 2)
treeSquare (Node1a i t1 t2) = Node1a (i ^ 2) (treeSquare t1) (treeSquare t2)

tree2b :: IO()
tree2b = showTree (pp1a (treeSquare (Node1a 1 (Leaf1a 2) (Node1a 3 (Leaf1a 4) (Leaf1a 5)))))

-- Exercise 2c
mapTree :: (Int -> Int) -> Tree1a -> Tree1a
mapTree f (Leaf1a i) = Leaf1a (f i)
mapTree f (Node1a i t1 t2) = Node1a (f i) (mapTree f t1) (mapTree f t2)

square :: Int -> Int
square n = n ^ 2

tree2c :: IO()
tree2c = showTree (pp1a (mapTree square (Node1a 1 (Leaf1a 2) (Node1a 3 (Leaf1a 4) (Leaf1a 5)))))

-- Exercise 2d
addNode :: Tree1b -> Tree1a
addNode (Leaf1b (i1, i2)) = Leaf1a (i1 + i2)
addNode (Node1b (i1, i2) t1 t2) = Node1a (i1 + i2) (addNode t1) (addNode t2)

-- Exercise 2e
mapTree' :: ((Int, Int) -> Int) -> Tree1b -> Tree1a
mapTree' f (Leaf1b (i1, i2)) = Leaf1a (f (i1, i2))
mapTree' f (Node1b (i1, i2) t1 t2) = Node1a (f (i1, i2)) (mapTree' f t1) (mapTree' f t2)

mult :: (Int, Int) -> Int
mult (a, b) = a * b

tree2e :: IO()
tree2e = showTree (pp1a (mapTree' mult (Node1b (1,2) (Leaf1b (3,4)) (Node1b (5,6) (Leaf1b (7,8)) (Leaf1b (9,10))))))

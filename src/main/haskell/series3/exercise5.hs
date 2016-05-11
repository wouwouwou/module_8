module S3E5 where
import FPPrac.Trees

data BinTree = BinLeaf
             | BinNode Int BinTree BinTree

ppbin :: BinTree -> RoseTree
ppbin BinLeaf = RoseNode "" []
ppbin (BinNode n t1 t2) = RoseNode (show n) [ppbin t1, ppbin t2]

insertTree :: Int -> BinTree -> BinTree
insertTree i BinLeaf = BinNode i BinLeaf BinLeaf
insertTree i (BinNode n t1 t2) | i < n = BinNode n (insertTree i t1) t2
                               | otherwise = BinNode n t1 (insertTree i t2)

makeTree :: [Int] -> BinTree
makeTree = foldr insertTree BinLeaf

-- Exercise 5
subtreeAt :: Int -> BinTree -> BinTree
subtreeAt _ BinLeaf = error "not found"
subtreeAt i (BinNode n t1 t2) | i == n = BinNode i t1 t2
                              | i < n = subtreeAt i t1
                              | otherwise = subtreeAt i t2

ex5 :: IO()
ex5 = showTreeList [ppbin $ subtreeAt 4 $ makeTree [4,3,7,5,6,1,8,11,9,2,4,10], ppbin $ makeTree [4,3,7,5,6,1,8,11,9,2,4,10]]

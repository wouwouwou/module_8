module S3E6 where
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
makeTree [] = BinLeaf
makeTree (x:xs) = insertTree x (makeTree xs)

cutOffAt :: Int -> BinTree -> BinTree
cutOffAt 0 _ = BinLeaf
cutOffAt _ BinLeaf = BinLeaf
cutOffAt i (BinNode n t1 t2) = BinNode n (cutOffAt (i-1) t1) (cutOffAt (i-1) t2)

ex6 :: IO()
ex6 = showTree (ppbin (cutOffAt 3 (makeTree [4,3,7,5,6,1,8,11,9,2,4,10])))

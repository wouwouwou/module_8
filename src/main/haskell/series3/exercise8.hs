module S3E8 where
import FPPrac.Trees
import Data.List

data BinTree = Empty
             | BinNode Int BinTree BinTree

tree :: BinTree
tree = BinNode 1 (BinNode 2 Empty (BinNode 3 (BinNode 4 Empty Empty) Empty)) Empty

ppbin :: BinTree -> RoseTree
ppbin Empty = RoseNode "" []
ppbin (BinNode n t1 t2) = RoseNode (show n) [ppbin t1, ppbin t2]

isBalanced :: BinTree -> Bool
isBalanced t = (maximum d - minimum d) <= 1
             where d = depth 0 t

depth :: Int -> BinTree -> [Int]
depth i Empty = [i]
depth i (BinNode _ t1 t2) = depth (i+1) t1 ++ depth (i+1) t2

ex8a :: IO()
ex8a = print (isBalanced tree)

ex8a' :: IO()
ex8a' = showTree (ppbin tree)

makeList :: BinTree -> [Int]
makeList Empty = []
makeList (BinNode n t1 t2) = makeList t1 ++ [n] ++ makeList t2

makeTree :: [Int] -> BinTree
makeTree [] = Empty
makeTree xs = BinNode (xs !! half) (makeTree (take half xs)) (makeTree (drop (half+1) xs))
            where half = length xs `quot` 2

balance :: BinTree -> BinTree
balance t = makeTree (sort (makeList t))

ex8b :: IO()
ex8b = showTree (ppbin (balance tree))

ex8c :: Bool
ex8c = isBalanced (balance tree)

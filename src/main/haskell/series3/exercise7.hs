module S3E5 where
import FPPrac.Trees

data BinTree = BinLeaf Int
             | BinNode Int BinTree BinTree

ppbin :: BinTree -> RoseTree
ppbin (BinLeaf n) = RoseNode (show n) []
ppbin (BinNode n t1 t2) = RoseNode (show n) [ppbin t1, ppbin t2]

replace :: String -> Int -> BinTree -> BinTree
replace []       i (BinLeaf _)       = BinLeaf i
replace _        _ (BinLeaf _)       = error "not found"
replace []       i (BinNode _ t1 t2) = BinNode i t1 t2
replace (c:path) i (BinNode n t1 t2) | c == 'l' = BinNode n (replace path i t1) t2
                                     | c == 'r' = BinNode n t1 (replace path i t2)
                                     | otherwise = error "invalid path"

ex7a :: IO()
ex7a = showTree (ppbin (replace "lrll" 100 (BinNode 1 (BinNode 2 (BinLeaf 3) (BinNode 4 (BinNode 5 (BinLeaf 6) (BinLeaf 7)) (BinLeaf 8))) (BinLeaf 9))))

subTree :: String -> BinTree -> BinTree
subTree []       t           = t
subTree _        (BinLeaf _) = error "not found"
subTree (c:path) (BinNode _ t1 t2) | c == 'l' = subTree path t1
                                   | c == 'r' = subTree path t2
                                   | otherwise = error "invalid path"

ex7b :: IO()
ex7b = showTree (ppbin (subTree "lr" (BinNode 1 (BinNode 2 (BinLeaf 3) (BinNode 4 (BinNode 5 (BinLeaf 6) (BinLeaf 7)) (BinLeaf 8))) (BinLeaf 9))))

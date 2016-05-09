module S4E1 where
import FPPrac.Trees

data BinTree a b = BinLeaf b
                 | BinNode a (BinTree a b) (BinTree a b)
                 deriving (Show)

data Unit = Unit
instance Show Unit where
  show Unit = ""

type Tree1a = BinTree Int Int
type Tree1b = BinTree (Int, Int) (Int, Int)
type Tree1c = BinTree Int Unit
type Tree4  = BinTree Unit Int

pp :: (Show a, Show b) => BinTree a b -> RoseTree
pp (BinLeaf n) = RoseNode (show n) []
pp (BinNode n t1 t2) = RoseNode (show n) [pp t1, pp t2]

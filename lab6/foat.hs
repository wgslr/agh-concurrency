import Data.Maybe
import qualified Data.Map as Map

data Rel = Dep | Indep deriving (Show, Eq)
type Stacks = Map.Map Char [Char]

alphabet = "abcd"

rel x y 
  | x == y  = Dep
  | not (elem x alphabet && elem y alphabet) = error ("Unknown relation for letters "  ++ [x] ++ " " ++ [y])
rel 'a' 'b' = Dep
rel 'a' 'c' = Dep
rel 'a' 'd' = Indep
rel 'b' 'c' = Indep
rel 'b' 'd' = Dep
rel 'c' 'd' = Dep
rel x y = rel y x

foat :: [Char] -> [[Char]]
foat = gather . buildStacks
  
buildStacks :: [Char] -> Stacks
buildStacks word = foldl (
    \stacks  l -> 
      foldl (\stacks' alph -> if l == alph 
          then Map.insertWith (++) l [l] stacks'
          else (if rel l alph == Dep then (Map.insertWith (++) alph "*" stacks')  else stacks')
        ) stacks  alphabet
  ) (initStacks word) (reverse word)

-- | Initialize empty stacks
initStacks :: [Char] -> Stacks
initStacks = foldl (\m l -> Map.insert l [] m) Map.empty

-- | Pop values from stacks to generate result
gather :: Stacks -> [[Char]]
gather stacks = gather' stacks []

-- | Pop values from stacks to generate result
gather' :: Stacks -> [[Char]] -> [[Char]]
gather' stacks results = case pop stacks of
  ([], _) -> reverse results
  (letters, stacks') -> gather' stacks' ((filter (/= '*') letters) : results) 

-- | Get heads of all stacks
pop :: (Map.Map Char [Char]) -> ([Char], Stacks)
pop stacks = (concat . map head' . Map.elems $ stacks, Map.map tail' stacks)

-- | List dependencies of a char in given alphabet
deps :: Char -> [Char] -> [Char]
deps l = filter ((== Dep) . rel l) 

-- | Find letter on top of some stack
headLetter :: Stacks -> Maybe Char
headLetter = listToMaybe . filter (/= '*') . map head . Map.elems

-- | Empty list-safe head
head' :: [a] -> [a]
head' [] = []
head' (x:_) = [x]

-- | Empty list-safe tail
tail' :: [a] -> [a]
tail' [] = []
tail' (_:xs) = xs
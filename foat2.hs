import Data.Maybe
import qualified Data.Map as Map

data Rel = Dep | Indep deriving (Show, Eq)
type Stacks = Map.Map Char [Char]

alphabet = "abcdef"

rel x y 
  | x == y  = Dep
  | not (elem x alphabet && elem y alphabet) = error ("Unknown relation for letters "  ++ [x] ++ " " ++ [y])
rel 'a' 'b' = Dep
rel 'a' 'c' = Dep
rel 'a' 'd' = Indep
rel 'a' 'e' = Dep
rel 'a' 'f' = Dep
rel 'b' 'c' = Dep
rel 'b' 'd' = Dep
rel 'b' 'e' = Indep
rel 'b' 'f' = Dep
rel 'c' 'd' = Indep
rel 'c' 'e' = Dep
rel 'c' 'f' = Indep
rel 'd' 'e' = Dep
rel 'd' 'f' = Dep
rel 'e' 'f' = Dep
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
  [] -> reverse results
  tops -> gather' (foldl (\ss l ->  Map.update (\(x:xs) -> Just xs) l ss) stacks dps) (filter (/= '*') tops : results)
        where dps = concat $ map (\x -> deps x alphabet) $ filter (/= '*') tops

-- | Get heads of all stacks
pop :: (Map.Map Char [Char]) -> [Char]
pop = concat . map head' . Map.elems

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

import Data.Maybe
import qualified Data.Map as Map

data Rel = Dep | Indep deriving (Show, Eq)

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
  
genFoat word = buildStacks' (word) 

buildStacks' :: [Char] -> (Map.Map Char [Char])
buildStacks' word = foldl (
    \stacks  l -> 
      foldl (\stacks' alph -> if l == alph 
          then Map.insertWith (++) l [l] stacks'
          else (if rel l alph == Dep then (Map.insertWith (++) alph "*" stacks')  else stacks')
        ) stacks  alphabet
  ) (initStacks word) (reverse word)

initStacks = foldl (\m l -> Map.insert l [] m) Map.empty

--result :: (Map.Map Char [Char]) -> [[Char]]
--result = result' . Map.elems


deps :: Char -> [Char] -> [Char]
deps l = filter $ (== Dep) . rel l

-- find letter on top of some stack
headLetter :: (Map.Map Char [Char]) -> Maybe Char
headLetter = listToMaybe . filter (/= '*') . map head . Map.elems
    --Map.foldr (\(top:_) prev -> if top == '*' then prev else Just top) Nothing

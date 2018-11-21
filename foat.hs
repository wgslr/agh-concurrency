data Rel = Dep | Indep deriving Show

rel x y | x == y  = Dep
rel 'a' 'b' = Dep
rel 'a' 'c' = Dep
rel 'a' 'd' = Indep
rel 'b' 'c' = Indep
rel 'b' 'd' = Dep
rel 'c' 'd' = Dep
rel x y =
  case rel y x of
    Indep -> Dep
    Dep -> Indep

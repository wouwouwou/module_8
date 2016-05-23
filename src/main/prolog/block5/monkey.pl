goal(state(_,_,_,has)).

init(state(door,floor,window,not)).

move(state(Pos,floor,Pos,Has),
     climb,
     state(Pos,box,Pos,Has)).
move(state(middle,box,middle,not),
     grasp,
     state(middle,box,middle,has)).
move(state(L1,floor,Pos,Has),
     walk(L1,L2),
     state(L2,floor,Pos,Has)).
move(state(L1,floor,L1,Has),
     push(L1,L2),
     state(L2,floor,L2,Has)).

solve(S) :- goal(S).

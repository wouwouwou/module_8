istree(t(nil, N, nil)) :- number(N), !.
istree(t(L,N,R)) :- istree(L), number(N), istree(R).

min(t(nil, A, _), N) :- number(A), N is A, !.
min(t(T, A, _), N) :- number(A), istree(T), min(T, N).

max(t(_, A, nil), N) :- number(A), N is A, !.
max(t(_, A, T), N) :- number(A), istree(T), max(T, N).

issorted(t(nil,N,nil)) :- !.
issorted(t(A, B, nil)) :- max(A, N).
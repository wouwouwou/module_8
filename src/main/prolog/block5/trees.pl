istree(t(nil, N, nil)) :- number(N), !.
istree(t(L,N,R)) :- istree(L), number(N), istree(R).

min(T, N) :-
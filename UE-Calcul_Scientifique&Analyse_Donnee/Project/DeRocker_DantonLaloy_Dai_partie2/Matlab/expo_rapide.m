function Ap = expo_rapide(A, p)

    Ap = eye(length(A));
    while (p > 1)
       if mod(p, 2)==1
           Ap = Ap * A;
       end
       p = floor(p/2);
       Ap = Ap * Ap;
    end
    Ap = Ap * A;
end
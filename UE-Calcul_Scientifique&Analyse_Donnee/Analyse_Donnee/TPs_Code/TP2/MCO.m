function Beta = MCO(x, y)
    n = length(x);
    B = [zeros(n,1); 1];
    
    C = [x.*x, x.*y, y.*y, x, y, ones(n, 1)];
    D = [1, 0, 1, 0, 0, 0];
    
    A = [C; D];
    
    Beta = pinv(A)*B;
%--------------------------------------------------------------------------
% ENSEEIHT - 1SN - Calcul scientifique
% TP1 - Orthogonalisation de Gram-Schmidt
% mgs.m
%--------------------------------------------------------------------------

function Q = mgs(A)

    % Recuperation du nombre de colonnes de A
    [~, m] = size(A);
    
    % Initialisation de la matrice Q avec la matrice A
    Q = A;
    
    for i = 1 : m
        for j = 1:i-1
            ps = Q(:,j)'*Q(:,i);
            Q(:,i) = Q(:,i) - ps*Q(:,j);
        end
        Q(:,i) = Q(:,i)/norm(Q(:,i));
    end
end
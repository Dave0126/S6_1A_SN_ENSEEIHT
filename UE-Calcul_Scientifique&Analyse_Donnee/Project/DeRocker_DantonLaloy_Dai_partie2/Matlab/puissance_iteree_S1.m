clear variables;clc
% tolerance relative minimum pour l'ecart entre deux iteration successives 
% de la suite tendant vers la valeur propre dominante 
% (si |lambda-lambda_old|/|lambda_old|<eps, l'algo a converge)
eps = 1e-8;
% nombre d iterations max pour atteindre la convergence 
% (si i > kmax, l'algo finit)
imax = 5000; 

% Generation d une matrice rectangulaire aleatoire A de taille n x p.
% On cherche le vecteur propre et la valeur propre dominants de AA^T puis
% de A^TA
n = 1500; p = 500;
A = 5*randn(n,p);

% AAt, AtA sont deux matrices carrees de tailles respectives (n x n) et 
% (p x p). Elles sont appelees "equations normales" de la matrice A
AAt = A*A'; AtA = A'*A;


%% Methode de la puissance iteree pour la matrice AtA de taille pxp
% Point de depart de l'algorithme de la puissance iteree : un vecteur
% aleatoire, normalise
v = ones(p,16); v = v/norm(v);

cv = false;
iv2 = 0;  % pour compter le nombre d iterations effectuees
t_v2 =  cputime; % pour calculer le temps d execution de l'algo
err2 = 0; % residu de la norme du vecteur propre. On sort de la boucle 
          % quand err2 <eps
disp('** A COMPLETER ** CONSIGNES EN COMMENTAIRE **')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CODER L ALGORITHME DE LA PUISSANCE ITEREE TEL QUE DONNE DANS L'ENONCE
% POUR LA MATRICE AtA
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Initialisation
lambdav2 = v' * AtA * v;
while(~cv)
   %iv2 = iv2 + 1;
   %y = AtA * V;
   %y = y/norm(y);
   %H = V' * y;
   %err2 = (norm(y - V * H) / norm(AtA));
   %cv = (err2 <= eps) || (iv2 >= imax);
   %V = mgs(y);
   
   y = AtA * v;
   v = y/norm(y);
   muv2 = lambdav2;
   lambdav2 = v' * AtA * v;
   iv2 = iv2 + 1;
   err2 = (norm(lambdav2 - muv2) / norm(muv2));
   cv = (err2 <= eps) || (iv2 >= imax);
    % /!\ Ce break sert a eviter que vous rentriez dans une boucle infini
    % si vous lancez ce script avant de l'avoir modifier. Pensez a le
    % supprimer quand vous coderez la puissance iteree
end
t_v2 = cputime-t_v2;


disp('** A COMPLETER ** CONSIGNES EN COMMENTAIRE **')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% APRES VOUS ETRE ASSURE DE LA CONVERGENCE DES DEUX METHODES, AFFICHER 
% L'ECART RELATIF ENTRE LES DEUX VALEURS PROPRES TROUVEES, ET LE TEMPS
% MOYEN PAR ITERATION POUR CHACUNE DES DEUX METHODES. 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('Erreur relative pour la methode avec la petite matrice = %0.3e\n',err2);
fprintf('Temps pour une ite avec la petite matrice = %0.3e\n',t_v2/iv2);

lambdav2(1,1)

D = eig(AtA);

fprintf('Valeur propre dominante (fonction eig) = %0.3e\n',D(end));

%--------------------------------------------------------------------------
% ENSEEIHT - 1SN - Analyse de donnees
% TP4 - Reconnaissance de chiffres manuscrits par k plus proches voisins
% fonction kppv.m
%
% Données :
% DataA      : les données d'apprentissage (connues)
% LabelA     : les labels des données d'apprentissage
%
% DataT      : les données de test (on veut trouver leur label)
% Nt_test    : nombre de données tests qu'on veut labelliser
%
% K          : le K de l'algorithme des k-plus-proches-voisins
% ListeClass : les classes possibles (== les labels possibles)
%
% Résultat :
% Partition : Indice de LabelA
%
%--------------------------------------------------------------------------
function [Partition, MatriceConfusion, TauxErreur] = kppv(DataA, labelA, DataT, labelT, Nt_test, K, ListeClass)

[Na,~] = size(DataA);

% Initialisation du vecteur d'étiquetage des images tests
Partition = zeros(Nt_test,1);

MatriceConfusion = zeros(length(ListeClass), length(ListeClass));

%disp(['Classification des images test dans ' num2str(length(ListeClass)) ' classes'])
%disp(['par la methode des ' num2str(K) ' plus proches voisins:'])

% Boucle sur les vecteurs test de l'ensemble de l'évaluation
for i = 1:Nt_test

    % Calcul des distances entre les vecteurs de test 
    % et les vecteurs d'apprentissage (voisins)
    % À COMPLÉTER
    dist = sum((DataA - repmat(DataT(i,:),Na, 1)).^2, 2);
    
    % On ne garde que les indices des K + proches voisins
    % À COMPLÉTER
    [~, dist_ind] = sort(dist);
    labelAtri = labelA(dist_ind);
    labelAtri = labelAtri(1:K);
    
    % Comptage du nombre de voisins appartenant à chaque classe
    % À COMPLÉTER
    compteur = zeros(1,length(labelA));
    for j = 1:K
        compteur(labelAtri(j)) = compteur(labelAtri(j)) + 1;
    end    
        
    % Recherche de la classe contenant le maximum de voisins
    % À COMPLÉTER
    maximum = max(compteur);
    indicesmax = find(compteur == maximum);
    
    % Si l'image test a le plus grand nombre de voisins dans plusieurs  
    % classes différentes, alors on lui assigne celle du voisin le + proche,
    % sinon on lui assigne l'unique classe contenant le plus de voisins 
    % À COMPLÉTER
    if length(indicesmax) == 1
        indicemax = indicesmax;
    else
        l = 1;
        while sum(indicesmax == labelAtri(l)) == 0
            l = l + 1;
        end
        indicemax = labelAtri(l);
    end        
        
    % Assignation de l'étiquette correspondant à la classe trouvée au point 
    % correspondant à la i-ème image test dans le vecteur "Partition" 
    % À COMPLÉTER
    Partition(i) = indicemax;
    MatriceConfusion(labelT(i), indicemax) = MatriceConfusion(labelT(i), indicemax) + 1;    
    
end
TauxErreur = 1 - sum(diag(MatriceConfusion))/Nt_test;
%MatriceConfusion = 0;
end


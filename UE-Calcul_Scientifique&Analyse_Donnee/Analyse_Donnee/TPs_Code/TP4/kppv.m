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
% Partition : pour les Nt_test données de test, le label calculé
%
%--------------------------------------------------------------------------
function [Partition] = kppv(DataA, labelA, DataT, Nt_test, K, ListeClass)

[Na,~] = size(DataA);
[Nt_test,~] = size(DataT);

% Initialisation du vecteur d'étiquetage des images tests
Partition = zeros(Nt_test,1);

disp(['Classification des images test dans ' num2str(length(ListeClass)) ' classes'])
disp(['par la methode des ' num2str(K) ' plus proches voisins:'])

% Boucle sur les vecteurs test de l'ensemble de l'évaluation
for i = 1:Nt_test
    
    disp(['image test n°' num2str(i)])

    %% Calcul des distances entre les vecteurs de test 
    % et les vecteurs d'apprentissage (voisins)
    % À COMPLÉTER
    distances = vecnorm((((ones(Na, 1) * DataT(i, :)) - DataT)));
    %% On ne garde que les indices des K + proches voisins
    % À COMPLÉTER
    [d, indppv] = sort (distances);
    % [~, indices] = sort(distances);
    indkppv = indppv(1 : k);
    % voisins = indppv(1: k);
    %% Comptage du nombre de voisins appartenant à chaque classe
    % À COMPLÉTER
    l = labelA(indkppv);
    % label = labelA(voisins);
    % comptage = histcounts(label, ListenClass);
    nbclasses = length(ListenClass);
    nbocc = zeros(nbclasses, 1);
    for j=1:nbclasses
        nbocc(j) = length(find(l == ListenClass(j)));
    end
    nbocc = histcounts(l, ListenClass);
    %% Recherche de la classe contenant le maximum de voisins
    % À COMPLÉTER
    m = max(nbocc);
    indices = find(nbocc == 1);
    % [valeurMax, indiceClass] = max(comptage);
    
    %% Si l'image test a le plus grand nombre de voisins dans plusieurs  
    % classes différentes, alors on lui assigne celle du voisin le + proche,
    % sinon on lui assigne l'unique classe contenant le plus de voisins 
    % À COMPLÉTER
    if(length(indices) == 1)
        result = ListenClass(indices);
    else
        result = labelA(indkppv(1));
    end
    
    
    
    %% Assignation de l'étiquette correspondant à la classe trouvée au point 
    % correspondant à la i-ème image test dans le vecteur "Partition" 
    % À COMPLÉTER
    
    Partition(i) = result;
end
end


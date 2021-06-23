clear;
close all;

load eigenfaces;



MatricePosition = zeros(6,4);

for i = 1:nb_personnes
    for j = 1:nb_postures
        % Tirage aleatoire d'une image de test :
        personne = i;
        posture = j;
        % si on veut tester/mettre au point, on fixe l'individu
        %individu = 10
        %posture = 6

        % Dimensions du masque
        ligne_min = 200;
        ligne_max = 350;
        colonne_min = 60;
        colonne_max = 290;

        ficF = strcat('./Data/', liste_personnes{personne}, liste_postures{posture}, '-300x400.gif');
        img = imread(ficF);
        img(ligne_min:ligne_max,colonne_min:colonne_max) = 0;
        image_test = double(transpose(img(:)));


        % Pourcentage d'information 
        per = 0.95;

        % Nombre N de composantes principales a prendre en compte 
        % [dans un second temps, N peut etre calcule pour atteindre le pourcentage
        % d'information avec N valeurs propres (contraste)] :
        N = 8;

        % Détermination de l'individu le plus proche avec l'algorithme des k plus
        % proches voisins
        nbClasse = 32;
        ListeClass = 1:nbClasse;
        K = 3;

        Label_ind = 1:nb_personnes_base*nb_postures_base;

        [partition, ~, ~] = kppv(X_masque, Label_ind, image_test, personne, 1, K, ListeClass);
        partition = partition - 1;


        % pour l'affichage (A CHANGER)
        personne_proche = floor(partition/4) + 1;;
        posture_proche = mod(partition, 4) + 1;
        MatricePosition(posture,posture_proche) = MatricePosition(posture,posture_proche) + 1;
    end
end

MatricePosition
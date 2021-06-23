figure
sgtitle("Courbes de distributions des valeurs propres")

subplot(2, 2, 1);

load(['A_' num2str(500) '_' num2str(1)]);

xlabel('indice de la valeur propre')
ylabel('valeur propre');
plot(1:500, D);
title("Matrice de type 1")

subplot(2, 2, 2);
load(['A_' num2str(500) '_' num2str(2)]);

xlabel('indice de la valeur propre')
ylabel('valeur propre');
plot(1:500, D);
title("Matrice de type 2")

subplot(2, 2, 3);
load(['A_' num2str(500) '_' num2str(3)]);

xlabel('indice de la valeur propre')
ylabel('valeur propre');
plot(1:500, D);
title("Matrice de type 3")

subplot(2, 2, 4);
load(['A_' num2str(500) '_' num2str(4)]);

xlabel('indice de la valeur propre')
ylabel('valeur propre');
plot(1:500, D);
title("Matrice de type 4")
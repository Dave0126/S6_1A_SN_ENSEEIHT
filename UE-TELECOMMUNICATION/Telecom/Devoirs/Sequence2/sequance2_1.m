close all;
clear all;
nb_bits = 1000;
bits = randi([0,1],1,nb_bits);
Fe = 24000;
Rb = 3000;

Symboles = 2 * bits -1;
Ns = 8;
Suite_diracs = kron(Symboles, [1 zeros(1,Ns-1)]);

%% Filtre mise en forme
h1 = ones(1, Ns);
h2 = rcosdesign(0.5,8,Ns);
x1 = filter(h1,1,Suite_diracs);
x2 = filter(h2,1,Suite_diracs);

%% Filtre canal
hc1 = [1, zeros(1, Ns-1)];
hc2 = [1, zeros(1, Ns-1)];
xc1 = filter(hc1, 1, x1);
xc2 = filter(hc2, 1, x2);

%% Filtre receception
xr1 = filter(h1, 1, xc1);
xr2 = filter(h2, 1, xc2);

%% Convolution
g1 = conv(h1, h1);
g2 = conv(h2, h2);
subplot(211);
plot(g1);
legend("g1");
subplot(212);
plot(g2);
legend("g2");

%% Tracez le diagramme de l'oeil en sortie du filtre de reception
figure("Name", "le diagramme de l'oeil");
subplot(211);
plot(reshape(xr1, [Ns, nb_bits]));
title("Le diagramme de l'oeil de canal 1");
hold on;
subplot(212);
plot(reshape(xr2, [Ns, nb_bits]));
title("Le diagramme de l'oeil de canal 2");
hold on;

%% Echantillonnage
X1ech = xr1(8: Ns: end);
X2ech = xr2(1: Ns: end);

%% Decision+Demapping
X1final = (sign(X1ech)+1)/2;
X2final = (sign(X2ech)+1)/2;

%% Taux Erreur Binaire
t1 = mean(bits ~= X1final);
t2 = mean(bits ~= X2final);



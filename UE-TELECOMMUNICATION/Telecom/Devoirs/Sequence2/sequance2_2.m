close all;
clear all;
nb_bits = 1000;
bits = randi([0,1],1,nb_bits);
Fe = 24000;
Te = 1/Fe;
Rb = 3000;
%BW = 1000;
BW = 4000;

Symboles = 2 * bits -1;
Ns = 8;
Suite_diracs = kron(Symboles, [1 zeros(1,Ns-1)]);

%% Filtre mise en forme
h1 = ones(1, Ns);
h2 = rcosdesign(0.5,8,Ns);
x1 = filter(h1,1,Suite_diracs);
x2 = filter(h2,1,Suite_diracs);

%% Filtre canal
fc = BW;
N = 10*Ns;
hc = (2*fc/Fe)*sinc(2*fc*[-(N-1)*Te/2: Te:(N-1)*Te/2]);
xc1 = filter(hc, 1, x1);
xc2 = filter(hc, 1, x2);

%% Filtre reception
xr1 = filter(h1, 1, xc1);
xr2 = filter(h2, 1, xc2);

%% Les figures de |H|
H1 = fft([h1 zeros(1, 1024-length(h1))]);
H2 = fft([h2 zeros(1, 1024-length(h2))]);
HC1 = fft([hc zeros(1, 1024-length(hc))]);
HC2 = fft([hc zeros(1, 1024-length(hc))]);
HR1 = fft([h1 zeros(1, 1024-length(h1))]);
HR2 = fft([h1 zeros(1, 1024-length(h1))]);

figure();
subplot(311);
plot(linspace(-Fe/2, Fe/2, length(H1)), fftshift(abs(H1.*HR1)));
title("Canal1: |H(f) * Hr(f)|");
subplot(312);
plot(linspace(-Fe/2, Fe/2, length(HC1)), fftshift(abs(HC1)));
title("Canal1: |Hc(f)|");
subplot(313);
hold on;
title("Canal1: Reponse enfrequance");
plot(linspace(-Fe/2, Fe/2, length(H1)), 20*log10(fftshift(abs(H1.*HR1))));
plot(linspace(-Fe/2, Fe/2, length(HC1)), 20*log10(fftshift(abs(HC1))));
legend("|H(f) * Hr(f)|", "|Hc(f)|");
hold off;

figure();
subplot(311);
plot(linspace(-Fe/2, Fe/2, length(H2)), fftshift(abs(H2.*HR2)));
title("Canal2: |H(f) * Hr(f)|");
subplot(312);
plot(linspace(-Fe/2, Fe/2, length(HC2)), fftshift(abs(HC2)));
title("Canal2: |Hc(f)|");
subplot(313);
hold on;
title("Canal2: Reponse enfrequance");
plot(linspace(-Fe/2, Fe/2, length(H2)), 20*log10(fftshift(abs(H2.*HR2))));
plot(linspace(-Fe/2, Fe/2, length(HC2)), 20*log10(fftshift(abs(HC2))));
legend("|H(f) * Hr(f)|", "|Hc(f)|");
hold off;

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
X1ech = xr1(7: Ns: end);
% 1000Hz : n0=3
X2ech = xr2(1: Ns: end);
% 1000Hz : n0=4

%% Decision+Demapping
X1final = (sign(X1ech)+1)/2;
X2final = (sign(X2ech)+1)/2;

%% Taux Erreur Binaire
t1 = mean(bits ~= X1final);
t2 = mean(bits ~= X2final);



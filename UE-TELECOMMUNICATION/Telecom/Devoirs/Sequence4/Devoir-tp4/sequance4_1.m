%% --------------------------------------------------------
% Partie 3: Utilisation de la chaine passe-bas équivalente pour le calcul et l’estimation du taux d’erreur binaire
% Author : Guohao DAI / Groupe E / 1A / SN
%% --------------------------------------------------------
close all;
clear all;
nb_bits = 25000;
bits = randi([0,1],1,nb_bits);
Fe = 10000;
Rb = 2000;
Tb = 1/Rb;
Rs = Rb;
M = 4;
Ts = log2(M) / Rb;
Te = 1/Fe;
Ns = Fe*Ts;
Fp = 2000;
ALPHA = 0.35;
SPAN = 8;

%% Mapping
Symboles_Ak = 2 * bits(1:2:end) - 1;
Symboles_Bk = 2 * bits(2:2:end) - 1;
Symboles = Symboles_Ak + 1i*Symboles_Bk;

%% Diracs
Suite_diracs = kron(Symboles, [1 zeros(1,Ns-1)]);
% Suite_diracs_Ak = kron(Symboles_Ak, [1 zeros(1,Ns-1)]);
% Suite_diracs_Bk = kron(Symboles_Bk, [1 zeros(1,Ns-1)]);

%% Filtre mise en forme
h1 = rcosdesign(ALPHA,SPAN,Ns);
retard = SPAN * Ns / 2;
Xe = filter(h1,1,[Suite_diracs,zeros(1,retard)]);
Xe = Xe(retard+1 : end);
% x1_Ak = filter(h1,1,Suite_diracs_Ak);
% x1_Bk = filter(h1,1,Suite_diracs_Bk);

%% --3.1.1 Tracer les signaux générés sur les voies en phase et en quadrature ainsi que le signal transmis sur fréquence porteuse.
figure("Name", "Generation de signal Xe");
% les voies en phase
% Print the real part of Xe
subplot(2,1,1);
plot(real(Xe));
axis([0 length(Xe) -1 1]);
title('Generation de signal Xe: Real Part');
% les voies en quadrature
% Print the imag part of Xe
subplot(2,1,2);
plot(imag(Xe));
axis([0 length(Xe) -1 1]);
title('Generation de signal Xe: Imag Part');
% saveas(gcf,'Generation_Xe_frequence_imag', 'png');

% Transposition de frequence
t = (0:length(Xe)-1) * Te;
X = real(Xe.*exp(2 * 1i * pi * Fp * t));
figure("Name", "Transposition de signal X");
plot(X);
axis([0 length(Xe) -1 1]);
title("Transposition de signal X (Real Number)");
% saveas(gcf,'Transposition_X_frequence', 'png');

%% --3.1.2 Estimer et tracer la densité spectrale de puissance du signal modulé sur fréquence porteuse.
% DSP
figure("Name", "Le Figure de DSP de signal modulé sur fréquence porteuse");
DSP_theorique = (1 / length(X)) * abs(fft(X, 2^nextpow2(length(X)))).^2;
semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(X))),fftshift(DSP_theorique));
title('DSP sur fréquence porteuse');
% saveas(gcf,'DSP_frequence', 'png');

%% --3.1.3 Implanter la chaine complète sans bruit afin de vérifier que le TEB obtenu est bien nul.
% Retour en band de base
X1_retour = X.* cos(2*pi*Fp*t);
X2_retour = X.* sin(2*pi*Fp*t);

% Filtre de Passe-bas
h_passe_bas = ones(1,3);
X1_passe_bas = filter(h_passe_bas, 1, X1_retour);
X2_passe_bas = filter(h_passe_bas, 1, X2_retour);
X_passe_bas = X1_passe_bas -1i*X2_passe_bas;

% Demodulation de bande de base
% Filtre receception
hr = h1;
X_reception = filter(hr, 1, [X_passe_bas,zeros(1,retard)]);
X_reception = X_reception(retard+1 : end);

% Echantillonnage
X_ech = X_reception(1:Ns:end);

% Decision+Demapping
X_final = zeros(1,nb_bits);
X_final(1:2:end) = (sign(real(X_ech))+1)/2;
X_final(2:2:end) = (sign(imag(X_ech))+1)/2; 

% TEB sans bruit
NB_err = length(find(bits ~= X_final));
TEB_sans_bruit = NB_err/length(bits);
fprintf("3.1.3 Le TEB de la chaine sur fréquence porteuse sans bruit est %f \n", TEB_sans_bruit);

%% --3.1.4 & 3.1.5 Rajouter le bruit et tracer le TEB 
% TEB avec bruit
Px = mean(abs(X).^2);
EbSurN0 = zeros(1,9);
NB_err_bruit = zeros(1,9);
TEB_bruit = zeros(1,9);
for i = 0:8
    EbSurN0(i+1) = 10^(i / 10);
    sigma = Px * Ns / (2*log2(M) * EbSurN0(i+1));
    bruit_real = sqrt(sigma) * randn(1,length(real(X)));
    bruit_imag = sqrt(sigma) * randn(1,length(imag(X)));
    X_bruit = X + bruit_real + 1i*bruit_imag;
    % Retour en band de base
    X1_retour_bruit = X_bruit.* cos(2*pi*Fp*t);
    X2_retour_bruit = X_bruit.* sin(2*pi*Fp*t);
    % Filtre de Passe-bas
    X1_passe_bas_bruit = filter(h_passe_bas, 1, X1_retour_bruit);
    X2_passe_bas_bruit = filter(h_passe_bas, 1, X2_retour_bruit);
    X_passe_bas_bruit = X1_passe_bas_bruit -1i*X2_passe_bas_bruit;
    % Filtre receception
    hr = h1;
    X_reception_bruit = filter(hr, 1, [X_passe_bas_bruit,zeros(1,retard)]);
    X_reception_bruit = X_reception_bruit(retard+1 : end);
    % Echantillonnage
    X_ech_bruit = X_reception_bruit(1:Ns:end);
    % Decision+Demapping
    X_final_bruit = zeros(1,nb_bits);
    X_final_bruit(1:2:end) = (sign(real(X_ech_bruit))+1)/2;
    X_final_bruit(2:2:end) = (sign(imag(X_ech_bruit))+1)/2; 
    % TEB
    NB_err_bruit(i+1) = length(find(bits ~= X_final_bruit));
    TEB_bruit(i+1)= NB_err_bruit(i+1)/length(bits);
    %figure ;
    %plot(reshape(X_reception_bruit, [Ns, nb_bits]));
    %name = strcat("Le diagramme de l'oeil de Eb/N_0 =",num2str(i),"dB");
    %title(name);
    
end
figure("Name","Les TEB de la chaine sur fréquence porteuse avec bruit");
semilogy(0:8,TEB_bruit,'x-');
hold on
semilogy(0:8,qfunc(sqrt(2*EbSurN0)));
hold on
title("Signal avec bruit: Le diagramme de TEB simule et TEB theorique");
xlabel("Eb/N0 (dB)");
ylabel("TEB");
legend("TEB-sim", "TEB-the");
% saveas(gcf,'TEB_frenquence_bruit', 'png');



%% ------------- 3.2 ---------------------
%% --3.2.1 Tracer les signaux générés sur les voies en phase et en quadrature
figure("Name", "Generation de signal Xe");
% les voies en phase
% Print the real part of Xe
subplot(2,1,1);
plot(real(Xe));
axis([0 length(Xe) -1 1]);
title('Generation de signal Xe: Real Part');
% les voies en quadrature
% Print the imag part of Xe
subplot(2,1,2);
plot(imag(Xe));
axis([0 length(Xe) -1 1]);
title('Generation de signal Xe: Imag Part');
% saveas(gcf,'Generation_Xe_passe_bas_imag', 'png');


%% --3.2.2 Estimer puis tracer la densité spectrale de puissance de l’enveloppe complexe associée au signal modulé sur fréquence porteuse.
% DSP de Xe
figure("Name", "Le Figure de DSP de signal Xe");
DSP_Xe_theorique = (1 / length(Xe)) * abs(fft(Xe, 2^nextpow2(length(Xe)))).^2;
semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(Xe))),fftshift(DSP_Xe_theorique));
title('DSP-Xe-Theorique');
% saveas(gcf,'DSP_passe_bas', 'png');
% Compare two DSPs
figure("Name","Comparaison de 2 DSPs");
semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(X))),fftshift(DSP_theorique));
hold on;
semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(Xe))),fftshift(DSP_Xe_theorique));
hold on;
title("Comparaison entre les deux DSPs des deux parties");
xlabel("Fréquences normalisées");
ylabel("Densité spectrale de puissance");
legend("Chaine sur fréquence porteuse","Chaine passe-bas équivalente");
% saveas(gcf,'DSPs_Comparaison', 'png');


%% --3.2.3 Implanter la chaine complète sans bruit afin de vérifier que le TEB obtenu est bien nul.

% Filtre receception
hr = h1;
X_reception2 = filter(hr,1,[Xe zeros(1,retard)]);
X_reception2 = X_reception2(retard+1 : end);

% Echantillonnage
X_ech2 = X_reception2(1:Ns:end);

% Decision+Demapping
X_final2 = zeros(1,nb_bits);
X_final2(1:2:end) = (sign(real(X_ech2))+1)/2;
X_final2(2:2:end) = (sign(imag(X_ech2))+1)/2; 

% TEB sans bruit
NB_err2 = length(find(bits ~= X_final2));
TEB_sans_bruit2 = NB_err2/length(bits);
fprintf("3.2.3 Le TEB de la chaine passe-bas équivalente sans bruit est %f \n", TEB_sans_bruit2);


%% --3.2.3 Rajouter le bruit et tracer le taux d’erreur binaire obtenu en fonction du rapport signal à bruit par bit à l’entrée du récepteur (E b /N 0 ) en décibels. On prendra des valeurs de (E b /N 0 ) dB allant de 0 à 8 dB.
% TEB avec bruit
Px2 = mean(abs(Xe).^2);
NB_err2_bruit = zeros(1,9);
TEB_bruit2 = zeros(1,9);
for i = 0:8
    sigma2 = Px * Ns / (2*log2(M) * EbSurN0(i+1));
    bruit_real2 = sqrt(sigma2) * randn(1,length(real(Xe)));
    bruit_imag2 = sqrt(sigma2) * randn(1,length(imag(Xe)));
    X_bruit2 = Xe + bruit_real2 + 1i*bruit_imag2;
    % Filtre receception
    hr = h1;
    X_reception_bruit2 = filter(hr,1,[X_bruit2 zeros(1,retard)]);
    X_reception_bruit2 = X_reception_bruit2(retard+1 : end);
    % Echantillonnage
    X_ech_bruit2 = X_reception_bruit2(1:Ns:end);
    % Decision+Demapping
    X_final_bruit2 = zeros(1,nb_bits);
    X_final_bruit2(1:2:end) = (sign(real(X_ech_bruit2))+1)/2;
    X_final_bruit2(2:2:end) = (sign(imag(X_ech_bruit2))+1)/2; 

    % TEB
    NB_err2_bruit(i+1) = length(find(bits ~= X_final_bruit2));
    TEB_bruit2 (i+1)= NB_err2_bruit(i+1)/length(bits);
    %figure ;
    %plot(reshape(X_reception_bruit, [Ns, nb_bits]));
    %name = strcat("Le diagramme de l'oeil de Eb/N_0 =",num2str(i),"dB");
    %title(name);
    
    % Les constellations en sortie de l’échantillonneur pour chaque Eb/N0 .
    scatterplot(X_ech_bruit2);
    name = strcat("Constellations en sortie d'éch. | Eb/N_0 = ",num2str(i),"dB");
    title(name);
    xlim([-2 2]);
    ylim([-2 2]);
    filename = strcat("Constellation_ech_EbSurN0_",num2str(i));
    % saveas(gcf,filename, 'png');
    
end
figure("Name","Les TEB de la chaine passe-bas équivalente avec bruit");
semilogy(0:8,TEB_bruit2,'x-');
title("Taux d'erreur binaire de la chaine passe-bas équivalente");
xlabel("Eb/N0 (dB)");
ylabel("TEB");
% saveas(gcf,'TEB_passe_bas_bruit', 'png');

%% --3.2.5 Tracer les constellations en sortie du mapping et en sortie de l’échantillonneur pour une valeur donnée de Eb/N0.
% Les constellations en sortie du mapping.
scatterplot(Symboles);
title("Les constellations en sortie du mapping");
xlim([-2 2]);
ylim([-2 2]);
% saveas(gcf,'Constellation_mapping', 'png');
% Les constellations en sortie de l’échantillonneur pour Eb/N0 = 8.
scatterplot(X_ech_bruit2);
title("Les constellations en sortie de l'échantillonneur pour Eb/N0 = 8");
xlim([-2 2]);
ylim([-2 2]);
% saveas(gcf,'Constellation_ech', 'png');

%% --3.2.6 Vérifier que l’on obtient bien le même TEB que celui obtenu avec la chaine simulée sur fréquence porteuse.
figure("Name","Comparaison les TEBs pour les deux chaines");
semilogy(0:8,TEB_bruit,'v-');
hold on;
semilogy(0:8,TEB_bruit2,'x-');
hold on;
title("Taux d'erreur binaire pour les deux chaines");
xlabel("Eb/N0 (en dB)");
ylabel("TEB");
legend("Chaine sur fréquence porteuse","Chaine passe-bas équivalente");
% saveas(gcf,'TEB_Comparaison', 'png');























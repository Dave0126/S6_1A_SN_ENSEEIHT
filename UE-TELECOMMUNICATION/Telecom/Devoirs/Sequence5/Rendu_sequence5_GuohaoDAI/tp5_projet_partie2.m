%% --------------------------------------------------------
% TP5 - Projet - Impact d’un canal de propagation sélectif en fréquence et introduction à l’égalisation
% Partie 2: Egalisation ZFE avec bruit
% Author : Guohao DAI / Groupe E / 1A / SN
%% --------------------------------------------------------
close all;
clear all;
% Nb de bits
nb_bits = 100;
% Creer les bits par random
bits = randi([0,1],1,nb_bits);
% Frequence d'echantillonnage
Fe = 24000;
% Debit binaire
Rb = 6000;
% Temps de chaque binaire
Tb = 1/Rb;
% Order de la modulation
M = 2;
% Periode de chaque symbole
Ts = log2(M) / Rb;
% Periode d'echantillonnage
Te = 1/Fe;
Ns = Fe/Rb;


% Mapping a moyenne null & Suechantillonnage
Symboles = 2 * bits -1;
Suite_diracs = kron(Symboles, [1 zeros(1,Ns-1)]);

% Filtre de mise en forme
h = ones(1, Ns);
% h = [ones(1, Ns/2), -ones(1,Ns/2)];
Signal_send = filter(h,1,Suite_diracs);

% Filtre de canal
hc = kron([1, 0.5], [1 zeros(1, Ns-1)]);
Signal_canal = filter(hc,1,Signal_send);

%%
Px = mean(abs(Signal_canal).^2);
EbSurN0 = zeros(1,11);
TEB = zeros(1,11);

for i = 0:10
    
   EbSurN0(i+1) = 10^(i / 10);
    sigma = Px * Ns / (2*log2(M) * EbSurN0(i+1));
    % bruit = sqrt(sigma) * randn(1,length(Signal_canal));
    bruit = 0;
    
    % Ajouter les bruits
    Signal_bruit = Signal_canal + bruit;
    
    % Filtre reception
    hr = fliplr(h);
    Signal_rec = filter(hr, 1, Signal_bruit);
    
    % Echantillonnage
    Signal_ech = Signal_rec(Ns: Ns: end);
    
    X_toeplitz = toeplitz(Signal_ech, [Signal_ech(1) zeros(1,length(Signal_ech)-1)]);
    C = ((X_toeplitz)^(-1)) * [1 zeros(1,size(X_toeplitz,2)-1)].';
    
    
    Signal_egalise = filter(C.', 1, Signal_ech);

    
    % Decision+Demapping
    Signal_final = (sign(Signal_egalise)+1)/2 ;
    
    % TEB
    TEB(i+1) = mean(Signal_final ~= bits);
    
%     % Les constellations en sortie du mapping.
%     scatterplot(Signal_ech);
%     name = strcat("Constellations en sortie d'éch. | Eb/N_0 = ",num2str(i),"dB");
%     title(name);
    
%     figure ;
%     plot(reshape(Signal_rec, [Ns, nb_bits]));
%     name = strcat("Le diagramme de l'oeil de Eb/N_0 =",num2str(i),"dB");
%     title(name);
end
figure;
    plot(Signal_rec);
%% TEB
 figure;
 % TEB simule
 semilogy(0:10,TEB,"v-");
 hold on
 % TEB theorique sans canal multitrajet
 semilogy(0:10,qfunc(sqrt(2*EbSurN0)),"x-");
 hold on
 % TEB theorique avec canal multitrajet
 semilogy(0:10,0.5*qfunc(sqrt((0.4)*EbSurN0)) + 0.5*qfunc(1.5*sqrt((1.6)*EbSurN0)),"o-");
 hold on
 xlabel("E_b/N_0 (dB)");
 ylabel("TEB");
 title("Chaine 2: Le diagramme de TEB simule et TEB theorique");
 legend("TEB-sim", "TEB-the", "TEB-the-canal");

close all;
clear all;
nb_bits = 6000;
bits = randi([0,1],1,nb_bits);
Fe = 24000;
Rb = 6000;

Symboles = 2 * bits -1;
Ns = 4;
M = 2;
Suite_diracs = kron(Symboles, [1 zeros(1,Ns-1)]);

%% Filtre mise en forme
h1 = ones(1, Ns);
%h2 = rcosdesign(0.5,8,Ns);
x1 = filter(h1,1,Suite_diracs);
%x2 = filter(h2,1,Suite_diracs);

%% Filtre canal
hc1 = [1, zeros(1, Ns-1)];
%hc2 = [1, zeros(1, Ns-1)];
xc1 = filter(hc1, 1, x1);
%xc2 = filter(hc2, 1, x2);
%xr2 = filter(h2, 1, xc2);

%%
Px = mean(abs(x1).^2);
EbSurN0 = zeros(1,9);
TEB = zeros(1,9);

for i = 0:8
    
    EbSurN0(i+1) = 10^(i / 10);
    sigma = Px * Ns / (2*log2(M) * EbSurN0(i+1));
    bruit = sqrt(sigma) * randn(1,length(x1));
    % bruit = 0;
    signal_bruit = x1 + bruit;
    % Filtre receception
    xr1 = filter(h1, 1, signal_bruit);
    % Echantillonnage
    X1ech = xr1(4: Ns: end);
    % Decision+Demapping
    X1final = (sign(X1ech) + 1)/2;
    % TEB
    TEB(i+1) = mean(X1final ~= bits);
    figure ;
    plot(reshape(xr1, [Ns, nb_bits]));
    name = strcat("Le diagramme de l'oeil de Eb/N_0 =",num2str(i),"dB");
    title(name);
    
end
%% TEB
 figure;
 semilogy(0:8,TEB);
 hold on
 semilogy(0:8,Qfunc(sqrt(2*EbSurN0)));
 hold on
 title("Chaine 1: Le diagramme de TEB simule et TEB theorique");
 legend("TEB-sim", "TEB-the");
close all;
clear all;
nb_bits = 10000;
bits = randi([0,1],1,nb_bits);
Fe = 24000;
Rb = 6000;
Tb = 1/Rb;
Rs = Rb;
Ts = 1/Rs;
Te = 1/Fe;

Symboles = (2 * bi2de(reshape(bits,2,length(bits)/2).')-3).';
Ns = Fe*Ts;
M = 4;
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
    X1Dec = zeros(size(X1ech));
    X1Dec(find(X1ech < -8)) = -3;
    X1Dec(find(X1ech > -8 & X1ech <= 0)) = -1;
    X1Dec(find(X1ech > 0 & X1ech <= 8)) = 1;
    X1Dec(find(X1ech > 8)) = 3;
    X1final = reshape(de2bi((X1Dec + 3)/2).',1,length(bits));
    % TEB
    TEB(i+1) = mean(X1final ~= bits);
    figure ;
    plot(reshape(xr1, [Ns, nb_bits/2]));
    name = strcat("Le diagramme de l'oeil de Eb/N_0 =",num2str(i),"dB");
    title(name);
    
end
%% TES
 TES = 1 - normcdf(sqrt(2*EbSurN0));
 figure;
 semilogy(0:8,TES);
 hold on
 semilogy(0:8,Qfunc(3/2*sqrt(4/5*EbSurN0)));
 hold on
 title("Chaine 2: Le diagramme de TES simule et TES theorique");
 legend("TES-sim", "TES-the");
 figure;
 %% TEB
 semilogy(0:8,TEB);
 hold on
 semilogy(0:8,Qfunc(3/4*sqrt(4/5*EbSurN0)));
 hold on
 title("Chaine 2: Le diagramme de TEB simule et TEB theorique");
 legend("TEB-sim", "TEB-the");
 
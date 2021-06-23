%% --------------------------------------------------------
% Partie 4: Comparaison de modulations sur fréquence porteuse
% Author : Guohao DAI / Groupe E / 1A / SN
%% --------------------------------------------------------
close all;
clear all;
nb_bits = 120000;
bits = randi([0,1],1,nb_bits);
Fe = 12000;
Rb = 6000;
Tb = 1/Rb;
Rs = Rb;
M = 4;
Ts = log2(M) / Rb;
Te = 1/Fe;
Ns = 4 ;
Fp = 2000;
ALPHA = 0.35;
SPAN = 4;

%% --4.1.1 Implanter la chaine complète sans bruit, avec une fréquence d’échantillonnage F e = 12 kHz, afin de vérifier que le TEB obtenu est bien nul.
% La chaine 4-ASK sans bruit
    M_ASK = 4;
    n_ASK = log2(M_ASK);
    % Mapping 
    Symboles_4ASK = (2 * bi2de(reshape(bits,n_ASK,length(bits)/n_ASK).')-3).';
    % Diracs
    Suite_diracs_4ASK = kron(Symboles_4ASK, [1 zeros(1,Ns-1)]);
    % Filtre mise en forme
    h1 = rcosdesign(ALPHA,SPAN,Ns,'sqrt');
    retard = SPAN * Ns / 2;
    Xe_4ASK = filter(h1,1,[Suite_diracs_4ASK,zeros(1,retard)]);
    Xe_4ASK = Xe_4ASK(retard+1 : end);
    % Filtre de réception
    hr = h1;
    Xr_4ASK = filter(hr,1,[Xe_4ASK zeros(1,retard)]);
    Xr_4ASK = Xr_4ASK(retard+1 : end);

    % Echantillonnage
    X_ech_4ASK = Xr_4ASK(1:Ns:end);
    
    % Decision + Demapping
    X_dec_4ASK = zeros(1,length(X_ech_4ASK));
    for i=1:length(X_dec_4ASK)
        if X_ech_4ASK(i) <= -2
            X_dec_4ASK(i)= -3;
        end
        if (-2 < X_ech_4ASK(i)) & (X_ech_4ASK(i) <= 0)
            X_dec_4ASK(i) = -1;
        end
        if (0 < X_ech_4ASK(i)) & (X_ech_4ASK(i) <= 2)
            X_dec_4ASK(i) = 1;
        end
        if X_ech_4ASK(i) > 2
            X_dec_4ASK(i) = 3;
        end
    end
    X_final_4ASK = reshape(de2bi((X_dec_4ASK + 3)/2).',1,length(bits));
    
    % TEB
    TEB_4ASK = mean(bits ~= X_final_4ASK);
    fprintf("4.2.1 (1) Le TEB pour la chaine 4-ASK sans bruit est : %f \n",TEB_4ASK);
    
    % Les constellations en sortie du mapping
    scatterplot(Symboles_4ASK);
    title("Les constellations en sortie du mapping : 4ASK");
    xlim([-6 6]);
    ylim([-6 6]);
    saveas(gcf,'constellations_4ASK', 'png');
% La chaine QPSK sans bruit
    M_QPSK = 4;
    n_QPSK = log2(M_QPSK);
    
    % Mapping
    Symboles_QPSK = bi2de(reshape(bits,length(bits)/n_QPSK, n_QPSK)).';
    X_map_QPSK = pskmod(Symboles_QPSK, M_QPSK, pi/4, 'gray');
    
    % Decision + Demapping
    X_demap_QPSK = pskdemod(X_map_QPSK, M_QPSK, pi/4, 'gray');
    X_final_QPSK = de2bi(X_demap_QPSK.');
    X_final_QPSK = X_final_QPSK(:)';
    
    % TEB
    TEB_QPSK = mean(bits ~= X_final_QPSK);
    fprintf("4.2.1 (2) Le TEB pour la chaine QPSK sans bruit est : %f \n",TEB_QPSK);
    
    % Les constellations en sortie du mapping
    scatterplot(X_map_QPSK);
    title("Les constellations en sortie du mapping : QPSK");
    xlim([-2 2]);
    ylim([-2 2]);
    saveas(gcf,'constellations_QPSK', 'png');
    
    
% La haine 8-PSK sans bruit
    M_8PSK = 8;
    n_8PSK = log2(M_8PSK);
    
    % Mapping
    Symboles_8PSK = bi2de(reshape(bits, length(bits)/n_8PSK, n_8PSK)).';
    X_map_8PSK = pskmod(Symboles_8PSK, M_8PSK, pi/8,'gray');
    
    % Decision + Demapping
    X_demap_8PSK = pskdemod(X_map_8PSK,M_8PSK,pi/8,'gray');
    X_final_8PSK = de2bi(X_demap_8PSK.');
    X_final_8PSK = X_final_8PSK(:)';
    
    % TEB
    TEB_8PSK = mean(bits ~= X_final_8PSK);
    fprintf("4.2.1 (3) Le TEB pour la chaine 8-PSK sans bruit est : %f \n",TEB_8PSK);

    % Les constellations en sortie du mapping
    scatterplot(X_map_8PSK);
    title("Les constellations en sortie du mapping : 8PSK");
    xlim([-2 2]);
    ylim([-2 2]);
    saveas(gcf,'constellations_8PSK', 'png');
    
% La chaine 16-QAM sans bruit
    M_16QAM = 16;
    n_16QAM = log2(M_16QAM);
    
    % Mapping
    Symboles_16QAM = bi2de(reshape(bits, length(bits)/n_16QAM, n_16QAM)).';
    X_map_16QAM = qammod(Symboles_16QAM, M_16QAM, 'gray');
    
    % Decision + Demapping
    X_demap_16QAM = qamdemod(X_map_16QAM, M_16QAM, 'gray');
    X_final_16QAM = de2bi(X_demap_16QAM.');
    X_final_16QAM = X_final_16QAM(:)';
    
    % TEB
    TEB_16QAM = mean(bits ~= X_final_16QAM);
    fprintf("4.2.1 (4) Le TEB pour la chaine 8-PSK sans bruit est : %f \n",TEB_16QAM);
    
    % Les constellations en sortie du mapping
    scatterplot(X_map_16QAM);
    title("Les constellations en sortie du mapping : 16QAM");
    xlim([-5 5]);
    ylim([-5 5]);
    saveas(gcf,'constellations_16QAM', 'png');
    


%% --4.1.2 Rajouter le bruit

EbSurN0 = zeros(1,9);

% Initialiser tous les TEBs
TEB_4ASK_bruit = zeros(1,9);
TEB_QPSK_bruit = zeros(1,9);
TEB_8PSK_bruit = zeros(1,9);
TEB_16QAM_bruit = zeros(1,9);

for j = 0:8
    
    Suite_diracs_4ASK_bruite = kron(Symboles_4ASK, [1 zeros(1,Ns-1)]);
    Suite_diracs_QPSK_bruite = kron(X_map_QPSK, [1 zeros(1,Ns-1)]);
    Suite_diracs_8PSK_bruite = kron(X_map_8PSK, [1 zeros(1,Ns-1)]);
    Suite_diracs_16QAM_bruite = kron(X_map_16QAM, [1 zeros(1,Ns-1)]);
    
    % Mise en forme des signals
        % 4ASK
        Xe_4ASK_bruit = filter(h1,1,[Suite_diracs_4ASK_bruite zeros(1,retard)]);
        Xe_4ASK_bruit = Xe_4ASK_bruit(retard +1 : end);
        % QPSK
        Xe_QPSK_bruit = filter(h1,1,[Suite_diracs_QPSK_bruite zeros(1,retard)]);
        Xe_QPSK_bruit = Xe_QPSK_bruit(retard +1 : end);
        % 8PSK
        Xe_8PSK_bruit = filter(h1,1,[Suite_diracs_8PSK_bruite zeros(1,retard)]);
        Xe_8PSK_bruit = Xe_8PSK_bruit(retard +1 : end);
        % 16QAM
        Xe_16QAM_bruit = filter(h1,1,[Suite_diracs_16QAM_bruite zeros(1,retard)]);
        Xe_16QAM_bruit = Xe_16QAM_bruit(retard +1 : end);
    
    EbSurN0(j+1) = 10^(j / 10);
    
    % Sigma carre pour tous les signals
    sigma_4ASK = mean(abs(Xe_4ASK_bruit).^2) * Ns / (2*log2(M_ASK) * EbSurN0(j+1));
    sigma_QPSK = mean(abs(Xe_QPSK_bruit).^2) * Ns / (2*log2(M_QPSK) * EbSurN0(j+1));
    sigma_8PSK = mean(abs(Xe_8PSK_bruit).^2) * Ns / (2*log2(M_8PSK) * EbSurN0(j+1));
    sigma_16QAM = mean(abs(Xe_16QAM_bruit).^2) * Ns / (2*log2(M_16QAM) * EbSurN0(j+1));
    
    % Ajouer les bruits
        % 4ASK
        bruit_real_4ASK = sqrt(sigma_4ASK) * randn(1,length(real(Xe_4ASK_bruit)));
        bruit_imag_4ASK = sqrt(sigma_4ASK) * randn(1,length(imag(Xe_4ASK_bruit)));
        Xe_4ASK_bruit = Xe_4ASK_bruit + bruit_real_4ASK + 1i*bruit_imag_4ASK;
        % QPSK
        bruit_real_QPSK = sqrt(sigma_QPSK) * randn(1,length(real(Xe_QPSK_bruit)));
        bruit_imag_QPSK = sqrt(sigma_QPSK) * randn(1,length(imag(Xe_QPSK_bruit)));
        Xe_QPSK_bruit = Xe_QPSK_bruit + bruit_real_QPSK + 1i*bruit_imag_QPSK;
        % 8PSK
        bruit_real_8PSK = sqrt(sigma_8PSK) * randn(1,length(real(Xe_8PSK_bruit)));
        bruit_imag_8PSK = sqrt(sigma_8PSK) * randn(1,length(imag(Xe_8PSK_bruit)));
        Xe_8PSK_bruit = Xe_8PSK_bruit + bruit_real_8PSK + 1i*bruit_imag_8PSK;
        % 16QAM
        bruit_real_16QAM = sqrt(sigma_16QAM) * randn(1,length(real(Xe_16QAM_bruit)));
        bruit_imag_16QAM = sqrt(sigma_16QAM) * randn(1,length(imag(Xe_16QAM_bruit)));
        Xe_16QAM_bruit = Xe_16QAM_bruit + bruit_real_16QAM + 1i*bruit_imag_16QAM;
        
    % Filtre de réception
    hr = h1;
        % 4ASK
        Xr_4ASK_bruit = filter(hr,1,[Xe_4ASK_bruit zeros(1,retard)]);
        Xr_4ASK_bruit = Xr_4ASK_bruit(retard+1 : end);
        % QPSK
        Xr_QPSK_bruit = filter(hr,1,[Xe_QPSK_bruit zeros(1,retard)]);
        Xr_QPSK_bruit = Xr_QPSK_bruit(retard+1 : end);
        % 8PSK
        Xr_8PSK_bruit = filter(hr,1,[Xe_8PSK_bruit zeros(1,retard)]);
        Xr_8PSK_bruit = Xr_8PSK_bruit(retard+1 : end);
        % 16QAM
        Xr_16QAM_bruit = filter(hr,1,[Xe_16QAM_bruit zeros(1,retard)]);
        Xr_16QAM_bruit = Xr_16QAM_bruit(retard+1 : end);

    % Echantillonnage
    X_ech_4ASK_bruit = Xr_4ASK_bruit(1:Ns:end);
    X_ech_QPSK_bruit = Xr_QPSK_bruit(1:Ns:end);
    X_ech_8PSK_bruit = Xr_8PSK_bruit(1:Ns:end);
    X_ech_16QAM_bruit = Xr_16QAM_bruit(1:Ns:end);
    
    % Decision + Demapping
        % 4ASK
        X_dec_4ASK_bruit = zeros(1,length(X_ech_4ASK_bruit));
        for i=1:length(X_dec_4ASK_bruit)
            if X_ech_4ASK_bruit(i) <= -2
                X_dec_4ASK_bruit(i)= -3;
            end
            if (-2 < X_ech_4ASK_bruit(i)) & (X_ech_4ASK_bruit(i) <= 0)
                X_dec_4ASK_bruit(i) = -1;
            end
            if (0 < X_ech_4ASK_bruit(i)) & (X_ech_4ASK_bruit(i) <= 2)
                X_dec_4ASK_bruit(i) = 1;
            end
            if X_ech_4ASK_bruit(i) > 2
                X_dec_4ASK_bruit(i) = 3;
            end
        end
        X_final_4ASK_bruit = reshape(de2bi((X_dec_4ASK_bruit + 3)/2).',1,length(bits));
        % QPSK
        X_demap_QPSK_bruit = pskdemod(X_ech_QPSK_bruit, M_QPSK, pi/M_QPSK, 'gray');
        X_final_QPSK_bruit = de2bi(X_demap_QPSK_bruit.');
        X_final_QPSK_bruit = X_final_QPSK_bruit(:)';
        % 8PSK
        X_demap_8PSK_bruit = pskdemod(X_ech_8PSK_bruit, M_8PSK, pi/M_8PSK, 'gray');
        X_final_8PSK_bruit = de2bi(X_demap_8PSK_bruit.');
        X_final_8PSK_bruit = X_final_8PSK_bruit(:)';
        % 16QAM
        X_demap_16QAM_bruit = qamdemod(X_ech_16QAM_bruit, M_16QAM, 'gray');
        X_final_16QAM_bruit = de2bi(X_demap_16QAM_bruit.');
        X_final_16QAM_bruit = X_final_16QAM_bruit(:)';
    

    % TEB
    TEB_4ASK_bruit(j+1) = mean(X_final_4ASK_bruit ~= bits);
    TEB_QPSK_bruit(j+1) = mean(X_final_QPSK_bruit ~= bits);
    TEB_8PSK_bruit(j+1) = mean(X_final_8PSK_bruit ~= bits);
    TEB_16QAM_bruit(j+1) = mean(X_final_16QAM_bruit ~= bits);
    
    % Les constellations en sortie de l'échantillonneur
    if (j == 0 | j == 3 | j == 5 | j == 8 )
        % 4ASK
        scatterplot(X_ech_4ASK_bruit);
        name = strcat("Constellations en sortie d'éch: 4-ASK|Eb/N_0 = ",num2str(j),"dB");
        title(name);
        xlim([-6 6]);
        ylim([-6 6]);
        filename = strcat("4ASK_EbSurN0_",num2str(j));
        saveas(gcf,filename, 'png');
        % QPSK
        scatterplot(X_ech_QPSK_bruit);
        name = strcat("Constellations en sortie d'éch: QPSK|Eb/N_0 = ",num2str(j),"dB");
        title(name);
        xlim([-2 2]);
        ylim([-2 2]);
        filename = strcat("QPSK_EbSurN0_",num2str(j));
        saveas(gcf,filename, 'png');
        % 8-PSK
        scatterplot(X_ech_8PSK_bruit);
        name = strcat("Constellations en sortie d'éch: 8-PSK|Eb/N_0 = ",num2str(j),"dB");
        title(name);
        xlim([-2 2]);
        ylim([-2 2]);
        filename = strcat("8PSK_EbSurN0_",num2str(j));
        saveas(gcf,filename, 'png');
        % 16-QAM
        scatterplot(X_ech_16QAM_bruit);
        name = strcat("Constellations en sortie d'éch: 16-QAM|Eb/N_0 = ",num2str(j),"dB");
        title(name);
        xlim([-5 5]);
        ylim([-5 5]);
        filename = strcat("16QAM_EbSurN0_",num2str(j));
        saveas(gcf,filename, 'png');
     end

    
end


   

% To print figures of TEB
    % 4ASK
    figure;
    semilogy(0:8,TEB_4ASK_bruit,'-x');
    hold on
    semilogy(0:8,qfunc(3/4*sqrt(4/5*EbSurN0)));
    hold on
    title("La chaine de 4-ASK avec bruit: Le diagramme de TEB_sim et TEB_the");
    xlabel("Eb/N0 (dB)");
    ylabel("TEB");
    legend("TEB-sim", "TEB-the");
    saveas(gcf,'TEB_4ASK', 'png');
    % QPSK
    figure;
    semilogy(0:8,TEB_QPSK_bruit,'-*');
    hold on
    semilogy(0:8,qfunc(sqrt(2*EbSurN0)));
    hold on
    title("La chaine de QPSK avec bruit: Le diagramme de TEB_sim et TEB_the");
    xlabel("Eb/N0 (dB)");
    ylabel("TEB");
    legend("TEB-sim", "TEB-the");
    saveas(gcf,'TEB_QPSK', 'png');
    % 8PSK
    figure;
    semilogy(0:8,TEB_8PSK_bruit,'-v');
    hold on
    semilogy(0:8,(2/3)*qfunc(sqrt(2*EbSurN0*sin(pi/M_8PSK))));
    hold on
    title("La chaine de 8-PSK avec bruit: Le diagramme de TEB_sim et TEB_the");
    xlabel("Eb/N0 (dB)");
    ylabel("TEB");
    legend("TEB-sim", "TEB-the");
    saveas(gcf,'TEB_8PSK', 'png');
    % 16QAM
    figure;
    semilogy(0:8,TEB_16QAM_bruit,'-o');
    hold on
    semilogy(0:8,qfunc(3/4*sqrt(4/5*EbSurN0)));
    hold on
    title("La chaine de 16-QAM avec bruit: Le diagramme de TEB_sim et TEB_the");
    xlabel("Eb/N0 (dB)");
    ylabel("TEB");
    legend("TEB-sim", "TEB-the");
    saveas(gcf,'TEB_16QAM', 'png');
%% --4.2.1     En utilisant les tracés obtenus pour leurs TEBs, comparer et classer les différentes chaines de transmision en en termes d’efficacité en puissance.
% Comparaison de TEBs pour tous les chaines.
figure("Name", "Comparaison de TEBs simules pour tous les chaines");
semilogy(0:8,TEB_4ASK_bruit,'-x');
hold on
semilogy(0:8,TEB_QPSK_bruit,'-*');
hold on
semilogy(0:8,TEB_8PSK_bruit,'-v');
hold on
semilogy(0:8,TEB_16QAM_bruit,'-o');
hold on
title("Comparaison de TEBs simules pour tous les chaines");
xlabel("Eb/N0 (dB)");
ylabel("TEB");
legend("4-ASK", "QPSK", "8-PSK", "16-QAM");
saveas(gcf,'TEB_comparaison', 'png');


%% --4.2.2 Pour un même débit binaire, tracer les densités spectrales de puissance des signaux
% Tracer les DSPs des signaux émis
    % 4-ASK
    figure();
    DSP_4ASK_bruit = (1 / length(Xe_4ASK_bruit)) * abs(fft(Xe_4ASK_bruit, 2^nextpow2(length(Xe_4ASK_bruit)))).^2;
    semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(Xe_4ASK_bruit))),fftshift(DSP_4ASK_bruit));
    hold on
    DSP_QPSK_bruit = (1 / length(Xe_QPSK_bruit)) * abs(fft(Xe_QPSK_bruit, 2^nextpow2(length(Xe_QPSK_bruit)))).^2;
    semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(Xe_QPSK_bruit))),fftshift(DSP_QPSK_bruit));
    hold on
    DSP_8PSK_bruit = (1 / length(Xe_8PSK_bruit)) * abs(fft(Xe_8PSK_bruit, 2^nextpow2(length(Xe_8PSK_bruit)))).^2;
    semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(Xe_8PSK_bruit))),fftshift(DSP_8PSK_bruit));
    hold on
    DSP_16QAM_bruit = (1 / length(Xe_16QAM_bruit)) * abs(fft(Xe_16QAM_bruit, 2^nextpow2(length(Xe_16QAM_bruit)))).^2;
    semilogy(linspace(-1/Ns,1/Ns,2^nextpow2(length(Xe_16QAM_bruit))),fftshift(DSP_16QAM_bruit));
    hold on
    legend("4-ASK", "QPSK", "8-PSK", "16-QAM");
    title("Tracer les DSPs des signaux émis");
    xlabel("Fréquences normalisées");
    ylabel("Densité spectrale de puissance");
    saveas(gcf,'DSP_comparaison', 'png');









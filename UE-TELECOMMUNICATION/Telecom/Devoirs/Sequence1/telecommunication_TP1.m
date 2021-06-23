close all;
clear all;
nb_bits = 1000;
bits = randi([0,1],1,nb_bits);
Fe = 24000;
Rb = 6000;

%Modulateur 1
Symboles1 = 2 * bits -1;
Ns1 = 4;
Suite_diracs1 = kron(Symboles1, [1 zeros(1,Ns1-1)]);
h1 = ones(1, Ns1);
SignalModule1 = filter(h1,1,Suite_diracs1);
figure('Name', 'Signal 1');
subplot(2,2,1);
plot((0:(nb_bits*Ns1 - 1))/Rb, SignalModule1);
axis([0 (nb_bits*Ns1 - 1)/Rb -1.5 1.5]);
title('Signal1');
%Part of calulating and printing DSP1_Theorique
DSP1_theorique = (1 / length(SignalModule1)) * abs(fft(SignalModule1, 2^nextpow2(length(SignalModule1)))).^2;
subplot(2,2,2);
semilogy(linspace(0,1,length(DSP1_theorique)),fftshift(DSP1_theorique));
title('DSP1-Theorique');
%Part of calulatting and printing DSP1_Simulaire
DSP1_simulaire = pwelch(SignalModule1,[],[],[],Fe,'twosided', 'centered');
subplot(2,2,4);semilogy(DSP1_simulaire);
title('DSP1-Simulaire');

%Moulateur 2
Symboles2 = bi2de((reshape(bits, 2, []).'),'left-msb') .* 2 -3;
Ns2 = 8;
Suite_diracs2 = kron(Symboles2', [1 zeros(1,Ns2-1)]);
h2 = ones(1, Ns2);
SignalModule2 = filter(h2,1,Suite_diracs2);
figure('Name', 'Signal 2');
subplot(2,2,1);
plot((0:(nb_bits*Ns2/2 - 1))/Rb, SignalModule2);
axis([0 (nb_bits*Ns2/2 - 1)/Rb -3.5 3.5]);
title('Signal2');
%Part of calulating and printing DSP2_Theorique
DSP2_theorique = (1 / length(SignalModule2)) * abs(fft(SignalModule2, 2^nextpow2(length(SignalModule2)))).^2;
subplot(2,2,2);
semilogy(linspace(0,1,length(DSP2_theorique)),fftshift(DSP2_theorique));
title('DSP2-Theorique');
%Part of calulatting and printing DSP2_Simulaire
DSP2_simulaire = pwelch(SignalModule2,[],[],[],Fe,'twosided', 'centered');
subplot(2,2,4);semilogy(DSP2_simulaire);
title('DSP2-Simulaire');


%Moulateur 3
Symboles3 = 2 * bits -1;
Ns3 = 4;
Suite_diracs3 = kron(Symboles3, [1 zeros(1,Ns3-1)]);
h3 = [-ones(1, floor(Ns3/2)) ones(1, ceil(Ns3/2))];
SignalModule3 = filter(h3,1,Suite_diracs3);
figure('Name', 'Signal 3');
subplot(2,2,1);
plot((0:(nb_bits*Ns3 - 1))/Rb, SignalModule3);
axis([0 (nb_bits*Ns3 - 1)/Rb -1.5 1.5]);
title('Signal3');
%Part of calulating and printing DSP3_Theorique
DSP3_theorique = (1 / length(SignalModule3)) * abs(fft(SignalModule3, 2^nextpow2(length(SignalModule3)))).^2;
subplot(2,2,2);
semilogy(linspace(0,1,length(DSP3_theorique)),fftshift(DSP3_theorique));
title('DSP3-Theorique');
%Part of calulatting and printing DSP3_Simulaire
DSP3_simulaire = pwelch(SignalModule3,[],[],[],Fe,'twosided', 'centered');
subplot(2,2,4);semilogy(DSP3_simulaire);
title('DSP3-Simulaire');



%Moulateur 4
Symboles4 = 2 * bits -1;
Ns4 = 4;
Suite_diracs4 = kron(Symboles4, [1 zeros(1,Ns4-1)]);
h4 = rcosdesign(0.5,8,Ns4);
SignalModule4 = filter(h4,1,Suite_diracs4);
figure('Name', 'Signal 4');
subplot(2,2,1);
plot((0:(nb_bits*Ns4 - 1))/Rb, SignalModule4);
axis([0 (nb_bits*Ns4 - 1)/Rb -1.5 1.5]);
%Part of calulatting and printing DSP4_Simulaire
DSP4_simulaire = pwelch(SignalModule4,[],[],[],Fe,'twosided', 'centered');
subplot(2,2,4);semilogy(DSP4_simulaire);
title('DSP4-Simulaire');


%2
figure('Name', 'DSP-Simulaire');
semilogy(DSP1_simulaire);
hold on;
semilogy(DSP2_simulaire);
hold on;
semilogy(DSP3_simulaire);
hold on;
semilogy(DSP4_simulaire);
title('Compare for DSP-Simulaire of each signal')
legend('Signal 1', 'Signal 2', 'Signal 3', 'Signal 4');

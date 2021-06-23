close all;
clear all;
nb_bits = 100;
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
plot((0:(nb_bits*Ns1 - 1))/Rb, SignalModule1);
axis([0 (nb_bits*Ns1 - 1)/Rb -1.5 1.5]);


%Moulateur 2
Symboles2 = bi2de(reshape(bits, [], 2)) .* 2 -3;
Ns2 = 8;
Suite_diracs2 = kron(Symboles2', [1 zeros(1,Ns2-1)]);
h2 = ones(1, Ns2);
SignalModule2 = filter(h2,1,Suite_diracs2);
figure('Name', 'Signal 2');
plot((0:(nb_bits*Ns2/2 - 1))/Rb, SignalModule2);
axis([0 (nb_bits*Ns2/2 - 1)/Rb -3.5 3.5]);


%Moulateur 3
Symboles3 = 2 * bits -1;
Ns3 = 4;
Suite_diracs3 = kron(Symboles3, [1 zeros(1,Ns3-1)]);
h3 = [-ones(1, floor(Ns3/2)) ones(1, ceil(Ns3/2))];
SignalModule3 = filter(h3,1,Suite_diracs3);
figure('Name', 'Signal 3');
plot((0:(nb_bits*Ns3 - 1))/Rb, SignalModule3);
axis([0 (nb_bits*Ns3 - 1)/Rb -1.5 1.5]);



%Moulateur 4
Symboles4 = 2 * bits -1;
Ns4 = 4;
Suite_diracs4 = kron(Symboles4, [1 zeros(1,Ns4-1)]);
h4 = rcosdesign(0.5,8,Ns4);
SignalModule4 = filter(h4,1,Suite_diracs4);
figure('Name', 'Signal 4');
plot((0:(nb_bits*Ns4 - 1))/Rb, SignalModule4);
axis([0 (nb_bits*Ns4 - 1)/Rb -1.5 1.5]);



figure('Name', 'DSP');
PSD1 = 10*log10(pwelch(SignalModule1));
plot(PSD1);
hold on;
PSD2 = 10*log10(pwelch(SignalModule2));
plot(PSD2);
hold on;
PSD3 = 10*log10(pwelch(SignalModule3));
plot(PSD3);
hold on;
PSD4 = 10*log10(pwelch(SignalModule4));
plot(PSD4);
title('')
legend('Signal 1', 'Signal 2', 'Signal 3', 'Signal 4');

figure;
pwelch(SignalModule1,[],[],[],Fe,'twosided');
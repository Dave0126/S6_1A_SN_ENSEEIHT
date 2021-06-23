function [result] = moyenne(im)
R = single(im(:, :, 1));
V = single(im(:, :, 2));
B = single(im(:, :, 3));

MAX = max(1, R + V + B);

Rbar = mean(R./MAX ,'all');
Vbar = mean(V./MAX ,'all');
result = [Rbar, Vbar];
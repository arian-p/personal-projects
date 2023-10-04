%% HOW TO USE:
% Load a file or group of file using either block of code below, then hit
% "run". If the wordcloud is giving junk words, add it to any of the
% replacewords strings. This program will automatically save an image of
% your wordcloud.

%% load and initialize
% Option 1: pull all txt files in directory
% st = dir();
% filelist = [];
% for n = 3:length(st)
%     names = st(n).name;
%     if strcmp(names(end-3:end), '.txt')
%         filelist = [filelist, string(names)];
%     end
% end
% 
% %now we run a big loop for every file. 

% Option 2, pull a single filename. use dir() in command and set
%filename = to it. NEEDS TO BE STRING
filelist = ["myExample.txt"]

%% the loop
for n = 1:length(filelist)
fhtxt = filelist(n);
    
dt1 = lower(fileread(fhtxt));
mask = dt1 >= 'a' & dt1 <= 'z' | dt1 == ' ' | dt1 == 10;
dt1(~mask) = [];
dt1a = string(dt1);

format short g
wordCountRaw = split(join(dt1a));
days = daysact(datetime(2000, 12, 23) , datetime(2001,3,3));
totalWords = numel(wordCountRaw);
wordPerDay = totalWords/days;
time = sprintf('Total words: %d, Words Per Day: %.2f', totalWords, wordPerDay)

%% replace words
replaceWordsA = ["something" "because" "people" "think" "about" "there" "really" "going"...
    "don't" "maybe" "guess" "almost" "other" "though" "thing" "where" "anyway" "around"];

replaceWordsB = ["always" "thats" "would" "don't" "being" "every" "which" "pretty" "these" "getting"...
    "matter" "should" "there" "myself" "after" "doing" "remember" "didn't"];

replaceWordsC = ["dream" "through" "seems" "having" "person" "important" "honestly" "point" "first" "better" "while" "great" ...
    "could" "issue" "little" "still" "right" "except" "reason" "means" "making"...
    "phone" "trying"];

replaceWordsD = ["tried" "hours" "natural" "talking" "didnt" "doesnt" "start" "waking" "between" "spent"...
    "might" "stuff" "today" "probably" "instead" "situation" "before" "different" "sense" "experience" "clear" "early"];

replaceWordsE = ["enough" "looking" "outside" "house" "whatever" "found" "makes" "wasnt" "arent" "basically" "finished" "least" "wasnt" "definetly" "later" "never"...
    "certainly" "later" "works" "minutes" "small" "enough" "coming" "lots" "once"];

replaceWordsF = [split("with that just this have what feel know your want read make they some both does head need were sure form  back will feel high with that just also felt lets find this then very been sleep time most nice talk long from cant than same only when here them into take done very been")]';
replaceWordsG = [split("easy made help real best else fact woke life last mind well work more like dont good over kind cool care idea went hand wake able hope gets look isnt deal")]';
replaceWordsH = [split("next list year hall kept said case much down till stop prac less holy papa keep even rest hour came many comes yeah full ings fully fine note their literally pack lost mean fine tually move part pick girl rest meet")]';
replaceWordsI = [split("inte ring ones late pres again left late wait took clean week self says info goes tice noon come quite step possibly random possible tonight applied plan taking soon such especially")]';
dt1a = replace(dt1a,[replaceWordsA, replaceWordsB, replaceWordsC, replaceWordsD, replaceWordsE, replaceWordsF, replaceWordsG, replaceWordsH, replaceWordsI], " ");

saveStrNum = length(strfind(dt1a, ' cs '));



%% organize, plot, save
words = split(join(dt1a));
words(strlength(words)<4) = [];
words = [words; repelem(["cs"], length(strfind(dt1a, ' cs ')))'];
words = sort(words);

C = categorical(words);
fig = figure;

wordcloud(C);
fhtxt2 = strrep(fhtxt, '_', '/')
title(['Text Analysis', fhtxt2{1}(8:end-4)]);

saveas(fig, ['Text Analysis', fhtxt{1}(8:end-4), '.png']);


end

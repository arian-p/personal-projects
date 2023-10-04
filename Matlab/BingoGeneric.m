%HOW TO USE: Fill in strArr1 for tiles, freeStrArr for free space options,
%edit title, and run. Use snipping tool to take a screenshot, and edit
%board.


%make a string array that has all of our class options. redundancies are
%fine. Have at least 25 items.
rng("shuffle")
strArr1 = ["example 1", "example 2", "example 3", "example 4", "example 5", "example 6", "example 7","example 8", "example 9", "example 10", "example 11", "example 12", "example 13", "example 14", "example 15", "example 16", "example 17", "example 18", "example 19", "example 20", "example 21" "xxx", "xxx","xxx", "xxx", "xxx","xxx", "xxx"];
strArr = strjust(strArr1);
freeStrArr = ["free space 1", "free space 2", "free space 3"];

%owe make a permutation vector of length 25, that selects from the
%list of our 25+ options. This is reshaped into a 5/5 matrix.
board = reshape(strArr(randperm(length(strArr), 25)), 5, 5); %this is an array.
        
%replace the middle with a free space
board(3, 3) = "FREE SPACE" + newline + newline + freeStrArr(randperm(length(freeStrArr), 1));


fig = uifigure('Name','My Bingo');
fig.Position = [100 100 500 315];
g = uigridlayout(fig,[6 5]);

for ii=1:5
    for jj=1:5
        lbl = uilabel(g,'Text', board(ii, jj));
        lbl.Layout.Row = ii + 1;
        lbl.Layout.Column = jj;
        lbl.HorizontalAlignment = 'center';
        lbl.FontSize = 8;
        lbl.WordWrap = 'on';
        
    end   
end
%now get title
lbl = uilabel(g,'Text', "Bingo for today! (" + cellstr(datetime(now, "ConvertFrom", "datenum", "Format", "MM/dd/yy hh:mm a")) + ")");
lbl.Layout.Row = 1;
lbl.Layout.Column = [1, 5];
lbl.HorizontalAlignment = 'center';
lbl.FontSize = 20;


g.RowHeight = {'fit', '1x', '1x', '1x', '1x'};


%axis off
%title(,'fontsize',15,'color','k');

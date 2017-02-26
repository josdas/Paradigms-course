function getLine(){
    return readline().split(' ').map(function(x){
       return parseInt(x); 
    });
}

function getInt(){
    return parseInt(readline());
}

var L = getLine();
var n = L[0];
var m = L[1];
var P = [];

for(var i = 0; i < n; i++){
    P[i] = getLine();
}
var T = [];
var G = [];
for(var i = 0; i < m; i++){
    T[i] = 0;
}
G[0] = 0;
for(var i = 1; i < n; i++){
    var min = n;
    for(var j = 0; j < m; j++){
        if(P[i - 1][j] > P[i][j]){
            T[j] = i;
        }
        min = Math.min(min, T[j]);
    }
    G[i] = min;
}
var k = getInt();
for(var i = 0; i < k; i++){
    var L = getLine();
    var l = L[0] - 1;
    var r = L[1] - 1;
    print(G[r] <= l ? "Yes" : "No");
}
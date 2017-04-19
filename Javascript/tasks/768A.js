var n = parseInt(readline());
var p = readline().split(' ').map(function(x){
    return parseInt(x);
});
var minv = 1e9;
var maxv = -1e9;
p.forEach(function(x){
   minv = Math.min(minv, x);
   maxv = Math.max(maxv, x); 
});
print(p.filter(function(x){
    return minv < x && x < maxv;
}).length);
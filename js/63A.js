var n = parseInt(readline())
var t = []
for(var i = 0; i < n; i++){
    var s = readline();
    t.push(s);
}
t = t.map(function(x){
   return x.split(' '); 
});
var G = [/rat/, /woman|child/, /^man/, /captain/];
G.forEach(function(reg){
    t.filter(function(x){
        return reg.test(x[1]);
    }).forEach(function(x){
        print(x[0]);
    });
});
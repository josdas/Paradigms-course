var L = readline().split(' ').map(function(x){
   return parseInt(x); 
});
var n = L[0];
var k = L[1];
var p = readline().split(' ').map(function(x){
    return parseInt(x);
});

var m = {};
m[0] = 1;
var sum = 0;
var res = 0;

var se = [];
for(var i = 0, t = 1; i < 50; i++){
    se.push(t);
    t *= k;
    if(Math.abs(t) > 2e15){
        break;
    }
}
se.sort();
se = se.filter(function(x, ind, self){
   return self.indexOf(x) === ind; 
});
for(var i = 0; i < n; i++){
    sum += p[i];
    se.forEach(function(value){
       var ind = sum - value;
       if(ind in m){
           if(ind in m){
               res += m[ind];
           }
       }
    });
    if(!(sum in m)){
        m[sum] = 0;
    }
    m[sum]++;
}
print(res);
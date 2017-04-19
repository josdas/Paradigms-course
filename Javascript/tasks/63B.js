function getLine(){
    return readline().split(' ').map(function(x){
       return parseInt(x); 
    });
}

var L = getLine();
var n = L[0];
var k = L[1];
var p = getLine();
var r = 0;
while(true){
    if(p.every(function(x){
        return x == k;
    })){
        break;
    }
    p = p.map(function(x, ind, self){
        if(ind === self.indexOf(x) && x < k){
            x++;
        }
        return x;
    });
    r++;
}
print(r);
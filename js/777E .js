function getLine(){
    return readline().split(' ').map(a => parseInt(a));  
}
function getInt(){
    return parseInt(readline());
}
function SegmentTree(n){
    this.n = n;
    var P = [];
    P.length = n * 4 + 1;
    var self = this;
    
    function Fun(x, y){
        return Math.max(x, y);
    }
    
    function build(v, l, r){
        if(l == r){
            P[v] = 0;
            return;
        }
        var s = (l + r) >> 1;
        build(v * 2, l, s);
        build(v * 2 + 1, s + 1, r);
        P[v] = Fun(P[v * 2], P[v * 2 + 1]);
    }
    build(1, 0, n - 1);
    
    var get = function(v, l, r, lq, rq){
        if(lq > rq){
            return 0;
        }
        if(lq == l && rq == r){
            return P[v];    
        }
        var s = (l + r) >> 1;
        return Fun(get(v * 2, l, s, lq, Math.min(rq, s)),
                get(v * 2 + 1, s + 1, r, Math.max(lq, s + 1), rq));
    }
    
    this.get = function(l, r){
        return get(1, 0, self.n - 1, l, r);
    };
    
    var upd = function(v, l, r, x, t){
        if(l == r){
            P[v] = Fun(P[v], t);
            return;
        }
        var s = (l + r) >> 1;
        if(x <= s){
            upd(v * 2, l, s, x, t);
        }
        else{
            upd(v * 2 + 1, s + 1, r, x, t);
        }
        P[v] = Fun(P[v * 2], P[v * 2 + 1]);
    }
    this.upd = function(x, t){
        upd(1, 0, self.n - 1, x, t);  
    };
}

var n = getInt();
var P = [];
for(var i = 0; i < n; i++){
    P.push(getLine());
}

P.sort(function(a, b){
    if(a[1] == b[1]){
        return a[0] - b[0];
    }
    return a[1] - b[1];
});
var W = P.map(x => x[1]).concat(P.map(x => x[0]));
var Z = W.sort((a, b) => a - b).filter((x, ind, self) => ind === 0 || self[ind - 1] != x);
var Go = {};
Z.forEach((x, ind) => Go[x] = ind);

var m = Z.length;
var dp = new SegmentTree(m);

for(var i = 0; i < n; i++){
    var up = Go[P[i][0]];
    var t = dp.get(up + 1, m - 1);
    var s = Go[P[i][1]];
    dp.upd(s, t + P[i][2]);
}

print(dp.get(0, m - 1));
var h = readline().split(' ');
var n = parseInt(readline());
var story = [];

story.push(h.join(' '));

for(var i = 0; i < n; i++){
   var v = readline().split(' ');
   var ind = h.indexOf(v[0]);
   h[ind] = v[1];
   story.push(h.join(' '));
}

print(story.join('\n'));
function get_function(fnctn, number_arguments) {
    return function(stack) {
        temp = [];
        for(var i = number_arguments - 1; i >= 0; i--) {
            temp[i] = stack.pop();
        }
        return fnctn.apply(null, temp);
    }
}

function get_const(t) {
    if(typeof t != 'function'){
        if(typeof t == 'number'){
            t = cnst(t);
        } else {
            t = variable(t);
        }
    }
    return t;
}

function cnst(x) {
    return function() {
        return x;
    };
}

function variable(s) {
    var variables = "xyzuvw";
    var e = variables.indexOf(s);
    return function() {
        return arguments[e];
    }
}

function get_super_function(fnctn, number_arguments, temp) {
    return function() {
        var tmp = [];
        for(var i = 0; i < number_arguments; i++) {
            tmp[i] = get_const(temp[i]).apply(null, arguments);
        }
        return fnctn.apply(null, tmp);
    }
}

function add() {
    return get_super_function(function(a, b) { return a + b; }, 2, arguments);
}

function subtract() {
    return get_super_function(function(a, b) { return a - b; }, 2, arguments);
}

function multiply() {
    return get_super_function(function(a, b) { return a * b; }, 2, arguments);
}

function divide() {
    return get_super_function(function(a, b) { return a / b; }, 2, arguments);
}

function negate() {
    return get_super_function(function(x) { return -x; }, 1, arguments);
}

function min3() {
    return get_super_function(Math.min, 3, arguments);
}

function max5() {
    return get_super_function(Math.max, 5, arguments);
}

var functions = {
    '+': get_function(add, 2),
    '-': get_function(subtract, 2),
    '/': get_function(divide, 2),
    '*': get_function(multiply, 2),
    'negate': get_function(negate, 1),
    'min3': get_function(min3, 3),
    'max5': get_function(max5, 5)
};

var consts = {
    'pi': Math.PI,
    'e': Math.E
};

var pi = cnst(Math.PI);
var e = cnst(Math.E);

this['x'] = variable('x');
this['y'] = variable('y');
this['z'] = variable('z');
function parse(str) {
    var stack = [];
    str.split(' ')
       .filter(function(x) { return x.length > 0; })
       .forEach(function(t) {
            if(!isNaN(parseInt(t))) {
                stack.push(cnst(parseInt(t)));
            } else if(t in functions) {
                stack.push(functions[t](stack));
            } else if(t in consts) {
                stack.push(cnst(consts[t]));
            } else {
                stack.push(variable(t));
            }
    });
    return stack.pop();
}
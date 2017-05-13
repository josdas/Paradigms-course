var VARIABLE = {
    "x" : 0,
    "y" : 1,
    "z" : 2
};
var CONST_ZERO = new Const(0);
var CONST_ONE = new Const(1);
var CONST_TWO = new Const(2);
function OperationWithNumber(operation, numberArguments) {
    this.getOperation = function() {
        return operation;
    }
    this.getNumberArguments = function() {
        return numberArguments;
    }
}

function Const(t) {
    this.calc = function() {
        return t;
    };
}
Const.prototype.evaluate = function () {
    return this.calc();
};
Const.prototype.toString = function() {
    return this.calc().toString();
};
Const.prototype.diff = function(v) {
    return CONST_ZERO;
};

function Variable(ind) {
    this.getName = function() {
        return ind;
    };
    var temp = VARIABLE[ind];
    this.getVariable = function() {
        return temp;
    };
}
Variable.prototype.evaluate = function () {
    return arguments[this.getVariable()];
};
Variable.prototype.toString = function() {
    return this.getName();
};
Variable.prototype.diff = function(x) {
    if(x === this.getName()) {
        return CONST_ONE;
    }
    return CONST_ZERO;
};

var Operation = function() {
    var temp = Array.prototype.slice.call(arguments);
    this.getArguments = function() {
        return temp;
    };
};
Operation.prototype.evaluate = function() {
    var temp = arguments;
    var res = this.getArguments().map(function(t) { return t.evaluate.apply(t, temp) });
    return this.doEvaluate.apply(this, res);
};
Operation.prototype.toString = function() {
    return this.getArguments().join(" ") + " " + this.getName();
};
Operation.prototype.diff = function (v) {
    var arg = this.getArguments();
    return this.doDiff.apply(this, arg.concat(
        arg.map(function (value) {
            return value.diff(v);
        })
    ));
};

function newOperation(name, doEvaluate, doDiff, construct) {
    this.getName = function() {
        return name;
    };
    this.doEvaluate = doEvaluate;
    this.doDiff = doDiff;
    this.constructor = construct;
}
newOperation.prototype = Operation.prototype;

function getNewOperation(name, doEvaluate, doDiff) {
    var temp = function(arr) {
        var tmp = arguments;
        Operation.apply(this, tmp);
    }
    temp.prototype = new newOperation(name, doEvaluate, doDiff);
    return temp;
}

var Add = getNewOperation(
    "+",
    function (a, b) {
        return a + b;
    },
    function (a, b, da, db) {
        return new Add(da, db);
    }
);

var Subtract = getNewOperation(
    "-",
    function (a, b) {
        return a - b;
    },
    function (a, b, da, db) {
        return new Subtract(da, db);
    }
);

var Multiply = getNewOperation(
    "*",
    function (a, b) {
        return a * b;
    },
    function (a, b, da, db) {
        return new Add(new Multiply(da, b), new Multiply(a, db));
    }
);

var Divide = getNewOperation(
    "/",
    function (a, b) {
        return a / b;
    },
    function (a, b, da, db) {
        return new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Square(b));
    }
);

var Negate = getNewOperation(
    "negate",
    function (a) {
        return -a;
    },
    function (a, da) {
        return new Negate(da);
    }
);

var Square = getNewOperation(
    "square",
    function (a) {
        return a * a;
    },
    function (a, da) {
        return new Multiply(new Multiply(CONST_TWO, a), da);
    }
);

var Sqrt = getNewOperation(
    "sqrt",
    function (a) {
        return Math.sqrt(Math.abs(a));
    },
    function (a, da) {
        return new Divide(new Multiply(a, da), new Multiply(CONST_TWO, new Sqrt(new Multiply(new Square(a), a))));
    }
);


function doOperation(stack, operation) {
    var numberArguments = operation.getNumberArguments();
    var temp = [];
    for(var i = numberArguments - 1; i >= 0; i--) {
        temp[i] = stack.pop();
    }
    var obj = Object.create(operation.getOperation().prototype);
    operation.getOperation().apply(obj, temp);
    return obj;
}

var OPERATION = {
    "+" : new OperationWithNumber(Add, 2),
    "-" : new OperationWithNumber(Subtract, 2),
    "/" : new OperationWithNumber(Divide, 2),
    "negate" : new OperationWithNumber(Negate, 1),
    "sqrt" : new OperationWithNumber(Sqrt, 1),
    "square" : new OperationWithNumber(Square, 1),
    "*" : new OperationWithNumber(Multiply, 2)
};

function parse(str) {
    var stack = [];
    str.split(' ')
    .filter(function(x) { return x.length > 0; })
    .forEach(function(t) {
        if(!isNaN(parseInt(t))) {
            stack.push(new Const(parseInt(t)));
        } else if(t in OPERATION) {
            stack.push(doOperation(stack, OPERATION[t]));
        } else {
            stack.push(new Variable(t));
        }
    });
    return stack.pop();
}
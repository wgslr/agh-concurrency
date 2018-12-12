const EXP = 1.5;
const MAX_EAT = 500;

var Fork = function () {
    this.state = 0;
    return this;
}

function beb(pred, action, backoff) {
    setTimeout(() =>{
        if (pred()) {
        action();
    } else {
        // console.log(`Predicate ${pred} false`);
        beb(pred, action, backoff * EXP);
    }
    }, Math.random() * backoff);
}


Fork.prototype.acquire = function (cb) {
    beb(() => this.state == 0,
        () => {
            this.state = 1;
            cb();
        },
        10);
}

Fork.prototype.release = function () {
    this.state = 0;
}

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function (count) {
    const f1 = this.forks[this.f1];
    const f2 = this.forks[this.f2];
    const id = this.id;

    if (count <= 0) {
        return;
    }

    f1.acquire(() => {
        console.log(`${id} got fork ${this.f1}`);
        f2.acquire(() => {
            console.log(`${id} got fork ${this.f2}`);
            const eatTime = Math.random() * MAX_EAT;
            console.log(`${id} eating for ${eatTime}`);
            setTimeout(() => {
                console.log(`${id} finished`);
                f1.release();
                f2.release();
                this.startNaive(count - 1);
            }, eatTime);
        })
    });
}

Philosopher.prototype.startAsym = function (count) {
    const id = this.id;
    const f1 = this.forks[id % 2 == 0 ? this.f1 : this.f2];
    const f2 = this.forks[id % 2 == 0 ? this.f2 : this.f1];

    if (count <= 0) {
        return;
    }

    f1.acquire(() => {
        console.log(`${id} got fork ${this.f1}`);
        f2.acquire(() => {
            console.log(`${id} got fork ${this.f2}`);
            const eatTime = Math.random() * MAX_EAT;
            console.log(`${id} eating for ${eatTime}`);
            setTimeout(() => {
                console.log(`${id} finished`);
                f1.release();
                f2.release();
                this.startNaive(count - 1);
            }, eatTime);
        })
    });
}

Philosopher.prototype.startConductor = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}


var N = 5;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    // philosophers[i].startNaive(10);
    philosophers[i].startAsym(10);
}

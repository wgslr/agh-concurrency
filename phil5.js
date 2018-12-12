const BACKOFF = 10;
const EXP = 2;
const MAX_EAT = 3000;

var Fork = function () {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function (cb) {
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
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

function beb(pred, action, backoff) {
    if (pred()) {
        action();
    } else {
        console.log(`Predicate ${pred} false`);
        backoff *= EXP;
        setTimeout(beb, backoff, pred, action, backoff);
    }
}

Philosopher.prototype.startNaive = function (count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    if (count <= 0) {
        return;
    }

    beb(() => f1.state == 0,
        () => {
            f1.state == 1;
            console.log(`${id} got fork 1`);
            beb(() => f2.state == 0,
                () => {
                    console.log(`${id} got fork 2`);
                    f2.state = 1;
                    const eatTime = Math.random() * MAX_EAT;
                    console.log(`${id} eating for ${eatTime}`);
                    setTimeout(() => {
                        f0.state = f1.state = 0;
                    console.log(`${id} finished`);
                        this.startNaive(count - 1);
                    }, eatTime);
                },
                BACKOFF
            )
        },
        BACKOFF);

    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

Philosopher.prototype.startAsym = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
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
    philosophers[i].startNaive(10);
}

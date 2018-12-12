const EXP = 1.5;
const MAX_EAT = 500;

let totalBackoff = 0;

const Fork = function () {
    this.state = 0;
    return this;
}

function beb(pred, action, backoff) {
    setTimeout(() => {
        if (pred()) {
            action();
        } else {
            backoff *= EXP;
            totalBackoff += backoff;
            beb(pred, action, backoff);
        }
    }, Math.random() * backoff);
}

Fork.prototype.acquire = function (cb) {
    beb(() => this.state == 0,
        () => {
            this.state = 1;
            cb();
        },
        1);
}

Fork.prototype.release = function () {
    this.state = 0;
}

const Waiter = function () {
    this.count = 0;
    return this;
}

Waiter.prototype.free = function (cb) {
    this.count--;
}

Waiter.prototype.acquire = function (cb) {
    beb(() => this.count < 4,
        () => {
            this.count++;
            console.log(`waiter count ${this.count}`);
            cb();
        },
        1);
}

const Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
}

// Philosopher.prototype.startNaive = function (count) {
//     const f1 = this.forks[this.f1];
//     const f2 = this.forks[this.f2];
//     const id = this.id;

//     if (count <= 0) {
//         return;
//     }

//     f1.acquire(() => {
//         console.log(`${id} got fork ${this.f1}`);
//         f2.acquire(() => {
//             console.log(`${id} got fork ${this.f2}`);
//             const eatTime = Math.random() * MAX_EAT;
//             console.log(`${id} eating for ${eatTime}`);
//             setTimeout(() => {
//                 console.log(`${id} finished`);
//                 f1.release();
//                 f2.release();
//                 this.startNaive(count - 1);
//             }, eatTime);
//         })
//     });
// }

Philosopher.prototype.startAsym = function (count) {
    const id = this.id;
    const f1 = this.forks[id % 2 == 0 ? this.f1 : this.f2];
    const f2 = this.forks[id % 2 == 0 ? this.f2 : this.f1];

    if (count <= 0) {
        return;
    }

    f1.acquire(() => {
        // console.log(`asym ${id} got fork ${this.f1}`);
        f2.acquire(() => {
            // console.log(`asym ${id} got fork ${this.f2}`);
            const eatTime = Math.random() * MAX_EAT;
            // console.log(`asym ${id} eating for ${eatTime}`);
            setTimeout(() => {
                console.log(`asym ${id} finished ${11 - count}th time`);
                f1.release();
                f2.release();
                this.startAsym(count - 1);
            }, eatTime);
        })
    });
}

Philosopher.prototype.startConductor = function (count, waiter) {
    const f1 = this.forks[this.f1];
    const f2 = this.forks[this.f2];
    const id = this.id;

    if (count <= 0) {
        return;
    }

    waiter.acquire(() => {
        f1.acquire(() => {
            // console.log(`wait ${id} got fork ${this.f1}`);
            f2.acquire(() => {
                // console.log(`wait ${id} got fork ${this.f2}`);
                const eatTime = Math.random() * MAX_EAT;
                // console.log(`wait ${id} eating for ${eatTime}`);
                setTimeout(() => {
                    console.log(`wait ${id} finished ${11 - count}th time`);
                    f1.release();
                    f2.release();
                    waiter.free();
                    this.startConductor(count - 1, waiter);
                }, eatTime);
            })
        });
    });
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
setTimeout(() => {
    console.log(`asym; ${totalBackoff}`);
    console.log("\n\n");
    const waiter = new Waiter();
    for (var i = 0; i < N; i++) {
        philosophers[i].startConductor(10, waiter);
    }
    setTimeout(() => {
        console.log(`waiter; ${totalBackoff}`);
    }, 25000);
}, 25000);

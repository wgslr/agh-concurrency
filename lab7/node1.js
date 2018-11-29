function printAsync(s, cb) {
   var delay = Math.floor((Math.random()*1000)+500);
   setTimeout(function() {
       console.log(s);
       if (cb) cb();
   }, delay);
}

printAsync("1", () => console.log("Callback 1 run"));
printAsync("2");
printAsync("3");

console.log('done!');

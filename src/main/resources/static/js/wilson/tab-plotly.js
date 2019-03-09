TESTER = document.getElementById('tester');
// TESTER = $('#tester')[0];
// document.getElementById("id") return HTML DOM object
// $('#id') return jQuery Object, it is not the same, but $('#id')[0] return HTML DOM Object

Plotly.plot( TESTER, [{
    x: [1, 2, 3, 4, 5],
    y: [1, 2, 4, 8, 16] }], {
    margin: { t: 0 } } );

// this function will replaced by ajax call
function getRandomData() {
    return Math.random();
}

console.log("getRandomData() : " + getRandomData());

var cnt = 0;
targetTag = $('#extendsGraph')[0];

Plotly.plot(targetTag, [{
    y: [getRandomData()],
    type: 'line'
}]);


setInterval(function() {
    Plotly.extendTraces(targetTag, {y: [[getRandomData()]]}, [0]);
    cnt++;
    if(cnt > 500) {
        Plotly.relayout(targetTag, {
            xaxis: {
                range:[cnt-500, cnt]
            }
        }, 15)
    }
}, 200);
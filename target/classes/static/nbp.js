function getPosts() {
    console.log("start");
    const ajax = new XMLHttpRequest();
    ajax.open("GET", "https://jsonplaceholder.typicode.com/posts");
    ajax.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("dane odebrane");
            const posts = JSON.parse(this.response);
            let div = document.getElementById("rates");
            div.innerHTML = "";
            for (post of posts) {
                div.innerHTML += `<p><strong>${post.title}</strong>:<br>${post.body}</p>`;
            }
        }
    }
    ajax.send();
}

function getRates() {

    fetch("http://api.nbp.pl/api/exchangerates/tables/A?format=json")
        .then(response => response.json())
        .then(response => {
            let div = document.getElementById("rates");
//            div.innerHTML = "";
            div.innerHTML += `<h3>Tabela kursów walut</h3> <h5>${response[0].no} &nbsp&nbsp Data: ${response[0].effectiveDate}</h5><hr>`;
            div.innerHTML += '<h5>[KOD] N A Z W A&nbsp&nbsp&nbsp&nbsp&nbspKurs średni w zł</h5>'

            for (rate of response[0].rates) {
                div.innerHTML += `<p>[${rate.code}] ${rate.currency} = ${rate.mid} </p>`;


            }
            div.innerHTML += `<h5>- KONIEC TABELI -</h5>`;
        })
        .catch(a => console.log("Odrzucono połączenie: " + a));
}
function getRates() {
    console.log("start");
    const ajax = new XMLHttpRequest()
    ajax.open("GET", "https://jsonplaceholder.typicode.com/posts");

//    ajax.open("GET", "http://api.nbp.pl/api/exchangerates/tables/A?format=json");
    ajax.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("dane odebrane");
            //         console.log(this.response);
            const posts = JSON.parse(this.response);
            let div = document.getElementById("rates");
            for (post of posts) {
                div.innerHTML += "<p>" + post.title + "</p>";
            }

        }
    }
    ajax.send();

}
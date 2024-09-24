const txts = [
    "Hi! i'm",
    "Yuri Oliveira",
    "I'm a software engineer, studying Computer Science, electronics and games."
];

let i = 0;
let j = 0;
let speed = 30;

function typeWriter() {
    if (i < txts.length) {
        if (j < txts[i].length) {
            document.getElementById("txt-box-introduction").innerHTML += txts[i].charAt(j);
            j++
            setTimeout(typeWriter, speed);
        } else {
            j = 0;
            i++;
            document.getElementById("txt-box-introduction").innerHTML += "<br>"
            setTimeout(typeWriter, speed);
        }
    } else {
        document.getElementById("button-txt").style.display = "block";
    }
}

window.onload = () => {
    document.getElementById("button-txt").style.display = "none";
    typeWriter();
}

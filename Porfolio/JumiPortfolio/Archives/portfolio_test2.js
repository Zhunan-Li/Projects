let imagesContainer = document.querySelectorAll("div.image");
let originalSize = {};

function portfolioReSize() {
    let portfolioWidth = window.innerWidth;
    let lines = {};
    imagesContainer.forEach(function (container) {
        if (Object.keys(originalSize).includes(container.toString())) {
            if (container.getBoundingClientRect().width >= originalSize[container.toString()]) {
                container.classList.remove("full");
            }
        }
        let y = container.getBoundingClientRect().y;
        if (Object.keys(lines).includes(y.toString())) {
            lines[y].push(container);
        } else {
            lines[y] = [container];
        }
    });
    Object.keys(lines).forEach(function (key) {
        if (lines[key].length === 1) {
            originalSize[lines[key][0].toString()] = lines[key][0].getBoundingClientRect().width;
            lines[key][0].classList.add("full");
        } else {
            lines[key].forEach(function (element) {
                // element.style.height = `${lines[key][0].getBoundingClientRect().height}px`;
            });
        }
    });
}


window.addEventListener('resize', function (event) {
    portfolioReSize();
})

portfolioReSize();

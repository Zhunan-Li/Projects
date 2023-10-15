const gallery = document.querySelector("div#gallery");


function ReSize() {
    let galleryWidth = gallery.getBoundingClientRect().width;
    let currentLineWidth = 0;
    let currentLine = [];
    let currentTotalPercentage = 0;
    let processedImages = 0;
    let images = gallery.querySelectorAll("div.image");
    let lineCount = 0;
    let lineHeight = 0;
    images.forEach(function (container) {
        let percentage = parseFloat(container.getAttribute("percentage"));
        let minWidth = parseFloat(container.getAttribute("min-width"));
        processedImages++;
        container.querySelector("img").style.width = `${container.getBoundingClientRect().width}px`;

        function nextLine() {
            // process now line and prepare for next line
            if (currentLine.length === 0) {
                currentLine.push(container);
                currentTotalPercentage += parseFloat(container.getAttribute("percentage"));
            }
            let left = 0;
            currentLine.forEach(function (c) {
                c.style.left = `${left}px`;
                c.style.top = `${lineHeight}px`;
                let width = ((galleryWidth - (5 * currentLine.length - 1)) / currentTotalPercentage) * parseFloat(c.getAttribute("percentage"));
                c.style.width = `${width}px`;
                left += (width + 5);
                c.style.height = `${currentLine[0].getBoundingClientRect().height}px`;
                c.querySelector("img").style.height = `${currentLine[0].getBoundingClientRect().height}px`;
            });
            lineHeight += (5 + currentLine[0].getBoundingClientRect().height);
            currentLineWidth = 0;
            currentLine = [];
            currentTotalPercentage = 0;
            lineCount++;
        }

        if (currentLineWidth + minWidth <= galleryWidth) {
            // line ok
            currentLine.push(container);
            currentTotalPercentage += percentage;
            currentLineWidth += minWidth;
        } else {
            nextLine();
        }
        if (processedImages === images.length) {
            nextLine();
        }
    });
}

window.addEventListener("resize", function () {
    ReSize();
});
setInterval(function () {
    ReSize();
}, 100);
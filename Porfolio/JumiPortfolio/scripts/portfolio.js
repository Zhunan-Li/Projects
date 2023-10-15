const gallery = document.querySelector("div#gallery");
const panelBTN = document.querySelector("div#gallery-BTNs-container div");

function ReSize() {
    let galleryChild = [];
    for (let i = 0; i < gallery.children.length; i++) {
        galleryChild.push(gallery.children[i]);
    }
    let imageLines = _getImageLines(galleryChild);
    imageLines.forEach(function (line) {
        _resizeLineImages(line);
    });

    if (window.innerWidth <= 1250) {
        // panel 的按鈕高度
        let currentHeight = panelBTN.getBoundingClientRect().height;
        panelBTN.style.transform = `translateY(${(window.innerHeight - 600) + (125 - panelBTN.getBoundingClientRect().width)}px)`;
    }
}

// 決定哪幾張圖片是同一行
function _getImageLines(imageElements) {
    const galleryWidth = gallery.getBoundingClientRect().width;

    let imageLines = [];

    let currentLine = [];
    let currentLineWidth = 0;
    imageElements.forEach(ele => {
        // console.log(ele)
        // console.log(currentLineWidth + _getImageMinWidth(ele))
        // console.log(galleryWidth)
        // console.log(currentLineWidth + _getImageMinWidth(ele) <= galleryWidth)
        if (ele.classList.contains("section-label")) {
            imageLines.push(currentLine);
            currentLine = [];
            currentLineWidth = 0;
        }
        // 如果圖片加上去後不會超過gallery寬度 -> 把圖片加入該行
        else if ((currentLineWidth + _getImageMinWidth(ele)) <= galleryWidth) {
            currentLine.push(ele);
            currentLineWidth += _getImageMinWidth(ele);
        } else {
            // 超過了 換行
            if (currentLine.length === 0) {
                imageLines.push([ele]);
                currentLineWidth = 0;
            } else {
                imageLines.push(currentLine);
                currentLine = [ele];
                currentLineWidth = _getImageMinWidth(ele);
            }
        }

        // 最後一張圖片 直接push到最後一行
        if (!ele.classList.contains("section-label") && ele === imageElements[imageElements.length - 1]) {
            imageLines.push(currentLine)
        }
    });

    return imageLines;
}

// 取得該圖片的 min-width
function _getImageMinWidth(element) {
    return parseFloat(element.getAttribute("min-width"));
}

function _resizeLineImages(line) {
    let galleryWidth = gallery.getBoundingClientRect().width;
    if (line.length === 1) {
        // 只有一張圖片的話
        if (line[0].naturalWidth > galleryWidth) {
            let ratio = line[0].naturalHeight / line[0].naturalWidth;
            line[0].style.height = `${line[0].naturalHeight - (line[0].naturalWidth - galleryWidth) * ratio}px`;
        } else {
            let ratio = line[0].naturalHeight / line[0].naturalWidth;
            line[0].style.height = `${line[0].naturalHeight + (galleryWidth - line[0].naturalWidth) * ratio}px`;
        }
    } else {
        // console.log(line);
        // 不只一張圖片
        // calculate average height
        let averageOriginalHeight = 0;
        line.forEach(function (image) {
            averageOriginalHeight += image.naturalHeight;
        });
        averageOriginalHeight = averageOriginalHeight / line.length;

        function setLineHeight(height) {
            // set every image in line's height
            line.forEach(function (image) {
                image.style.height = `${height}px`;
            });
        }

        function getOriginalLineWidth() {
            let totalWidth = 0;
            line.forEach(function (image) {
                totalWidth += image.naturalWidth;
            });
            return totalWidth;
        }


        setLineHeight(averageOriginalHeight);
        let targetWidth = getOriginalLineWidth() + (3 * (line.length - 1));
        let ratio = averageOriginalHeight / getOriginalLineWidth();
        if (targetWidth > galleryWidth) {
            // decrease height -> decrease width
            let difWidth = targetWidth - galleryWidth;
            // console.log(difWidth)
            // console.log(averageOriginalHeight);
            setLineHeight(averageOriginalHeight - (difWidth * ratio) - 1);
        } else {
            // increase
            let difWidth = galleryWidth - targetWidth;
            setLineHeight(averageOriginalHeight + (difWidth * ratio));
        }
    }
}

window.addEventListener('load', function () {
    window.addEventListener("resize", function () {
        ReSize();
    });
    ReSize();

    //init start
    const portfolioBTns = [];
    document.querySelectorAll("div#gallery-BTNs-container a").forEach(function (element) {
        portfolioBTns.push(element);
    });
    const portfolioBTnsTexts = [];
    document.querySelectorAll("div#gallery-BTNs-container h1").forEach(function (element) {
        portfolioBTnsTexts.push(element);
    });

    const galleryBTNsBg = document.querySelector("#gallery-BTNs-bg");

    function expandGalleryBTNsBG() {
        galleryBTNsBg.style.width = `${portfolioBTnsTexts[1].getBoundingClientRect().width}px`;
        portfolioBTnsTexts.forEach(function (element) {
            element.style.opacity = "1";
        });
    }

    function foldGalleryBTnsBG() {
        galleryBTNsBg.style.width = "0";
        portfolioBTnsTexts.forEach(function (element) {
            element.style.opacity = "0";
        });
    }

    // init end
    sideBar();

    function sideBar() {
        portfolioBTnsTexts.forEach(function (element) {
            element.addEventListener('click', function (event) {
                portfolioBTns[portfolioBTnsTexts.indexOf(event.target)].click();
            });
        });
        if (window.innerWidth >= 1250) {
            // sidebar buttons
            portfolioBTns.forEach(function (element) {
                element.addEventListener('mouseover', function (event) {
                    event.target.style.borderColor = "orange";
                    portfolioBTnsTexts[portfolioBTns.indexOf(event.target)].style.color = "orange";
                });
                element.addEventListener('mouseout', function (event) {
                    event.target.style.borderColor = "white";
                    portfolioBTnsTexts[portfolioBTns.indexOf(event.target)].style.color = "white";
                });
            });
            portfolioBTnsTexts.forEach(function (element) {
                element.addEventListener('mouseover', function (event) {
                    event.target.style.color = "orange";
                    portfolioBTns[portfolioBTnsTexts.indexOf(event.target)].style.borderColor = "orange";
                });
                element.addEventListener('mouseout', function (event) {
                    event.target.style.color = "white";
                    portfolioBTns[portfolioBTnsTexts.indexOf(event.target)].style.borderColor = "white";
                });
            });
            document.querySelector("div#gallery-BTNs").addEventListener('mouseenter', function () {
                expandGalleryBTNsBG();
            });
            document.querySelector("div#gallery-BTNs").addEventListener('mouseleave', function () {
                foldGalleryBTnsBG();
            });
        } else {
            const galleryBTNsBgBTN = document.querySelector("div#gallery-BTNs-container div");
            let expanded = false;
            document.addEventListener('click', function (event) {
                if ((event.target === galleryBTNsBgBTN || portfolioBTns.includes(event.target) || portfolioBTnsTexts.includes(event.target)) && !expanded) {
                    expandGalleryBTNsBG();
                    portfolioBTns.forEach(function (element) {
                        element.style.opacity = "1";
                        element.style.pointerEvents = "fill";
                    });
                    expanded = true;
                } else {
                    foldGalleryBTnsBG();
                    portfolioBTns.forEach(function (element) {
                        element.style.opacity = "0";
                        element.style.pointerEvents = "none";
                    });
                    expanded = false;
                }
            });
        }
    }

    portfolioBTns[3].addEventListener('click', function () {
        window.scroll(0, 0);
    });
});
const body = document.querySelector('div#body');
const title = body.querySelector('div#title h1');
const doorImage = body.querySelector('div#door img');
const pagesBTNs = document.querySelectorAll('div#pages a');
const home = body.querySelector('div#homePage');
const homeContent = home.querySelector("#homePage > div");
const portfolio = body.querySelector('div#portfolioPage');
const contact = body.querySelector('div#contactPage');


window.addEventListener('load', function () {
    const url = document.location;
    const portfolioParameters = ["portfolio", "PIXELARTS", "LOWPOLYMODELS", "CONCEPTARTS"];
    doorImage.parentElement.style.height = `${doorImage.getBoundingClientRect().height}px`;
    home.style.height = `${doorImage.getBoundingClientRect().height}px`;
    contact.style.height = `${doorImage.getBoundingClientRect().height}px`;

    function loadHome() {
        _switchPage(home);
        _setTitle("HOME");
        _setBTNColor(["red", "black", "black"]);
        _setUrlParameter("#home");
    }

    function loadPortfolio() {
        _switchPage(portfolio);
        _setTitle("PORTFOLIO");
        _setBTNColor(["black", "red", "black"]);
        _setUrlParameter(portfolioParameters.includes(url.hash.substring(1)) ? `#${url.hash.substring(1)}` : "#portfolio");
    }

    function loadContact() {
        _switchPage(contact);
        _setTitle("CONTACT");
        _setBTNColor(["black", "black", "red"]);
        _setUrlParameter("#contact");
    }

    switch (true) {
        case (portfolioParameters.includes(url.hash.substring(1))):
            loadPortfolio();
            break;
        case (url.hash.substring(1) === "contact"):
            loadContact();
            home.style.transitionDuration = "1s";
            break;
        default:
            loadHome();
    }

    home.style.transitionDuration = "1s";
    contact.style.transitionDuration = "1s";

    pagesBTNs[0].addEventListener('click', function () {
        loadHome();
    });
    pagesBTNs[1].addEventListener('click', function () {
        loadPortfolio();
    });
    pagesBTNs[2].addEventListener('click', function () {
        loadContact();
    });

    let times = 0;
    let interval = setInterval(function () {
        if (times < 10) {
            ReSize();
            times++;
        } else {
            body.style.height = `${portfolio.getBoundingClientRect().height}px`;
            clearInterval(interval);
        }
    }, 10);

    window.addEventListener("resize", function () {
        doorImage.style.bottom = `${doorImage.getBoundingClientRect().height}px`;
    });
});

function _switchPage(targetPage) {
    if (targetPage !== portfolio) {
        // 只要不是切換到portfolio -> 放下door
        doorImage.style.transitionDelay = "";
        doorImage.style.bottom = "0";
        portfolio.style.height = `${doorImage.getBoundingClientRect().height}px`;
        portfolio.style.overflow = "hidden";
        title.parentElement.style.position = "relative";
        if (targetPage === home) {
            home.style.opacity = "1";
            contact.style.opacity = "0";
            homeContent.style.marginTop = "";
            home.style.backgroundColor = "white";
            home.style.pointerEvents = "all";
        } else {
            home.style.opacity = "0";
            contact.style.opacity = "1";
            contact.style.pointerEvents = "all";
        }
    } else {
        doorImage.style.transitionDelay = "1s";
        doorImage.style.bottom = `${doorImage.getBoundingClientRect().height}px`;
        portfolio.style.height = "";
        portfolio.style.overflow = "";
        title.parentElement.style.position = "sticky";
        home.style.opacity = "0";
        home.style.pointerEvents = "none";
        contact.style.opacity = "0";
        contact.style.pointerEvents = "none";
    }

    body.style.height = `${portfolio.getBoundingClientRect().height}px`;
}

function _setBTNColor(colors) {
    document.querySelectorAll("#pages a")[0].style.color = colors[0];
    document.querySelectorAll("#pages a")[1].style.color = colors[1];
    document.querySelectorAll("#pages a")[2].style.color = colors[2];
}

function _setUrlParameter(page) {
    window.location.assign(`${window.location.origin + window.location.pathname}${page}`);
}

function _setTitle(text) {
    let delay = 0;
    if (body.childElementCount > 1) {
        delay = 2000;
    }
    title.animate([{
        opacity: 1
    }, {
        opacity: 0
    }, {
        opacity: 1
    }], {duration: delay});
    setTimeout(function () {
        title.textContent = text;
    }, delay / 2);
}

// var keys = {37: 1, 38: 1, 39: 1, 40: 1};
//
// function preventDefault(e) {
//     var e = window.event || e; // old IE support
//     var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
//     if (delta === 1) {
//         window.scroll(0, window.scrollY - 10);
//     } else {
//         window.scroll(0, window.scrollY + 10);
//     }
//     e.preventDefault();
// }
//
// function preventDefaultForScrollKeys(e) {
//     if (keys[e.keyCode]) {
//         preventDefault(e);
//         return false;
//     }
// }
//
// // modern Chrome requires { passive: false } when adding event
// var supportsPassive = false;
// try {
//     window.addEventListener("test", null, Object.defineProperty({}, 'passive', {
//         get: function () {
//             supportsPassive = true;
//         }
//     }));
// } catch (e) {
// }
//
// var wheelOpt = supportsPassive ? {passive: false} : false;
// var wheelEvent = 'onwheel' in document.createElement('div') ? 'wheel' : 'mousewheel';
//
// // call this to Disable
// function disableScroll() {
//     window.addEventListener('DOMMouseScroll', preventDefault, false); // older FF
//     window.addEventListener(wheelEvent, preventDefault, wheelOpt); // modern desktop
//     // window.addEventListener('touchmove', preventDefault, wheelOpt); // mobile
//     window.addEventListener('keydown', preventDefaultForScrollKeys, false);
// }
//
// disableScroll();
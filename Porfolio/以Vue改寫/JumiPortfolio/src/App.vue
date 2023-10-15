<script setup>
import {onMounted, ref} from "vue";
import {getCurrentPage, playScriptAnimation, currentPage} from "@/utils";
import Home from "@/components/Home.vue";
import Contact from "@/components/Contact.vue";
import Gallery from "@/components/Gallery.vue";

let pageTitle;
let pageTitleText;
let galleryContainer;
let galleryCoverImg;
let mainPageSection;
const pageMap = {
    "#home": undefined,
    "#portfolio": undefined,
    "#contact": undefined
};

onMounted(() => {
    pageTitle = document.querySelector("div.page-title");
    pageTitleText = document.querySelector("div.page-title h1");
    galleryContainer = document.querySelector("div#gallery-container");
    galleryCoverImg = document.querySelector("div.gallery-cover img");
    mainPageSection = document.querySelector("main");
    pageMap["#home"] = document.querySelector("div#home-content");
    pageMap["#portfolio"] = document.querySelector("div#portfolio-content");
    pageMap["#contact"] = document.querySelector("div#contact-content");
    const _targetPage = Object.keys(pageMap).includes(getCurrentPage()) ? getCurrentPage() : "#home";
    window.location.hash = _targetPage;
    currentPage.value = _targetPage;
    pageTitleText.innerText = _targetPage.substring(1).toUpperCase();
    Object.keys(pageMap).filter(k => k !== _targetPage).forEach(k => pageMap[k].style.opacity = "0");
    if (_targetPage === "#portfolio") {
        pageTitle.style.boxShadow = "0 45px 18px 8px rgba(0, 0, 0, 0.38)";
        galleryCoverImgController(true);
        window.addEventListener("load", () => {
            galleryCoverImg.style.bottom = `${galleryCoverImg.getBoundingClientRect().height}px`;
            const galleryTotalHeight = `${document.querySelector("#gallery-container").getBoundingClientRect().height + 100}px`;
            mainPageSection.style.height = galleryTotalHeight;
            document.querySelector("div#gallery-navigator-bar").style.height = galleryTotalHeight;
        });
    } else {
        galleryCoverImgController(false)
    }
});

function galleryCoverImgController(show) {
    if (show) {
        galleryCoverImg.style.height = null;
        galleryCoverImg.style.opacity = 1;
        galleryContainer.style.height = null;
        galleryContainer.style.overflow = null;
        const galleryTotalHeight = `${document.querySelector("#gallery-container").getBoundingClientRect().height + 100}px`;
        mainPageSection.style.height = galleryTotalHeight;
        document.querySelector("div#gallery-navigator-bar").style.height = galleryTotalHeight;
    } else {
        galleryCoverImg.style.height = 0;
        galleryCoverImg.style.opacity = 0;
        galleryContainer.style.height = 0;
        galleryContainer.style.overflow = "hidden";
        mainPageSection.style.height = null;
        document.querySelector("div#gallery-navigator-bar").style.height = null;
    }
}

function changePage(targetPage) {
    const lastPage = getCurrentPage();

    if (targetPage === lastPage) return;

    window.location.hash = targetPage;
    currentPage.value = targetPage;
    playScriptAnimation(pageMap[lastPage], [{opacity: 1}, {opacity: 0}], 1500);
    playScriptAnimation(pageMap[targetPage], [{opacity: 0}, {opacity: 1}], 1500);
    playScriptAnimation(pageTitleText, [{opacity: 1}, {opacity: 0}, {opacity: 1}], 3000);
    if (targetPage !== "#portfolio" && lastPage === "#portfolio") {
        playScriptAnimation(pageTitle, [{boxShadow: "0 45px 18px 8px rgba(0, 0, 0, 0.38)"}, {boxShadow: "0 45px 18px 8px rgba(0, 0, 0, 0)"}], 1500);
        pageTitle.style.boxShadow = "0 45px 18px 8px rgba(0, 0, 0, 0.0)";
        galleryCoverImg.style.bottom = null;
    }
    if (targetPage === "#portfolio") {
        playScriptAnimation(pageTitle, [{boxShadow: "0 45px 18px 8px rgba(0, 0, 0, 0)"}, {boxShadow: "0 45px 18px 8px rgba(0, 0, 0, .38)"}], 1000);
        pageTitle.style.boxShadow = "0 45px 18px 8px rgba(0, 0, 0, 0.38)";
        galleryCoverImgController(true);
        galleryCoverImg.style.bottom = `${galleryCoverImg.getBoundingClientRect().height}px`;
    }
    setTimeout(() => {
        pageTitleText.innerText = targetPage.substring(1).toUpperCase();
        pageMap[lastPage].style.opacity = "0";
        pageMap[targetPage].style.opacity = "1";
        if (targetPage !== "#portfolio" && lastPage === "#portfolio") {
            galleryCoverImgController(false);
        }
    }, 1500);
}
</script>

<template>
    <header>
        <div class="banner">
            <div class="top-white-div">
                <img src="./assets/top.webp" alt="Banner Image">
            </div>
            <img src="./assets/banner.webp" alt="Banner Image" class="bg">
        </div>
        <div class="items">
            <div class="avatar">
                <img src="./assets/avatar.webp" alt="Avatar">
            </div>
            <div class="text">
                <h1>JUMI</h1>
                <h3>ART PORTFOLIO</h3>
            </div>
            <div class="page-btn">
                <a @click="changePage('#home')" :class="{red: currentPage==='#home'}">HOME</a>
                <a @click="changePage('#portfolio')" :class="{red: currentPage==='#portfolio'}">PORTFOLIO</a>
                <a @click="changePage('#contact')" :class="{red: currentPage==='#contact'}">CONTACT</a>
            </div>
        </div>
    </header>

    <main>
        <div class="page-title">
            <h1></h1>
        </div>
        <div id="content">
            <div id="home-content">
                <Home></Home>
            </div>
            <div id="contact-content">
                <Contact></Contact>
            </div>
            <div id="portfolio-content">
                <Gallery></Gallery>
                <div class="gallery-cover">
                    <img src="./assets/door.png" alt="Door image">
                </div>
            </div>
        </div>
    </main>
</template>

<style lang="scss" scoped>
header {
  @import "assets/main.css";

  .banner {
    width: 100%;
    height: 280px;
    overflow: hidden;

    img.bg {
      margin-top: -23%;
      width: 100%;
    }

    .top-white-div {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      height: 360px;
      margin-top: -80px;
      background-image: url("./assets/top.webp");
      background-repeat: repeat-x;
      min-width: 300px;

      img {
        visibility: hidden;
        width: 100%;
      }
    }
  }

  .items {
    display: flex;
    flex-direction: column;
    align-items: center;

    .avatar {
      z-index: 2;
      width: 150px;
      margin-top: -77px;

      img {
        width: 100%;
        border-radius: 50%;
      }
    }

    .text {
      margin-top: 20px;
      text-align: center;
      pointer-events: none;

      h3 {
        color: gray;
      }
    }

    .page-btn {
      margin-top: 30px;
      color: black;

      .red {
        color: red;
      }

      a {
        transition-duration: 0.5s;
        margin-left: 30px;
        cursor: pointer;
      }
    }
  }

  @media (max-width: 1440px) {
    .banner {
      .top-white-div {
        background-image: unset;
        background-repeat: unset;

        img {
          position: absolute;
          left: 0;
          bottom: 0;
          right: 0;
          width: 100%;
          visibility: visible;
        }
      }
    }
  }

  @media (max-width: 1024px) {
    .banner {
      height: 200px;

      img.bg {
        margin-top: -20%;
      }

      .top-white-div {
        height: 280px;
      }
    }
  }

  @media (max-width: 600px) {
    .banner {
      img.bg {
        transform: scale(2);
        margin-top: -10%;
      }

      .top-white-div {
        z-index: 1;
      }
    }
    .items {
      .avatar {
        width: 100px;
        margin-top: -47px;
      }
    }
  }
}

main {
  position: relative;

  .page-title {
    position: sticky;
    top: 0;
    z-index: 2;
    margin-top: 70px;
    padding-bottom: 30px;
    width: 100%;
    background-color: white;

    &.shadow {
      box-shadow: 0 45px 18px 8px rgba(0, 0, 0, .38);
    }

    h1 {
      text-align: center;
      border-style: none none solid none;
      width: 50%;
      margin-left: 50%;
      transform: translateX(-50%);
      min-width: 300px;
      cursor: default;
    }
  }

  #content {
    top: 0;

    #portfolio-content {
      .gallery-cover {
        position: absolute;
        top: 0;
        width: 100vw;
        overflow: hidden;
        pointer-events: none;

        img {
          position: relative;
          bottom: 0;
          width: 100%;
          transition-duration: 3s;
        }
      }
    }

    #home-content, #portfolio-content, #contact-content {
      position: absolute;
      width: 100%;
    }
  }
}
</style>

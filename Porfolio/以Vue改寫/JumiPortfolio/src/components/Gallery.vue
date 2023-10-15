<script setup>
import {portfolioWidth} from "@/Config";
import GalleryImage from "@/components/GalleryImage.vue";
import {onMounted, ref} from "vue";
import {createGallery} from "@/components/gallery";
import {currentPage, getCurrentPage} from "@/utils";

const images = ref([]);
const categoryTitleEleMap = {};
onMounted(() => {
    images.value = createGallery();
    window.addEventListener("load", () => {
        for (const img of images.value) {
            categoryTitleEleMap[img.name] = document.querySelector(`div.${img.name.replaceAll(' ', '-')}-HEAD`);
        }
    });
    window.addEventListener("click", (event) => {
        if (!Object.values(categoryTitleEleMap).includes(event.target) && navigatorMenuToggle.value && !event.target.hasAttribute("navigator")) {
            navigatorMenuToggleBtn();
        }
    });
});

function scrollToTop() {
    scrollTo({
        top: 0,
        behavior: "smooth"
    });
    if (navigatorMenuToggle.value) navigatorMenuToggleBtn();
}

function scrollToEle(categoryName) {
    const categoryTitleEle = categoryTitleEleMap[categoryName];
    scrollTo({
        top: categoryTitleEle.getBoundingClientRect().top + window.scrollY - 100,
        behavior: "smooth"
    });
    if (navigatorMenuToggle.value) navigatorMenuToggleBtn();
}

const navigatorMenuToggle = ref(false);

function navigatorMenuToggleBtn() {
    navigatorMenuToggle.value = !navigatorMenuToggle.value;
}
</script>

<template>
    <div id="gallery-container">
        <div class="gallery-content" v-for="img in images">
            <div class="category-head" :class="`${img.name.replaceAll(' ', '-')}-HEAD`">
                <h6>{{ img.name }}</h6>
            </div>
            <GalleryImage v-for="i in img.images" :img-url="i.file" :width="i.width"
                          :height="i.height" :min-width="i.minWidth"></GalleryImage>
        </div>
    </div>
    <div id="gallery-navigator-bar" :class="{showNavigatorMenu: navigatorMenuToggle}" navigator>
        <div id="gallery-navigator-btn-list" v-if="currentPage === '#portfolio'" navigator>
            <div class="gallery-navigator-btn" v-for="img in images" navigator>
                <div class="btn-icon" @click="scrollToEle(img.name)" navigator></div>
                <h1 @click="scrollToEle(img.name)" navigator>{{ img.name }}</h1>
            </div>
            <div class="gallery-navigator-btn" navigator>
                <div class="btn-icon" @click="scrollToTop" navigator></div>
                <h1 @click="scrollToTop" navigator>RETURN TO TOP</h1>
            </div>
        </div>
        <div id="gallery-navigator-bar-toggle-btn" v-if="currentPage === '#portfolio'" @click="navigatorMenuToggleBtn"
             navigator>
            <div navigator></div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
div#gallery-navigator-bar {
  position: absolute;
  top: 0;
  left: 0;
  width: 5vw;
  height: 100vh;
  transition-duration: 0.5s;

  &.showNavigatorMenu {
    width: 310px;
    background-color: rgba(0, 0, 0, .5);

    div#gallery-navigator-btn-list {
      div.gallery-navigator-btn {
        .btn-icon {
          pointer-events: fill;
        }

        h1 {
          pointer-events: fill;
          opacity: 1;
        }
      }
    }

    #gallery-navigator-btn-list {
      .gallery-navigator-btn {
        .btn-icon {
          opacity: 1 !important;
        }
      }
    }
  }

  div#gallery-navigator-btn-list {
    position: sticky;
    top: 200px;
    height: 200px;

    div.gallery-navigator-btn {
      height: 3vw;
      margin-top: 30px;
      margin-left: 30px;

      &:hover {
        .btn-icon {
          cursor: pointer;
          border: 3px solid orange;
        }

        h1 {
          color: orange;
          cursor: pointer;
        }
      }

      .btn-icon {
        position: absolute;
        display: block;
        width: 10px;
        height: 10px;
        background-color: transparent;
        border: 3px solid #fff;
        transition-duration: 0.5s;
        transform: rotate(45deg);
        box-shadow: 0 0 7px 5px rgba(0, 0, 0, .5), inset 0 0 7px 5px rgba(0, 0, 0, .5);
        cursor: default;
        opacity: 0;
        pointer-events: none;
      }

      h1 {
        position: absolute;
        opacity: 0;
        transition-duration: 0.5s;
        color: white;
        width: 200px;
        margin-left: 25px;
        font-family: Inter, sans-serif;
        font-size: 20px;
        cursor: pointer;
        pointer-events: none;
      }
    }
  }
}

@media (max-width: 1249px) {
  #gallery-navigator-bar {
    #gallery-navigator-bar-toggle-btn {
      position: sticky;
      margin-left: 10px;
      top: 99vh;
      width: 10vw;
      height: 10vw;
      background-color: rgba(0, 0, 0, .5);
      border-radius: 50%;
      min-width: 50px;
      min-height: 50px;
      transform: translateY(-100%);

      div {
        position: relative;
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%);
        width: 50%;
        height: 10%;
        background-color: white;

        &:before {
          display: block;
          content: "";
          width: 100%;
          height: 100%;
          background-color: white;
          transform: translateY(-200%);
        }

        &:after {
          display: block;
          content: "";
          width: 100%;
          height: 100%;
          background-color: white;
          transform: translateY(100%);
        }
      }
    }
  }
}

@media (min-width: 1250px) {
  #gallery-navigator-bar {
    &:hover {
      width: 310px;
      background-color: rgba(0, 0, 0, .5);

      div#gallery-navigator-btn-list {
        div.gallery-navigator-btn {
          .btn-icon {
            pointer-events: fill;
          }

          h1 {
            pointer-events: fill;
            opacity: 1;
          }
        }
      }
    }

    #gallery-navigator-btn-list {
      .gallery-navigator-btn {
        .btn-icon {
          opacity: 1 !important;
        }
      }
    }
  }
}

div#gallery-container {
  position: absolute;
  top: 30px;
  left: 50%;
  min-width: 300px;
  width: v-bind(portfolioWidth);
  transform: translateX(-50%);
  margin-top: 30px;

  .gallery-content {
    display: flex;
    flex-wrap: wrap;

    .category-head {
      margin-top: 80px;
      width: 100%;
      margin-bottom: 80px;
      border-color: transparent transparent black transparent;
      border-style: solid;

      h6 {
        font-size: 30px;
        cursor: default;
      }
    }
  }
}
</style>
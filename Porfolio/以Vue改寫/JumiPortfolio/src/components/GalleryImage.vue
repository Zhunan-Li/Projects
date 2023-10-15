<template>
    <div class="blur-img">
        <img alt="Img" ref="imgEle">
    </div>
</template>

<script setup>
import {onMounted, ref} from "vue";

const imgEle = ref();
const props = defineProps(["imgUrl", "minWidth", "width", "height"]);
const thumbUrl = `url("../${props.imgUrl}_thumb.jpg")`;
const width = `${props.width}px`
const height = `${props.height}px`;

onMounted(() => {
    const img = imgEle.value;
    img.style.opacity = "0";

    window.addEventListener("load", () => {
        img.src = `${props.imgUrl}`;
        img.addEventListener("load", () => {
            img.style.opacity = "1";
        });
    });
});
</script>

<style scoped lang="scss">
div.blur-img {
  width: v-bind(width);
  height: v-bind(height);
  background-image: v-bind(thumbUrl);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  margin-top: 1px;
  margin-left: 1px;

  img {
    width: v-bind(width);
    height: v-bind(height);
    transition-duration: 0.5s;
  }
}
</style>
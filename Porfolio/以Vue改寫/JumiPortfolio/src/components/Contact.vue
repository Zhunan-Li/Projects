<script setup>
import {discordId} from "../Config";

function copyDiscordID() {

}
</script>

<template>
    <div class="container">
        <img src="../assets/dc.webp" alt="Discord">
        <div id="text">
            <h3>Hit me up on Discord!</h3>
            <h1>{{ discordId }}</h1>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.container {
  margin-left: 10vw;

  margin-top: 3vw;
  display: flex;

  img {
    width: 18%;
    height: 18%;
    margin-left: 20%;
  }

  #text {
    margin-top: 5vw;
    margin-left: 5vw;

    h3 {
      font-family: Inter, sans-serif;
      font-size: 1.5vw;
      color: #5c4f6e;
      cursor: default;
    }

    h1 {
      margin-left: -8px;
      font-family: Silkscreen, cursive;
      color: #3c2d52;
      font-size: 4vw;
      letter-spacing: -0.6vw;
      cursor: default;
    }
  }
}
</style>
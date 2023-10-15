<template>
    <div class="container">
        <img src="../assets/hello.webp" alt="Hello">
        <div class="text">
            <h1>HEY I'M</h1>
            <h2>JUMI</h2>
            <h6>I make art and stuff.<br>Hit me up :)</h6>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.container {
  margin-left: 15vw;
  display: flex;
  text-align: center;

  img {
    width: 40%;
  }

  .text {
    margin-left: 5vw;

    h1, h2 {
      font-family: Silkscreen, cursive;
      letter-spacing: -0.5vw;
      cursor: default;
    }

    h1 {
      font-size: 4vw;
    }

    h2 {
      color: #ff641ad1;
      margin-top: -4vw;
      font-size: 9vw;
    }

    h6 {
      margin-top: 30px;
      font-family: Inter, sans-serif;
      font-size: 1.5vw;
      cursor: default;
    }
  }

}
</style>
<script setup>
</script>
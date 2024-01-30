<!-- <script>
  import { onMount } from "svelte";
  import Swiper from "swiper";
  import "swiper/swiper-bundle.css";

  let swiper;

  onMount(() => {
    swiper = new Swiper(".swiper-container", {
      loop: true,
      slidesPerView: 2, 
      spaceBetween: 30, 
      autoplay : 3000
    });
  });
</script>

<style>
  .swiper-container {
    width: 100%;
    height: 300px;
    overflow: hidden;
  }
  .swiper-slide {
    text-align: center;
    font-size: 30px;
    background-color: #f0f0f0;
  }
</style>

<div class="swiper-container">
  <div class="swiper-wrapper">
    {#each [1, 2, 3] as slide}
      <div class="swiper-slide">슬라이드 {slide}</div>
    {/each}
  </div>
</div> -->

<script>
  let testInput;
</script>

<style>
  .error-msg {
    display: none;
  }
  .error-msg.active {
    display: block;
  }
</style>

<input type="text" bind:this={testInput}>
<div class="error-msg">에러메세지</div>
<button>클릭</button>
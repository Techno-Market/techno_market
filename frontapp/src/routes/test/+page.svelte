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
// +page.svelte

<script>
  import { Swiper, SwiperSlide } from 'swiper/svelte';
  import 'swiper/css';
  import 'swiper/css/navigation';
  import 'swiper/css/pagination';

  // Your additional imports or logic for the page
</script>

<Swiper
  spaceBetween={50}
  slidesPerView={3}
  on:slideChange={() => console.log('slide change')}
  on:swiper={(e) => console.log(e.detail[0])}
>
  <SwiperSlide>Slide 1</SwiperSlide>
  <SwiperSlide>Slide 2</SwiperSlide>
  <SwiperSlide>Slide 3</SwiperSlide>
  <SwiperSlide>Slide 4</SwiperSlide>
  <!-- 추가적인 슬라이드 추가 -->
</Swiper>

<!-- Your page content goes here -->

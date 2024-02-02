<script>
	import { onMount } from 'svelte';
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.css';

	let swiper;

	onMount(() => {
		swiper = new Swiper('.main-swiper', {
			loop: true,
			slidesPerView: 2,
			spaceBetween: 20,
            centeredSlides: true,
			pagination: {
				el: '.swiper-pagination',
				clickable: true
			}
			// navigation: {
			// 	nextEl: '.swiper-button-next',
			// 	prevEl: '.swiper-button-prev'
			// }
		});
	});
</script>

<style>
    .main-visual-area .swiper-box .main-swiper .swiper-slide {
        border-radius: 20px;
        overflow: hidden;
    }
    .main-visual-area .swiper-box .main-swiper .swiper-slide > img {
        width: 100%;
    }
    .main-visual-area .swiper-box .main-swiper .swiper-button-prev,
    .main-visual-area .swiper-box .main-swiper .swiper-button-next {
        width: 32px;
    }
    .main-visual-area .swiper-box .main-swiper .swiper-button-prev {
        left: 28px;
    }
    .main-visual-area .swiper-box .main-swiper .swiper-button-next {
        right: 28px;
    }
    .main-visual-area .swiper-box .main-swiper .swiper-button-prev::after,
    .main-visual-area .swiper-box .main-swiper .swiper-button-next::after {
        display: none;
    }
</style>

<div class="main-visual-area ofh w100per rel zi1 mt40">
    <div class="swiper-box wh100per rel zi1">
        <div class="main-swiper swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <img src="/img/main_img_1.jpg" alt="">
                </div>
                <div class="swiper-slide">
                    <img src="/img/main_img_2.jpg" alt="">
                </div>
                <div class="swiper-slide">
                    <img src="/img/main_img_3.jpg" alt="">
                </div>
                <div class="swiper-slide">
                    <img src="/img/main_img_1.jpg" alt="">
                </div>
                <div class="swiper-slide">
                    <img src="/img/main_img_2.jpg" alt="">
                </div>
                <div class="swiper-slide">
                    <img src="/img/main_img_3.jpg" alt="">
                </div>
            </div>
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-no-static-element-interactions -->
            <div class="swiper-button-next" on:click={() => swiper.slideNext()}>
                <img src="/img/arrow_next_point_030095.svg" alt="Next" />
            </div>
            <!-- svelte-ignore a11y-click-events-have-key-events -->
			<!-- svelte-ignore a11y-no-static-element-interactions -->
            <div class="swiper-button-prev" on:click={() => swiper.slidePrev()}>
                <img src="/img/arrow_prev_point_030095.svg" alt="Prev" />
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
</div>
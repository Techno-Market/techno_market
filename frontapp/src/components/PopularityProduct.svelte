<script>
	import { onMount } from 'svelte';
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.css';

	let swiper;

	onMount(() => {
		swiper = new Swiper('.popularity-product-swiper', {
			slidesPerView: 5,
			spaceBetween: 20,
			navigation: {
				nextEl: '.swiper-button-next',
				prevEl: '.swiper-button-prev'
			}
		});
	});
</script>

<div class="product-swiper-box wh100per rel mt40 zi1">
    <div class="popularity-product-swiper swiper-container ofh">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_1_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                        </button>
                    </div>
                    <h3 class="c222 mt20 f16 tal">아이폰14 프로 128GB</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">320,000원</h4>
                        <span class="c999 f14">1일전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">대전</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
                    </ul>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_2_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                        </button>
                    </div>
                    <h3 class="c222 mt20 f16 tal">플립4 256GB</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">400,000원</h4>
                        <span class="c999 f14">3시간전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">서울</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
                    </ul>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_3_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                        </button>
                    </div>
                    <h3 class="c222 mt20 f16 tal">아이패드5 256GB</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">600,000원</h4>
                        <span class="c999 f14">3일전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">부산</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
                    </ul>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_4_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                        </button>
                    </div>
                    <h3 class="c222 mt20 f16 tal">애플워치5</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">200,000원</h4>
                        <span class="c999 f14">5시간전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">서울</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
                    </ul>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_5_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                        </button>
                    </div>
                    <h3 class="c222 mt20 f16 tal">삼성 노트북</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">500,000원</h4>
                        <span class="c999 f14">3일전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">대전</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
                    </ul>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_1_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                        </button>
                    </div>
                    <h3 class="c222 mt20 f16 tal">아이폰14 프로 128GB</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">320,000원</h4>
                        <span class="c999 f14">1일전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">대전</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
                    </ul>
                </a>
            </div>
            <div class="swiper-slide">
                <a href="/">
                    <div class="img-box rel">
                        <img src="/img/product_img_2_1.jpg" alt="">
                        <button class="favor-box img-box w24 abs" id="favor_btn" style="top: 12px; right: 12px;">
                            <img src="/img/ico_heart.svg" alt="">
                    </div>
                    <h3 class="c222 mt20 f16 tal">플립4 256GB</h3>
                    <div class="mt12 flex aic jcsb">
                        <h4 class="c222 f18 tb">400,000원</h4>
                        <span class="c999 f14">3시간전</span>
                    </div>
                    <ul class="mt20 flex g4">
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">서울</li>
                        <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
                    </ul>
                </a>
            </div>
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
</div>
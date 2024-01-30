<script>
	export let data;

	console.log(data.data.sellArticle.photo);

	import { onMount } from 'svelte';
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.css';

	let swiper;

	onMount(() => {
		swiper = new Swiper('.swiper-container', {
			loop: true,
			slidesPerView: 1,
			spaceBetween: 0,
			pagination: {
				el: '.swiper-pagination',
				clickable: true
			},
			navigation: {
				nextEl: '.swiper-button-next',
				prevEl: '.swiper-button-prev'
			}
		});
	});
</script>

<div class="product-detail-area bsb w100per rel zi2 pt60">
	<div class="con w100per">
		<div class="product-infor-box-1 flex jcsb g40">
			<div class="product-detail-swiper-box wh100per rel zi1">
				<div class="swiper-container">
					<div class="swiper-wrapper">
					{#each data.data.sellArticle.photo as photo}

						<div class="swiper-slide">
							<div class="img-box rel">
								<img src="{photo.filePath}" alt="{photo.origFileName}" />
							</div>
						</div>

						
					{/each}
					 
						<div class="swiper-slide">
							<div class="img-box rel">
								<img src="/img/product_img_2_1.jpg" alt="" />
							</div>
						</div>
						<div class="swiper-slide">
							<div class="img-box rel">
								<img src="/img/product_img_3_1.jpg" alt="" />
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
					<div class="swiper-pagination"></div>
				</div>
			</div>
			<div class="product-text-box">
				<div class="flex aic jcsb">
					<ul class="flex g4">
						<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">{data.data.sellArticle.area}</li>
						{#if data.data.sellArticle.directly}
							<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
						{/if}
						{#if data.data.sellArticle.parcel}
							<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
						{/if}
					</ul>
					<ul class="flex aic g16">
						<li class="flex aic g4">
							<span class="img-box w20">
								<img src="/img/ico_heart_777.svg" alt="" />
							</span>
							<span class="c777 f14">5</span>
						</li>
						<li class="flex aic g4">
							<span class="img-box w20">
								<img src="/img/ico_chat_777.svg" alt="" />
							</span>
							<span class="c777 f14">3</span>
						</li>
						<li class="flex aic g4">
							<span class="img-box w20">
								<img src="/img/ico_eye_777.svg" alt="" />
							</span>
							<span class="c777 f14">{data.data.sellArticle.viewCount}</span>
						</li>
					</ul>
				</div>
				<h3 class="c777 f16 mt20">{data.data.sellArticle.category}</h3>
				<h1 class="c222 tb f24 lh140 mt8">{data.data.sellArticle.subject}</h1>
				<h2 class="c222 tb f32 mt20 flex aib">
					{data.data.sellArticle.price.toLocaleString()}<span class="tl f24">원</span>
				</h2>
				<span class="c222 f16 tm mt20">빵빵이 판매자</span>
				<p
					class="f16 c777 lh160 mt20 bsb pt16 pr16 pb16 pl16"
					style="border-top: 1px solid #dbdbdb; border-bottom: 1px solid #dbdbdb;"
				>
					{@html data.data.sellArticle.content.replace(/\r\n/g, '<br>')}
				</p>

				<!--본인 작성 글이 아닐 경우-->
				<div class="flex aic g12 bsb pl16 pr16 mt20">
					<button class="favor-box img-box w32" id="favor_btn">
						<img src="/img/ico_heart.svg" alt="" />
					</button>
					<a href="/" class="btn-type-1" style="width: calc(100% - 44px);">채팅하기</a>
				</div>

				<!--본인 작성 글-->
				<div class="flex aic g12 bsb pl16 pr16 mt20">
					<a href="/" class="btn-type-1 w50per">수정하기</a>
					<a href="/" class="btn-type-1-2 w50per">삭제하기</a>
				</div>
			</div>
		</div>
	</div>
</div>

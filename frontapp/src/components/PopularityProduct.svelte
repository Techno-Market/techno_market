<script>
	import { onMount } from 'svelte';
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.css';

	let swiper;
	const backendUrl = import.meta.env.VITE_BACKEND_URL;

	let data = [];

	onMount(async () => {
		swiper = new Swiper('.popularity-product-swiper', {
			slidesPerView: 5,
			spaceBetween: 20,
			navigation: {
				nextEl: '.swiper-button-next',
				prevEl: '.swiper-button-prev'
			}
		});

		try {
			let res = await fetch(`${backendUrl}/api/articles`, {
        credentials: 'include',
    });
			data = await res.json();
            data.data.articles.content.sort((a, b) => b.viewCount - a.viewCount);
		} catch (error) {
			console.error('데이터를 불러오는 중 에러 발생:', error);
		}
	});

	function displayedAt(createdAt) {
		const milliSeconds = new Date() - createdAt;
		const seconds = milliSeconds / 1000;

		if (seconds < 60) {
			return `방금 전`;
		}

		const minutes = seconds / 60;
		if (minutes < 60) {
			return `${Math.floor(minutes)}분 전`;
		}

		const hours = minutes / 60;
		if (hours < 24) {
			return `${Math.floor(hours)}시간 전`;
		}

		const days = hours / 24;
		if (days < 2) {
			return `1일 전`;
		} else if (days < 7) {
			return `${Math.floor(days)}일 전`;
		}

		const weeks = days / 7;
		if (weeks < 5) {
			return `${Math.floor(weeks)}주 전`;
		}

		const months = days / 30;
		if (months < 12) {
			return `${Math.floor(months)}개월 전`;
		}

		const years = days / 365;
		return `${Math.floor(years)}년 전`;
	}
</script>

<div class="product-swiper-box wh100per rel mt20 zi1">
	<div class="popularity-product-swiper swiper-container ofh">
		<div class="swiper-wrapper">
			{#if data && data.data}
				{#each data.data.articles.content.slice(0, 10) as article}
					<div class="swiper-slide">
						<a href="/sales_post/detail/{article.id}">
							<div class="img-box rel">
								<img
									src={`${backendUrl}/api/gen/${article.photo[0].filePath}`}
									alt={article.photo[0].origFileName}
								/>
							</div>
							<h3 class="c222 mt20 f16 tal">{article.subject}</h3>
							<div class="mt12 flex aic jcsb">
								<h4 class="c222 f18 tb">
									{article.price.toLocaleString()}<span class="tl f16 c777 inblock ml4">원</span>
								</h4>
								<span class="c999 f14">{displayedAt(new Date(article.createDate))}</span>
							</div>
							<ul class="mt20 flex g4">
								<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">{article.area}</li>
								{#if article.directly}
									<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
								{/if}
								{#if article.parcel}
									<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
								{/if}
							</ul>
						</a>
					</div>
				{/each}
			{/if}
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

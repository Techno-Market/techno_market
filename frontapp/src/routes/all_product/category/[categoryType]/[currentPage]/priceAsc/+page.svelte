<script>
    export let data;
	import axios from 'axios';
	import { goto } from '$app/navigation';

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
	function generatePageButtons(totalPages) {
            const buttons = [];
            for (let i = 0; i < totalPages; i++) {
                buttons.push(i + 1);
            }
            return buttons;
            }

	async function changePage(categoryType ,page) {
		try {
			console.log("검색어 : " + categoryType);
			console.log("페이지 : " + page);
			const upperCaseCategory = categoryType.toUpperCase();

			const response = await axios.get(`http://localhost:8080/api/articles/priceAsc?page=${page + 1}&category=${upperCaseCategory}`);
			data.result = response.data;
			
			goto(`/all_product/category/${categoryType}/${page}/priceAsc`);
		} catch (error) {
			console.error('Error fetching data:', error);
		}
	}
</script>

<div class="sub-cnt-area w100per rel zi1">
    <div class="con w100per">
		<h1 class="title-text lh120 tb flex aic g8">{data.categoryType}<span class="tb f16 c777">{data.result.data.articles.totalElements}개의 상품</span></h1>
		<div class="flex aic jce g16 f14 c999">
			<a href="/all_product/category/{data.categoryType}/0/priceDesc">가격 높은 순↑</a>
			<a href="/all_product/category/{data.categoryType}/0/priceAsc">가격 낮은 순↓</a>
		</div>
		{#if data.result.data.articles.content.length === 0}
            <p class="mt100 tac f18 c777">해당 카테고리 판매글이 없습니다.</p>
        {/if}
        <ul class="product-box flex fww">
            {#each data.result.data.articles.content as item (item.id)}
                <li>
                    <a href="/sales_post/detail/{item.id}">
                        {#if item.photo && item.photo[0]}
							<div class="img-box rel">
								<img
									src={`http://localhost:8080/api/gen/${item.photo[0].filePath}`}
									alt={item.photo[0].origFileName}
								/>
								<button
									class="favor-box img-box w24 abs"
									id="favor_btn"
									style="top: 12px; right: 12px;"
								>
									<img src="/img/ico_heart.svg" alt="" />
								</button>
							</div>
						{/if}
                        <h3 class="c222 mt20 f16 tal">{item.subject}</h3>
                        <div class="mt12 flex aic jcsb">
							<h4 class="c222 f18 tb">
								{item.price.toLocaleString()}<span class="tl f16 c777 inblock ml4">원</span>
							</h4>
							<span class="c999 f14">{displayedAt(new Date(item.createDate))}</span>
						</div>
                        <ul class="mt20 flex g4">
                            <li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">{item.area}</li>
                            {#if item.directly}
								<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
							{/if}
							{#if item.parcel}
								<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
							{/if}
                        </ul>
                    </a>
                </li>
            {/each}
        </ul>
    </div>
</div>

<div class="paging-box flex jcc mt40">
	<ul class="flex aic jcc">
		{#if data.result.data.articles.number > 0}
			<!-- 현재 페이지가 첫 페이지가 아닐 때만 이전 버튼을 표시 -->
			<!-- svelte-ignore a11y-click-events-have-key-events -->
			<!-- svelte-ignore a11y-no-noninteractive-element-interactions -->
			<li class="page-btn" on:click={() => changePage(data.categoryType, data.result.data.articles.number - 1)}>
				<a href="">이전</a>
			</li>
		{/if}
		{#each generatePageButtons(data.result.data.articles.totalPages) as button}
		<!-- svelte-ignore a11y-click-events-have-key-events -->
			<!-- svelte-ignore a11y-no-noninteractive-element-interactions -->
			<li
				class="num"
				on:click={() => data.result.data.articles.number !== button - 1 && changePage(data.categoryType, button - 1)}
			>
				<a href="" class:active={data.result.data.articles.number === button - 1}>{button}</a>
			</li>
		{/each}
		{#if data.result.data.articles.number < data.result.data.articles.totalPages - 1}
			<!-- 현재 페이지가 마지막 페이지가 아닐 때만 다음 버튼을 표시 -->
			<!-- svelte-ignore a11y-click-events-have-key-events -->
			<!-- svelte-ignore a11y-no-noninteractive-element-interactions -->
			<li class="page-btn" on:click={() => changePage(data.categoryType, data.result.data.articles.number + 1)}>
				<a href="">다음</a>
			</li>
		{/if}
	</ul>
</div>
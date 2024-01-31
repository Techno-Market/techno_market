<script>
	export let data;

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

<div class="sub-cnt-area w100per rel zi1">
	<div class="con w100per">
		<h1 class="title-text lh120 tb">전체 상품</h1>
		<ul class="product-box flex fww">
			{#each data.data.articles as article}
				<li>
					<a href="/sales_post/detail/{article.id}">
						{#if article.photo && article.photo[0]}
							<div class="img-box rel">
								<img
									src={`http://localhost:8080/gen/${article.photo[0].filePath}`}
									alt={article.photo[0].origFileName}
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
				</li>
			{/each}
		</ul>
	</div>
</div>

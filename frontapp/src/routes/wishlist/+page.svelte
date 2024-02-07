<script>
	export let data;
	import { onMount } from 'svelte';
	let username = '';

	onMount(() => {
	init();
	fetch('http://localhost:8080/api/user/me', {
			credentials: 'include'
		})
			.then((response) => response.json())
			.then((data) => {
				if (data) {
					username = data.data?.item?.username;
				}
			})
			.catch((error) => {
				// 실패시 처리
				console.error(error);
			});
		})

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
	let isFavorited = false;

// UI 업데이트 함수 (찜 여부에 따라 하트 이미지 업데이트 등의 작업을 수행)
function updateUI() {
    // 예시: 찜 여부에 따라 하트 이미지 업데이트
    const heartImage = document.getElementById('heartImage');
    if (heartImage) {
        heartImage.src = isFavorited ? "/img/ico_heart_active.svg" : "/img/ico_heart.svg";
    }
}

async function init() {
    try {
        const response = await fetch(`http://localhost:8080/api/wishlists/favorites/${data?.data?.wishLists[0]?.sellArticle?.id}`, {
            credentials: 'include',
        });
        
        if (response.ok) {
            const responseData = await response.json();
            isFavorited = responseData.isFavorited;
            console.log("init: " + isFavorited);
            updateUI(); // API 호출 후에 UI 업데이트 함수 호출
        } else {
            console.error('Error fetching user favorites:', response.statusText);
        }
    } catch (error) {
        console.error('Error fetching user favorites:', error);
    }
}



</script>

<div class="sub-cnt-area w100per rel zi1">
	<div class="con w100per">
		<h1 class="title-text lh120 tb">찜한 상품</h1>
		<ul class="product-box flex fww">
			{#each data.data.wishLists as wishlist}
				<li>
					<a href="/sales_post/detail/{wishlist.sellArticle.id}">
						{#if wishlist.sellArticle.photo && wishlist.sellArticle.photo[0]}
							<div class="img-box rel">
								<img
									src={`http://localhost:8080/api/gen/${wishlist.sellArticle.photo[0].filePath}`}
									alt={wishlist.sellArticle.photo[0].origFileName}
								/>
								<button 
									class="favor-box img-box w24 abs"
									id="favor_btn"
									style="top: 12px; right: 12px;"
								>
								<img id="heartImage" src={isFavorited ? "/img/ico_heart_active.svg" : "/img/ico_heart.svg"} alt="" />
								</button>
							</div>
						{/if}
						<h3 class="c222 mt20 f16 tal">{wishlist.sellArticle.subject}</h3>
						<div class="mt12 flex aic jcsb">
							<h4 class="c222 f18 tb">
								{wishlist.sellArticle.price.toLocaleString()}<span class="tl f16 c777 inblock ml4">원</span>
							</h4>
							<span class="c999 f14">{displayedAt(new Date(wishlist.sellArticle.createDate))}</span>
						</div>
						<ul class="mt20 flex g4">
							<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">{wishlist.sellArticle.area}</li>
							{#if wishlist.sellArticle.directly}
								<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
							{/if}
							{#if wishlist.sellArticle.parcel}
								<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
							{/if}
						</ul>
					</a>
				</li>
			{/each}
		</ul>
	</div>
</div>


<script>
	export let data;

	import { onMount } from 'svelte';
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.css';

	let swiper;
	let username = '';

	onMount(() => {
		swiper = new Swiper('.swiper-container', {
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
			
		fetchAnswers();
	});

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

	async function deleteArticle() {
		const confirmed = window.confirm('정말로 삭제하시겠습니까?');
		if (confirmed) {
			try {
				const response = await fetch(
					`http://localhost:8080/api/articles/${data.result.data.sellArticle.id}`,
					{
						method: 'DELETE',
						credentials: 'include'
					}
				);

				if (response.ok) {
					goto('/all_product/0', { replaceState: true });
					window.alert('삭제되었습니다');
				} else {
					// 삭제 실패
					console.error('Failed to delete article');
					window.alert('삭제가 실패하였습니다');
				}
			} catch (error) {
				console.error('Error while deleting article:', error);
			}
		} else {
			console.log('삭제가 취소되었습니다.');
		}
	}

	const modifyArticle = (at) => {
    const confirmed = window.confirm('수정하시겠습니까?');
    if (confirmed) {
        editingAnswer = at;
        modifiedComment = at.comment;
        goto(`/sales_post/modify/${data.result.data.sellArticle.id}`, { replaceState: true });
    }
};
	async function deleteAnswer(ans) {
		// 확인을 눌렀을 때 true 반환, 취소를 눌렀을 때 false 반환
		const confirmed = window.confirm('정말로 삭제하시겠습니까?');

		if (confirmed) {
			const answerId = ans.id;
			try {
				const response = await fetch(`http://localhost:8080/api/answers/${answerId}`, {
					method: 'DELETE',
					credentials: 'include'
				});

				if (response.ok) {
					window.alert('삭제되었습니다');
					fetchAnswers();
				} else {
					window.alert('삭제가 실패하였습니다');
					console.error('Failed to delete answer');
				}
			} catch (error) {
				console.error('Error while deleting answer:', error);
			}
		} else {
			console.log('삭제가 취소되었습니다.');
		}
	}

	const submitAnswerForm = async (event) => {
		event.preventDefault();

		const form = event.target;
		const formData = new FormData(form);

		// 클라이언트 측 유효성 검사 수행
		const comment = formData.get('comment');
		console.log('댓글 : ' + comment);

		const errors = {};
		if (!comment.trim()) {
			errors.comment = '댓글을 입력하세요.';
		}
		if (Object.keys(errors).length > 0) {
			updateErrorMessages(errors);
			return;
		}
		try {
			const response = await fetch(`http://localhost:8080/api/answers/${data.articleId}`, {
				method: 'POST',
				credentials: 'include',
				body: formData
			});

			if (response.ok) {
				const responseData = await response.json();
				console.log(responseData);
				window.alert('저장되었습니다.');
				fetchAnswers();
			} else {
				const responseData = await response.json();
				console.error(responseData);
				window.alert('저장에 실패했습니다.');
			}
		} catch (error) {
			console.error('Error submitting form:', error);
		}
	};
	const updateErrorMessages = (errors) => {
		for (const fieldName in errors) {
			const errorElement = document.querySelector(
				`.error-text-box[data-field="${fieldName}"] .error-text`
			);
			if (errorElement) {
				errorElement.textContent = errors[fieldName];
			}
		}
	};
	const fetchAnswers = async () => {
		try {
			const response = await fetch(`http://localhost:8080/api/answers/${data.articleId}`, {
				method: 'GET',
				credentials: 'include'
			});

			if (response.ok) {
				const newAnswers = await response.json();
				console.log('댓글 : ' + newAnswers);
				data.result2.data.answers = newAnswers.data.answers;
			} else {
				console.error('Failed to fetch answers data');
			}
		} catch (error) {
			console.error('Error while fetching answers data:', error);
		}
	};
	let editingAnswer = null;
	let modifiedComment = '';

	const modifyAnswer = (ans) => {
		editingAnswer = ans;
		modifiedComment = ans.comment;
	};

	const cancelModification = () => {
		editingAnswer = null;
		modifiedComment = '';
	};

	const submitModifiedAnswer = async (event) => {
		event.preventDefault();
		const form = event.target;
		const formData = new FormData(form);

		const comment = formData.get('modifiedComment');
		console.log("댓글 확인 : " + comment);
		
		const errors = {};
		if (!comment || !comment.trim()) {
			errors.comment = '댓글을 입력하세요.';
		}
		if (Object.keys(errors).length > 0) {
			updateErrorMessages(errors);
			return;
		}

        try {
			const response = await fetch(`http://localhost:8080/api/answers/${editingAnswer.id}`, {
				method: 'PATCH',
				credentials: 'include',
				body: formData
			});

			if (response.ok) {
				const responseData = await response.json();
				window.alert('저장되었습니다.');
				fetchAnswers();
			} else {
				const responseData = await response.json();
				window.alert('저장에 실패했습니다.');
			}
		} catch (error) {
			console.error('Error submitting form:', error);
		}
	};
	
    let isFavorited = false;

    // API를 통한 찜 상태 업데이트 함수
    async function toggleFavorite(articleId) {
		const confirmMessage = isFavorited ? '찜을 해제하시겠습니까?' : '찜을 하시겠습니까?';

    	const confirmed = window.confirm(confirmMessage);

		if (confirmed) {
        try {
            const response = await fetch(`http://localhost:8080/api/wishlists/toggleFavorite/${articleId}`, {
                method: 'POST',
                credentials: 'include',
            });

            if (response.ok) {
                const responseData = await response.json();
                const isToggled = responseData.message.includes("찜 추가 성공");
                // isFavorited 값을 업데이트
                isFavorited = isToggled;
				console.log("찜 여부: " + isFavorited);
				if(isFavorited) {
					window.alert("찜 목록에 추가하였습니다");
				} else {
					window.alert("찜 목록에서 해제하였습니다")
				}
				
                // UI 업데이트 등의 추가 작업 수행
                updateUI();
            } else {
                console.error('Error toggling favorite:', response.statusText);
            }
        } catch (error) {
            console.error('Error toggling favorite:', error);
        }
    }
}

    // UI 업데이트 함수 (찜 여부에 따라 하트 이미지 업데이트 등의 작업을 수행)
    function updateUI() {
        // 예시: 찜 여부에 따라 하트 이미지 업데이트
        const heartImage = document.getElementById('heartImage');
        if (heartImage) {
            heartImage.src = isFavorited ? "/img/ico_heart_active.svg" : "/img/ico_heart.svg";
        }
    }


	export async function init() {
		try {
			const response = await fetch(`http://localhost:8080/api/wishlists/favorites/${data.articleId}`, {
				credentials: 'include',
			});
			
			if (response.ok) {
				const responseData = await response.json();
				isFavorited = responseData.isFavorited;
				console.log("init :" + isFavorited)
				updateUI();
			} else {
				console.error('Error fetching user favorites:', response.statusText);
			}
		} catch (error) {
			console.error('Error fetching user favorites:', error);
		}
	}
	init();
</script>

<style>
	.answer-box > li {
		box-sizing: border-box;
		padding: 20px 0;
	}
	.answer-box > li:not(:first-child) {
		border-top: 1px solid #dbdbdb;
	}
</style>

<div class="product-detail-area bsb w100per rel zi2 pt60">
	<div class="con w100per">

		<!-- 상품 정보 영역 -->
		<div class="product-infor-box-1 flex jcsb g40">
			<div class="product-detail-swiper-box wh100per rel zi1">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						{#each data.result.data.sellArticle.photo as photo}
							<div class="swiper-slide">
								<div class="img-box rel">
									<img
										src={`http://localhost:8080/api/gen/${photo.filePath}`}
										alt={photo.origFileName}
									/>
								</div>
							</div>
						{/each}
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
					{#if username && data.result.data.sellArticle.author.username === username}
					<button
						class="favor-box img-box w40 abs zi2 {isFavorited ? 'active' : ''}"
						id="favor_btn"
						style="top: 20px; right: 20px;"
						on:click={() => toggleFavorite(data.articleId)}
					>
						<img id="heartImage" src={isFavorited ? "/img/ico_heart_active.svg" : "/img/ico_heart.svg"} alt="" />
					</button>
					{/if}
				</div>
			</div>
			<div class="product-text-box">
				<div class="flex aic jcsb">
					<ul class="flex g4">
						<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">
							{data.result.data.sellArticle.area}
						</li>
						{#if data.result.data.sellArticle.directly}
							<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">직거래</li>
						{/if}
						{#if data.result.data.sellArticle.parcel}
							<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b00A71B cfff">택배거래</li>
						{/if}
					</ul>
					<ul class="flex aic g16">
						<li class="flex aic g4">
							<span class="img-box w20">
								<img src="/img/ico_heart_777.svg" alt="" />
							</span>
							<span class="c777 f14">0</span>
						</li>
						<li class="flex aic g4">
							<span class="img-box w20">
								<img src="/img/ico_eye_777.svg" alt="" />
							</span>
							<span class="c777 f14">{data.result.data.sellArticle.viewCount}</span>
						</li>
					</ul>
				</div>
				<h3 class="c777 f16 mt20">{data.result.data.sellArticle.category}</h3>
				<h1 class="c222 tb f24 lh140 mt8">{data.result.data.sellArticle.subject}</h1>
				<h2 class="c222 tb f32 mt20 flex aib">
					{data.result.data.sellArticle.price.toLocaleString()}<span class="tl f24">원</span>
				</h2>
				<span class="c222 f16 tm mt20">{data.result.data.sellArticle.author.nickName}</span>
				<p
					class="f16 c777 lh160 mt20 bsb pt16 pr16 pb16 pl16"
					style="border-top: 1px solid #dbdbdb;"
				>
					{@html data.result.data.sellArticle.content.replace(/\r\n/g, '<br>')}
				</p>

				
				<!--본인 작성 글-->
				{#if username && data.result.data.sellArticle.author.username === username}
					<div class="flex aic g12 bsb pl16 pr16 mt20">
						<a on:click={modifyArticle} class="btn-type-1 w50per">수정하기</a>
						<button on:click={deleteArticle} class="btn-type-1-2 w50per">삭제하기</button>
					</div>
				{/if}
			</div>
		</div>
		
		<!-- 댓글 영역 -->
		<div class="answer-area bsb mt60 pt60" style="border-top: 1px solid #dbdbdb">
			<h3 class="c222 f18 tb">댓글 - {data.result2.data.answers.length}개</h3>
			<ul class="answer-box flex fdc mt8">
				{#each data.result2.data.answers as ans}
					{#if editingAnswer === ans}
					<li>
						<form on:submit|preventDefault={submitModifiedAnswer}>
							<div class="input-type-1 rel">
								<input type="text" name="modifiedComment" bind:value={modifiedComment} placeholder="수정된 댓글을 입력하세요" style="padding-right: 100px;" />
								<div class="c999 f15 flex g12 tb abs y-middle" style="right: 20px;">
									<button type="submit" class="tb">저장</button>
									<button on:click={cancelModification} class="tb">취소</button>
								</div>
							</div>
							<div class="error-text-box wsn flex g8 mt8" data-field="modifiedComment">
								<span class="error-text f14 cCC0000"></span>
							</div>
						</form>
					</li>
					{:else}
						<li class="answer-box rel">
							<div class="flex aic jcsb">
								<h3 class="c222 f15 tb">{ans.user.nickName}</h3>
								{#if username && ans.user.username === username}
								<div class="flex g8 f12 c999">
									<button on:click={() => modifyAnswer(ans)} class="">수정</button>
									<button on:click={deleteAnswer(ans)} class="">삭제</button>
								</div>
								{/if}
							</div>
							<p class="c333 f14 mt8">{ans.comment}</p>
							<span class="mt8 c999 f12">{displayedAt(new Date(ans.createDate))}</span>
						</li>
					{/if}
				{/each}
			</ul>
			<form on:submit|preventDefault={submitAnswerForm}>
				<div class="input-type-1 rel mt20">
					<input type="text" name="comment" placeholder="댓글을 입력하세요" style="padding-right: 60px;" />	
					<button type="submit" class="c333 f15 tb abs y-middle" style="right: 20px;">등록</button>
				</div>
				<div class="error-text-box wsn flex g8 mt8" data-field="comment">
					<span class="error-text f14 cCC0000"></span>
				</div>
			</form>
		</div>

	</div>
</div>


<script>
	export let data;

	console.log(data.result.data.sellArticle.photo);

	import { onMount } from 'svelte';
	import Swiper from 'swiper';
	import 'swiper/swiper-bundle.css';

	let swiper;
	let username = "";

	onMount(() => {
		fetchAnswers();
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
				credentials: "include"
		})
				.then(response => response.json())
				.then(data => {
						if ( data ) {
								username = data.data?.item?.username;
						}
				})
				.catch(error => {
						// 실패시 처리
						console.error(error);
				});

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
        try {
            const response = await fetch(`http://localhost:8080/api/articles/${data.result.data.sellArticle.id}`, {
                method: 'DELETE',
				credentials: 'include'
            });

            if (response.ok) {
                goto('/all_product/0', { replaceState: true }); 
            } else {
                // 삭제 실패
                console.error('Failed to delete article');
            }
        } catch (error) {
            console.error('Error while deleting article:', error);
        }
    }
	async function deleteAnswer(ans) {
    // 확인을 눌렀을 때 true 반환, 취소를 눌렀을 때 false 반환
    const confirmed = window.confirm("정말로 삭제하시겠습니까?");
    
    if (confirmed) {
        const answerId = ans.id;
        try {
            const response = await fetch(`http://localhost:8080/api/answers/${answerId}`, {
                method: 'DELETE',
                credentials: 'include'
            });

            if (response.ok) {
                window.alert("삭제되었습니다");
                fetchAnswers();
            } else {
                window.alert("삭제가 실패하였습니다");
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
		console.log("댓글 : " + comment);
		
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
            // AJAX 요청을 통해 댓글 데이터 가져오기
            const response = await fetch(`http://localhost:8080/api/answers/${data.articleId}`, {
                method: 'GET',
                credentials: 'include'
            });

            if (response.ok) {
                const newAnswers =await response.json();
                // 새로운 댓글 데이터로 업데이트
				console.log("댓글 : " + newAnswers);
                data.result2.data.answers = newAnswers.data.answers;
            } else {
                // 댓글 데이터 가져오기 실패
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
			console.log('Requesting with comment:', comment);
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
</script>

<div class="product-detail-area bsb w100per rel zi2 pt60">
	<div class="con w100per">
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
					<div class="swiper-pagination"></div>
				</div>
			</div>
			<div class="product-text-box">
				<div class="flex aic jcsb">
					<ul class="flex g4">
						<li class="f13 bdr4 bsb pt4 pb4 pl8 pr8 b005DE8 cfff">{data.result.data.sellArticle.area}</li>
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
								<img src="/img/ico_chat_777.svg" alt="" />
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
					style="border-top: 1px solid #dbdbdb; border-bottom: 1px solid #dbdbdb;"
				>
					{@html data.result.data.sellArticle.content.replace(/\r\n/g, '<br>')}
				</p>

				<!--본인 작성 글이 아닐 경우-->
				<div class="flex aic g12 bsb pl16 pr16 mt20">
					<button class="favor-box img-box w32" id="favor_btn">
						<img src="/img/ico_heart.svg" alt="" />
					</button>
					<a href="/" class="btn-type-1" style="width: calc(100% - 44px);">채팅하기</a>
				</div>
				<!--본인 작성 글-->
				{#if username && data.result.data.sellArticle.author.username === username}
					<div class="flex aic g12 bsb pl16 pr16 mt20">
						
						<a href="/sales_post/modify/{data.result.data.sellArticle.id}" class="btn-type-1 w50per">수정하기</a>
						<button on:click={deleteArticle} class="btn-type-1-2 w50per">삭제하기</button>
					</div>
				{/if}
			</div>
		</div>
	</div>
</div>
<form on:submit|preventDefault={submitAnswerForm}>
	<h3 class="c333 f18 tb mb16">댓글<span class="tb cCC0000 inblock">*</span></h3>
	<div class="input-type-1">
		<input type="text" name="comment" placeholder="댓글을 입력하세요" />
	</div>
	<div class="error-text-box wsn flex g8 mt8" data-field="comment">
		<span class="error-text f14 cCC0000"></span>
	</div>
	<div class="flex g8 mgc mt80 w100per" style="max-width: 360px;">
		<input type="submit" value="저장" class="btn-type-1 w100per" />
	</div>
</form>
{#each data.result2.data.answers as ans}
	{#if editingAnswer === ans}
		<form on:submit|preventDefault={submitModifiedAnswer}>
			<h3 class="c333 f18 tb mb16">댓글 수정<span class="tb cCC0000 inblock">*</span></h3>
			<div class="input-type-1">
				<input type="text" name="modifiedComment" bind:value={modifiedComment} placeholder="수정된 댓글을 입력하세요" />
			</div>
			<div class="error-text-box wsn flex g8 mt8" data-field="modifiedComment">
				<span class="error-text f14 cCC0000"></span>
			</div>
			<div class="flex g8 mgc mt80 w100per" style="max-width: 360px;">
				<input type="submit" value="저장" class="btn-type-1 w50per" />
				<button on:click={cancelModification} class="btn-type-1 w50per">취소</button>
			</div>
		</form>
	{:else}
		<ul>
			<li>{ans.id}</li>
			<li>{ans.comment}</li>
			<li>{ans.user.nickName}</li>
			<li>{displayedAt(new Date(ans.createDate))}</li>
			{#if username && ans.user.username === username}
				<button on:click={deleteAnswer(ans)} class="btn-type-1-2 w50per">삭제하기</button>
				<button on:click={() => modifyAnswer(ans)} class="btn-type-1-2 w50per">수정하기</button>
			{/if}
		</ul>
	{/if}
{/each}
<script>
	import { goto } from '$app/navigation';
	export let data;
	// import { navigate } from 'svelte-routing';
	import { onMount } from 'svelte';
	let directly = false;
	let parcel = false;
	let selectedImages = [];
	let existingImages = []; // 기존에 저장된 이미지 목록

	let category = ''; // 기존에 저장된 카테고리 값
	let subject = ''; // 기존에 저장된 제목 값
	let content = ''; // 기존에 저장된 내용 값
	let price = ''; // 기존에 저장된 금액 값
	let area = ''; // 기존에 저장된 지역 값

	onMount(async () => {
		category = data.data.sellArticle.category;
		subject = data.data.sellArticle.subject;
		content = data.data.sellArticle.content;
		price = data.data.sellArticle.price.toString();
		area = data.data.sellArticle.area;
		existingImages = data.data.sellArticle.photo; // 기존 이미지 목록 가져오기
		directly = data.data.sellArticle.directly;
		parcel = data.data.sellArticle.parcel;

		// 이미지 미리보기에 기존 이미지 추가
		if (existingImages && Array.isArray(existingImages)) {
			const imgPreviewContainer = document.getElementById('imgPreviewContainer');
			existingImages.forEach((photo) => {
				const imgBox = document.createElement('li');
				imgBox.className = 'img-box rel';

				const imgPreview = document.createElement('img');
				imgPreview.src = `http://localhost:8080/api/gen/${photo.filePath}`;

				const removeButton = document.createElement('button');
				removeButton.className = 'img-box abs w20 zi2 xy-tr cp';
				removeButton.innerHTML = '<img src="/img/ico_point_x.svg" alt="">';
				removeButton.onclick = function () {
					imgPreviewContainer.removeChild(imgBox);
					// 기존 이미지 목록에서도 제거
					const index = existingImages.indexOf(photo);
					if (index !== -1) {
						existingImages.splice(index, 1);
					}
				};

				imgBox.appendChild(imgPreview);
				imgBox.appendChild(removeButton);

				imgPreviewContainer.appendChild(imgBox);
			});
		}

		// 택배/직거래 체크 여부 설정
		document.getElementById('directly').checked = directly;
		document.getElementById('parcel').checked = parcel;
	});

	const submitArticleForm = async (event) => {
		event.preventDefault();

		const confirmed = window.confirm('변경사항을 저장하시겠습니까?');

		if (confirmed) {
		const form = event.target;
		const formData = new FormData(form);

		const uniqueImages = Array.from(new Set(selectedImages)); // 중복 제거
		uniqueImages.forEach((image, index) => {
			formData.append('postImage', image);
		});

		// 클라이언트 측 유효성 검사 수행
		const area = formData.get('area');
		const category = formData.get('category');
		const subject = formData.get('subject');
		const postImage = formData.get('postImage');
		const content = formData.get('content');
		const price = formData.get('price');

		const errors = {};

		const numericPrice = Number(price.replace(/,/g, ''));
		formData.set('price', numericPrice);
		formData.set('directly', directly);
		formData.set('parcel', parcel);

		if (!category) {
			errors.category = '카테고리를 선택하세요.';
		}

		if (!subject.trim()) {
			errors.subject = '제목을 입력하세요.';
		}

		// if (!postImage) {
		// 	errors.postImage = '이미지를 선택하세요.';
		// }

		if (!content.trim()) {
			errors.content = '내용을 입력하세요.';
		}

		if (!price || isNaN(numericPrice) || numericPrice <= 0) {
			errors.price = '유효한 금액을 입력하세요.';
		}

		if (!area.trim()) {
			errors.area = '지역을 입력하세요.';
		}

		if (!directly && !parcel) {
			errors.transactionType = '거래 유형을 선택하세요.';
		}

		// 클라이언트 측에서 유효성 검사 실패 시 제출 중단
		if (Object.keys(errors).length > 0) {
			updateErrorMessages(errors);
			return;
		}

		// 서버로 데이터 전송
		try {
			const response = await fetch(
				`http://localhost:8080/api/articles/${data.data.sellArticle.id}`,
				{
					method: 'PATCH',
					credentials: 'include',
					body: formData
				}
			);

			if (response.ok) {
				const responseData = await response.json();
				console.log(responseData);
				window.alert('수정되었습니다.');
				goto(`/sales_post/detail/${data.data.sellArticle.id}`);
			} else {
				const responseData = await response.json();
				console.error(responseData);
				window.alert('수정에 실패했습니다.');
			}
		} catch (error) {
			console.error('Error submitting form:', error);
		}
	}
	};

	const updateErrorMessages = (errors) => {
		// 각 오류 메시지 업데이트
		for (const fieldName in errors) {
			const errorElement = document.querySelector(
				`.error-text-box[data-field="${fieldName}"] .error-text`
			);
			if (errorElement) {
				errorElement.textContent = errors[fieldName];
			}
		}
	};

	function previewImage(event) {
		const imgPreviewContainer = document.getElementById('imgPreviewContainer');

		// 새로운 이미지만 추가
		for (const file of event.target.files) {
			if (!selectedImages.includes(file)) {
				// 중복 체크
				const reader = new FileReader();

				reader.onload = function (e) {
					const imgPreview = document.createElement('img');
					imgPreview.src = e.target.result;

					const imgBox = document.createElement('li');
					imgBox.className = 'img-box rel';

					const removeButton = document.createElement('button');
					removeButton.className = 'img-box abs w20 zi2 xy-tr cp';
					removeButton.innerHTML = '<img src="/img/ico_point_x.svg" alt="">';
					removeButton.onclick = function () {
						imgPreviewContainer.removeChild(imgBox);
						const index = selectedImages.indexOf(file);
						if (index !== -1) {
							selectedImages.splice(index, 1);
						}
					};

					imgBox.appendChild(imgPreview);
					imgBox.appendChild(removeButton);

					imgPreviewContainer.appendChild(imgBox);

					// 이미지 추가 시에 selectedImages 배열에도 추가
					selectedImages.push(file);
				};

				reader.readAsDataURL(file);
			}
		}

		// 파일 선택이 변경되었음을 알림
		event.target.value = null; // 이 부분이 추가되었습니다.
	}

	// 숫자 포맷팅 함수
	const formatNumber = (value) => {
		return value.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	};

	const handleInput = (event) => {
		// 입력된 값에서 숫자만 추출하고, 쉼표를 추가하여 price 변수에 저장
		price = formatNumber(event.target.value);
	};

	const confirmAndProceed = async (callback) => {
    const confirmed = window.confirm('변경사항을 저장하지 않고 나가시겠습니까?');

    if (confirmed) {
      callback();
    }
  };
  
  const handleCancel = () => {
    confirmAndProceed(() => {
		goto(`/sales_post/detail/${data.data.sellArticle.id}`);
    });
  };
</script>

<div class="cnt-area w100per rel zi2">
	<div class="con w100per">
		<h1
			class="title-text lh120 c222 tb f32 mb40 tac wow fadeIn"
			data-wow-delay="0.3s"
			data-wow-duration="0.6s"
		>
			판매 등록 수정
		</h1>
		<div class="signup-box flex fdc wow fadeIn" data-wow-delay="0.6s" data-wow-duration="0.6s">
			<form on:submit|preventDefault={submitArticleForm}>
				<ul class="flex fdc g36">
					<li>
						<h3 class="c333 f18 tb mb16">카테고리<span class="tb cCC0000 inblock">*</span></h3>
						<div class="select-type-2 rel">
							<select name="category" bind:value={category}>
								<option value="">모든 카테고리</option>
								<option value="핸드폰">핸드폰</option>
								<option value="테블릿">테블릿</option>
								<option value="노트북">노트북</option>
								<option value="모니터">모니터</option>
								<option value="스마트워치">스마트워치</option>
								<option value="이어폰">이어폰</option>
							</select>
							<span class="arrow img-box abs y-middle">
								<img src="/img/arrow_bottom_999999.svg" alt="" />
							</span>
						</div>
						<div class="error-text-box wsn flex g8 mt8" data-field="category">
							<span class="error-text f14 cCC0000"></span>
						</div>
					</li>
					<li>
						<h3 class="c333 f18 tb mb16">제목<span class="tb cCC0000 inblock">*</span></h3>
						<div class="input-type-1">
							<input type="text" name="subject" bind:value={subject} placeholder="제목" />
						</div>
						<div class="error-text-box wsn flex g8 mt8" data-field="subject">
							<span class="error-text f14 cCC0000"></span>
						</div>
					</li>
					<li>
						<h3 class="c333 f18 tb mb16">이미지<span class="tb cCC0000 inblock">*</span></h3>
						<ul id="imgPreviewContainer" class="product-img-area flex g8">
							<li class="file-btn">
								<input
									multiple="multiple"
									type="file"
									id="product_img_btn"
									on:change={previewImage}
								/>
								<label for="product_img_btn" class="wh100per block rel cp">
									<div class="img-box w32 abs xy-middle">
										<img src="/img/ico_plus_333.svg" alt="" />
									</div>
								</label>
							</li>
						</ul>
						<div class="error-text-box wsn flex g8 mt8" data-field="postImage">
							<span class="error-text f14 cCC0000"></span>
						</div>
					</li>
					<li>
						<h3 class="c333 f18 tb mb16">내용<span class="tb cCC0000 inblock">*</span></h3>
						<div class="textarea-type-1">
							<textarea name="content" bind:value={content} placeholder="내용"></textarea>
						</div>
						<div class="error-text-box wsn flex g8 mt8" data-field="content">
							<span class="error-text f14 cCC0000"></span>
						</div>
					</li>
					<li>
						<ul class="flex g8">
							<li class="w50per">
								<h3 class="c333 f18 tb mb16">금액<span class="tb cCC0000 inblock">*</span></h3>
								<div class="input-type-1 rel">
									<input
										type="text"
										name="price"
										placeholder="금액"
										style="padding-right: 24px;"
										id="moneyInput"
										bind:value={price}
										on:input={handleInput}
									/>
									<span class="abs y-middle f16 c999" style="right: 16px;">원</span>
								</div>
								<div class="error-text-box wsn flex g8 mt8" data-field="price">
									<span class="error-text f14 cCC0000"></span>
								</div>
							</li>
							<li class="w50per">
								<h3 class="c333 f18 tb mb16">지역<span class="tb cCC0000 inblock">*</span></h3>
								<div class="input-type-1">
									<input type="text" name="area" bind:value={area} placeholder="지역" />
								</div>
								<div class="error-text-box wsn flex g8 mt8" data-field="area">
									<span class="error-text f14 cCC0000"></span>
								</div>
							</li>
						</ul>
					</li>
					<li>
						<h3 class="c333 f18 tb mb16">거래 유형<span class="tb cCC0000 inblock">*</span></h3>
						<div class="flex aic g32">
							<div class="check-text-type-3 type-2 flex aic">
								<input type="checkbox" bind:checked={directly} id="directly" />
								<label for="directly">
									<span class="text">직거래</span>
								</label>
							</div>
							<div class="check-text-type-3 type-2 flex aic">
								<input type="checkbox" bind:checked={parcel} id="parcel" />
								<label for="parcel">
									<span class="text">택배거래</span>
								</label>
							</div>
						</div>
						<div class="error-text-box wsn flex g8 mt8" data-field="transactionType">
							<span class="error-text f14 cCC0000"></span>
						</div>
					</li>
				</ul>
				<div class="flex g8 mgc mt80 w100per" style="max-width: 360px;">
					<input type="submit" value="저장" class="btn-type-1 w100per" />
					<a on:click={handleCancel} class="btn-type-1-2 w100per">취소</a>
				</div>
			</form>
		</div>
	</div>
</div>

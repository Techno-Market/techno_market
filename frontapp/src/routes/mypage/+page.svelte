<script>
    import { onMount } from 'svelte';

    let member = {};
    onMount(()=> {
        // 로그인한 회원정보 불러오기
        fetch('http://localhost:8080/api/user/me', {
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => {
            // 성공시 데이터 member 에 담기
            if ( data ) {
                member = data.data?.item;
                nickName = member.nickName;
            }
        })
        .catch(error => {
            // 실패시 처리
            console.error(error);
        });
    })

    let nickName ="";
    let password = "";
    let password1 = "";
    let password2 = "";

    let nickNameEmpty = false;
    let password1Empty = false;
    let password2Empty = false;

    let nickNameDuplicationSuccess = true;
    let nickNameDuplicationFail = false;

    let passwordCheckSuccess = false;
    let passwordCheckFail = false;

    $: isDisabled = !passwordCheckSuccess;

    let passwordMatchSuccess = false;
    let passwordMatchFail = false;

    //닉네임 중복 검사
    const handleNickNameInput = async () => {
        nickNameEmpty = false;

        let nickNameValue = document.querySelector('#nickName').value;

        if (!nickNameValue) {
            nickNameDuplicationSuccess = false;
            nickNameDuplicationFail = false;
            return;
        }

        let response = await fetch(`http://localhost:8080/api/user/nickNameCheck/${encodeURIComponent(nickNameValue)}`);
        let result = await response.json();

        if (nickNameValue === member.nickName) {
            nickNameDuplicationSuccess = true;
            nickNameDuplicationFail = false;
        }
        else {
            if (result.data) {
            nickNameDuplicationSuccess = false;
            nickNameDuplicationFail = true;
            } else {
                nickNameDuplicationSuccess = true;
                nickNameDuplicationFail = false;
            }
        }
    };

    //기존 패스워스 검사
    const handlePasswordInput = async () => {
            const response = await fetch('http://localhost:8080/api/user/passwordCheck', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: "include",
            body: JSON.stringify({password}),
        });
        let result = await response.json();

        if (result.data) {
            passwordCheckSuccess = true;
            passwordCheckFail = false;
        }
        else {
            passwordCheckSuccess = false;
            passwordCheckFail = true;
        }
    };

    //패스워드 일치 검사
    const handlePassword2Input = () => {
        password2Empty = false;

        if (password1 === password2) {
            passwordMatchSuccess = true;
            passwordMatchFail = false;
        }
        else {
            passwordMatchSuccess = false;
            passwordMatchFail = true;
        }
    };
		

    const submitSignupFormModify = async (event) => {
        event.preventDefault();

        if (nickName.trim() === "") nickNameEmpty = true;
        else nickNameEmpty = false;

        if (passwordCheckSuccess) {
            if (password1.trim() === "") password1Empty = true;
            else password1Empty = false;

            if (password2.trim() === "") password2Empty = true;
            else password2Empty = false;
        }

        if (nickNameDuplicationSuccess && !nickNameEmpty && !password1Empty && !password2Empty && passwordMatchSuccess) {
            try {
                const data = {
                    nickName,
                    password: password1,
                };
                const response = await fetch('http://localhost:8080/api/user/mypage', {
                    method: 'PATCH',
                    credentials: "include",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                });

                if (response.ok) {
                    const responseData = await response.json();
                    console.log(responseData);
                    window.alert('수정이 완료되었습니다.');
                    // window.location.href = 'http://localhost:5173/';
                } else {
                    const responseData = await response.json();
                    console.error(responseData);
                    window.alert('수정이 실패했습니다.');
                }
            } catch (error) {
                console.error('Error submitting form:', error);
            }
        }
        else window.alert('입력 내용을 확인해 주세요.');
    };
</script>

<style>
    .error-text,
    .confirm-text {
        display: none;
    }
    .error-text.active,
    .confirm-text.active {
        display: block;
    }
    
</style>

<div class="signup-area login-cnt-area w100per rel zi2">
    <div class="con w100per">
        <h1 class="title-text lh120 tb tac">내 정보</h1>
        <form on:submit|preventDefault={submitSignupFormModify}>
            <div class="signup-box flex fdc">
                <ul class="flex fdc g36">
                    <li>
                        <h3 class="c333 f18 tb mb16">아이디</h3>
                        <div class="input-type-1">
                            <input type="text" placeholder="아이디" value="{member.username}" disabled>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">닉네임<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <input type="text" placeholder="닉네임" id="nickName" bind:value={nickName} on:input={handleNickNameInput}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${nickNameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                            <span class={`error-text f14 cCC0000 mt8 ${nickNameDuplicationFail ? 'active' : ''}`}>중복된 닉네임 입니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${nickNameDuplicationSuccess ? 'active' : ''}`}>사용가능한 닉네임 입니다.</span>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">현재 비밀번호<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <div class="input-btn-box flex g8">
                                <div class="input-type-1">
                                    <input type="password" placeholder="비밀번호" bind:value={password}>
                                </div>
                                <button type="button" class="btn-type-2" on:click={handlePasswordInput}>확인</button>
                            </div>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${passwordCheckFail ? 'active' : ''}`}>비밀번호가 일치하지 않습니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${passwordCheckSuccess ? 'active' : ''}`}>비밀번호가 일치합니다.</span>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">새로운 비밀번호<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <input type="password" placeholder="비밀번호" bind:value={password1} on:input={() => password1Empty = false} disabled={isDisabled}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${password1Empty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        </div>
                        <div class="input-type-1 mt8">
                            <input type="password" placeholder="비밀번호 확인" bind:value={password2} on:input={handlePassword2Input} disabled={isDisabled}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${password2Empty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                            <span class={`error-text f14 cCC0000 mt8 ${passwordMatchFail ? 'active' : ''}`}>비밀번호가 일치하지 않습니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${passwordMatchSuccess ? 'active' : ''}`}>비밀번호가 일치합니다.</span>
                        </div>
                    </li>
                    <!-- <li>
                        <h3 class="c333 f18 tb mb16">이메일<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <input type="text" placeholder="이메일" bind:value={email} disabled>
                        </div>
                    </li> -->
                    <li>
                        <h3 class="c333 f18 tb mb16">이름</h3>
                        <div class="input-type-1">
                            <input type="text" placeholder="이름" value="{member.name}" disabled>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">생년월일</h3>
                        <div class="date-type-1">
                            <input type="date" value="{member.birthDate}" disabled>
                        </div>
                    </li>
                </ul>
                <div class="flex g8 mt60 w100per">
					<input type="submit" value="수정" class="btn-type-1 w100per" />
					<a href="/" class="btn-type-1-2 w100per">취소</a>
				</div>
            </div>
        </form>
    </div>
</div>
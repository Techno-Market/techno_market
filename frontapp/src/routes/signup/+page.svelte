<script>
    let username = "";
    let nickName = "";
    let password1 = "";
    let password2 = "";
    let email = "";
    let emailNum = "";
    let name = "";
    let birthDate = "";
    let privacy = false;

    let usernameEmpty = false;
    let nickNameEmpty = false;
    let password1Empty = false;
    let password2Empty = false;
    let emailEmpty = false;
    let emailNumEmpty = false;
    let nameEmpty = false;
    let birthDateEmpty = false;
    let privacyEmpty = false;

    let usernameDuplicationSuccess = false;
    let usernameDuplicationFail = false;

    let nickNameDuplicationSuccess = false;
    let nickNameDuplicationFail = false;

    let passwordMatchSuccess = false;
    let passwordMatchFail = false;

    //아이디 중복 검사
    const handleusernameInput = async () => {
        usernameEmpty = false;

        let usernameValue = document.querySelector('#username').value;

        if (!usernameValue) {
            usernameDuplicationSuccess = false;
            usernameDuplicationFail = false;
            return;
        }

        let response = await fetch(`http://localhost:8080/api/user/userNameCheck/${encodeURIComponent(usernameValue)}`);
        let result = await response.json();
        if (result.data) {
            usernameDuplicationSuccess = false;
            usernameDuplicationFail = true;
        } else {
            usernameDuplicationSuccess = true;
            usernameDuplicationFail = false;
        }
    };

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
        if (result.data) {
            nickNameDuplicationSuccess = false;
            nickNameDuplicationFail = true;
        } else {
            nickNameDuplicationSuccess = true;
            nickNameDuplicationFail = false;
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
		
    const submitSignupForm = async (event) => {
        event.preventDefault();

        //빈 공백 유효성 검사
        if (username.trim() === "") usernameEmpty = true;
        else usernameEmpty = false;

        if (nickName.trim() === "") nickNameEmpty = true;
        else nickNameEmpty = false;

        if (password1.trim() === "") password1Empty = true;
        else password1Empty = false;

        if (password2.trim() === "") password2Empty = true;
        else password2Empty = false;

        if (email.trim() === "") emailEmpty = true;
        else emailEmpty = false;

        if (emailNum.trim() === "") emailNumEmpty = true;
        else emailNumEmpty = false;

        if (name.trim() === "") nameEmpty = true;
        else nameEmpty = false;

        if (birthDate.trim() === "") birthDateEmpty = true;
        else birthDateEmpty = false;

        if (!privacy) privacyEmpty = true;
        else privacyEmpty = false;

        if (!usernameEmpty && !nickNameEmpty && !password1Empty && !password2Empty && !emailEmpty && !emailNumEmpty && !nameEmpty && !birthDateEmpty && !privacyEmpty && passwordMatchSuccess && usernameDuplicationSuccess && nickNameDuplicationSuccess) {
            try {
                const data = {
                    username,
                    nickName,
                    password: password1,
                    email,
                    name,
                    birthDate,
                };

                const response = await fetch('http://localhost:8080/api/user/signup', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                });

                if (response.ok) {
                    window.alert('회원가입이 완료되었습니다.');
                    window.location.href = 'http://localhost:5173/login';
                } else {
                    window.alert('회원가입이 실패했습니다.');
                }
            } catch (error) {
                console.error('Error submitting form:', error);
            }
        }
        else window.alert('입력 내용을 확인해 주세요.');
    };

    let verificationEmailSent = false; // 이메일 전송 성공 여부
    let emailVerificationSuccess = false; // 이메일 인증 성공 여부
    let emailVerificationError = false; // 이메일 인증 실패 여부

    const sendVerificationEmail = async () => {
        try {
            // 이메일 전송 요청
            const response = await fetch('http://localhost:8080/api/email/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email }),
            });

            if (response.ok) {
                // 성공적으로 이메일이 전송되었을 때의 로직
                verificationEmailSent = true;
            } else {
                // 이메일 전송 실패 시의 로직
                const responseData = await response.json();
                window.alert(`이메일 전송 실패: ${responseData.message}`);
            }
        } catch (error) {
            console.error('Error sending verification email:', error);
        }
    };

    const verifyEmail = async () => {
        const verificationCode = document.querySelector('input[name="verificationCode"]').value;
        const email = document.querySelector('input[name="email"]').value;

        try {
            const response = await fetch('http://localhost:8080/api/email/verify', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: email,
                    code: verificationCode,
                }),
            });

            if (response.ok) {
                emailVerificationSuccess = true;
                emailVerificationError = false;

            } else {
                // 실패 시 사용자에게 메시지 표시 또는 다른 처리 수행
                emailVerificationSuccess = false;
                emailVerificationError = true;

            }
        } catch (error) {
            console.error('Error verifying code:', error);
        }
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
        <h1 class="title-text lh120 tb tac wow fadeInUp" data-wow-delay="0.3s" data-wow-duration="0.6s">회원가입</h1>
        <form on:submit|preventDefault={submitSignupForm}>
            <div class="signup-box flex fdc wow fadeInUp" data-wow-delay="0.6s" data-wow-duration="0.6s">
                <ul class="flex fdc g36">
                    <li>
                        <h3 class="c333 f18 tb mb16">아이디<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <input type="text" placeholder="아이디" id="username" bind:value={username} on:input={handleusernameInput}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${usernameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                            <span class={`error-text f14 cCC0000 mt8 ${usernameDuplicationFail ? 'active' : ''}`}>중복된 아이디 입니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${usernameDuplicationSuccess ? 'active' : ''}`}>사용가능한 아이디 입니다.</span>
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
                        <h3 class="c333 f18 tb mb16">비밀번호<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <input type="password" placeholder="비밀번호" bind:value={password1} on:input={() => password1Empty = false}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${password1Empty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        </div>
                        <div class="input-type-1 mt8">
                            <input type="password" placeholder="비밀번호 확인" bind:value={password2} on:input={handlePassword2Input}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${password2Empty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                            <span class={`error-text f14 cCC0000 mt8 ${passwordMatchFail ? 'active' : ''}`}>비밀번호가 일치하지 않습니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${passwordMatchSuccess ? 'active' : ''}`}>비밀번호가 일치합니다.</span>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">이메일<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-btn-box flex g8">
                            <div class="input-type-1">
                                <input type="text" placeholder="이메일" name="email" bind:value={email} on:input={() => emailEmpty = false}>
                            </div>
                            <button type="button" class="btn-type-2" on:click={sendVerificationEmail}>전송</button>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${emailEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                            <span class="error-text f14 cCC0000 mt8">올바른 이메일 형식이 아닙니다.</span>
                            <span class="error-text f14 cCC0000 mt8">이미 인증된 이메일 입니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${verificationEmailSent ? 'active' : ''}`}>인증번호가 발송되었습니다.</span>
                        </div>
                        <div class="input-btn-box flex g8 mt8">
                            <div class="input-type-1">
                                <input type="text" placeholder="인증번호" name="verificationCode" bind:value={emailNum} on:input={() => emailNumEmpty = false}>
                            </div>
                            <button type="button" class="btn-type-2" on:click={verifyEmail}>확인</button>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${emailNumEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                            <span class={`error-text f14 cCC0000 mt8 ${emailVerificationError ? 'active' : ''}`}>인증번호가 일치하지 않습니다.</span>
                            <span class={`confirm-text f14 c009521 mt8 ${emailVerificationSuccess ? 'active' : ''}`}>인증번호가 일치합니다.</span>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">이름<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="input-type-1">
                            <input type="text" placeholder="이름" bind:value={name} on:input={() => nameEmpty = false}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${nameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        </div>
                    </li>
                    <li>
                        <h3 class="c333 f18 tb mb16">생년월일<span class="tb cCC0000 inblock">*</span></h3>
                        <div class="date-type-1">
                            <input type="date" bind:value={birthDate} on:input={() => birthDateEmpty = false}>
                        </div>
                        <div class="error-text-box wsn flex g8">
                            <span class={`error-text f14 cCC0000 mt8 ${birthDateEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        </div>
                    </li>
                </ul>
                <div class="check-text-type-2 mt100 flex aic">
                    <input type="checkbox" id="privacy" bind:checked={privacy} on:change={() => privacyEmpty = false}>
                    <label for="privacy">
                        <span class="text">개인정보 수집 및 이용 동의</span>
                    </label>
                </div>
                <div class="error-text-box wsn flex g8">
                    <span class={`error-text f14 cCC0000 mt8 ${privacyEmpty ? 'active' : ''}`}>필수 동의 항목 입니다.</span>
                </div>
                <button type="submit" class="btn-type-1 mt40">회원가입</button>
            </div>
        </form>
    </div>
</div>
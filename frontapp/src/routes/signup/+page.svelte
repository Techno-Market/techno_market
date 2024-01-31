<script>
    let username = "";
    let nickname = "";
    let password1 = "";
    let password2 = "";
    let email = "";
    let name = "";
    let birthDate = "";
    let privacy = false;

    let usernameEmpty = false;
    let nicknameEmpty = false;
    let password1Empty = false;
    let password2Empty = false;
    let emailEmpty = false;
    let nameEmpty = false;
    let birthDateEmpty = false;
    let privacyEmpty = false;

    const handleSignup = () => {
        if (username.trim() === "") usernameEmpty = true;
        else usernameEmpty = false;

        if (nickname.trim() === "") nicknameEmpty = true;
        else nicknameEmpty = false;

        if (password1.trim() === "") password1Empty = true;
        else password1Empty = false;

        if (password2.trim() === "") password2Empty = true;
        else password2Empty = false;

        if (email.trim() === "") emailEmpty = true;
        else emailEmpty = false;

        if (name.trim() === "") nameEmpty = true;
        else nameEmpty = false;

        if (birthDate.trim() === "") birthDateEmpty = true;
        else birthDateEmpty = false;

        if (!privacy) privacyEmpty = true;
        else privacyEmpty = false;

        if (!usernameEmpty && !nicknameEmpty && !password1Empty && !password2Empty && !emailEmpty && !nameEmpty && !birthDateEmpty && !privacyEmpty) {
            console.log("회원가입 성공");
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
        <div class="signup-box flex fdc wow fadeInUp" data-wow-delay="0.6s" data-wow-duration="0.6s">
            <ul class="flex fdc g36">
                <li>
                    <h3 class="c333 f18 tb mb16">아이디<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="text" placeholder="아이디" bind:value={username} on:input={() => usernameEmpty = false}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${usernameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000"></span>
                        <span class="error-text f14 cCC0000">중복된 아이디 입니다.</span>
                        <span class="confirm-text f14 c009521">사용가능한 아이디 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">닉네임<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="text" placeholder="닉네임" bind:value={nickname} on:input={() => nicknameEmpty = false}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${nicknameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">중복된 닉네임 입니다.</span>
                        <span class="confirm-text f14 c009521">사용가능한 아이디 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">비밀번호<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="password" placeholder="비밀번호" bind:value={password1} on:input={() => password1Empty = false}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${password1Empty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">사용할 수 없는 비밀번호 입니다.</span>
                        <span class="confirm-text f14 c009521">사용가능한 비밀번호 입니다.</span>
                    </div>
                    <div class="input-type-1 mt8">
                        <input type="password" placeholder="비밀번호 확인" bind:value={password2} on:input={() => password2Empty = false}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${password2Empty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">비밀번호가 일치하지 않습니다.</span>
                        <span class="confirm-text f14 c009521">비밀번호가 일치합니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">이메일<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-btn-box flex g8">
                        <div class="input-type-1">
                            <input type="text" placeholder="이메일">
                        </div>
                        <button type="button" class="btn-type-2">전송</button>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">올바른 이메일 형식이 아닙니다.</span>
                        <span class="error-text f14 cCC0000">이미 인증된 이메일 입니다.</span>
                        <span class="confirm-text f14 c009521">인증번호가 발송되었습니다.</span>
                    </div>
                    <div class="input-btn-box flex g8 mt8">
                        <div class="input-type-1">
                            <input type="text" placeholder="인증번호" bind:value={email} on:input={() => emailEmpty = false}>
                        </div>
                        <button type="button" class="btn-type-2">확인</button>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${emailEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">인증번호가 일치하지 않습니다.</span>
                        <span class="confirm-text f14 c009521">인증번호가 일치합니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">이름<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="text" placeholder="이름" bind:value={name} on:input={() => nameEmpty = false}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${nameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">생년월일<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="date-type-1">
                        <input type="date" bind:value={birthDate} on:input={() => birthDateEmpty = false}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class={`error-text f14 cCC0000 ${birthDateEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                    </div>
                </li>
            </ul>
            <div class="check-text-type-2 mt100 flex aic">
                <input type="checkbox" id="privacy" bind:checked={privacy} on:change={() => privacyEmpty = false}>
                <label for="privacy">
                    <span class="text">개인정보 수집 및 이용 동의</span>
                </label>
            </div>
            <div class="error-text-box wsn flex g8 mt8">
                <span class={`error-text f14 cCC0000 ${privacyEmpty ? 'active' : ''}`}>필수 동의 항목 입니다.</span>
            </div>
            <button type="button" class="btn-type-1 mt40" on:click={handleSignup}>회원가입</button>
        </div>
    </div>
</div>
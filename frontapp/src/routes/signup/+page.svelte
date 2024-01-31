<script>
    let username = "";
    let nickname = "";
    let password1 = "";
    let password2 = "";
    let email = "";
    let name = "";
    let birthDate = "";
    let privacy = "";
</script>

<div class="signup-area login-cnt-area w100per rel zi2">
    <div class="con w100per">
        <h1 class="title-text lh120 tb tac wow fadeInUp" data-wow-delay="0.3s" data-wow-duration="0.6s">회원가입</h1>
        <div class="signup-box flex fdc wow fadeInUp" data-wow-delay="0.6s" data-wow-duration="0.6s">
            <ul class="flex fdc g36">
                <li>
                    <h3 class="c333 f18 tb mb16">아이디<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="text" placeholder="아이디" bind:this={username}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">중복된 아이디 입니다.</span>
                        <span class="confirm-text f14 c009521">사용가능한 아이디 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">닉네임<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="text" placeholder="닉네임" bind:this={nickname}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">중복된 닉네임 입니다.</span>
                        <span class="confirm-text f14 c009521">사용가능한 아이디 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">비밀번호<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="password" placeholder="비밀번호" bind:this={password1}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">사용할 수 없는 비밀번호 입니다.</span>
                        <span class="confirm-text f14 c009521">사용가능한 비밀번호 입니다.</span>
                    </div>
                    <div class="input-type-1 mt8">
                        <input type="password" placeholder="비밀번호 확인" bind:this={password2}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
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
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">올바른 이메일 형식이 아닙니다.</span>
                        <span class="error-text f14 cCC0000">이미 인증된 이메일 입니다.</span>
                        <span class="confirm-text f14 c009521">인증번호가 발송되었습니다.</span>
                    </div>
                    <div class="input-btn-box flex g8 mt8">
                        <div class="input-type-1">
                            <input type="text" placeholder="인증번호" bind:this={email}>
                        </div>
                        <button type="button" class="btn-type-2">확인</button>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                        <span class="error-text f14 cCC0000">인증번호가 일치하지 않습니다.</span>
                        <span class="confirm-text f14 c009521">인증번호가 일치합니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">이름<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="text" placeholder="이름" bind:this={name}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">생년월일<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="date-type-1">
                        <input type="date" bind:this={birthDate}>
                    </div>
                    <div class="error-text-box wsn flex g8 mt8">
                        <span class="error-text f14 cCC0000">필수 입력 항목 입니다.</span>
                    </div>
                </li>
            </ul>
            <div class="check-text-type-2 mt100 flex aic">
                <input type="checkbox" id="privacy" bind:this={privacy}>
                <label for="privacy">
                    <span class="text">개인정보 수집 및 이용 동의</span>
                </label>
            </div>
            <div class="error-text-box wsn flex g8 mt8">
                <span class="error-text f14 cCC0000">필수 체크 항목 입니다.</span>
            </div>
            <button type="button" class="btn-type-1 mt40" id="signupBtn">회원가입</button>
        </div>
    </div>
</div>
<script>
    function submitLoginForm() {
        const form = this;

        form.username.value = form.username.value.trim();

        if (form.username.value.length === 0) {
            alert('Username is required');
            form.username.focus();
            return;
        }

        form.password.value = form.password.value.trim();

        if (form.password.value.length === 0) {
            alert('Password is required');
            form.password.focus();
            return;
        }

        fetch('http://localhost:8080/api/user/login', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: form.username.value,
                password: form.password.value
            })
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error(error);
            });
    }
</script>

<form on:submit|preventDefault={submitLoginForm}>
    <div class="login-area login-cnt-area w100per rel zi1">
        <div class="con w100per">
            <h1 class="title-text lh120 tb tac wow fadeInUp" data-wow-delay="0.3s" data-wow-duration="0.6s">로그인</h1>
            <div class="login-box flex fdc wow fadeInUp" data-wow-delay="0.6s" data-wow-duration="0.6s">
                <div class="input-type-1">
                    <input type="text" name="username" id="username" placeholder="아이디">
                </div>
                <div class="input-type-1 mt12">
                    <input type="password" name="password" id="password" placeholder="비밀번호">
                </div>
                <div class="error-text-box wsn flex g8 mt8">
                    <span class="error-text f14 cCC0000">아이디 또는 비밀번호가 올바르지 않습니다.</span>
                </div>
                <button type="submit" class="btn-type-1 mt16">로그인</button>

                <div class="check-text-type-1 mt16">
                    <input type="checkbox" class="save_id" id="saveId" name="saveId">
                    <label for="saveId">
                        <span class="text c555 f14">아이디 저장</span>
                    </label>
                </div>
                <div class="add-menu-box flex jcc aic g12 mt40">
                    <a href="" class="c777 f14">아이디 찾기</a>
                    <span class="line"></span>
                    <a href="" class="c777 f14"> 비밀번호 찾기</a>
                    <span class="line"></span>
                    <a href="" class="c777 f14">회원가입</a>
                </div>
                <div class="sns-login-box mt70">
                    <h3 class="tm c333 f16 tac rel">SNS 계정으로 로그인</h3>
                    <ul class="sns-box flex aic jcc mt40 g50">
                        <li class="w36">
                            <a href="" class="img-box">
                                <img src="/img/ico_google.svg" alt="">
                            </a>
                        </li>
                        <li class="w36">
                            <a href="" class="img-box">
                                <img src="/img/ico_naver.svg" alt="">
                            </a>
                        </li>
                        <li class="w36">
                            <a href="/oauth2/authorization/kakao" class="img-box">
                                <img src="/img/ico_kakao.svg" alt="">
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</form>
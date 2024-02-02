<script>
  let username = "";
  let password = "";
  let usernameErrorVisible = false;
  let passwordErrorVisible = false;

  const handleSignup = () => {
    // 검증 로직 추가
    if (username.trim() === "") {
      usernameErrorVisible = true;
    } else {
      usernameErrorVisible = false;
    }

    if (password.trim() === "") {
      passwordErrorVisible = true;
    } else {
      passwordErrorVisible = false;
    }

    // 회원가입 로직 추가
    if (!usernameErrorVisible && !passwordErrorVisible) {
      // 실제 회원가입 로직 수행
      console.log("회원가입 성공");
    }
  };
</script>

<style>
  .error-text {
    display: none;
    color: #CC0000;
    font-size: 14px;
  }

  .error-text.active {
    display: block;
  }
</style>

<!-- 나머지 HTML 코드 -->
<input type="text" placeholder="아이디" bind:value={username} on:input={() => usernameErrorVisible = false}>
<span class={`error-text f14 cCC0000 ${usernameErrorVisible ? 'active' : ''}`}>필수 입력 항목 입니다.</span>

<input type="password" placeholder="비밀번호" bind:value={password} on:input={() => passwordErrorVisible = false}>
<span class={`error-text f14 cCC0000 ${passwordErrorVisible ? 'active' : ''}`}>필수 입력 항목 입니다.</span>

<button type="button" class="btn-type-1 mt40" on:click={handleSignup}>회원가입</button>

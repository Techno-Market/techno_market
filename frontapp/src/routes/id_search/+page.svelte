<script>
    let email ="";
    let name ="";
    
    let emailEmpty = false;
    let nameEmpty = false;

    const handleEmailInput = async () => {
        
    }

    const handleNameInput = async () => {
        
    }
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

<div class="id-search-area login-cnt-area w100per rel zi2">
    <div class="con w100per">
        <h1 class="title-text lh120 tb tac">아이디
            찾기</h1>
        <div class="flex fdc">
            <ul class="flex fdc g36">
                <li>
                    <h3 class="c333 f18 tb mb16">이메일<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="email" bind:value={email} on:input={handleEmailInput} placeholder="이메일">
                    </div>
                    <div class="error-text-box wsn flex g8">
                        <span class={`error-text f14 cCC0000 mt8 ${emailEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                    </div>
                </li>
                <li>
                    <h3 class="c333 f18 tb mb16">이름<span class="tb cCC0000 inblock">*</span></h3>
                    <div class="input-type-1">
                        <input type="email" bind:value={name} on:input={handleNameInput} placeholder="이름">
                    </div>
                    <div class="error-text-box wsn flex g8">
                        <span class={`error-text f14 cCC0000 mt8 ${nameEmpty ? 'active' : ''}`}>필수 입력 항목 입니다.</span>
                    </div>
                </li>
            </ul>
            <button type="submit" class="btn-type-1 mt80">아이디 찾기</button>
        </div>
    </div>
</div>
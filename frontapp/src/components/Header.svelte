<script>
    import { goto } from '$app/navigation';

    let searchQuery = '';
    let currentPage = 0; 

    function performSearch() {
        const searchKeyword = searchQuery.trim();
        if (searchKeyword !== '') {
            goto(`/all_product/search/${encodeURIComponent(searchKeyword)}/${currentPage}`);
        }
    }
    function handleKeyPress(event) {
        if (event.key === 'Enter') {
            performSearch();
        }
    }

    import { onMount } from 'svelte';

    let member = {};
    let isLogin = false;
    onMount(()=> {
        // 로그인한 회원정보 불러오기
        fetch('http://localhost:8080/api/user/me', {
            credentials: "include"
        })
            .then(response => response.json())
            .then(data => {
                // 성공시 데이터 member 에 담기
                if ( data ) {
                    console.log(data.data?.item);
                    member = data.data?.item;
                    if (data.data?.item) {
                        isLogin = true
                        goto("/")
                    }
                }
            })
            .catch(error => {
                // 실패시 처리
                isLogin = false;
                console.error(error);
            });
    })

    const logout = () => {
           fetch('http://localhost:8080/api/user/logout', {
               method: "POST",
               credentials: "include"
           })
               .then(response => response.json())
               .then(data => {
                   console.log(data);
                   goto("/")
                   location.reload(true);
               })
               .catch(error => {
                   // 실패시 처리
                   console.error(error);
               });
    }
</script>
<header class="header-area w100per bfff fixed zi5 xy-tl">
    <div class="con wh100per">
        <div class="header-box-1 w100per flex aic jcsb h70">
            <div class="left-box flex aic g40">
                <a href="/" class="logo-box img-box">
                    <img src="/img/logo.svg" alt="">
                </a>
                <div class="flex fdc g8" style="width: 480px;">
                    <div class="search-type-1 w100per rel">
                        <input type="search" bind:value={searchQuery} placeholder="검색어를 입력해 주세요." autocomplete="off" on:keypress={handleKeyPress}>
                        <button class="search-btn img-box abs y-middle" on:click={performSearch}>
                            <img src="/img/ico_search_222.svg" alt="">
                        </button>
                    </div>
                </div>
            </div>
            <div class="right-box flex aic cg12">
                <ul class="sub-menu-box flex aic c999 f14 cg12">
                    {#if isLogin }
                        <li>
                            <a href="">내 정보</a>
                        </li>
                        <li>
                            <a href="">나의 판매글</a>
                        </li>
                        <li>
                            <a href="/wishlist" class="c333 f14 tm">찜 목록</a>
                        </li>
                        <li>
                            <a href="/" on:click={() => logout()} class="c333 f14 tm">로그아웃</a>
                        </li>
                    {:else}
                        <li>
                            <a href="/login">로그인</a>
                        </li>
                        <li>
                            <a href="/signup">회원가입</a>
                        </li>
                    {/if}
                </ul>
                {#if isLogin }
                    <span class="w1 h12 bddd"></span>
                    <span class="f14 tm c222">{member.nickName}</span>
                {/if}
            </div>
        </div>
        <div class="header-box-2 w100per h50 flex aic jcsb">
            <div class="left-box flex aic g24">
                <a href="http://localhost:5173/all_product/0" class="f14 b333 cfff bsb bdr4 pt8 pb8 pl16 pr16">ALL</a>
                <ul class="menu-box flex aic g32 c222 f16">
                    <li>
                        <a href="http://localhost:5173/all_product/category/mobile/0" class="tm">핸드폰</a>
                    </li>
                    <li>
                        <a href="http://localhost:5173/all_product/category/tablet/0" class="tm">테블릿</a>
                    </li>
                    <li>
                        <a href="http://localhost:5173/all_product/category/laptop/0" class="tm">노트북</a>
                    </li>
                    <li>
                        <a href="http://localhost:5173/all_product/category/monitor/0" class="tm">모니터</a>
                    </li>
                    <li>
                        <a href="http://localhost:5173/all_product/category/smartwatch/0" class="tm">스마트워치</a>
                    </li>
                    <li>
                        <a href="http://localhost:5173/all_product/category/earphone/0" class="tm">이어폰</a>
                    </li>
                </ul>
            </div>
            <div class="right-box">
                <a href="/sales_post/form" class="f12 b005DE8 cfff bsb bdr4 pt8 pb8 pl8 pr8">판매글 등록</a>
            </div>
        </div>
    </div>
</header>
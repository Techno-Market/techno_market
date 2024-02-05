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
            <div class="right-box flex aic cg24">
                <ul class="sub-menu-box flex aic cg12">
                    <li>
                        <a href="" class="c333 f14 tm test">내 정보</a>
                    </li>
                    <li>
                        <a href="" class="c333 f14 tm">채팅</a>
                    </li>
                    <li>
                        <a href="" class="c333 f14 tm">찜 목록</a>
                    </li>
                    <li>
                        <a href="" class="c333 f14 tm">로그아웃</a>
                    </li>
                    <li>
                        <a href="/login" class="c333 f14 tm test">로그인</a>
                    </li>
                    <li>
                        <a href="/signup" class="c333 f14 tm">회원가입</a>
                    </li>
                </ul>
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
        </div>
    </div>
</header>
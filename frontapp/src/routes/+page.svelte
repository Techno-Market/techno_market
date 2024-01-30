<script>
    import MainSwiper from '../components/MainSwiper.svelte';
    import SuggestionProduct from '../components/SuggestionProduct.svelte';
    import PopularityProduct from '../components/PopularityProduct.svelte';
</script>

<!-- 메인 스와이퍼 -->
<MainSwiper />

<!-- 추천 상품 스와이퍼 -->
<div class="cnt-area w100per rel zi1">
    <div class="con w100per">
        <h1 class="title-text lh120 c222 tb f20">당신을 위한 추천 상품!</h1>
        <SuggestionProduct />
    </div>
</div>


<!-- 인기 상품 스와이퍼 -->
<div class="cnt-area w100per rel zi1">
    <div class="con w100per">
        <h1 class="title-text lh120 c222 tb f20">인기있는 상품!</h1>
        <PopularityProduct />
    </div>
</div>
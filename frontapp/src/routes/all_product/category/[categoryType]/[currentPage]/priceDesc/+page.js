export async function load({ fetch, params }) {
    const searchKeyword = params.searchKeyword || '';
    const currentPage = params.currentPage;
    const categoryType = params.categoryType;

    const upperCaseCategory = categoryType.toUpperCase();

    let response = await fetch(`http://localhost:8080/api/articles/priceDesc?kw=${searchKeyword}&page=${currentPage}&category=${upperCaseCategory}`);
    let result = await response.json();
    let data;
    data = {
        result,
        searchKeyword,
        categoryType,
        currentPage,
      };
    
    return data;
}

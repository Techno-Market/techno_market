export async function load({ fetch, params }) {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
    const searchKeyword = params.searchKeyword || '';
    const currentPage = params.currentPage;
    const categoryType = params.categoryType;

    const upperCaseCategory = categoryType.toUpperCase();

    let response = await fetch(`${backendUrl}/api/articles/priceDesc?kw=${searchKeyword}&page=${currentPage}&category=${upperCaseCategory}`, {
      credentials: 'include'
  });
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

export async function load({ fetch, params }) {
    const categoryType = params.categoryType;
    const currentPage = params.currentPage;

    const upperCaseCategory = categoryType.toUpperCase();

    let response = await fetch(`http://localhost:8080/api/articles/category?category=${upperCaseCategory}&page=${currentPage}`, {
      credentials: 'include'
  });
    let result = await response.json();
    let data;
    data = {
        result,
        categoryType,
        currentPage,
      };
    
    return data;
}

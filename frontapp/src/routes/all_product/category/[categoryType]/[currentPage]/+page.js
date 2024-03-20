export async function load({ fetch, params }) {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
    const categoryType = params.categoryType;
    const currentPage = params.currentPage;

    const upperCaseCategory = categoryType.toUpperCase();

    let response = await fetch(`${backendUrl}/api/articles/category?category=${upperCaseCategory}&page=${currentPage}`, {
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

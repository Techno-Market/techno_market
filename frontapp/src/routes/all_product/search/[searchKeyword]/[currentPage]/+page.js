export async function load({ fetch, params }) {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
    const searchKeyword = params.searchKeyword;
    const currentPage = params.currentPage;

    let response = await fetch(`${backendUrl}/api/articles/search?kw=${searchKeyword}&page=${currentPage}`, {
      credentials: 'include'
  });
    let result = await response.json();
    let data;
    data = {
        result,
        searchKeyword,
        currentPage,
      };
    
    return data;
}

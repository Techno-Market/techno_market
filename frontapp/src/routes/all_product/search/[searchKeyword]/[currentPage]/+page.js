export async function load({ fetch, params }) {
    const searchKeyword = params.searchKeyword;
    const currentPage = params.currentPage;

    let response = await fetch(`http://localhost:8080/api/articles/search?kw=${searchKeyword}&page=${currentPage}`, {
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

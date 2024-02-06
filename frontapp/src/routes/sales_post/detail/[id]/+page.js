export async function load({params, fetch}) {
    let res = await fetch("http://localhost:8080/api/articles/" + params.id, {

        credentials: 'include'

    })
    const articleId = params.id;
    let result = await res.json();
    let data;
    data = {
        result,
        articleId,
      };
    return data;
}
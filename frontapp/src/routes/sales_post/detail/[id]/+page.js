export async function load({params, fetch}) {
    let res = await fetch("http://localhost:8080/api/articles/" + params.id, {
        credentials: 'include'
    })
    let res2 = await fetch("http://localhost:8080/api/answers", {
        credentials: 'include'
    })
    const articleId = params.id;
    let result = await res.json();
    let result2 = await res2.json();
    let data;
    data = {
        result,
        result2,
        articleId,
      };
    return data;
}
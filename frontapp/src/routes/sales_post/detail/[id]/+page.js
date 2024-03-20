export async function load({params, fetch}) {
    const backendUrl = import.meta.env.VITE_BACKEND_URL;

    let res = await fetch(`${backendUrl}/api/articles/` + params.id, {
        credentials: 'include'
    })
    let res2 = await fetch(`${backendUrl}/api/answers/` + params.id, {
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
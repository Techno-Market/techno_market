export async function load ({fetch, params}) {
    const backendUrl = import.meta.env.VITE_BACKEND_URL;


    let res = await  fetch(`${backendUrl}/api/articles?page=${params.page}`, {
        credentials: 'include'
    })
    let result = await res.json();

    return result;
}
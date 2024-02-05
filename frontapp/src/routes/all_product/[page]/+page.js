export async function load ({fetch, params}) {


    let res = await  fetch(`http://localhost:8080/api/articles?page=${params.page}`, {
        credentials: 'include'
    })
    let result = await res.json();

    return result;
}
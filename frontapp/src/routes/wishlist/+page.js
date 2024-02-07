export async function load ({fetch}) {
    let res = await fetch(`http://localhost:8080/api/wishlists`, {
        credentials: 'include'
    })
    let result = await res.json();
    console.log("결과" + result)
    return result;  
}
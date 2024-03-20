export async function load ({fetch}) {
    const backendUrl = import.meta.env.VITE_BACKEND_URL;
    let res = await fetch(`${backendUrl}/api/wishlists`, {
        credentials: 'include'
    })
    let result = await res.json();
    console.log("결과" + result);
    return result;  
}
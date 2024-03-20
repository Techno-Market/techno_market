export async function load({params, fetch}) {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  let res = await fetch(`${backendUrl}/api/articles/` + params.id, {
    credentials: 'include'
  })
  let result = await res.json();
  
  return result;
}
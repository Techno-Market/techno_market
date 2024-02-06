export async function load({params, fetch}) {
  let res = await fetch("http://localhost:8080/api/articles/" + params.id, {
    credentials: 'include'
  })
  let result = await res.json();
  
  return result;
}
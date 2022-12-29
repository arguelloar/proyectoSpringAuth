export default async function userLogout(){
    const response = await fetch("http://localhost:8080/api/auth/logout", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.json();
}

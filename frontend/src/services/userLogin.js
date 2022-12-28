export default async function userLogin(login){
    const response = await fetch("http://localhost:8080/api/auth/login", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
             body: JSON.stringify(login)
        });
        
        return response;
}

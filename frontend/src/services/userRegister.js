export default async function userRegister(register){
    const response = await fetch("http://localhost:8080/api/auth/register", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
             body: JSON.stringify(register)
        });
        
        return response;
}
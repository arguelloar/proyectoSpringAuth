export default async function userLogin(login){
    const response = await fetch("http://localhost:8080/api/auth/login", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
             body: JSON.stringify(login)
        });
        
        return response.json();
}

export async function userRegister(register){
    const response = await fetch("http://localhost:8080/api/auth/register", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
             body: JSON.stringify(register)
        });
        
        return response.json();
}

export async function userLogout(){
    const response = await fetch("http://localhost:8080/api/auth/logout", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.json();
}

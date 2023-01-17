export default async function userLogin(login){
    const response = await fetch(`${process.env.API}/auth/login`, {
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
    const response = await fetch(`${process.env.API}/auth/register`, {
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
    const response = await fetch(`${process.env.API}/auth/logout`, {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.json();
}

import Cookies from "js-cookie";
export async function cookieCheck(){
    const response = await fetch("http://localhost:8080/api/auth/token", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        }).catch("Cannot access to this resource without authentication");
        return response;
}

export function isPresent(){
    let cookies = Cookies.get("userLogin");
    if(cookies)return true;
    return false;
}
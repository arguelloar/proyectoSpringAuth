import Cookies from "js-cookie";
export async function cookieCheck(){
    const response = await fetch("https://api-arguelloar.onrender.com/api/auth/token", {
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
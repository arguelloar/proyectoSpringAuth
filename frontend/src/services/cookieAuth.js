import Cookies from "js-cookie";
export async function cookieCheck(){
    const response = await fetch(`${process.env.URL_API}/auth/token`, {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        }).catch("Cannot access to this resource without authentication");
        return response;
}

export function isPresent(){
    return Cookies.get("userLogin") != undefined ? true : false;
}